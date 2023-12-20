package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {


    private var tvInput : TextView? = null


    //마지막에 숫자를 입력했는지
    var lastNumeric : Boolean = false
    //마지막에 소숫점을 입력했는지
    var lastDot : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tvInput = findViewById(R.id.tvInput)
    }


    //버튼 눌렀을때 각 버튼에 해당하는 텍스트가져오게
    fun onDigit(view : View){
        tvInput?.append((view as Button).text)  // view가 Button일 경우에 텍스트를 가져오고싶다
        lastNumeric = true
        lastDot = false
    }

    //Clear버튼 눌렀을때 삭제되게(아무것도 안보이게)
    fun onClear(view : View){
        tvInput?.text = ""
    }


    // 점
    // 마지막 값이 점이 아닐때만 실행가능하도록
    // 따라서 마지막에 숫자를 입력한경우와 마지막에 소숫점을 입력한경우를 나눠서 생각
    fun onDecimalPoint(view : View){
        // 마지막에 입력한값이 숫자이고, 소숫점이 아닌경우라면 if문 실행
        if(lastNumeric && !lastDot){
            tvInput?.append(".")    //. 찍어줌
            lastNumeric = false  //플래그(flag)
            lastDot = true
        }
    }


    fun onOperator(view: View){
        //text가 비어있는지 아닌지 확인
        tvInput?.text?.let {

            // text가 비어있지 않다면 if문 실행
            // if문으로 마지막 입력값이 숫자인지 확인(마지막 입력값이 숫자여야 연산할수있기 때문) && 문자열에 연산자가 입력됐는지도 확인(연산자가 입력되야지 연산 가능하기 때문)
            // 마지막에 숫자를 입력했고, 기호가 앞에 포함된게 아니라면 if문 실행
            // it.toString()를 통해 연산자 한번만 입력가능하도록
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false     //플래그(flag)
                lastDot = false
            }
        }
    }


    // = 버튼눌렀을때 계산되도록(연산)
    fun onEqual(view : View){
        // = 버튼을 눌렀을때 마지막값이 숫자면 if문 실행
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""


            try {
                //tvValue가 -로 시작하는지 확인
                //tvValue가 -로 시작하면 if문 실행
                if(tvValue.startsWith("-")){
                    //prefix변수가 -이면, substring을 사용해서 인덱스값이 1인값부터 시작 (ex) -99 -> 99)
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }


                // if문으로 -가 문자열에 포함되어있는지 확인
                // -가 문자열에 포함되어있으면 if문 실행
                if(tvValue.contains("-")){

                    val splitValue = tvValue.split("-") // -로 문자 나누기
                    // ex) 99-1
                    var one = splitValue[0] //ex) 99
                    var two = splitValue[1] //ex) 1

                    // prefix변수가 비어있지않으면 if문 실행
                    if(prefix.isNotEmpty()){
                        // 첫번째 입력값은 -가 될수있기 때문에, 첫번째 입력값에는 -붙여줌
                        one = prefix + one
                    }

                    var result = one.toDouble() - two.toDouble()            //Double(실수)형태로 적용했기때문에 = 기호 누르면 51.0 이런식으로 나옴
                    tvInput?.text =removeZeroAfterDot(result.toString())   // 이두줄을 한줄로 바꾸려면 tvInput?.text = (one.toDouble() - two.toDouble()).toString()  으로 바꿀수있음


                    // +가 문자열에 포함되어있으면 if문 실행
                }else if(tvValue.contains("+")){

                    val splitValue = tvValue.split("+") // +로 문자 나누기
                    // ex) 99-1
                    var one = splitValue[0] //ex) 99
                    var two = splitValue[1] //ex) 1

                    // prefix변수가 비어있지않으면 if문 실행
                    if(prefix.isNotEmpty()){
                        // 첫번째 입력값은 -가 될수있기 때문에, 첫번째 입력값에는 -붙여줌
                        one = prefix + one
                    }

                    var result = one.toDouble() + two.toDouble()
                    tvInput?.text =removeZeroAfterDot(result.toString())


                    // /가 문자열에 포함되어있으면 if문 실행
                }else if(tvValue.contains("/")){

                    val splitValue = tvValue.split("/") // /로 문자 나누기
                    // ex) 99-1
                    var one = splitValue[0] //ex) 99
                    var two = splitValue[1] //ex) 1

                    // prefix변수가 비어있지않으면 if문 실행
                    if(prefix.isNotEmpty()){
                        // 첫번째 입력값은 -가 될수있기 때문에, 첫번째 입력값에는 -붙여줌
                        one = prefix + one
                    }

                    var result = one.toDouble() / two.toDouble()
                    tvInput?.text =removeZeroAfterDot(result.toString())


                    // *가 문자열에 포함되어있으면 if문 실행
                }else if(tvValue.contains("*")){

                    val splitValue = tvValue.split("*") // *로 문자 나누기
                    // ex) 99-1
                    var one = splitValue[0] //ex) 99
                    var two = splitValue[1] //ex) 1

                    // prefix변수가 비어있지않으면 if문 실행
                    if(prefix.isNotEmpty()){
                        // 첫번째 입력값은 -가 될수있기 때문에, 첫번째 입력값에는 -붙여줌
                        one = prefix + one
                    }

                    var result = one.toDouble() * two.toDouble()
                    tvInput?.text =removeZeroAfterDot(result.toString())

                }


            }catch (e: ArithmeticException){    //0으로 나누거나 산술적으로 계산불가한 경우를 예외로 인식해서 에러를 잡음
                e.printStackTrace()
            }
        }
    }



    // 정수일땐 뒤에붙는 .0을 제거 (35.0 -> 35)
    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains((".0"))){
            value = result.substring(0, result.length-2)
        }
        return value
    }


    // 숫자앞에 기호들이 포함됐는지 확인하기위한 메소드
    private fun isOperatorAdded(value : String) : Boolean {
        //isOperatorAdded문자열이 특정 문자로 시작하는지 확인 (startsWith사용)
        //-로 시작하면 무시(-는 맨앞에 쓰일수없게함)
        return if(value.startsWith("-")) {
            false
        }else{
            value.contains("/") || value.contains("*") || value.contains("+") || value.contains("-")
        }
    }
}