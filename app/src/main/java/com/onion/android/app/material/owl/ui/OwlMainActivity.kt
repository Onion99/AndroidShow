package com.onion.android.app.material.owl.ui

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.onion.android.R
import com.onion.android.app.base.BindingActivity
import com.onion.android.app.view.hide
import com.onion.android.databinding.ActivityOwlMainBinding

class OwlMainActivity : BindingActivity<ActivityOwlMainBinding>(R.layout.activity_owl_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            val navController = Navigation.findNavController(this@OwlMainActivity, R.id.nav_host)
            bottomNav.setupWithNavController(navController)

            lifecycleScope.launchWhenResumed {
                navController.addOnDestinationChangedListener { _, destination, _ ->
                    when (destination.id) {
                        else -> bottomNav.hide()
                    }
                }
            }
        }
    }
}