package com.example.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.navigation.databinding.Fragment3Binding


class Fragment3 : Fragment() {

    private lateinit var binding:Fragment3Binding
    private val requestCameraLauncher= registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode==Activity.RESULT_OK){
            val data:Intent?=it.data
            val imageBitmap=data?.extras?.get("data")as Bitmap
            binding.imageView.setImageBitmap(imageBitmap)
        }
    }
    private val requestPermission=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it)
            openCamera()
        else
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            title=null
        }
        binding=Fragment3Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnImage.setOnClickListener(){
            if(checkPermission())
                openCamera()
            else
                requestPermission.launch(android.Manifest.permission.CAMERA)

        }
    }
    private fun checkPermission():Boolean{
        return  ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
    }
    private fun openCamera(){
        requestCameraLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

}