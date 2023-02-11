package com.pgbank.personal.di

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.pgbank.personal.constants.Common
import kotlin.reflect.KClass


interface ResourceProvider {
    fun getString(resourceIdentifier: Int, vararg arguments: Any = arrayOf()): String

    fun getStringArray(resourceIdentifier: Int): Array<String>

    fun getInteger(resourceIdentifier: Int): Int

    fun getIntegerArray(resourceIdentifier: Int): Array<Int>

    fun getBoolean(resourceIdentifier: Int): Boolean

    fun getColor(resourceIdentifier: Int): Int

    fun startActvity(des: Class<out Activity>)

    fun startActvity(des: Class<out Activity>, bundle:Bundle)
    fun startActvity(des: KClass<*>, bundle:Bundle)

}

class AndroidResourceProvider
 constructor(private val context: Context)
    : ResourceProvider {
    override fun startActvity(des: Class<out Activity>) {
        Common.currentActivity.startActivity(Intent(Common.currentActivity, des))
    }
    override fun startActvity(des: Class<out Activity>, bundle:Bundle) {
        Common.currentActivity.startActivity(Intent(Common.currentActivity, des).putExtras(bundle))
    }

    override fun startActvity(des: KClass<*>, bundle: Bundle) {
        Common.currentActivity.startActivity(Intent(Common.currentActivity, des.java).putExtras(bundle))
    }

    override fun getString(@StringRes resourceIdentifier: Int, vararg arguments: Any): String {
        return if (arguments.isNotEmpty())
            context.resources.getString(resourceIdentifier, *arguments)
        else
            context.resources.getString(resourceIdentifier)
    }

    override fun getStringArray(@ArrayRes resourceIdentifier: Int): Array<String> =
        context.resources.getStringArray(resourceIdentifier)

    override fun getInteger(@IntegerRes resourceIdentifier: Int): Int =
        context.resources.getInteger(resourceIdentifier)

    override fun getIntegerArray(@ArrayRes resourceIdentifier: Int): Array<Int> =
        context.resources.getIntArray(resourceIdentifier).toTypedArray()


    override fun getBoolean(@BoolRes resourceIdentifier: Int): Boolean =
        context.resources.getBoolean(resourceIdentifier)

    override fun getColor(@ColorRes resourceIdentifier: Int): Int =
        ContextCompat.getColor(context, resourceIdentifier)

}

