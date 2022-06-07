package com.dong.onionui.material

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dong.onionui.databinding.ActivityMaterialShowBinding
import com.dong.onionui.material.data.PreferenceRepository
import com.dong.onionui.material.show.MaterialShowFragment
import com.onion.common.ui.viewbinding.viewBinding

class MaterialShowActivity : AppCompatActivity() {

    companion object{
        const val DEFAULT_PREFERENCES = "default_preferences"
        var  preferenceRepository:PreferenceRepository? =null
    }

    private val binding:ActivityMaterialShowBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ---- 初始化夜间模式监听 ------
        if(preferenceRepository == null)
            preferenceRepository = PreferenceRepository(getSharedPreferences(DEFAULT_PREFERENCES, Context.MODE_PRIVATE))
        preferenceRepository!!.nightModeLive.observe(this){
            delegate.localNightMode = it
        }
        setContentView(binding.root)
        for (i in 0..2){
            binding.tabLayout.addTab(binding.tabLayout.newTab().apply {
                text = "Test $i"
                tag = "Test $i"
            })
        }
        binding.viewPager.adapter = MainViewPagerAdapter(this,supportFragmentManager)
    }
}


class MainViewPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    enum class MainFragments(val titleRes: Int) {
        TEST1(1),
        TEST2(2),
        TEST3(3)
    }

    override fun getCount(): Int = MainFragments.values().size

    private fun getItemType(position: Int): MainFragments {
        return MainFragments.values()[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.getString(getItemType(position).titleRes)
    }

    override fun getItem(position: Int): Fragment {
        return when (getItemType(position)) {
            MainFragments.TEST1 -> MaterialShowFragment()
            MainFragments.TEST2 -> MaterialShowFragment()
            MainFragments.TEST3 -> MaterialShowFragment()
        }
    }
}