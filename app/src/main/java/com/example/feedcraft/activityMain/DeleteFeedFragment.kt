package com.example.feedcraft.activityMain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.data.UIApplication
import com.example.feedcraft.databinding.FragmentDeleteFeedBinding
import com.example.feedcraft.viewModels.EditorViewModel
import com.example.feedcraft.viewModels.MainViewModel

class DeleteFeedFragment : DialogFragment() {
    private var _binding: FragmentDeleteFeedBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeleteFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnOk = binding.btnDeleteOk
        val btnCancel = binding.btnDeleteCancel

//        binding.txtDelete.typeface =
//            ResourcesCompat.getFont(requireContext(), R.font.poppins_regular)

        btnOk.setOnClickListener {
            val photo = mainViewModel.getPhotoInformationFromPosition(requireContext(), UIApplication.itemSelectedPosition)
            val photoName = photo.imgName
            viewModel.deleteBitmapFromInternalStorageByName(requireContext(), photoName)
            mainViewModel.deletePhotoFromJsonByName(requireContext(), photoName)
            //previews.adapter?.notifyItemRemoved(UIApplication.itemSelectedPosition)
            UIApplication.photoDeleted.postValue("deleted")
            Toast.makeText(requireContext(), "Photo deleted!", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        btnCancel.setOnClickListener {
//            val actionCancel = DeleteFeedFragmentDirections.actionDeleteFeedFragmentToFeedFragment()
//            findNavController().navigate(actionCancel)
            findNavController().navigateUp()
        }
    }




}