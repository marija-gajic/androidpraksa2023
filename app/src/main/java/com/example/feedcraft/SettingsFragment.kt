package com.example.feedcraft

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.feedcraft.BuildConfig
import com.example.feedcraft.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val btnRate = binding.contRate
        val btnRecommend = binding.contRecommend
        val btnFeedback = binding.contFeedback
        val btnMoreApps = binding.contMoreApps
        val btnPrivacy = binding.contPrivacy
        val darkModeOn = binding.darkModeOn
        val darkModeOff = binding.darkModeOff

        if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_NO) {
            darkModeOn.isVisible = true
            darkModeOff.isVisible = false
        }
        else {
            darkModeOn.isVisible = false
            darkModeOff.isVisible = true
        }

        btnRate.setOnClickListener {
            val gameId = resources.getString(R.string.play_store_game_package_id)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/details?id=$gameId")
                setPackage("com.android.vending")
            }
            try {
                startActivity(intent)
            }
            catch (activityNotFoundEx:ActivityNotFoundException)
            {
                val text = getString(R.string.rate_unavailable)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
        btnRecommend.setOnClickListener {
            val gameId = resources.getString(R.string.play_store_game_package_id)
            val appLink = "https://play.google.com/store/apps/details?id=$gameId"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,  appLink)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.share_check_out_this_app))

            try {
                startActivity(shareIntent)
            }
            catch (activityNotFoundEx:ActivityNotFoundException)
            {
                val text = getString(R.string.share_unavailable)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }

        }
        btnFeedback.setOnClickListener {
            val email = getString(R.string.dev_email)
            val extraText = getString(R.string.write_feedback_below)
            val phoneModel = Build.MODEL
            val phoneDevice = Build.DEVICE
            val appName = BuildConfig.APPLICATION_ID
            val appVersion = BuildConfig.VERSION_CODE
            val androidVersion = Build.VERSION.SDK_INT
            val subject = "App name: $appName, App version: $appVersion, Device: $phoneDevice, Phone model: $phoneModel, Android version: $androidVersion"

          /*  lateinit var imageUri: Uri
            val appInfo: ApplicationInfo = context?.applicationContext?.packageManager?.getApplicationInfo(requireContext().packageName!!, 0)!!
            if (appInfo.icon != 0) {
                imageUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + appInfo.icon)
            }*/

            //val imageUri = getUriFromDrawableResId(requireContext(), R.drawable.logo)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, extraText)
                //putExtra(Intent.EXTRA_STREAM, imageUri)
            }
            try {
                startActivity(intent)
            }
            catch (activityNotFoundEx:ActivityNotFoundException)
            {
                val text = getString(R.string.feedback_unavailable)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
        btnMoreApps.setOnClickListener {
            val devId = resources.getString(R.string.play_store_dev)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    "https://play.google.com/store/apps/dev?id=$devId")
                setPackage("com.android.vending")
            }
            try {
                startActivity(intent)
            }
            catch (activityNotFoundEx:ActivityNotFoundException)
            {
                val text = getString(R.string.more_apps_unavailable)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
        btnPrivacy.setOnClickListener {
            val privacySite = resources.getString(R.string.privacy_site)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(
                    privacySite
                )
            }
            try {
                startActivity(intent)
            }
            catch (activityNotFoundEx:ActivityNotFoundException)
            {
                val text = getString(R.string.privacy_policy_unavailable)
                val duration = Toast.LENGTH_SHORT
                val toast = Toast.makeText(context, text, duration)
                toast.show()
            }
        }
        darkModeOn.setOnClickListener {
            darkModeOn.isVisible = false
            darkModeOff.isVisible = true

            if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }

        }
        darkModeOff.setOnClickListener {
            darkModeOn.isVisible = true
            darkModeOff.isVisible = false

            if (AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
            }
            else {
                AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
            }
        }
    }

    fun getUriFromDrawableResId(context: Context, drawableResId: Int): Uri? {

        val builder: StringBuilder = StringBuilder().append(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .append("://")
            .append(context.getResources().getResourcePackageName(drawableResId))
            .append("/")
            .append(context.getResources().getResourceTypeName(drawableResId))
            .append("/")
            .append(context.getResources().getResourceEntryName(drawableResId))
        return Uri.parse(builder.toString())
    }
}