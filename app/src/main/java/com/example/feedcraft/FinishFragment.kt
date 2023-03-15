package com.example.feedcraft

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.feedcraft.databinding.FragmentEditBinding
import com.example.feedcraft.databinding.FragmentFinishBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FinishFragment : Fragment() {
    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditorViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnShare = binding.btnShareFinish
        val btnSchedule = binding.btnScheduleFinish
        val btnSave = binding.btnSaveFinish
        val btnDiscard = binding.btnDiscardFinish
        val imgPreview = binding.imgPreview
        val captionField = binding.captionFieldFinish

        captionField.text = viewModel.getCaption()


        btnSchedule.setOnClickListener {
            val actionSchedule = FinishFragmentDirections.actionFinishFragmentToScheduleFragment()
            findNavController().navigate(actionSchedule)
        }
        btnSave.setOnClickListener {

            val tempBitmap = UIApplication.tempBitmap as Bitmap
            saveBitmap(tempBitmap, context?.filesDir.toString()+ File.separator+"creationAlbum", "temp.png")
            //requireActivity().finish()
        }
        btnShare.setOnClickListener {
            //TODO
        }
        btnDiscard.setOnClickListener {
            val actionDiscard = FinishFragmentDirections.actionFinishFragmentToEditFragment()
            findNavController().navigate(actionDiscard)
        }


    }

    private fun saveBitmap(bitmap: Bitmap, filePath: String, fileName: String): File {

        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)

        val dir = File(filePath)

        if (!dir.exists()) {
            dir.mkdir()
        }

        val f = File(dir, fileName)
        f.createNewFile()

        val fo = FileOutputStream(f)
        fo.write(bytes.toByteArray())
        fo.close()

        return f
    }

}