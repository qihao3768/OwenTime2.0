package com.example.owentime

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.example.owentime.bean.Register
import com.example.owentime.ui.LoginActivity
import com.hjq.shape.view.ShapeCheckBox
import com.tencent.mmkv.MMKV
import java.util.*

fun ImageView.load(url:String){
    val uri=Uri.parse(url)
    this.load(uri)
}

fun Activity.start(activity:Activity,clazz: Class<Activity>,finish: Boolean){
 start01(activity, clazz, finish)
}

fun Activity.start(activity: Activity, clazz: Class<Activity>, key: String, value: Any){
    start03(activity, clazz, key, value)
}

fun Activity.start(activity: Activity, clazz: Class<Activity>, params:HashMap<String,String>){
    start02(activity, clazz, params)
}

fun Activity.start(activity: Activity, clazz: Class<Activity>,intent: Intent){
    intent.setClass(activity,clazz)
    this.startActivity(intent)
}

fun Fragment.start(activity:Activity,clazz: Class<Activity>,finish: Boolean){
    start01(activity, clazz, finish)
}

fun Fragment.start(activity: Activity, clazz: Class<Activity>, key: String, value: Any){
   start03(activity, clazz, key, value)
}

fun Fragment.start(activity: Activity, clazz: Class<Activity>, params:HashMap<String,String>){
    start02(activity, clazz, params)
}

private fun start01(activity:Activity,clazz: Class<Activity>,finish:Boolean){
    val intent = Intent(activity,clazz)
    activity.startActivity(intent)
    if (finish){
        activity.finish()
    }

}

private fun start02(activity:Activity,clazz: Class<Activity>,params:HashMap<String,String>){
    val intent = Intent(activity,clazz)
    params.forEach {
        intent.putExtra(it.key,it.value.toString())
    }
    activity.startActivity(intent)
}
private fun start03(activity:Activity,clazz: Class<Activity>,key:String,value: Any){
    val intent = Intent(activity,clazz)

    when(value){
        is String->{
            intent.putExtra(key,value.toString())
        }
        is Int->{
            intent.putExtra(key,value.toInt())
        }
        is Double->{
            intent.putExtra(key,value.toDouble())
        }
    }

    activity.startActivity(intent)
}

/***
 * 防止快速点击
 */
fun View.click(listener: (view: View) -> Unit) {
    val minTime = 1000L
    var lastTime = 0L
    this.setOnClickListener {
        val tmpTime = System.currentTimeMillis()
        if (tmpTime - lastTime > minTime) {
            lastTime = tmpTime
            listener.invoke(this)
        }
    }
}

fun ViewGroup.checkLogin(msg: String):String{
    val userInfo=MMKV.defaultMMKV().decodeParcelable("user",Register::class.java)
    return if (userInfo==null){
        toast(msg)
        msg
    }else{
        ""
    }
}
//检查登录
fun ViewGroup.checkLogin(context:Context,todoListener: TodoListener){
//    val userInfo=MMKV.defaultMMKV().decodeParcelable("user",Register::class.java)
    val islogin=MMKV.defaultMMKV().decodeBool("islogin",false)
    this.setOnClickListener {
         if (islogin){
             todoListener.todo()
        }else{
             start01(context as Activity,LoginActivity().javaClass,false)
        }
    }

}

fun View.checkLogin(context:Context,todoListener: TodoListener){
    val islogin=MMKV.defaultMMKV().decodeBool("islogin",false)
    this.setOnClickListener {
        if (islogin){
            todoListener.todo()
        }else{
            start01(context as Activity,LoginActivity().javaClass,false)
        }
    }
}
fun toast(msg:String){
    val toast = Toast(App.getContext())
    val inflate =View.inflate(App.getContext(), R.layout.layout_toast,null )//自定义布局
    inflate.findViewById<TextView>(R.id.toast).text=msg
    toast.view = inflate //将自定义view设置给toast
    toast.setGravity(Gravity.CENTER, 0, 0) //自定义位置
    toast.show()

}

 fun EditText.checked(msg:String):String?{
    return if (this.text.isNullOrEmpty()){
        toast(msg)
        null
    }else{
        ""
    }
}
interface TodoListener{
    fun todo()
}