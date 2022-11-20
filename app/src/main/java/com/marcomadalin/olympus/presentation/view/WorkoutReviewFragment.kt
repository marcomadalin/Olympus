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
import com.marcomadalin.olympus.databinding.FragmentWorkoutReviewBinding
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

//TODO FIND BETTER WAY TO SHOW SUPERSETS

@AndroidEntryPoint
class WorkoutReviewFragment : Fragment() {

    private var _binding : FragmentWorkoutReviewBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel : WorkoutViewModel by activityViewModels()

    private lateinit var adapter : ExerciseReviewAdapter

    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        popupMenu()
        binding.backButtonSummary.setOnClickListener {
            navController.popBackStack()
            (activity as MainActivity).showNavigationBar()
        }
        binding.exerciseRecycler.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseReviewAdapter(workoutViewModel.selectedWorkout.value!!.exercises)
        adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
        binding.exerciseRecycler.adapter = adapter
        workoutViewModel.selectedWorkout.observe(viewLifecycleOwner) {updateWorkoutReview(it)}
    }

    private fun popupMenu() {
        binding.summaryMenu.setOnClickListener { view ->
            val popupMenu = PopupMenu(this.context, view)

            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.save -> {
                        //TODO SAVE
                        true
                    }
                    R.id.edit -> {
                        navController.navigate(R.id.workoutEdit)
                        true
                    }
                    R.id.delete -> {
                        workoutViewModel.deleteWorkout(workoutViewModel.selectedWorkout.value!!)
                        navController.navigate(R.id.history)
                        (activity as MainActivity).showNavigationBar()
                        //TODO FIX BUG DELETE
                        true
                    }
                    else -> false
                }
            }
            popupMenu.inflate(R.menu.workout_review_dropdown)
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

    private fun updateWorkoutReview(workout: Workout?) {
        if (workout != null) {
            adapter.supersets = workoutViewModel.selectedWorkout.value!!.supersets
            binding.summaryTitle.text = workout.name
            binding.summaryDate.text = workout.date.dayOfMonth.toString() + " " +
                    workout.date.month.toString().toLowerCase(Locale.ROOT) + " " + workout.date.year
            var volume = 0.0
            workout.exercises.forEach{
                    it -> it.sets.forEach{volume += it.weight}
            }
            binding.workoutVolume2.text = "$volume kg"
            binding.workoutTime2.text = ((workout.length.seconds%3600)/60).toString() + " min"
            if (workout.length.toHours().toInt() != 0) {
                binding.workoutTime2.text = workout.length.toHours().toString() + " h " + binding.workoutTime2.text
            }
            binding.summarytNote.text = workout.note
        }
    }

}