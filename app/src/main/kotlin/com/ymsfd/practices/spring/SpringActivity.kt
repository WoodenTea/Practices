package com.ymsfd.practices.spring

import android.content.Intent
import android.os.Bundle
import com.ymsfd.practices.R
import com.ymsfd.practices.ui.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_spring.*

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/4/19
 */
class SpringActivity : BaseActivity() {
    override fun _onCreate(savedInstanceState: Bundle?): Boolean {
        if (!super._onCreate(savedInstanceState)) {
            return false
        }

        setContentView(R.layout.activity_spring)
        enableToolbarUp(true)
        positionActivityButton.setOnClickListener { startActivity(Intent(this, PositionActivity::class.java)) }
        rotationActivityButton.setOnClickListener { startActivity(Intent(this, RotationActivity::class.java)) }
        scaleActivityButton.setOnClickListener { startActivity(Intent(this, ScaleActivity::class.java)) }

        return true
    }
}