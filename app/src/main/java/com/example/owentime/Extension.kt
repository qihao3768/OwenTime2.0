package com.example.owentime

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import coil.load
import com.example.owentime.bean.Register
import com.example.owentime.ui.LoginActivity
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

fun Activity.start(activity: Activity, clazz: Class<Activity>, params:HashMap<String,Any>){
    start02(activity, clazz, params)
}

fun Fragment.start(activity:Activity,clazz: Class<Activity>,finish: Boolean){
    start01(activity, clazz, finish)
}

fun Fragment.start(activity: Activity, clazz: Class<Activity>, key: String, value: Any){
   start03(activity, clazz, key, value)
}

fun Fragment.start(activity: Activity, clazz: Class<Activity>, params:HashMap<String,Any>){
    start02(activity, clazz, params)
}

private fun start01(activity:Activity,clazz: Class<Activity>,finish:Boolean){
    val intent = Intent(activity,clazz)
    activity.startActivity(intent)

}

private fun start02(activity:Activity,clazz: Class<Activity>,params:HashMap<String,Any>){
    val intent = Intent(activity,clazz)
    params.forEach {
        when(it.value){
            is String->{
                intent.putExtra(it.key,it.value.toString())
            }
            is Int->{
                intent.putExtra(it.key, (it.value as Int).toInt())
            }
            is Double->{
                intent.putExtra(it.key, (it.value as Double).toDouble())
            }
        }
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
fun ViewGroup.checkLogin(context:AppCompatActivity,msg: String):String{
    val userInfo=MMKV.defaultMMKV().decodeParcelable("user",Register::class.java)
    return if (userInfo==null){
        toast(msg)
        msg
    }else{
        start01(context,LoginActivity().javaClass,false)
        ""
    }
}

fun View.checkLogin(context:AppCompatActivity,msg: String):String{
    val userInfo=MMKV.defaultMMKV().decodeParcelable("user",Register::class.java)
    return if (userInfo==null){
        toast(msg)
        msg
    }else{
        start01(context,LoginActivity().javaClass,false)
        ""
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