package com.marcomadalin.olympus.presentation.view

import android.os.Bundle
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
import com.marcomadalin.olympus.databinding.FragmentMeasureReviewBinding
import com.marcomadalin.olympus.databinding.MeasureValueLayoutBinding
import com.marcomadalin.olympus.domain.model.Measure
import com.marcomadalin.olympus.presentation.view.recyclers.MeasureReviewAdapter
import com.marcomadalin.olympus.presentation.viewmodel.MeasuresViewModel
import java.time.LocalDate

class MeasureReviewFragment : Fragment() {

    private var _binding: FragmentMeasureReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var navController: NavController

    private val measureViewModel : MeasuresViewModel by activityViewModels()

    private lateinit var adapter: MeasureReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMeasureReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        navController = findNavController()

        binding.backButtonMeasure2.setOnClickListener {
            navController.navigate(R.id.action_measureReviewFragment_to_measureFragment)
        }

        binding.titleMeasure2.text =
            measureViewModel.selectedPart.value!!.toString().replace("_", " ")

        binding.create3.setOnClickListener {
            val builder = AlertDialog.Builder(this.requireContext(), R.style.MyDialog)
            val view = layoutInflater.inflate(R.layout.measure_value_layout, null, false)
            val dialogBinding = MeasureValueLayoutBinding.bind(view)

            dialogBinding.dateValeMeasure.text = LocalDate.now().toString()
            dialogBinding.buttonDelete.visibility = View.INVISIBLE

            builder.setView(view)
            val alertDialog = builder.show()

            dialogBinding.valueSave.setOnClickListener {
                measureViewModel.saveMeasure(
                    Measure(
                        0,
                        0,
                        LocalDate.now(),
                        dialogBinding.measureVal.text.toString().toDouble(),
                        measureViewModel.selectedPart.value!!
                    )
                )
                alertDialog.dismiss()
            }

            dialogBinding.valueCancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        adapter = MeasureReviewAdapter(::onValueClick)
        binding.valueRecycler.adapter = adapter
        binding.valueRecycler.layoutManager = LinearLayoutManager(this.context)

        measureViewModel.measureValues.observe(viewLifecycleOwner) {
            if (it!!.isEmpty()) binding.textView3.visibility = View.VISIBLE
            else binding.textView3.visibility = View.GONE
            adapter.measures = it!!
            adapter.notifyDataSetChanged()
        }

        measureViewModel.getMeasureValues()
    }

    private fun onValueClick(valuePosition: Int) {
        val measure = measureViewModel.measureValues.value!![valuePosition]
        measureViewModel.insertedValue.value = measure.value

        val builder = AlertDialog.Builder(this.requireContext(), R.style.MyDialog)
        val view = layoutInflater.inflate(R.layout.measure_value_layout, null, false)
        val dialogBinding = MeasureValueLayoutBinding.bind(view)

        dialogBinding.dateValeMeasure.text = measure.date.toString()
        dialogBinding.measureVal.setText(measure.value.toString())

        dialogBinding.measureVal.doOnTextChanged { _, _, _, _ ->
            if (! dialogBinding.measureVal.text.toString().isNullOrEmpty()) measureViewModel.insertedValue.value = dialogBinding.measureVal.text.toString().toDouble()
        }

        builder.setView(view)
        val alertDialog = builder.show()

        dialogBinding.valueSave.setOnClickListener {
            measure.value = measureViewModel.insertedValue.value!!
            measureViewModel.saveMeasure(measure)
            alertDialog.dismiss()
        }

        dialogBinding.valueCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        dialogBinding.buttonDelete.setOnClickListener {
            measureViewModel.deleteMeasure(measure)
            alertDialog.dismiss()

        }

    }
}
