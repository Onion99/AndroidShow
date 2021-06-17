package com.onion.android.app.pokemon.transformation

import android.app.Activity
import android.view.Window
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

fun Activity.onTransformationStartContainer(){
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
}