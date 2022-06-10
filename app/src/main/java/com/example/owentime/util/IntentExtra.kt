package com.example.owentime.util

import android.content.Intent
import kotlin.reflect.KProperty

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
