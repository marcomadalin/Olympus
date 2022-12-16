package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentRoutineReviewBinding
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.presentation.view.recyclers.RoutineReviewAdapter
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutineReviewFragment : Fragment() {

    private var _binding : FragmentRoutineReviewBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private val routineViewModel : RoutineViewModel by activityViewModels()

    private lateinit var adapter : RoutineReviewAdapter

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRoutineReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController()
        popupMenu()

        binding.backButtonRutine.setOnClickListener {
            navController.navigate(R.id.action_routineReviewFragment_to_workout)
            (activity as MainActivity).showNavigationBar()
        }
        binding.exerciseRoutineRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = RoutineReviewAdapter()
        binding.exerciseRoutineRecycler.adapter = adapter
        routineViewModel.selectedRoutine.observe(viewLifecycleOwner) {updateRoutine(it!!)}

        binding.startWorkoutButton.setOnClickListener{
            //TODO start workout
        }
    }

    private fun popupMenu() {
        binding.routineDropdown.setOnClickListener { view ->
            val popupMenu = PopupMenu(this.context, view)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.duplicate -> {
                        routineViewModel.createSelectedRoutineCopy()
                        true
                    }
                    R.id.edit -> {
                        //TODO edit routine
                        true
                    }
                    R.id.delete -> {
                        routineViewModel.deleteRoutine(routineViewModel.selectedRoutine.value!!)
                        navController.navigate(R.id.action_routineReviewFragment_to_workout)
                        (activity as MainActivity).showNavigationBar()
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.routine_review_dropdown)
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(mPopup, true)
            } catch (e: Exception){
                Log.e("Main" ,"Error showing menu icons.", e)
            } finally {
                popupMenu.show()
            }
        }
    }

    private fun updateRoutine(routine: Routine) {
        adapter.exercises = routine.exercises
        adapter.supersets = routine.supersets
        binding.routineTitleRev.text = routine.name
        binding.routineRevNote.text = routine.note
    }

}