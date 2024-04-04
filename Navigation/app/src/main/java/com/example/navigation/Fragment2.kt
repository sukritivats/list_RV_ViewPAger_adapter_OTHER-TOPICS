package com.example.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.navigation.databinding.Fragment2Binding
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import java.io.File

class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding

    private val videoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                val videoUri: Uri? = data?.data
                if (videoUri != null) {
                    boomerangEffect(videoUri)
                } else
                    Toast.makeText(requireContext(), "Failed to retrieve video", Toast.LENGTH_SHORT)
                        .show()

            } else
                Toast.makeText(requireContext(), "Video capture failed", Toast.LENGTH_SHORT).show()

        }
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it)
                playVideo()
            else
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.apply {
            title = null
        }
        binding = Fragment2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnVideo.setOnClickListener {
            if (checkPermission())
                playVideo()
            else
                permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
        binding.videoView.setOnClickListener {
            if (binding.videoView.isPlaying)
                binding.videoView.pause()
            else
                binding.videoView.start()
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun playVideo() {
        videoLauncher.launch(Intent(MediaStore.ACTION_VIDEO_CAPTURE))
    }

    private fun boomerangEffect(videoUri: Uri) {
        val inputVideoPath = getRealPathFromURI(videoUri)
        val outputVideoPath = getOutputVideoPath()

        Log.e("VideoIn", inputVideoPath)
        Log.e("VideoOut", outputVideoPath)

        val ffmpegCommand = arrayOf(
            "-y", // Overwrite output file without asking
            "-i", inputVideoPath,
            "-vf", "split=2[a][b];[b]reverse[c];[a][c]concat",
            "-an", // Disable audio
            "-vf", "setpts=${2.0}*PTS",
            outputVideoPath
        )
//        Runtime.getRuntime().exec(ffmpegCommand)
//        playProcessedVideo(outputDirectory.absolutePath)
            FFmpeg.getInstance(requireContext())
               .execute(ffmpegCommand, object : ExecuteBinaryResponseHandler() {
                override fun onSuccess(message: String?) {
                    playProcessedVideo(outputVideoPath)
                }

                override fun onFailure(message: String?) {
                    Log.e("FFmpeg", "Failed to process video: $message")
                }
            })
    }

    private fun playProcessedVideo(videoPath: String) {
        binding.videoView.setVideoPath(videoPath)
        binding.videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.start()
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        var realPath = ""
        cursor?.use {
            it.moveToFirst()
            val columnIndex = it.getColumnIndex(MediaStore.Video.VideoColumns.DATA)
            realPath = it.getString(columnIndex)
        }
        cursor?.close()
        return realPath
    }

    private fun getOutputVideoPath(): String {
        val outputDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        return "${outputDir.absolutePath}/output.mp4"
    }

}



