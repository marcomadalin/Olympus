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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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
            (activity as MainActivity).showNavigationBar()
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

        measureViewModel.measureValues.observe(viewLifecycleOwner) { values ->
            if (values!!.isNullOrEmpty()) {
                binding.textView3.visibility = View.VISIBLE
                binding.measureChart.data = null
                binding.measureChart.invalidate()
            }
            else {
                binding.textView3.visibility = View.GONE

                val dataEntries = LineDataSet(ArrayList(values!!.reversed().map{ Entry(it.date.dayOfYear.toFloat(), it.value.toFloat()) }), "")
                dataEntries.axisDependency = YAxis.AxisDependency.LEFT;
                dataEntries.color = R.color.black
                dataEntries.circleColors = mutableListOf(R.color.buttons)
                dataEntries.lineWidth = 2f

                val dataSets : ArrayList<ILineDataSet> = ArrayList()
                dataSets.add(dataEntries)

                val plotData = LineData(dataSets)
                plotData.setValueTextSize(12f)
                binding.measureChart.data = plotData

                binding.measureChart.description.isEnabled = false
                binding.measureChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                binding.measureChart.setNoDataText("No values registered")
                binding.measureChart.axisRight.isEnabled = false
                binding.measureChart.axisLeft.textSize = 12.0f
                binding.measureChart.axisRight.setDrawGridLines(false)
                binding.measureChart.axisLeft.setDrawGridLines(false)
                binding.measureChart.isAutoScaleMinMaxEnabled = true
                binding.measureChart.xAxis.textSize = 12.0f
                binding.measureChart.legend.isEnabled = false
                binding.measureChart.xAxis.setDrawGridLines(false)
                binding.measureChart.invalidate()
            }
            adapter.measures = values
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
