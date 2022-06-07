package com.dong.onionui.material.show

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dong.onionui.R
import com.dong.onionui.databinding.ActivityMaterialShowBinding
import com.dong.onionui.databinding.FragmentMaterialShowBinding
import com.dong.onionui.material.MaterialShowActivity
import com.onion.common.ui.viewbinding.viewBinding

class MaterialShowFragment:Fragment(R.layout.fragment_material_show) {
    private val binding:FragmentMaterialShowBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        MaterialShowActivity.preferenceRepository!!.isDarkThemeLive.observe(this) { isDarkTheme ->
            isDarkTheme?.let { binding.darkThemeSwitch.isChecked = it }
        }

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            MaterialShowActivity.preferenceRepository!!.isDarkTheme = isChecked
        }
    }
}