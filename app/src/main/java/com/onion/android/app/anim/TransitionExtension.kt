package com.onion.android.app.anim

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.core.view.ViewCompat
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.skydoves.transformationlayout.TransformationLayout

/**
 * App 转场动画
 */
/**
 * Activity 退场动画
 */
fun Activity.onTransformationExitContainer(){
    // 启用扩展屏幕功能。必须在setContentView（）之前调用它
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    // 设置退场动画
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    // 设置页面过渡期间页面里元素是否同样会被覆盖
    window.sharedElementsUseOverlay = false
}

/**
 * Activity 进场动画
 */
fun Activity.onTransformationEnterContainer(params: TransformationLayout.Params?){
    requireNotNull(params){
        "进场动画容器为空"
    }
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    // 设置过度动画的View的标识
    ViewCompat.setTransitionName(findViewById<View>(android.R.id.content), params.transitionName)
    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
}