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
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.MeasurePart
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

    private lateinit var binding: ActivityMainBinding

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

        val muscleDivision = Muscle.values().map{ Pair(it, 0) }.toList().associate { Pair(it.first, it.second) }.toMutableMap()
        muscleDivision[Muscle.Lats] = 55
        muscleDivision[Muscle.Hamstrings] = 22
        muscleDivision[Muscle.Chest] = 125
        userViewModel.saveUser(User(0, "Marco", 0, 51123.0, 123123, Duration.ofSeconds(1231231231), muscleDivision))

        val workout = Workout(0, 1, "Legs", "Pretty chill workout",
            Duration.ofSeconds(2350), LocalDate.now(),
            mutableListOf(),
            mutableListOf(), 0)

        val workout2 = Workout(0, 1, "Legs", "Pretty chill workout",
            Duration.ofSeconds(2350), LocalDate.now(),
            mutableListOf(),
            mutableListOf(), 0, true)

        val routine = Routine(0, 1, "Legs", "Pretty chill workout",
            mutableListOf(),
            mutableListOf())

        val e1 = ExerciseData(0, 1, "Deadlift", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 220.0, 230.75, 220.0, 3, true)
        val e2 = ExerciseData(0, 1, "Squat", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 190.0, 200.00, 190.0, 3, true)
        val e3 = ExerciseData(0, 1, "Bench Press", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 120.0, 130.75, 120.0, 3, true)
        val e4 = ExerciseData(0, 1, "Lat Pulldown", ExerciseType.Weight_Reps, false, Equipment.Machine, Muscle.Lats, emptySet(), 82.5, 100.25, 82.5, 10, true)
        val e5 = ExerciseData(0, 1, "Barbell Row", ExerciseType.Weight_Reps, false, Equipment.Barbell, Muscle.Upper_Back, emptySet(), 120.0, 150.75, 120.0, 10, true)

        val exercises : List<ExerciseData> = listOf(e1, e2 , e3, e4, e5)

        val m1 = Measure(0, 0, LocalDate.now(), 94.5, MeasurePart.Weight)
        val m2 = Measure(0, 0, LocalDate.parse("2023-01-01"), 98.77, MeasurePart.Weight)
        val m8 = Measure(0, 0, LocalDate.parse("2023-01-02"), 78.33, MeasurePart.Weight)
        val m9 = Measure(0, 0, LocalDate.parse("2023-01-03"), 66.77, MeasurePart.Weight)
        val m10 = Measure(0, 0, LocalDate.parse("2023-01-04"), 100.55, MeasurePart.Weight)
        val m11 = Measure(0, 0, LocalDate.parse("2023-01-05"), 22.3, MeasurePart.Weight)
        val m12 = Measure(0, 0, LocalDate.parse("2023-01-07"), 55.5, MeasurePart.Weight)


        val m5 = Measure(0, 0, LocalDate.now(), 44.0, MeasurePart.Chest)
        val m6 = Measure(0, 0, LocalDate.now(), 45.0, MeasurePart.Chest)

        val m7 = Measure(0, 0, LocalDate.now(), 45.5, MeasurePart.Left_Biceps)

        val measures = listOf(m1,m2, m5,m6,m7, m8, m9, m10, m11, m12)

        measureViewModel.saveAllMeasures(measures)

        exerciseDataViewModel.saveAllExercisesData(exercises)
        workoutViewModel.saveLiveWorkout(workout2)
        workoutViewModel.saveWorkout(workout)
        routineViewModel.saveRoutine(routine)
        workoutViewModel.getWorkouts()
        workoutViewModel.getLiveWorkout()
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
