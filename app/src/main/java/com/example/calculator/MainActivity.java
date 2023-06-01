package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView resultTV;
    private TextView calculateTV;
    private Integer intPushNumber;
    private Integer integerBit = 0;
    private Double pushNumber;
    private Double resultNumber;
    private String pushOperator;
    private String showText = "";
    private String pushShowText = "";
    private String originalNum = "";
    private boolean whetherDot = false;
    private boolean whetherHaveDot = false;
    private boolean whetherHavePercent = false;
    LinkedList<Double> numberList = new LinkedList<>();
    LinkedList<String> operatorList = new LinkedList<>();//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //获取textView
        resultTV = findViewById(R.id.resultTV);
        calculateTV = findViewById(R.id.calculateTV);

        //为每个按钮都注册了点击监听器
        findViewById(R.id.btn_ac).setOnClickListener(this);
        findViewById(R.id.btn_delete).setOnClickListener(this);
        findViewById(R.id.btn_percent).setOnClickListener(this);
        findViewById(R.id.btn_divide).setOnClickListener(this);
        findViewById(R.id.btn_plus).setOnClickListener(this);
        findViewById(R.id.btn_minus).setOnClickListener(this);
        findViewById(R.id.btn_add).setOnClickListener(this);
        findViewById(R.id.btn_equal).setOnClickListener(this);
        findViewById(R.id.btn_negative).setOnClickListener(this);
        findViewById(R.id.btn_point).setOnClickListener(this);
        findViewById(R.id.btn_zero).setOnClickListener(this);
        findViewById(R.id.btn_one).setOnClickListener(this);
        findViewById(R.id.btn_two).setOnClickListener(this);
        findViewById(R.id.btn_three).setOnClickListener(this);
        findViewById(R.id.btn_four).setOnClickListener(this);
        findViewById(R.id.btn_five).setOnClickListener(this);
        findViewById(R.id.btn_six).setOnClickListener(this);
        findViewById(R.id.btn_seven).setOnClickListener(this);
        findViewById(R.id.btn_eight).setOnClickListener(this);
        findViewById(R.id.btn_nine).setOnClickListener(this);

        // 获取GridLayout，获取设置好的列数值
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        int columnCount = gridLayout.getColumnCount();

        // 获取使用设备的界面宽度，获取权重分配后每列的宽度
         WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        // 方法过期了
         int width = (wm.getDefaultDisplay().getWidth())/columnCount-40;

        //给每一个Button设置与宽度相同的高度值
//        原写法
//        Button btn_ac = findViewById(R.id.btn_ac);
//        btn_ac.setHeight(width);

        //现写法，没有新建对象，不占用内存？
        ((Button)findViewById(R.id.btn_ac)).setHeight(width);
        ((Button)findViewById(R.id.btn_delete)).setHeight(width);
        ((Button)findViewById(R.id.btn_percent)).setHeight(width);
        ((Button)findViewById(R.id.btn_divide)).setHeight(width);
        ((Button)findViewById(R.id.btn_plus)).setHeight(width);
        ((Button)findViewById(R.id.btn_minus)).setHeight(width);
        ((Button)findViewById(R.id.btn_add)).setHeight(width);
        ((Button)findViewById(R.id.btn_equal)).setHeight(width);
        ((Button)findViewById(R.id.btn_negative)).setHeight(width);
        ((Button)findViewById(R.id.btn_point)).setHeight(width);
        ((Button)findViewById(R.id.btn_zero)).setHeight(width);
        ((Button)findViewById(R.id.btn_one)).setHeight(width);
        ((Button)findViewById(R.id.btn_two)).setHeight(width);
        ((Button)findViewById(R.id.btn_three)).setHeight(width);
        ((Button)findViewById(R.id.btn_four)).setHeight(width);
        ((Button)findViewById(R.id.btn_five)).setHeight(width);
        ((Button)findViewById(R.id.btn_six)).setHeight(width);
        ((Button)findViewById(R.id.btn_seven)).setHeight(width);
        ((Button)findViewById(R.id.btn_eight)).setHeight(width);
        ((Button)findViewById(R.id.btn_nine)).setHeight(width);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {

        String inputText = ((TextView)v).getText().toString();

        // 把用户输入的所有元素 分为【操作符】和【数字】2个 LinkedList有序数组
        // 遍历操作符 list，如果遇到乘除，所在顺序n，计算数字 list n 以及 n+1
        // 再将值赋给 数字 List n，移除 n+1
        // 持续遍历，直到 操作符list 只剩 + —
        switch (inputText) {

            // 点击了AC，全部清除按钮
            case "AC":
                calculateTV.setText("");
                pushNumber = null;
                integerBit = 0;
                pushOperator = "";
                showText = "";
                pushShowText = "";
                numberList.clear();
                operatorList.clear();
                resultTV.setText("");
                resultNumber = null;
                whetherDot = false;
                whetherHaveDot = false;
                whetherHavePercent = false;
                break;

            // 点击了加减乘除按钮
            case "+":
            case "−":
            case "×":
            case "÷":
                // 这种情况是 想使用运算完的结果继续运算
                // 运算完后resultNumber有值，数值列表和运算符列表都已经被清空了
                if (resultNumber != null & operatorList.size() == 0 & numberList.size() == 0){

                    Log.d(String.valueOf(resultNumber), "onClick: 计算结果继续运算了");

                    //if语句限制resultNumber的长度，大于9位时仅保留9位。
//                    if (equalMathRound(resultNumber)){
//                        //没有实现
//                    }
                    pushNumber = resultNumber;
                    if (equalMathRound(pushNumber)){
                        showText = String.valueOf(Math.round(pushNumber));
                    } else {
                        showText = String.valueOf(pushNumber);
                    }
                    pushShowText = "";
                    numberList.add(pushNumber);
                    pushNumber = null;
                    integerBit = 0;
                    resultTV.setText("");
                }else if (calculateTV.getText()==""){
                    // 计算模块获取不到任何输入值
                    // 点击 加减乘除 不进行任何操作
                    break;
                } else {
                    if (pushNumber != null){
                        if (equalMathRound(pushNumber)){
                            pushNumber = (double) Math.round(pushNumber);
                        }
                        showText += pushShowText;
                        pushShowText = "";
                        integerBit = 0;
                        numberList.add(pushNumber);
                        Log.d(String.valueOf(numberList), "onClick: NumberList");
                        pushNumber = null;
                    }
                }
                pushOperator = inputText;
                whetherHaveDot = false;
                whetherHavePercent = false;
                calculateTV.setText(showText+pushOperator);
                break;

            // 点击了正负值按钮
            case "±":
                Log.d(String.valueOf(pushNumber), "onClick: pushNumber");
                Log.d(pushShowText, "onClick: pushShowText");
                if (pushNumber == null){
                    break;
                }
                // 计算模块获取不到任何输入值
                // 点击 加减乘除 不进行任何操作
                if(calculateTV.getText()!=""){
                    calculateTV.setText(showText);
                    pushNumber = -pushNumber;
                    intPushNumber = -intPushNumber;

                    // 尝试直接对pushShowText进行更改
                    // 添加判断语句
                    // 如果显示负数，删除负号，而非2个负号
                    if (pushNumber > 0){
                        StringBuilder sb_pushShowText = new StringBuilder(pushShowText);
                        sb_pushShowText.deleteCharAt(0);
                        pushShowText = String.valueOf(sb_pushShowText);
                    } else if (pushNumber < 0){
                        pushShowText = "-"+pushShowText;
                    }

                    // 可以运行的，但是交互做的不太好，会出现每三位一逗号的设定不成立
//                    if (equalMathRound(pushNumber)){
//                        calculateTV.setText(showText+Math.round(pushNumber));
//                    } else {
//                        calculateTV.setText(showText+pushNumber);
//                    }

                    calculateTV.setText(showText + pushShowText);
                }
                break;

            // 点击了百分号按钮
            case "%":
                if (pushNumber != null){
                    pushNumber = pushNumber/100;
                    pushShowText += "%";
                    calculateTV.setText(showText+pushShowText);
                    whetherHavePercent = true;
                    break;
                }
            // 点击了后退按钮
            case "⬅︎":
                // 当pushNumber == null时，不进行任何操作
                if (pushNumber == null){
                    break;
                }
                // 首先判断什么情况可以进行后退操作
                // 即 整数时：至少有一位，小数时：至少有一位
                int length_pushNumber = pushNumber.toString().length();
                int length_intPushNumber = (Math.round(pushNumber)+"").length();

                Log.d(String.valueOf(length_intPushNumber), "onClick: ");
                Log.d(String.valueOf(length_pushNumber), "onClick: ");

//                if (equalMathRound(pushNumber) & length_intPushNumber > 1){
//                    Log.d(String.valueOf(pushNumber), "onClick: 现在pushNumber的值");
//                    //获取到pushNumber的值，转换String类型，删除最后一个字节
//
//                    //如果没有用户输入的是整数
//                    if (equalMathRound(pushNumber)){
//                        String stringPushNumber = String.valueOf(Math.round(pushNumber));
//                        StringBuilder stringBuffer1 = new StringBuilder(stringPushNumber);
//                        stringBuffer1 = stringBuffer1.deleteCharAt(stringPushNumber.length()-1);
//                        pushNumber = Double.parseDouble(String.valueOf(stringBuffer1));
//                        Log.d(String.valueOf(pushNumber), "onClick: 用户输入整数，后退操作后pushNumber的值");
//                    }
//                } else if (equalMathRound(pushNumber) & length_intPushNumber == 1) {
//                    pushNumber = 0.0;
//                    Log.d(String.valueOf(pushNumber), "onClick: 用户输入值长度为1，后退操作后pushNumber的值");
//                } else if (!equalMathRound(pushNumber)) {
//                    Log.d(String.valueOf(pushNumber), "onClick: 用户输入的小数，准备进行带小数点值的后退运算");
//                    String stringPushNumber = String.valueOf(pushNumber);
//                    StringBuilder stringBuffer1 = new StringBuilder(stringPushNumber);
//                    stringBuffer1 = stringBuffer1.deleteCharAt(stringPushNumber.length()-1);
//                    pushNumber = Double.parseDouble(String.valueOf(stringBuffer1));
//                    Log.d(String.valueOf(pushNumber), "onClick: 后退操作后pushNumber的值");
//                }

                if (equalMathRound(pushNumber)){
                    Log.d(pushNumber.toString(), "onClick: pushNumber 为整数");
                    if (length_intPushNumber > 1){
                        Log.d(pushNumber.toString(), "onClick: pushNumber 非个位数");
                        String stringPushNumber = String.valueOf(Math.round(pushNumber));
                        StringBuilder stringBuffer1 = new StringBuilder(stringPushNumber);
                        stringBuffer1 = stringBuffer1.deleteCharAt(stringPushNumber.length()-1);
                        pushNumber = Double.parseDouble(String.valueOf(stringBuffer1));
                        Log.d(String.valueOf(pushNumber), "onClick: pushNumber 为整数，后退操作后pushNumber的值");
                    } else if (length_intPushNumber == 1) {
                        Log.d(String.valueOf(pushNumber), "onClick: pushNumber 为整数，但为 个位数");
                        pushNumber = 0.0;
                        Log.d(String.valueOf(pushNumber), "onClick: pushNumber 为整数，后退操作后pushNumber的值");
                    }
                    pushShowText = String.valueOf(Math.round(pushNumber));
                } else {
                    Log.d(String.valueOf(pushNumber), "onClick: pushNumber 为小数");
                    String stringPushNumber = String.valueOf(pushNumber);
                    StringBuilder stringBuffer1 = new StringBuilder(stringPushNumber);
                    stringBuffer1 = stringBuffer1.deleteCharAt(stringPushNumber.length()-1);
                    pushNumber = Double.parseDouble(String.valueOf(stringBuffer1));
                    Log.d(String.valueOf(pushNumber), "onClick: 后退操作后pushNumber的值");
                    if (equalMathRound(pushNumber)){
                        pushShowText = String.valueOf(Math.round(pushNumber));
                    } else {
                        pushShowText = String.valueOf(pushNumber);
                    }
                }
                calculateTV.setText(showText+pushShowText);
                integerBit -= 1;
                break;
            //
            case ".":
                if (pushNumber != null){
                    if (!whetherHaveDot){
                        if (!whetherDot){
                            calculateTV.setText(showText+pushShowText+".");
                            whetherDot = true;
                            whetherHaveDot = true;
                        }
                    }
                }
                break;
            //
            case "=":

                if (numberList.size() == 0 & operatorList.size() == 0 & pushNumber != null){
                    resultNumber = pushNumber;
                    if (equalMathRound(resultNumber)){
                        resultTV.setText(String.valueOf(Math.round(resultNumber)));
                    } else {
                        resultTV.setText(String.valueOf(resultNumber));
                    }
                }
                if (numberList.size()!= 0 | operatorList.size()!=0 ){
                    // 数字列表 & 操作符列表为空，点等号，不进行任何操作

                    // 点击等号后运行的程序
                    // 如果pushNumber不为空
                    // 就是还存在 一个数字，等着下一个 点击运算符的操作 来推进数字列表
                    if (pushNumber != null){
                        numberList.add(pushNumber);
                    }

                    // 遍历操作符列表
                    // 每当发现 乘除 存在
                    // 在数字列表寻找，执行乘除运算
                    // 把运算结果重新赋值给左边的int，并从数字列表中移除右边的。
//                    for (int i = operatorList.size(); i >= 1; i--) {
//                        double num1 = numberList.get(i-1);
//                        double num2 = numberList.get(i);
//                        if (Objects.equals(operatorList.get(i-1), "×")){
//                            numberList.set(i-1, (double) (num1*num2));
//                            numberList.remove(i);
//                            operatorList.remove(i-1);
//                            Log.d("numberList", "onClick: numberList");
//                            Log.d(String.valueOf(operatorList), "onClick: operatorList");
//                        } else if (Objects.equals(operatorList.get(i-1), "÷")) {
//                            numberList.set(i-1, num1/num2);
//                            numberList.remove(i);
//                            operatorList.remove(i-1);
//                            Log.d(String.valueOf(numberList), "onClick: numberList");
//                            Log.d(String.valueOf(operatorList), "onClick: operatorList");
//                        }
//                    }

                    for (int i = 0; i <= operatorList.size()-1; i++) {
                        double num1 = numberList.get(i);
                        double num2 = numberList.get(i+1);
                        if (Objects.equals(operatorList.get(i), "÷")) {
                            numberList.set(i, num1/num2);
                            numberList.set(i+1,1.0);
                            operatorList.set(i,"×");
                            Log.d("onClick: numberList", String.valueOf(numberList));
                            Log.d("onClick: operatorList", String.valueOf(operatorList));
                        }
                    }


                    for (int i = operatorList.size(); i >= 1; i--) {
                        double num1 = numberList.get(i-1);
                        double num2 = numberList.get(i);
                        if (Objects.equals(operatorList.get(i-1), "×")){
                            numberList.set(i-1, (double) (num1*num2));
                            numberList.remove(i);
                            operatorList.remove(i-1);
                            Log.d("onClick: numberList", String.valueOf(numberList));
                            Log.d("onClick: operatorList", String.valueOf(operatorList));
                        }
                    }

                    // 如果运算符列表 为空了
                    // 将数字列表唯一的值 赋值给 resultNumber 即结果
                    if (operatorList.size() == 0){
                        resultNumber = numberList.get(0);
                    }

//                     上面的乘除没问题
//                     但是如果遇到 负数，计算就会有问题
//            for (int i = operatorList.size(); i >= 1; i--) {
//                int num1 = numberList.get(i-1);
//                int num2 = numberList.get(i);
//                if (Objects.equals(operatorList.get(i-1), "+")){
//                    numberList.set(i-1,num2+num1);
//                    numberList.remove(i);
//                    operatorList.remove(i-1);
//                    Log.d(String.valueOf(numberList), "onClick: ");
//                    Log.d(String.valueOf(operatorList), "onClick: ");
//                } else if (Objects.equals(operatorList.get(i-1), "−")) {
//                    numberList.set(i-1,num2-num1);
//                    numberList.remove(i);
//                    operatorList.remove(i-1);
//                    Log.d(String.valueOf(numberList), "onClick: ");
//                    Log.d(String.valueOf(operatorList), "onClick: ");
//                }
//            }

                    // 新思路
                    // 乘除运算结束后，进行加减运算
                    // 用一个大的表达式，不对数字列表和运算符列表remove
                    for (int i = 0; i < operatorList.size(); i++) {

                        //第一次运行的时候，是numberList里的前两个数进行加减，然后赋值给resultNumber
                        //之后再运行的时候，就是resultNumber和numberList的后一个数进行加减
                        if (i == 0){
                            if (Objects.equals(operatorList.get(i), "+")){
                                resultNumber = numberList.get(i) + numberList.get(i+1);
                            } else {
                                resultNumber = numberList.get(i) - numberList.get(i+1);
                            }
                        } else {
                            if (Objects.equals(operatorList.get(i), "+")){
                                resultNumber += numberList.get(i+1);
                            } else {
                                resultNumber -= numberList.get(i+1);
                            }
                        }
                    }

                    String resultShowText ="";

                    // 判断如果resultNumber有小数，就显示小数
                    // 如果resultNumber是整数，没有小数，就不显示小数
                    if (equalMathRound(resultNumber)){
                        resultShowText = String.valueOf(Math.round(resultNumber));
                        // 获取resultNumber一共有几位
                        int bit_resultNumber = resultShowText.length();
                        if ((bit_resultNumber/3) >= 1){
                            StringBuilder stringBuffer = new StringBuilder(String.valueOf(Math.round(resultNumber)));
                            for (int i = 1; i <= (bit_resultNumber-1)/3 ; i++) {
                                stringBuffer.insert(bit_resultNumber-i*3,",");
                                resultShowText = String.valueOf(stringBuffer);
                            }
                        }
                    } else {
                        // 实现逻辑，例如【1,222.3456】
//                        resultShowText = String.valueOf(Math.round(resultNumber));
//                        Log.d(resultShowText, "onClick: ");
////                        resultShowText = String.valueOf(resultNumber);
//                        int bit_intResultNumber = resultShowText.length();
//                        Log.d(String.valueOf(bit_intResultNumber), "onClick: ");
////                        int bit_resultNumber = resultShowText.length();
//                        if ((bit_intResultNumber/3) >= 1){
//                            StringBuilder stringBuffer = new StringBuilder(String.valueOf(resultNumber));
//                            for (int i = 1; i <= (bit_intResultNumber-1)/3 ; i++) {
//                                Log.d("TAG", "onClick: 运行了for循环");
//                                stringBuffer.insert(bit_intResultNumber-i*3,",");
//                                resultShowText = String.valueOf(stringBuffer);
//                            }
//                        }
                        resultShowText = String.valueOf(resultNumber);
                    }

//                    // 获取pushNumber一共有几位
//                    int bit_pushNumber = addNum.length();
//                    // 根据位数设置循环次数，每三位添加 ","
//                    if ((bit_pushNumber/3) >= 1){
//                        StringBuilder stringBuffer = new StringBuilder(String.valueOf(Math.round(pushNumber)));
//                        for (int i = 1; i <= (bit_pushNumber-1)/3 ; i++) {
//                            stringBuffer.insert(bit_pushNumber-i*3,",");
//                            pushShowText = String.valueOf(stringBuffer);
//                        }
//                    }

//                    String resultShowText = String.valueOf(resultNumber);
                    resultTV.setText(resultShowText);
//                    calculateTV.setText("");
                    pushNumber=null;
                    pushOperator="";
                    showText="";
                    numberList.clear();
                    operatorList.clear();
                }
                break;


            // 如果点击值为1，2，3，4，5，6，7，8，9，0
            default:

                Log.d(String.valueOf(resultNumber), "onClick: ");
                Log.d(String.valueOf(operatorList), "onClick: ");
                Log.d(String.valueOf(pushNumber), "onClick: ");
                Log.d(showText, "onClick: ");
                Log.d(pushShowText, "onClick: ");

                //如果resultNumber不为空，但是operatorList为空，pushNumber不为空，清空resultNumber
                if (resultNumber != null & operatorList.size() == 0 & pushNumber == null){
                    resultNumber = null;
                    pushShowText = "";
                }

                // 如果已经点了百分号，就不能再在后面点击数字了
                if (whetherHavePercent){
                    // 不进行任何操作
                    break;
                }

                // 如果 目前的数字位数 小于等于 8位，即还有添加的余地
                // 如果已经到达 9位了，就不进行任何操作
                if (integerBit <= 8){
                    if(whetherDot){
                        if (pushNumber != null){
                            pushNumber = Double.valueOf(Math.round(pushNumber) + "." + inputText);
//                        pushNumber = Double.valueOf("0" + "." + inputText);
                        }
                        pushShowText += "." + inputText;
                        calculateTV.setText(showText + pushShowText);
                        integerBit += 1;
                        whetherDot = false;
                        break;
                    } else {
                        double doubleInputText = Double.parseDouble(inputText);
                        intPushNumber = (int)doubleInputText;
                        resultTV.setText("");
                        if (pushOperator != null & !Objects.equals(pushOperator, "")){
                            operatorList.add(pushOperator);
                            showText += pushOperator;
                            pushShowText += inputText;
                            calculateTV.setText(showText+pushShowText);
                            pushNumber = doubleInputText;
                            pushOperator="";
                            integerBit += 1;
                            break;
                        }

                        // 如果 pushNumber 为空
                        if (pushNumber == null){
                            if (doubleInputText == Math.round(doubleInputText)){
                                calculateTV.setText(showText+intPushNumber);
                                pushNumber = doubleInputText;
                                pushShowText += inputText;
                            } else {
                                pushNumber = Double.parseDouble(inputText);
                                calculateTV.setText(showText+pushNumber);
                            }
                            integerBit += 1;

                        } else {
                            //如果 pushNumber 不为空
                            // 如果 pushNumber 还没有小数存在
                            if (pushNumber == Math.round(pushNumber)){

                                // 现在pushNumber中存在的数值
                                originalNum = String.valueOf(Math.round(pushNumber));

                                // 将点击的按钮数值，通过string相加的方式，组合在一起
                                String addNum = originalNum + inputText;

                                // 将组合后的String类型值，转换为Double，赋值给pushNumber
                                pushNumber = Double.valueOf(addNum);

                                if (Objects.equals(pushShowText, "0")){
                                    pushShowText = inputText;
                                } else {
                                    pushShowText += inputText;
                                }

                                // 最初版本 1.0，没有任何分隔符
//                            pushShowText += inputText;
//                            integerBit += 1;

                                // 第二版本 2.0，分隔符形式为 【777,777,77】,正常应该为【77,777,777】
                                // 是从后往前每三位，进行分割，后面永远是3位一分割，不是从前往后
//                            if (integerBit == 3){
//                                pushShowText += "," + inputText;
//                                integerBit = 0;
//                            } else {
//                                pushShowText += inputText;
//                            }
//                            integerBit += 1;

//                            if (equalMathRound(pushNumber)){
//                                calculateTV.setText(showText+Math.round(pushNumber));
//                            } else {
//                                calculateTV.setText(showText+pushNumber);
//                            }

                                //  第三版本 3.0，获取pushNumber有几个整数位
                                // .length()后需要减2，因为double类型还存在 “.” 和 “0”
                                //  int bit_pushNumber = pushNumber.toString().length()-2;

                                // 获取pushNumber一共有几位
                                int bit_pushNumber = addNum.length();

                                // 需要添加判断addNum中是否存在负数，即pushNumber是否为负数
                                // 是负数时【addNumber.length】需要减1，来排除【-号】
                                if (pushNumber < 0){
                                    bit_pushNumber -= 1;
                                    // 根据位数设置循环次数，每三位添加 ","
                                    if ((bit_pushNumber/3) >= 1){
                                        StringBuilder stringBuffer = new StringBuilder(String.valueOf(Math.round(pushNumber)));
                                        for (int i = 1; i <= (bit_pushNumber-1)/3 ; i++) {
                                            stringBuffer.insert(bit_pushNumber-i*3+1,",");
                                            pushShowText = String.valueOf(stringBuffer);
                                        }
                                    }
                                } else {
                                    // 根据位数设置循环次数，每三位添加 ","
                                    if ((bit_pushNumber/3) >= 1){
                                        StringBuilder stringBuffer = new StringBuilder(String.valueOf(Math.round(pushNumber)));
                                        for (int i = 1; i <= (bit_pushNumber-1)/3 ; i++) {
                                            stringBuffer.insert(bit_pushNumber-i*3,",");
                                            pushShowText = String.valueOf(stringBuffer);
                                        }
                                    }
                                }
                            } else {
                                //如果 pushNumber 中有小数存在
                                String originalNum = pushNumber.toString();
                                String addNum = originalNum + inputText;
                                pushNumber = Double.valueOf(addNum);
                                pushShowText += inputText;
                            }
                            calculateTV.setText(showText + pushShowText);
                            integerBit += 1;
                            break;
                        }
                    }
                }
        }
    }

    public boolean equalMathRound(double pushNumber){
        return pushNumber == Math.round(pushNumber);
    }
}