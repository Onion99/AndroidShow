package com.onion.android.app.plex.player.view

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.google.android.exoplayer2.Player
import com.onion.android.R
import com.onion.android.app.plex.player.controller.PlayerController
import com.onion.android.databinding.PlexControllerViewBinding
import javax.inject.Inject

class UIControllerView : FrameLayout {

    @Inject
    lateinit var playerController: PlayerController
    private val TIME_TO_HIDE_CONTROL = 5000
    private lateinit var binding: PlexControllerViewBinding
    private lateinit var countdownHandler: Handler


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setBinding(
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.plex_controller_view,
                this,
                true
            )
        )
        Thread(hideUIAction).start()
        countdownHandler = Handler(Looper.getMainLooper())

    }

    private fun setBinding(binding: PlexControllerViewBinding) {
        this.binding = binding
    }

    fun setPlayerController(playerController: PlayerController): UIControllerView {
        this.playerController = playerController
        binding.controller = playerController
        return this
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        countdownHandler.removeCallbacks(hideUIAction)
        if (binding.controllerPanel.visibility == VISIBLE) {
            val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
            alphaAnimation.duration = 500
            binding.controllerPanel.startAnimation(alphaAnimation)
            alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}
                override fun onAnimationEnd(animation: Animation) {
                    binding.controllerPanel.visibility = GONE
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })
        } else {
            if (playerController.playerPlaybackState.get() != Player.STATE_IDLE) {
                binding.controllerPanel.visibility = VISIBLE
                hideUiTimeout()
            }
        }
        return super.onTouchEvent(event)
    }

    ///////////////////////////////////////////////////////////////////////////
    // 销毁时处理
    ///////////////////////////////////////////////////////////////////////////
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        countdownHandler.removeCallbacks(hideUIAction)
    }

    ///////////////////////////////////////////////////////////////////////////
    // 隐藏动画
    ///////////////////////////////////////////////////////////////////////////
    private var hideUIAction = Runnable {
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
        alphaAnimation.duration = 500
        binding.controllerPanel.startAnimation(alphaAnimation)
        alphaAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                binding.controllerPanel.visibility = GONE
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    ///////////////////////////////////////////////////////////////////////////
    // 超时隐藏动画
    ///////////////////////////////////////////////////////////////////////////
    private fun hideUiTimeout() {
        countdownHandler.postDelayed(hideUIAction, TIME_TO_HIDE_CONTROL.toLong())
        val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
        alphaAnimation.duration = 500
        binding.controllerPanel.startAnimation(alphaAnimation)
    }

}