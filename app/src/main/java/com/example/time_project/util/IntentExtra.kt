package com.example.time_project.util

import android.content.Intent
import kotlin.reflect.KProperty
class IntentExtra {
    companion object {

        var Intent.code by IntentExtraString("code")
        var Intent.iurl by IntentExtraString("url")

        var Intent.icode by IntentExtraString("code")//商品代码
        var Intent.isku by IntentExtraString("sku")//skuid
        var Intent.iskuName by IntentExtraString("skuname")//skuid
        var Intent.inum by IntentExtraInt("num")//购买数量
        var Intent.icoupon by IntentExtraString("coupon")//优惠券id
        var Intent.iproductId by IntentExtraInt("productId")//商品ID

        var Intent.iSkip by IntentExtraBoolean("skip")//个人信息页是否显示跳过

        var Intent.iUserName by IntentExtraString("username")//用户名
        var Intent.iSex by IntentExtraInt("sex")//性别
        var Intent.iBirthday by IntentExtraString("birth")//生日
        var Intent.iHead by IntentExtraString("head")//头像

        var Intent.courseTitle by IntentExtraString("courseTitle")//课程标题
        var Intent.courseUrl by IntentExtraString("courseUrl")//课程URL
        var Intent.courseId by IntentExtraString("courseId")//课程ID
        var Intent.courseTime by IntentExtraInt("courseTime")//课程进度
        var Intent.courseDub by IntentExtraString("courseDub")//配音课程链接，如果有内容表示这节课需要配音
        var Intent.position by IntentExtraInt("position")//视频位置
        var Intent.shareImage by IntentExtraString("shareImage")//分享图标
    }
}
class IntentExtraString(private val key: String? = null) {
    private val KProperty<*>.extraName: String
        get() = this@IntentExtraString.key ?: name

    operator fun getValue(intent: Intent, property: KProperty<*>): String? =
        intent.getStringExtra(property.extraName)

    operator fun setValue(intent: Intent, property: KProperty<*>, value: String?) {
        intent.putExtra(property.extraName, value)
    }
}

class IntentExtraInt(private val key: String? = null) {
    private val KProperty<*>.extraName: String
        get() = this@IntentExtraInt.key ?: name

    operator fun getValue(intent: Intent, property: KProperty<*>): Int =
        intent.getIntExtra(property.extraName,-1)

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Int?) {
        intent.putExtra(property.extraName, value)
    }
}

class IntentExtraBoolean(private val key: String? = null) {
    private val KProperty<*>.extraName: String
        get() = this@IntentExtraBoolean.key ?: name

    operator fun getValue(intent: Intent, property: KProperty<*>): Boolean =
        intent.getBooleanExtra(property.extraName,false)

    operator fun setValue(intent: Intent, property: KProperty<*>, value: Boolean?) {
        intent.putExtra(property.extraName, value)
    }
}

