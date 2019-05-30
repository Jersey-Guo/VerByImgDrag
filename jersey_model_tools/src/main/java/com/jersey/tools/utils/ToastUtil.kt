package com.jersey.tools.utils

import android.content.Context
import android.text.TextUtils
import android.widget.Toast

/**
 * 用于防止toast多次弹出
 */

object ToastUtil {
    private var oldMsg: String? = null
    internal var toast: Toast? = null
    private var oneTime: Long = 0
    private var twoTime: Long = 0

    fun showToast(context: Context, s: String) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT)
            toast!!.show()
            oneTime = System.currentTimeMillis()
        } else {
            twoTime = System.currentTimeMillis()
            if (TextUtils.equals(oldMsg, s)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast!!.show()
                }
            } else {
                oldMsg = s
                toast!!.setText(s)
                toast!!.show()
            }
        }
        oneTime = twoTime
    }


    fun showToast(context: Context, resId: Int) {
        showToast(context, context.getString(resId))
    }
}
