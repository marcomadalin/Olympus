package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.marcomadalin.olympus.R
import com.marcomadalin.olympus.databinding.ActivityMainBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.MeasuresViewModel
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Duration
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModels()

    private val workoutViewModel: WorkoutViewModel by viewModels()

    private val exerciseDataViewModel: ExerciseViewModel by viewModels()

    private val routineViewModel : RoutineViewModel by viewModels()

    private val measureViewModel : MeasuresViewModel by viewModels()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController(binding.fragView)


        binding.navbar.menu.forEach { item ->
            item.actionView?.setOnLongClickListener { view ->
                view.performClick()
                TooltipCompat.setTooltipText(view, null)
                true
            }
        }

        binding.navbar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.routine -> {
                    if (workoutViewModel.liveWorkout.value != null) navController.navigate(R.id.liveWorkoutFragment)
                    else navController.navigate(R.id.routine)
                    }
                R.id.history -> navController.navigate(R.id.history)
                R.id.exercise -> navController.navigate(R.id.exercise)
                R.id.measure -> navController.navigate(R.id.measure)
                R.id.profile -> navController.navigate(R.id.profile)
                else -> {false}
            }
            true
        }

        if (!userViewModel.existsUser()) {

            userViewModel.saveUser(
                User(
                    0,
                    "User",
                )
            )

            val workout = Workout(
                0, 1, "Legs", "Pretty chill workout",
                Duration.ofMillis(235066), LocalDate.parse("2023-01-23"),
                mutableListOf(),
                mutableListOf(), 0
            )

            val workout2 = Workout(
                0, 1, "Legs", "Felt quite fresh, but weak on squats",
                Duration.ofMillis(123123), LocalDate.parse("2023-01-24"),
                mutableListOf(),
                mutableListOf(), 0,
            )

            val e1 = ExerciseData(
                0,
                1,
                "Deadlift",
                ExerciseType.Weight_Reps,
                true,
                Equipment.Barbell,
                Muscle.Hamstrings,
                emptySet(),
            )
            val e2 = ExerciseData(
                0,
                1,
                "Squat",
                ExerciseType.Weight_Reps,
                true,
                Equipment.Barbell,
                Muscle.Quads,
            )
            val e3 = ExerciseData(
                0,
                1,
                "Bench Press",
                ExerciseType.Weight_Reps,
                true,
                Equipment.Machine,
                Muscle.Chest,
                emptySet(),
            )
            val e4 = ExerciseData(
                0,
                1,
                "Lat Pulldown",
                ExerciseType.Weight_Reps,
                false,
                Equipment.Machine,
                Muscle.Lats,
                emptySet(),
            )
            val e5 = ExerciseData(
                0,
                1,
                "Barbell Row",
                ExerciseType.Weight_Reps,
                false,
                Equipment.Barbell,
                Muscle.Upper_Back,
                emptySet(),
            )

            val exercises: List<ExerciseData> = listOf(e1, e2, e3, e4, e5)


            exerciseDataViewModel.saveAllExercisesData(exercises)
            workoutViewModel.saveWorkout(workout)
            workoutViewModel.saveWorkout(workout2)
        }
        routineViewModel.getRoutines()
        workoutViewModel.getWorkouts()
        workoutViewModel.getWorkout()
        workoutViewModel.getLiveWorkout()
        exerciseDataViewModel.getExercisesData()
        measureViewModel.getAllMeasures()
    }

    fun hideNavigationBar() {
        binding.navbar.visibility = View.GONE
        binding.dropShadow.visibility = View.GONE
    }

    fun showNavigationBar() {
        binding.navbar.visibility = View.VISIBLE
        binding.dropShadow.visibility = View.VISIBLE
    }
}
