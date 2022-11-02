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
import com.marcomadalin.olympus.domain.model.Set
import com.marcomadalin.olympus.domain.model.User
import com.marcomadalin.olympus.domain.model.Workout
import com.marcomadalin.olympus.domain.model.enums.SetType
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import com.marcomadalin.olympus.presentation.viewmodel.WorkoutViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val userViewModel: UserViewModel by viewModels()
    private val workoutViewModel: WorkoutViewModel by viewModels()

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
        userViewModel.saveUser(User(0, "Marco", 12, true, emptySet(), emptySet()))
        var set1 = Set(0, 0, 10.0, 10, 1, 9.0, 10, SetType.Normal, 0)
        var set2 = Set(0, 0, 10.0, 9, 1, 9.0, 10, SetType.Normal, 1)
        var set3 = Set(0, 0, 10.0, 8, 0, 9.0, 10, SetType.Normal, 2)
        var exercise1 = Exercise(0, 0, 0, Duration.ofSeconds(120),"Felt Fresh", 0, mutableListOf(set1, set2, set3))

        var set4 = Set(0, 0, 15.0, 10, 9, 12.0, 14, SetType.Normal, 0)
        var set5 = Set(0, 0, 15.0, 9, 9, 12.0, 14, SetType.Normal, 1)
        var exercise2 = Exercise(0, 0, 1, Duration.ofSeconds(80),"Second set was easier", 0, mutableListOf(set4, set5))

        var set6 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Warmup, 0)
        var set7 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Normal, 1)
        var set8 = Set(0, 0, 100.0, 6, 0, 90.0, 10, SetType.Drop, 2)
        var exercise3 = Exercise(0, 0, 0, Duration.ofSeconds(130),"", 0, mutableListOf(set6, set7, set8))

        var set9 = Set(0, 0, 10.0, 10, 2, 9.0, 10, SetType.Normal, 0)
        var set10 = Set(0, 0, 10.0, 9, 2, 9.0, 10, SetType.Normal, 1)
        var set11 = Set(0, 0, 10.0, 8, 2, 9.0, 10, SetType.Normal, 2)
        var exercise4 = Exercise(0, 0, 0, Duration.ofSeconds(150),"Felt heavy", 0, mutableListOf(set9, set10, set11))

        var set12 = Set(0, 0, 170.0, 10, 9, 272.5, 10, SetType.Warmup, 0)
        var set13 = Set(0, 0, 250.0, 9, 8, 272.5, 10, SetType.Warmup, 1)
        var set14 = Set(0, 0, 275.0, 8, 0, 272.5, 10, SetType.Failure, 2)
        var set15 = Set(0, 0, 275.0, 8, 0, 272.5, 10, SetType.Failure, 4)
        var exercise5 = Exercise(0, 0, 0, Duration.ofSeconds(130),"Can improve next time", 0, mutableListOf(set12, set13, set14, set15))

        var workout = Workout(0, 1, "Legs", "Pretty chill workout",Duration.ofSeconds(1780), LocalDate.now(), mutableListOf(exercise1, exercise2, exercise3, exercise4, exercise5))
        GlobalScope.launch {
            workoutViewModel.saveWorkout(workout)
            workoutViewModel.getWorkout()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.deleteUsers()
        workoutViewModel.deleteWorkouts()
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
