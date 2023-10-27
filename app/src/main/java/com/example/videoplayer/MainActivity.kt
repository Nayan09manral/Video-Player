package com.example.videoplayer

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.videoplayer.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        requestingRuntimePermission()
        setFragment(VideosFragment())

//        for Nav Drawer
        toggle = ActionBarDrawerToggle(this,binding.root,R.string.open,R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.videoView -> setFragment(VideosFragment())
                R.id.folderView -> setFragment(FoldersFragment())
            }
            Toast.makeText(this@MainActivity,"Item clicked",Toast.LENGTH_SHORT).show()
            return@setOnItemSelectedListener true
        }

        binding.navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.feedbackNav -> Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                R.id.themesNav -> Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                R.id.sortOrderNav ->Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                R.id.aboutNav -> Toast.makeText(this,"yes",Toast.LENGTH_SHORT).show()
                R.id.exitNav -> exitProcess(1)

            }
            true
        }
    }

    private fun setFragment(fragment: Fragment)
    {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentFL, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }
    private fun requestingRuntimePermission(): Boolean{
        if(ActivityCompat.checkSelfPermission(this,WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 13) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
            else
                ActivityCompat.requestPermissions(this, arrayOf(WRITE_EXTERNAL_STORAGE),13)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)

    }
}