package com.onion.android.app.anim

import android.os.Parcelable
import android.widget.FrameLayout
import androidx.transition.PathMotion
import com.google.android.material.transition.MaterialArcMotion

class TransformationLayout{
    // 动画规矩
    enum class Motion(private val value: Int){
        ARC(0),
        LINEAR(1);
        fun getPathMotion(): PathMotion?{
            if(value == 0) return MaterialArcMotion()
            return null
        }
    }
    //
    enum class Params(

    ):Parcelable
}