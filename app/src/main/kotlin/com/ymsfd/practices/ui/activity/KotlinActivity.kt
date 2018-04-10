package com.ymsfd.practices.ui.activity

import android.os.Bundle
import android.widget.TextView
import com.ymsfd.practices.R

/**
 * Created by WoodenTea.
 * Date: 27/01/2017
 * Time: 21:42
 */
class KotlinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        enableToolbarUp(true)
        val textView: TextView = findViewById(R.id.name)
        textView.text = getString(R.string.app_name)

        val expr: Expr = Expr.Const(0.01)
        eval(expr)
    }

    private fun eval(expr: Expr): Double = when (expr) {
        is Expr.Const -> {
            expr.number
        }
        is Expr.Sum -> eval(expr.e1) + eval(expr.e2)
        Expr.NotANumber -> Double.NaN
    }

    sealed class Expr {
        class Const(val number: Double) : Expr()
        class Sum(val e1: Expr, val e2: Expr) : Expr()
        object NotANumber : Expr()
    }
}