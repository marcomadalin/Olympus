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
import com.marcomadalin.olympus.domain.model.Exercise
import com.marcomadalin.olympus.domain.model.ExerciseData
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.Equipment
import com.marcomadalin.olympus.domain.model.enums.ExerciseType
import com.marcomadalin.olympus.domain.model.enums.Muscle
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.viewmodel.ExerciseDataViewModel
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModels()

    private val workoutViewModel: WorkoutViewModel by viewModels()
    private val exerciseDataViewModel: ExerciseDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    @OptIn(DelicateCoroutinesApi::class)
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
        userViewModel.saveUser(User(0, "Marco", 12, true, emptySet(), emptySet()))
        val set1 = Set(0, 0, 10.0, 10, 1, 9.0, 10, SetType.Normal, 0)
        val set2 = Set(0, 0, 10.0, 9, 1, 9.0, 10, SetType.Normal, 1)
        val set3 = Set(0, 0, 10.0, 8, 0, 9.0, 10, SetType.Normal, 2)
        val exercise1 = Exercise(0, 0, 0, Duration.ofSeconds(120),"Felt Fresh", 0, mutableListOf(set1, set2, set3))

        val set4 = Set(0, 0, 15.0, 10, 9, 12.0, 14, SetType.Normal, 0)
        val set5 = Set(0, 0, 15.0, 9, 9, 12.0, 14, SetType.Normal, 1)
        val exercise2 = Exercise(0, 0, 1, Duration.ofSeconds(80),"Second set was easier", 0, mutableListOf(set4, set5))

        val set6 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Warmup, 0)
        val set7 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Normal, 1)
        val set8 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Drop, 2)
        val exercise3 = Exercise(0, 0, 0, Duration.ofSeconds(130),"", 0, mutableListOf(set6, set7, set8))

        val set9 = Set(0, 0, 10.0, 10, 2, 9.0, 10, SetType.Normal, 0)
        val set10 = Set(0, 0, 10.0, 9, 2, 9.0, 10, SetType.Normal, 1)
        val set11 = Set(0, 0, 10.0, 8, 2, 9.0, 10, SetType.Normal, 2)
        val exercise4 = Exercise(0, 0, 0, Duration.ofSeconds(150),"Felt heavy", 0, mutableListOf(set9, set10, set11))

        val set12 = Set(0, 0, 170.0, 10, 9, 272.5, 10, SetType.Warmup, 0)
        val set13 = Set(0, 0, 250.0, 9, 8, 272.5, 10, SetType.Warmup, 1)
        val set14 = Set(0, 0, 275.0, 8, 0, 272.5, 10, SetType.Failure, 2)
        val set15 = Set(0, 0, 275.0, 8, 0, 272.5, 10, SetType.Failure, 4)
        val exercise5 = Exercise(0, 0, 0, Duration.ofSeconds(130),"Can improve next time", 0, mutableListOf(set12, set13, set14, set15))

        val workout = Workout(0, 1, "Legs", "Pretty chill workout",
            Duration.ofSeconds(1780), LocalDate.now(),
            mutableListOf(exercise1, exercise2, exercise3, exercise4, exercise5),
            mutableListOf())

        val e1 = ExerciseData(0, 1, "Deadlift", ExerciseType.WeightReps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 220.0, 230.75, 220.0, 3, true)
        val e2 = ExerciseData(0, 1, "Squat", ExerciseType.WeightReps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 190.0, 200.00, 190.0, 3, true)
        val e3 = ExerciseData(0, 1, "Bench Press", ExerciseType.WeightReps, true, Equipment.Barbell, Muscle.Hamstrings, emptySet(), 120.0, 130.75, 120.0, 3, true)
        val e4 = ExerciseData(0, 1, "Lat Pulldown", ExerciseType.WeightReps, false, Equipment.Machine, Muscle.Lats, emptySet(), 82.5, 100.25, 82.5, 10, true)
        val e5 = ExerciseData(0, 1, "Barbell Row", ExerciseType.WeightReps, false, Equipment.Barbell, Muscle.Upper_Back, emptySet(), 120.0, 150.75, 120.0, 10, true)

        val exercises : List<ExerciseData> = listOf(e1, e2 , e3, e4, e5)
        GlobalScope.launch {
            workoutViewModel.saveWorkout(workout)
            exerciseDataViewModel.saveAllExercisesData(exercises)
        }
    }

    override fun onStop() {
        super.onStop()
        userViewModel.deleteUsers()
        workoutViewModel.deleteWorkouts()
        exerciseDataViewModel.deleteAllExercisesData()
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
