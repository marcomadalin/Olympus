package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.marcomadalin.olympus.databinding.ActivityMainBinding
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.Routine
import com.marcomadalin.olympus.domain.model.Statistic
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.domain.model.enums.StatisticTimeframe
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseViewModel
import com.marcomadalin.olympus.presentation.viewmodel.RoutineViewModel
import com.marcomadalin.olympus.presentation.viewmodel.StatisticViewModel
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

    private val statisticViewModel : StatisticViewModel by viewModels()

    private val routineViewModel : RoutineViewModel by viewModels()

    //private val measureViewModel: MeasureViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        binding.navbar.setupWithNavController(findNavController(binding.fragView))

        binding.navbar.menu.forEach { item ->
            item.actionView?.setOnLongClickListener { view ->
                view.performClick()
                TooltipCompat.setTooltipText(view, null)
                true
            }
        }
        userViewModel.saveUser(User(0, "Marco", 0, 51123.0, 123123, Duration.ofSeconds(1231231231), emptyMap()))

        val workout = Workout(0, 1, "Legs", "Pretty chill workout",
            Duration.ofSeconds(2350), LocalDate.now(),
            mutableListOf(),
            mutableListOf(), 0)

        val routine = Routine(0, 1, "Legs", "Pretty chill workout",
            mutableListOf(),
            mutableListOf())

        val e1 = ExerciseData(0, 1, "Deadlift", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 220.0, 230.75, 220.0, 3, true)
        val e2 = ExerciseData(0, 1, "Squat", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 190.0, 200.00, 190.0, 3, true)
        val e3 = ExerciseData(0, 1, "Bench Press", ExerciseType.Weight_Reps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 120.0, 130.75, 120.0, 3, true)
        val e4 = ExerciseData(0, 1, "Lat Pulldown", ExerciseType.Weight_Reps, false, Equipment.Machine, Muscle.Lats, emptySet(), 82.5, 100.25, 82.5, 10, true)
        val e5 = ExerciseData(0, 1, "Barbell Row", ExerciseType.Weight_Reps, false, Equipment.Barbell, Muscle.Upper_Back, emptySet(), 120.0, 150.75, 120.0, 10, true)

        val stat = Statistic(0, 0, StatisticTimeframe.AllTime, 55, 0, 51123.0, 123123, Duration.ofSeconds(1231231231),0.0,0.0,0, emptyMap())
        statisticViewModel.statistic.postValue(stat)

        val exercises : List<ExerciseData> = listOf(e1, e2 , e3, e4, e5)
        exerciseDataViewModel.saveAllExercisesData(exercises)
        workoutViewModel.saveWorkout(workout)
        routineViewModel.saveRoutine(routine)
        workoutViewModel.getWorkouts()
    }

    fun hideNavigationBar() {
        binding.navbar.isVisible = false
        binding.dropShadow.isVisible = false
    }

    fun showNavigationBar() {
        binding.navbar.isVisible = true
        binding.dropShadow.isVisible = true
    }
}
