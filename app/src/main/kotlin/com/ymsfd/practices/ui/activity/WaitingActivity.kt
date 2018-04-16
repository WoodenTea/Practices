package com.ymsfd.practices.ui.activity

import android.os.Bundle
import com.ymsfd.practices.R

/**
 * Description:
 * Author: WoodenTea
 * Date: 2018/4/12
 */
class WaitingActivity : BaseActivity() {
    override fun startup(savedInstanceState: Bundle?): Boolean {
        if (!super.startup(savedInstanceState)) {
            return false
        }

        setContentView(R.layout.activity_waiting)
        enableToolbarUp(true)

        return true
    }
}