package com.wyboltech.liquidsalarycalc

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setListeners()

    }

    private fun calculateLiquidSalary() {
        val grossSalaryValue = et_gross_salary.text.toString()
        var dependentNumberValue = et_dependent_number.text.toString()
        var otherDiscountValue = et_other_discount.text.toString()
        var inss = "0.0"
        var irrf = "0.0"
        val calculationBasis: Double

        if (!(grossSalaryValue.isBlank() && grossSalaryValue.toDouble() >= 0 && dependentNumberValue.isNotBlank())) {

            inss = calculateINSS(grossSalaryValue.toDouble())

            if (dependentNumberValue == ""){
                dependentNumberValue = "0"
            }
            calculationBasis =
                grossSalaryValue.toDouble() - inss.toDouble() - (dependentNumberValue.toInt() * 189.59)
            irrf = calculateIRRF(calculationBasis)

            if(otherDiscountValue.isBlank() || otherDiscountValue.toDouble() < 0){
                otherDiscountValue = "0.0"
            }


        } else {
            Toast.makeText(applicationContext, "Campos incorretos, favor preencher com valores válidos!", Toast.LENGTH_LONG).show()
        }

        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra(GROSS_SALARY, grossSalaryValue)
        intent.putExtra(IRRF_VALUE, irrf)
        intent.putExtra(INSS_VALUE, inss)
        intent.putExtra(OTHER_DISCOUNT, otherDiscountValue)
        startActivity(intent)
    }

    private fun calculateINSS(grossSalary: Double): String {

        var inssValue = 0.0

        when {
            grossSalary <= 1045.0 -> {
                inssValue = grossSalary * 7.5 / 100
            }
            grossSalary in 1045.01..2089.60 -> {
                inssValue = grossSalary * 9.0 / 100 - 15.67
            }
             grossSalary in 2089.61..3134.40  -> {
                inssValue = grossSalary * 12.0 / 100 - 78.36
            }
            grossSalary in 3134.41..6101.06 -> {
                inssValue = grossSalary * 14.0 / 100 - 141.05
            }
            grossSalary > 6101.06 -> {
                inssValue = 713.10
            }
        }
        return inssValue.toString()
    }

    private fun calculateIRRF(calculationBase: Double): String {
        var irrfValue = 0.0

        when {
            calculationBase <= 1903.98 -> {
                return irrfValue.toString()
            }
            calculationBase in 1903.99..2826.65 -> {
                irrfValue = calculationBase * 7.5 /100 - 142.8
            }
            calculationBase in 2826.66..3751.05 -> {
                irrfValue = calculationBase * 15.0 /100 - 354.8
            }
            calculationBase in 3751.06.. 4664.68 -> {
                irrfValue = calculationBase * 22.5 /100 - 636.13
            }
            calculationBase > 4664.68 -> {
                irrfValue = calculationBase * 27.5/100 - 869.36
            }
        }
        return irrfValue.toString()
    }

    private fun setListeners() {
        btn_calculate.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_calculate -> {
                calculateLiquidSalary()
            }
        }
    }
}