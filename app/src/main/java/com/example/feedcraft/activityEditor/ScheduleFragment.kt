package com.example.feedcraft.activityEditor

import android.app.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.work.*
//import com.example.feedcraft.ScheduleFragmentDirections
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentScheduleBinding
import com.example.feedcraft.notifications.ReminderWorker
import com.example.feedcraft.viewModels.EditorViewModel
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    var cal = Calendar.getInstance()
    var chosenYear = 0
    var chosenMonth = 0
    var chosenDay = 0
    var chosenHour = 0
    var chosenMin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnDone = binding.btnDoneSchedule
        val notifTxt = binding.textFieldSchedule
        val btnBack = binding.btnBackSchedule
        val addPicture = binding.addPhotoCheckbox
        var datePicker = binding.datePicker
        val timePicker = binding.timePicker

        btnDone.setOnClickListener {
            if (addPicture.isChecked) {
                UIApplication.addPhotoFlag = "checked"
            }
            val userSelectedDateTime = Calendar.getInstance()
            userSelectedDateTime.set(chosenYear, chosenMonth, chosenDay, chosenHour , chosenMin)
            val todayDateTime = Calendar.getInstance()
            val delayInSeconds = (userSelectedDateTime.timeInMillis/1000L) - (todayDateTime.timeInMillis/1000L)
            createWorkRequest(notifTxt.text.toString(), delayInSeconds)
            Toast.makeText(requireContext(), "Reminder set", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        btnBack.setOnClickListener {
            val actionBack = ScheduleFragmentDirections.actionScheduleFragmentToFinishFragment()
            findNavController().navigate(actionBack)
        }

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
                chosenYear = year
                chosenMonth = monthOfYear
                chosenDay = dayOfMonth
            }
        }

        datePicker.setOnClickListener (object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(requireContext(),
                    dateSetListener,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()

            }
        })

        timePicker.setOnClickListener {
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                binding.timePicker.text = SimpleDateFormat("HH:mm").format(cal.time)
                chosenHour = hour
                chosenMin = minute
            }
            TimePickerDialog(requireContext(), timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }


    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.datePicker.text = sdf.format(cal.getTime())
    }

    private fun createWorkRequest(message: String,timeDelayInSeconds: Long  ) {

        val myWorkRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(timeDelayInSeconds, TimeUnit.SECONDS)
            .setInputData(workDataOf(
                "title" to "Reminder",
                "message" to message,
            )
            )
            .build()

        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
    }





}





