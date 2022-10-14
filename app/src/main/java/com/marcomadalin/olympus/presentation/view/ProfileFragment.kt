package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcomadalin.olympus.databinding.FragmentProfileBinding
import com.marcomadalin.olympus.presentation.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.lang.String
import java.util.*

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { userViewModel.getUser() }
        userViewModel.userModel.observe(viewLifecycleOwner) {
            binding.profileText.text = String.format(Locale.ENGLISH,"%d: " + it.name, it.id)
        }

    }
}