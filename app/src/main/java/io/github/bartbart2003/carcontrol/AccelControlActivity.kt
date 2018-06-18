package io.github.bartbart2003.carcontrol

import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_accel_control.*
import kotlinx.android.synthetic.main.app_bar_accel_control.*
import kotlinx.android.synthetic.main.content_accel_control.*

class AccelControlActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, SensorEventListener {

    lateinit var sensorManager: SensorManager
    lateinit var mAccelerometer: Sensor
    var currentState = 0
    // 0 = stop all
    // 1 = forward
    // 2 = backward
    // 3 = forward left
    // 4 = backward left
    // 5 = forward right
    // 6 = backward right
    override fun onSensorChanged(p0: SensorEvent?) {
        textView7.setText("x: " + p0!!.values[0] + "\n" + "y: " + p0.values[1] + "\n" + "z: " + p0.values[2])
        // forward left
        if (p0.values[0] > 3 && p0.values[1] <= -2)
        {
            if (currentState != 3) {
                currentState = 3
                sendRequest("fl")
                textView6.setText("↰")
            }
        }
        // backward left
        else if (p0.values[0] > 3 && p0.values[1] > 2)
        {
            if (currentState != 4) {
                currentState = 4
                sendRequest("bl")
                textView6.setText("↲")
            }
        }
        // forward right
        else if (p0.values[0] < -3 && p0.values[1] <= -2)
        {
            if (currentState != 5) {
                currentState = 5
                sendRequest("fr")
                textView6.setText("↱")
            }
        }
        // backward right
        else if (p0.values[0] < -3 && p0.values[1] > 2)
        {
            if (currentState != 6) {
                currentState = 6
                sendRequest("br")
                textView6.setText("↳")
            }
        }
        // backward
        else if (p0.values[1] > 3)
        {
            if (currentState != 2) {
                currentState = 2
                sendRequest("bw")
                textView6.setText("↓")
            }
        }
        // forward
        else if (p0.values[1] < -3)
        {
            if (currentState != 1) {
                currentState = 1
                sendRequest("fw")
                textView6.setText("↑")
            }
        }
        // stop all
        else if (currentState != 0)
        {
            currentState = 0
            sendRequest("sa")
            textView6.setText("STOP ALL")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accel_control)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, mAccelerometer , SensorManager.SENSOR_DELAY_NORMAL)

        nav_view.setNavigationItemSelectedListener(this)
    }

    fun sendRequest(f: String)
    {
        sendRequestTask().execute(editText3.text.toString() + "/" + f)
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
            R.id.nav_buttons -> {
                startActivity(Intent(this, ButtonsControlActivity::class.java))
            }
            R.id.nav_joystick -> {
                startActivity(Intent(this, JoystickControlActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
