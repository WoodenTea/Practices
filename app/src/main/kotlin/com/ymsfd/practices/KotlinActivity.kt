package com.ymsfd.practices

import android.os.Bundle
import android.widget.TextView
import com.ymsfd.practices.ui.activity.BaseActivity

/**
 * Created by WoodenTea.
 * Date: 27/01/2017
 * Time: 21:42
 */
class KotlinActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        enableToolbarHomeButton(true)
        val textView: TextView = findViewById(R.id.name) as TextView
        textView.text = getString(R.string.app_name)

        val expr: Expr = Expr.Const(0.01)
        eval(expr)
    }

    fun eval(expr: Expr): Double = when (expr) {
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