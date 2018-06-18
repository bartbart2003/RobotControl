package io.github.bartbart2003.carcontrol

import android.content.Context
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.activity_buttons_control.*
import kotlinx.android.synthetic.main.app_bar_buttons_control.*
import kotlinx.android.synthetic.main.content_buttons_control.*
import android.content.Intent
import java.net.HttpURLConnection
import java.net.URL
import android.os.AsyncTask

class ButtonsControlActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttons_control)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        button8.setOnTouchListener { v: View, m: MotionEvent ->
            sendRequest("sa")
            true
        }

        button9.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "cl")
            true
        }

        button10.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "cr")
            true
        }

        button11.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "bw")
            true
        }

        button12.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "fw")
            true
        }
        button13.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "fr")
            true
        }
        button14.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "fl")
            true
        }
        button3.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "bl")
            true
        }
        button2.setOnTouchListener {v: View, m: MotionEvent ->
            handleTouch(m, "br")
            true
        }
        nav_view.setNavigationItemSelectedListener(this)
    }

    fun handleTouch(m: MotionEvent, f: String) {
        if (m.actionMasked.equals(0))
        {
            sendRequest(f)
        }
        if (m.actionMasked.equals(1))
        {
            sendRequest("sa")
        }
    }

    fun sendRequest(f: String)
    {
        sendRequestTask().execute(editText2.text.toString() + "/" + f)

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.action_about) {
            startActivity(Intent(this, AboutActivity::class.java))
            return true
        }
        else if (item.itemId == R.id.action_manualcontrol) {
            sendRequest("mc")
            return true
        }
        else if (item.itemId == R.id.action_linefollower) {
            sendRequest("lf")
            return true
        }
        else if (item.itemId == R.id.action_distanceeneable) {
            sendRequest("de")
            return true
        }
        else if (item.itemId == R.id.action_distancedisable) {
            sendRequest("dd")
            return true
        }
        else if (item.itemId == R.id.action_reboot) {
            sendRequest("rb")
            return true
        }
        else if (item.itemId == R.id.action_shutdown) {
            sendRequest("sd")
            return true
        }
        else
        {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_accelerometer -> {
                startActivity(Intent(this, AccelControlActivity::class.java))
            }
            R.id.nav_joystick -> {
                startActivity(Intent(this, JoystickControlActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}

class sendRequestTask() : AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg url: String): String? {
        try {
            val url = URL(url[0])
            val urlConnection = url.openConnection() as HttpURLConnection
            val data = urlConnection.inputStream.bufferedReader().readText()
            urlConnection.disconnect()
            return data
        }
        catch (e: Exception)
        {
            return null
        }
    }
}