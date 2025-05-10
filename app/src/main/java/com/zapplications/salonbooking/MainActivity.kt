package com.zapplications.salonbooking

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zapplications.salonbooking.core.extensions.gone
import com.zapplications.salonbooking.core.extensions.hideKeyboard
import com.zapplications.salonbooking.core.extensions.visible
import com.zapplications.salonbooking.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(FLAG_SECURE, FLAG_SECURE)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, 0)
            insets
        }

        initBottomNavigationView()
    }

    private fun initBottomNavigationView() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        navController?.let { controller ->
            bottomNavigationBar.setupWithNavController(navController = controller)
            controller.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.homeFragment -> bottomNavigationBar.visible()
                    R.id.favouritesFragment -> bottomNavigationBar.visible()
                    R.id.bookingsFragment -> bottomNavigationBar.visible()
                    else -> bottomNavigationBar.gone()
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus is EditText) {
            val outRect = Rect()
            currentFocus?.getGlobalVisibleRect(outRect)
            if (ev?.action == MotionEvent.ACTION_DOWN && !outRect.contains(ev.x.toInt(), ev.y.toInt())) {
                hideKeyboard()
                currentFocus?.clearFocus()
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
