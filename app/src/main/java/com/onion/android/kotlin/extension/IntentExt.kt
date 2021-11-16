package com.base.ext

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment


///////////////////////////////////////////////////////////////////////////
// Intent扩展
// 简单调用
// startActivity(intentFor<SomeOtherActivity>("id" to 5).singleTop())
// 传参
// startActivity<SomeOtherActivity>(
//     "id" to 5,
//     "city" to "Denpasar"
///////////////////////////////////////////////////////////////////////////

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) =
    IntentFun.internalStartActivity(this, T::class.java, params)

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
    IntentFun.internalStartActivity(requireActivity(), T::class.java, params)

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) =
    IntentFun.internalStartActivityForResult(this, T::class.java, requestCode, params)


inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) =
    startActivityForResult(
        IntentFun.createIntent(requireActivity(), T::class.java, params),
        requestCode
    )
