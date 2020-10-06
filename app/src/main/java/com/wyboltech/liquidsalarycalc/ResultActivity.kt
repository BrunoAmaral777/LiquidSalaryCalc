package com.wyboltech.liquidsalarycalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        setListeners()
        val intent = intent
        var grossSalaryResValue = intent.getStringExtra(GROSS_SALARY)
        var inssValue = intent.getStringExtra(INSS_VALUE)
        var irrfValue = intent.getStringExtra(IRRF_VALUE)
        var otherDiscounts = intent.getStringExtra(OTHER_DISCOUNT)
        val netSalary = grossSalaryResValue!!.toDouble() - inssValue!!.toDouble() - irrfValue!!.toDouble() - otherDiscounts!!.toDouble()
        val discountsSum = ((inssValue!!.toDouble() + irrfValue!!.toDouble() + otherDiscounts!!.toDouble()) * 100) / grossSalaryResValue.toDouble()

        grossSalaryResValue = "%.2f".format(grossSalaryResValue.toDouble())
        tv_res_calc_gross_sal.text = grossSalaryResValue

        inssValue = "-" + "%.2f".format(inssValue.toDouble())
        tv_res_calc_inss.text = inssValue

        irrfValue = "-" + "%.2f".format(irrfValue.toDouble())
        tv_res_calc_irrf.text = irrfValue
        

        otherDiscounts = "-" + "%.2f".format(otherDiscounts.toDouble())
        tv_res_calc_other_disc.text = otherDiscounts

        val netSalaryFormatted = "%.2f".format(netSalary)
        tv_res_calc_net_salary.text = netSalaryFormatted

        val discountsSumFormatted = "%.2f".format(discountsSum) + " %"
        tv_res_calc_discounts.text = discountsSumFormatted

    }

    private fun setListeners(){

        btn_result_back.setOnClickListener{
            finish()
        }
    }
}