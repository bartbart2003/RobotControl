package io.github.bartbart2003.carcontrol

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.github.controlwear.virtual.joystick.android.JoystickView
import kotlinx.android.synthetic.main.activity_joystick_control.*
import kotlinx.android.synthetic.main.app_bar_joystick_control.*
import kotlinx.android.synthetic.main.content_joystick_control.*

class JoystickControlActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joystick_control)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        var currentDirection = 0
        joystickView.setOnMoveListener(object: JoystickView.OnMoveListener {
            override fun onMove(angle: Int, strength: Int) {

                //    <--\
                // ( )   |
                //(   ) -/
                // (_)

                if (strength > 60) {
                    // Forward
                    if (angle >= 55 && angle < 125)
                    {
                        if (currentDirection != 1) {
                            currentDirection = 1
                            sendRequest("fw")
                        }
                    }
                    // Forward left
                    else if (angle >= 125 && angle < 180)
                    {
                        if (currentDirection != 2) {
                            currentDirection = 2
                            sendRequest("fl")
                        }
                    }
                    // Back left
                    else if (angle >= 190 && angle < 235)
                    {
                        if (currentDirection != 3) {
                            currentDirection = 3
                            sendRequest("bl")
                        }
                    }
                    // Backward
                    else if (angle >= 235 && angle < 305)
                    {
                        if (currentDirection != 4) {
                            currentDirection = 4
                            sendRequest("bw")
                        }
                    }
                    // Back right
                    else if (angle >= 305)
                    {
                        if (currentDirection != 5) {
                            currentDirection = 5
                            sendRequest("br")
                        }
                    }

                    // Forward right
                    else
                    {
                        if (currentDirection != 6) {
                            currentDirection = 6
                            sendRequest("fr")
                        }
                    }
                }
                else
                {
                    if (currentDirection != 0) {
                        currentDirection = 0
                        sendRequest("sa")
                    }
                }
            }
        }, 50)
        nav_view.setNavigationItemSelectedListener(this)
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
            R.id.nav_buttons -> {
                startActivity(Intent(this, ButtonsControlActivity::class.java))
            }
            R.id.nav_accelerometer -> {
                startActivity(Intent(this, AccelControlActivity::class.java))
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}