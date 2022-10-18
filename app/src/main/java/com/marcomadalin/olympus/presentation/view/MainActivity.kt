package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.TooltipCompat
import androidx.core.view.forEach
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
        var set1 = Set(0, 0, 10, 10, 9, 9, 10, SetType.Normal, 0)
        var set2 = Set(0, 0, 10, 9, 9, 9, 10, SetType.Normal, 1)
        var set3 = Set(0, 0, 10, 8, 9, 9, 10, SetType.Normal, 2)
        var exercise1 = Exercise(0, 0, 0, Duration.ofSeconds(40),"Felt Fresh", 0, listOf(set1, set2, set3))

        var set4 = Set(0, 0, 15, 10, 9, 12, 14, SetType.Normal, 0)
        var set5 = Set(0, 0, 15, 9, 9, 12, 14, SetType.Normal, 1)
        var exercise2 = Exercise(0, 0, 1, Duration.ofSeconds(40),"Felt Fresh", 0, listOf(set4, set5))

        var workout = Workout(Duration.ofSeconds(1300), LocalDate.now(), listOf(exercise1, exercise2))
        workout.name = "Legs"
        workout.note = "Felt easy"
        workout.userId = 1
        workoutViewModel.saveWorkout(workout)
    }

    override fun onDestroy() {
        super.onDestroy()
        userViewModel.deleteUsers()
        workoutViewModel.deleteWorkouts()
    }
}
