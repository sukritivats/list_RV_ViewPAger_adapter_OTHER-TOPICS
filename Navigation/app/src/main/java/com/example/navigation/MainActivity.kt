package com.example.navigation

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.navigation.Fragment1.Fragment1
import com.example.navigation.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var binding: ActivityMainBinding

    val navController by lazy { findNavController(R.id.fragmentContainerView) }

    lateinit var notification: Notification
    lateinit var notificationManager:NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //notifications

        notificationManager= NotificationManagerCompat.from(this)

        // toolbar + Drawer
        setSupportActionBar(binding.toolbar)
        binding.navView.setNavigationItemSelectedListener(this)
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.apply {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }


        // bottom navigation view
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navF1 -> navController.navigate(R.id.fragment13)
                R.id.navF2 -> navController.navigate(R.id.fragment23)
                R.id.navF3 -> navController.navigate(R.id.fragment33)
            }
            true
        }
    }

    private fun showNotification(){
        createNotificationChannel()
        val intent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationId = System.currentTimeMillis().toInt()
    // build notification
        notification= NotificationCompat.Builder(this,0.toString())
        .setContentTitle("Notification Title")
        .setContentText("Here will be the content")
        .setSmallIcon(R.drawable.icon)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(pendingIntent)
        .build()

    if(ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.POST_NOTIFICATIONS)==PackageManager.PERMISSION_GRANTED){
        notificationManager.notify(notificationId,notification)
    }

}
    private fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val channel = NotificationChannel(0.toString(),"name",NotificationManager.IMPORTANCE_DEFAULT).apply {
                description="This is me NOTIFICATION"
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    /*    private fun replaceFragments(fragment: Fragment) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit()
        }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navNotifications -> showNotification()
            R.id.navCuisines -> navController.navigate(R.id.cuisines)
            R.id.nav_boomerang -> navController.navigate(R.id.boomerangFragment)
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
