package com.example.navigation

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaExtractor
import android.media.MediaFormat
import android.media.MediaMuxer
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.navigation.databinding.FragmentBoomerangBinding
import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler
import com.github.hiteshsondhi88.libffmpeg.FFmpeg
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class BoomerangFragment : Fragment() {

    private var binding: FragmentBoomerangBinding? = null
    private var selectedVideoPath: String? = null
    private var command: String? = "ffmpeg -ss 0 -t 1 -an -i 1.mp4 -y -filter_complex \"[0]split[b][c];[c]reverse[r];[b][r]concat\" out.mp4"
    private var currentVideoUri: Uri? = null

    private val videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val videoUri: Uri? = data?.data
            if (videoUri != null) {
                // Get the path of the selected video directly
                val cursor = requireContext().contentResolver.query(videoUri, null, null, null, null)
                cursor?.use { c ->
                    if (c.moveToFirst()) {
                        val columnIndex = c.getColumnIndex(MediaStore.Video.Media.DATA)
                        selectedVideoPath = c.getString(columnIndex)
                    }
                }

                if (selectedVideoPath != null) {
                    applyBoomerangEffect(Uri.parse(selectedVideoPath))
//                    binding?.videoView?.setVideoURI(videoUri)
//                    binding?.videoView?.start()
                } else {
                    Toast.makeText(requireContext(), "Failed to retrieve video path", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Failed to retrieve video", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Video capture failed", Toast.LENGTH_SHORT).show()
        }
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            selectVideoFromStorage()
        } else {
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoomerangBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnBoomerang?.setOnClickListener {
            if (checkPermission()) {
                selectVideoFromStorage()
            } else {
                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        binding?.videoView?.setOnClickListener {
            if (binding?.videoView?.isPlaying == true) {
                binding?.videoView?.pause()
            } else {
                binding?.videoView?.start()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun selectVideoFromStorage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        videoLauncher.launch(intent)
    }
    private fun applyBoomerangEffect(videoUri: Uri) {
        val outputVideoPath = File(requireContext().cacheDir, "boomerang_video.mp4").absolutePath

        val ffmpegCommand = arrayOf(
            "-y",
            "-i", videoUri.toString(), // Use the videoUri directly
            "-vf", "split=2[a][b];[b]reverse[c];[a][c]concat",
            "-an",
            "setpts=${2.0}*PTS",
            outputVideoPath
        )

        FFmpeg.getInstance(requireContext()).execute(ffmpegCommand, object : ExecuteBinaryResponseHandler() {
            override fun onSuccess(message: String?) {
                // Perform UI operations on the main thread
                requireActivity().runOnUiThread {
                    // Set the video URI and start playback
                    binding?.videoView?.setVideoURI(videoUri)
                    binding?.videoView?.start()
                    currentVideoUri = videoUri
                }
            }

            override fun onFailure(message: String?) {
                // Perform UI operations on the main thread
                requireActivity().runOnUiThread {
                    // Show failure message
                    Toast.makeText(requireContext(), "Failed to process video", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    // Add method to save the boomerang effect
    private fun saveBoomerangEffect() {
        // Check if currentVideoUri is not null
        currentVideoUri?.let { uri ->
            // Code to save the video with boomerang effect
            // For simplicity, let's just display a toast message
            Toast.makeText(requireContext(), "Boomerang effect saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
