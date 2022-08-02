package com.dong.onionui.ui.act

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dong.onionui.R
import com.dong.onionui.databinding.ActivityFragmentContainerBinding
import com.dong.onionui.helper.AndroidUtilities
import com.dong.onionui.helper.LayoutHelper
import com.dong.onionui.ui.view.CardModel
import com.dong.onionui.ui.view.CardsStackView
import com.dong.onionui.ui.view.SwitchButton
import com.dong.onionui.ui.view.celebrate.CelebrateView
import com.dong.onionui.ui.view.celebrate.Party
import com.dong.onionui.ui.view.celebrate.Position
import com.dong.onionui.ui.view.celebrate.emitter.Emitter
import com.onion.common.ui.viewbinding.viewBinding
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.TimeUnit

class FragmentContainerActivity : AppCompatActivity(){

    private val binding: ActivityFragmentContainerBinding by viewBinding()
    private var showfragment = Fragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        showfragment = CustomViewFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_container_view,showfragment,R.id.fragment_container_view.toString()).setPrimaryNavigationFragment(showfragment).setReorderingAllowed(true).commitAllowingStateLoss()
    }
}


class CustomViewFragment:Fragment(){

    private val celebrateTimer = Timer()
    private lateinit var celebrateView:CelebrateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val rootLayout = LinearLayout(requireContext())
        rootLayout.clipChildren = false
        rootLayout.clipToPadding = false
        rootLayout.orientation = LinearLayout.VERTICAL
        rootLayout.layoutParams = LayoutHelper.createCommonViewGroup()
        rootLayout.gravity = Gravity.CENTER_HORIZONTAL
        // CelebrateView
        celebrateView = CelebrateView(context)
        rootLayout.addView(celebrateView,LayoutHelper.MATCH_PARENT,AndroidUtilities.dp(300f))
        // switchButton
        val switchButton = SwitchButton(requireContext())
        rootLayout.addView(switchButton,LayoutHelper.WRAP_CONTENT,LayoutHelper.WRAP_CONTENT)
        switchButton.layoutParams = (switchButton.layoutParams as LinearLayout.LayoutParams).apply {
            topMargin = AndroidUtilities.dp(10f)
            bottomMargin = AndroidUtilities.dp(6f)
        }
        // CardsStackView
        val cardsStackView = CardsStackView(requireContext())
        cardsStackView.setUpCardStack(ArrayList<CardModel>().apply {
            add(
                CardModel(
                    primaryTitle = "Marry Martin",
                    secondaryTitle = "Chicago, USA",
                    image = "https://i.ibb.co/8Bc6hwX/Rectangle-9.png"
                )
            )
            add(
                CardModel(
                    primaryTitle = "Pamela",
                    secondaryTitle = "Phoenix",
                    image = "https://cdn.pixabay.com/photo/2017/08/06/15/13/woman-2593366__340.jpg"
                )
            )
            add(
                CardModel(
                    primaryTitle = "Massey",
                    secondaryTitle = "New York",
                    image = "https://cdn.pixabay.com/photo/2016/03/23/04/01/woman-1274056_960_720.jpg"
                )
            )
            add(
                CardModel(
                    primaryTitle = "Yema",
                    secondaryTitle = "Milano",
                    image = "https://picsum.photos/id/1011/5472/3648"
                )
            )
            add(
                CardModel(
                    primaryTitle = "Wilson",
                    secondaryTitle = "Chicago",
                    image = "https://picsum.photos/id/1025/4951/3301"
                )
            )
            add(
                CardModel(
                    primaryTitle = "John",
                    secondaryTitle = "Houston",
                    image = "https://picsum.photos/id/1012/3973/2639"
                )
            )
        })
        rootLayout.addView(cardsStackView,LayoutHelper.MATCH_PARENT,LayoutHelper.WRAP_CONTENT)
        return rootLayout
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showCelebrate()
    }

    private fun  showCelebrate(){
        celebrateTimer.schedule(object :TimerTask(){
            override fun run() {
                celebrateView.start(
                    listOf(
                        Party(
                            speed = 0f,
                            maxSpeed = 30f,
                            damping = 0.9f,
                            spread = 360,
                            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                            position = Position.Relative(0.5, 0.3)
                        )
                    )
                )
            }
        },0,230)
    }

    override fun onDestroy() {
        celebrateTimer.cancel()
        super.onDestroy()
    }
}