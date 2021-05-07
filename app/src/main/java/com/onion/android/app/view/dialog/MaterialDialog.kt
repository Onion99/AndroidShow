package com.onion.android.app.view.dialog

import android.app.Dialog
import android.content.Context


/**
 * typealias 关键字的作用就是将一个类映射到另一个类上面，或者可以说是给一个类起个别名。
 * https://blog.csdn.net/qq_34589749/article/details/103646995
 */
typealias DialogCallback = (MaterialDialog) -> Unit

class MaterialDialog(
    val windowContext: Context
    ): Dialog(windowContext){

}