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
    var buttonCL = false
    var buttonCR = false
    var buttonBW = false
    var buttonFW = false
    var buttonFR = false
    var buttonFL = false
    var buttonBL = false
    var buttonBR = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buttons_control)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        button8.setOnTouchListener { v: View, m: MotionEvent ->
            buttonCL = false
            buttonCR = false
            buttonBL = false
            buttonBR = false
            buttonFW = false
            buttonBW = false
            buttonFL = false
            buttonFR = false
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
        if (m.actionMasked == 0)
        {
            if (f == "cl")
            {
                buttonCL = true
            }
            if (f == "cr")
            {
                buttonCR = true
            }
            if (f == "bl")
            {
                buttonBL = true
            }
            if (f == "br")
            {
                buttonBR = true
            }
            if (f == "fw")
            {
                buttonFW = true
            }
            if (f == "bw")
            {
                buttonBW = true
            }
            if (f == "fl")
            {
                buttonFL = true
            }
            if (f == "fr")
            {
                buttonFR = true
            }
            sendRequest(f)
        }
        if (m.actionMasked == 1)
        {
            if (f == "cl")
            {
                buttonCL = false
            }
            if (f == "cr")
            {
                buttonCR = false
            }
            if (f == "bl")
            {
                buttonBL = false
            }
            if (f == "br")
            {
                buttonBR = false
            }
            if (f == "fw")
            {
                buttonFW = false
            }
            if (f == "bw")
            {
                buttonBW = false
            }
            if (f == "fl")
            {
                buttonFL = false
            }
            if (f == "fr")
            {
                buttonFR = false
            }
            handleButtons(f)
        }
    }

    fun sendRequest(f: String)
    {
        sendRequestTask().execute(editText2.text.toString() + "/" + f)
    }

    fun handleButtons(b: String)
    {
        if (buttonCL)
        {
            sendRequest("cl")
        }
        else if (buttonCR)
        {
            sendRequest("cr")
        }
        else if (buttonBW)
        {
            sendRequest("bw")
        }
        else if (buttonFW)
        {
            sendRequest("fw")
        }
        else if (buttonFR)
        {
            sendRequest("fr")
        }
        else if (buttonFL)
        {
            sendRequest("fl")
        }
        else if (buttonBL)
        {
            sendRequest("bl")
        }
        else if (buttonBR)
        {
            sendRequest("br")
        }
        else
        {
            sendRequest("sa")
        }
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
            val urlConnection = URL(url[0]).openConnection() as HttpURLConnection
            urlConnection.setConnectTimeout(300)
            urlConnection.inputStream.bufferedReader()
            urlConnection.disconnect()
            return ""
        }
        catch (e: Exception)
        {
            return null
        }
    }
}