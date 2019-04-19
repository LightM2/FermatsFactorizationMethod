package com.svitlanamozharovska.fermatsfactorizationmethod

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException
import android.R.integer
import java.math.BigInteger


class MainActivity : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tResult: TextView = findViewById(R.id.textView4)



        button.setOnClickListener {
            val numb = editText2.text.toString()
            try {
                val number = numb.toInt()
                val aAndB = fermatMethod(number)
                var result = "    $number = "
                for (i in 0..(aAndB.size-1)){
                    when (i) {
                        0 -> result += aAndB[i]
                        else -> result += " * ${aAndB[i]}"
                    }
                }
                tResult.text = result
            }catch (e:NumberFormatException){
                println(numb)
                println(e.message)
            }
        }
    }


    fun fermatMethod(number: Int):MutableList<String>{
        val n = number.toDouble()
        val x:Int = Math.ceil(Math.sqrt(n)).toInt()
        var s = x
        var aAndB = mutableListOf<String>()

        for (i in 0..number){
            val y = Math.pow(s.toDouble() + i,2.0) - n
            if (Math.sqrt(y)==Math.ceil(Math.sqrt(y))){
                val a = s+i + Math.sqrt(y).toInt()
                val b = s+i - Math.sqrt(y).toInt()
                val bigIntegerA = BigInteger.valueOf(a.toLong())
                val probablePrimeA = bigIntegerA.isProbablePrime(Math.log(a.toDouble()).toInt())
                val bigIntegerB = BigInteger.valueOf(b.toLong())
                val probablePrimeB = bigIntegerB.isProbablePrime(Math.log(b.toDouble()).toInt())
                when{
                    probablePrimeA and probablePrimeB -> {
                        aAndB.add(a.toString())
                        aAndB.add(b.toString())
                    }
                    probablePrimeA.not() -> {
                        aAndB = fermatMethod(a)
                        if (probablePrimeB){
                            aAndB.add(b.toString())
                        }
                    }
                    probablePrimeB.not() -> {
                        aAndB.add(a.toString())
                        val aAndBb = fermatMethod(b)
                        aAndB.add(aAndBb[0])
                        aAndB.add(aAndBb[1])
                    }

                }
                return aAndB
            }
        }
        return aAndB
    }
}
