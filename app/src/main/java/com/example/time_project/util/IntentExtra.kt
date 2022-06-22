package com.example.time_project.util

import android.content.Intent
import com.example.time_project.ui.HomeFragment
import kotlin.reflect.KProperty
class IntentExtra {
    companion object {

        var Intent.code by IntentExtraString("code")
        var Intent.iurl by IntentExtraString("url")

        var Intent.icode by IntentExtraString("code")//商品代码
        var Intent.isku by IntentExtraString("sku")//skuid
        var Intent.inum by IntentExtraString("num")//购买数量
        var Intent.icoupon by IntentExtraString("coupon")//优惠券id
        var Intent.iproductId by IntentExtraInt("productId")//商品ID
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
