package com.jerse.verify

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.jersey.tools.utils.ToastUtil
import com.jersey.tools.view.CustomVerImageView

class MainActivity : AppCompatActivity() {

    private var mMoveImageView: CustomVerImageView? = null

    private var mVerImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mMoveImageView =findViewById(R.id.activity_ver_move_circle)
        mVerImageView = findViewById(R.id.activity_ver_circle);
        setMoveImageView()
    }
    private fun setMoveImageView(){
        mMoveImageView?.setOnVerCallBack(mVerImageView,object : CustomVerImageView.OnVerCallback {
            override fun onSuccess() {
                ToastUtil.showToast(baseContext,"verify is success")
            }

            override fun onFailure() {
                ToastUtil.showToast(baseContext,"verify is failure")
            }
        })
    }
}
