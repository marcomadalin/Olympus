package com.marcomadalin.olympus.presentation.view

import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.FragmentLiveWorkoutBinding
import com.marcomadalin.olympus.databinding.TimerLayoutBinding
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.view.recyclers.ExerciseLiveAdapter
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import java.time.Duration
import java.time.LocalDate


class LiveWorkoutFragment : Fragment() {
    private var _binding: FragmentLiveWorkoutBinding? = null
    private val binding get() = _binding!!

    private val workoutViewModel: WorkoutViewModel by activityViewModels()

    private val exerciseViewModel: ExerciseViewModel by activityViewModels()

    private lateinit var adapter: ExerciseLiveAdapter

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLiveWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        workoutViewModel.selectedDate.value = LocalDate.now()
        (activity as MainActivity).showNavigationBar()

        navController = findNavController()

        if (exerciseViewModel.selectedExercises.value!!.isNotEmpty()) {
            val workout = workoutViewModel.liveWorkout.value!!
            exerciseViewModel.selectedExercises.value!!.forEach { selectedExercise ->
                val exercise = Exercise(
                    0,
                    selectedExercise.value,
                    workout.id,
                    0,
                    selectedExercise.key,
                    Duration.ofSeconds(0),
                    "",
                    workout.exercises.size,
                    mutableListOf()
                )
                val set = Set(0, exercise.id, 0.0, 0, 0, 0.0, 0, 0, SetType.Normal, exercise.sets.size, false)
                exercise.sets.add(set)

                if (exerciseViewModel.selectOne.value!!) {
                    val position = exerciseViewModel.swappedExercisePosition.value!!
                    for ((index, superset) in workout.supersets.withIndex()) {
                        if (superset.contains(exerciseViewModel.oldExerciseId.value!!)) {
                            superset.remove(exerciseViewModel.oldExerciseId.value!!)
                            if (superset.size == 1) workout.supersets.removeAt(index)
                            break
                        }
                    }
                    exerciseViewModel.deleteExercise(workout.exercises[position])
                    exercise.exerciseNumber = position
                    workout.exercises[position] = exercise
                }
                else workout.exercises.add(exercise)
            }
            exerciseViewModel.selectedExercises.value = mutableMapOf()
        }

        binding.button.setOnClickListener { addExercise() }

        binding.done4.setOnClickListener{
            for (exercise in workoutViewModel.liveWorkout.value!!.exercises) {
                for (set in exercise.sets) set.completed = true
            }
            workoutViewModel.liveWorkout.value!!.length = Duration.ofSeconds((SystemClock.elapsedRealtime() - binding.time.base)/1000)
            Log.d("TEST", workoutViewModel.liveWorkout.value!!.length.toString())
            workoutViewModel.liveWorkout.value!!.date = LocalDate.now()
            workoutViewModel.liveWorkout.value!!.isLive = false

            workoutViewModel.selectedWorkout.value = workoutViewModel.liveWorkout.value
            workoutViewModel.liveWorkout.value = null
            workoutViewModel.saveWorkout(workoutViewModel.selectedWorkout.value!!)
            navController.navigate(R.id.action_liveWorkoutFragment_to_workoutLiveFinished)
            (activity as MainActivity).hideNavigationBar()
        }

        binding.timerButton.setOnClickListener{
            val builder = AlertDialog.Builder(this.requireContext(), R.style.MyDialog)
            val view = layoutInflater.inflate(R.layout.timer_layout, null, false)
            val dialogBinding = TimerLayoutBinding.bind(view)

            builder.setView(view)
            val alertDialog = builder.show()

            dialogBinding.timer.isCountDown = true
            if (workoutViewModel.chronoValue.value != null) {
                dialogBinding.timer.start()
                dialogBinding.timer.base = workoutViewModel.chronoValue.value!!
            }
            else dialogBinding.timer.base = SystemClock.elapsedRealtime()

            dialogBinding.timer.setOnChronometerTickListener {
                if (dialogBinding.timer.base <= SystemClock.elapsedRealtime()) {
                    dialogBinding.timer.stop()
                    SystemClock.elapsedRealtime()
                    workoutViewModel.chronoValue.value = null
                    val defaultRingtoneUri: Uri =
                        RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

                    val mediaPlayer = MediaPlayer()

                    try {
                        mediaPlayer.setDataSource(requireContext(), defaultRingtoneUri)
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION)
                        mediaPlayer.prepare()
                        mediaPlayer.setOnCompletionListener { mp -> mp.release() }
                        mediaPlayer.start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            dialogBinding.min1.setOnClickListener{
                dialogBinding.timer.base = SystemClock.elapsedRealtime() + 60000
                dialogBinding.timer.start()
                workoutViewModel.chronoValue.value = dialogBinding.timer.base

            }

            dialogBinding.min3.setOnClickListener{
                dialogBinding.timer.base = SystemClock.elapsedRealtime() + 180000
                dialogBinding.timer.start()
                workoutViewModel.chronoValue.value = dialogBinding.timer.base
            }

            dialogBinding.min5.setOnClickListener{
                dialogBinding.timer.base = SystemClock.elapsedRealtime() + 300000
                dialogBinding.timer.start()
                workoutViewModel.chronoValue.value = dialogBinding.timer.base
            }

            dialogBinding.secMore.setOnClickListener{
                dialogBinding.timer.base = dialogBinding.timer.base + 30000
                dialogBinding.timer.start()
                workoutViewModel.chronoValue.value = dialogBinding.timer.base
            }

            dialogBinding.secLess.setOnClickListener {
                if (dialogBinding.timer.base - 30000 > SystemClock.elapsedRealtime()) {
                    dialogBinding.timer.base = dialogBinding.timer.base - 30000
                    dialogBinding.timer.start()
                    workoutViewModel.chronoValue.value = dialogBinding.timer.base
                }
                else dialogBinding.timer.base = SystemClock.elapsedRealtime()
            }

            dialogBinding.skip.setOnClickListener{
                dialogBinding.timer.stop()
                dialogBinding.timer.base = SystemClock.elapsedRealtime()
                workoutViewModel.chronoValue.value = null
            }

            dialogBinding.close.setOnClickListener{
                dialogBinding.timer.stop()
                alertDialog.dismiss()
            }

            alertDialog.setOnDismissListener {
                if (binding.time.base > SystemClock.elapsedRealtime()) workoutViewModel.chronoValue.value = binding.time.base - SystemClock.elapsedRealtime()
            }

        }

        binding.cancelLive.setOnClickListener{
            workoutViewModel.deleteWorkout(workoutViewModel.liveWorkout.value!!)
            workoutViewModel.liveWorkout.value = null
            navController.navigate(R.id.routine)
        }

        if (workoutViewModel.liveWorkout.value!!.length.toMillis().toInt() == 0) {
            binding.time.base = SystemClock.elapsedRealtime()
            workoutViewModel.liveWorkout.value!!.length = Duration.ofMillis(binding.time.base)
        }
        else binding.time.base = workoutViewModel.liveWorkout.value!!.length.toMillis()
        binding.time.start()

        binding.recyclerViewLive.layoutManager = LinearLayoutManager(this.context)
        adapter = ExerciseLiveAdapter(::addSet, ::deleteSet, ::onItemClick)

        adapter.exercises = workoutViewModel.liveWorkout.value!!.exercises
        adapter.supersets = workoutViewModel.liveWorkout.value!!.supersets

        binding.recyclerViewLive.adapter = adapter
        binding.recyclerViewLive.isNestedScrollingEnabled = false

        val workout = workoutViewModel.liveWorkout.value!!

        adapter.supersets = workoutViewModel.liveWorkout.value!!.supersets
        binding.workoutEditTitle2.setText(workout.name)
        binding.workoutEditTitle2.doOnTextChanged { _, _, _, _ -> workout.name = binding.workoutEditTitle2.text.toString() }

        binding.summarytNote4.setText(workout.note)
        binding.summarytNote4.doOnTextChanged { _, _, _, _ -> workout.note = binding.summarytNote4.text.toString() }
    }

    override fun onStop() {
        super.onStop()
        if (workoutViewModel.liveWorkout.value != null) workoutViewModel.saveLiveWorkout(workoutViewModel.liveWorkout.value!!)
    }


    private fun onItemClick(itemId: Int, exercisePosition : Int) : Boolean {
        return when (itemId) {
            R.id.order -> {
                workoutViewModel.editingRoutine.value = false
                workoutViewModel.editingLive.value = true
                navController.navigate(R.id.workoutReorderFragment)
                true
            }
            R.id.superset -> {
                workoutViewModel.editingRoutine.value = false
                workoutViewModel.editingLive.value = true
                navController.navigate(R.id.workoutSupersetFragment)
                true
            }
            R.id.swap -> {
                exerciseViewModel.selectOne.value = true
                exerciseViewModel.oldExerciseId.value = workoutViewModel.liveWorkout.value!!.exercises[exercisePosition].id
                exerciseViewModel.swappedExercisePosition.value = exercisePosition
                navController.navigate(R.id.selectExerciseFragment)
                true
            }
            R.id.deleteExercise -> {
                deleteExercise(exercisePosition)
                true
            }
            else -> false
        }
    }

    private fun addExercise() {
        exerciseViewModel.selectOne.value = false
        navController.navigate(R.id.selectExerciseFragment)
    }

    private fun deleteExercise(exercisePosition : Int) {
        val workout = workoutViewModel.liveWorkout.value!!
        exerciseViewModel.deleteExercise( workout.exercises[exercisePosition])
        for (i in exercisePosition until workout.exercises.size) --workout.exercises[i].exerciseNumber

        for ((index, superset) in workout.supersets.withIndex()) {
            if (superset.contains(workout.exercises[exercisePosition].id)) {
                superset.remove(workout.exercises[exercisePosition].id)
                if (superset.size == 1) workout.supersets.removeAt(index)
                break
            }
        }
        adapter.supersets = workout.supersets
        workout.exercises.removeAt(exercisePosition)
        adapter.exercises = workout.exercises
        adapter.notifyDataSetChanged()
    }

    private fun addSet(exercisePosition : Int) {
        val workout = workoutViewModel.liveWorkout.value!!
        val exercise = workout.exercises[exercisePosition]
        val set = Set(exercise.sets.last())
        set.setNumber = exercise.sets.size
        set.type = SetType.Normal
        set.completed = false
        exercise.sets.add(set)
        adapter.exercises = workout.exercises
        adapter.notifyItemChanged(exercisePosition)
    }

    private fun deleteSet(exercisePosition: Int, setPosition : Int) {
        val workout = workoutViewModel.liveWorkout.value!!
        val exercise = workout.exercises[exercisePosition]
        exerciseViewModel.deleteSet(exercise.sets[setPosition])
        exercise.sets.removeAt(setPosition)

        if (exercise.sets.isEmpty()) deleteExercise(exercisePosition)
        else {
            adapter.exercises = workout.exercises
            adapter.notifyItemChanged(exercisePosition)
        }
    }
}