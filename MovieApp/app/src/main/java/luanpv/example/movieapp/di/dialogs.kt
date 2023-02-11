package com.pgbank.personal.di

import com.pgbank.personal.constants.Common
import luanpv.example.movieapp.dialogs.LoadingDialog
import org.koin.dsl.module

val dialogs = module {
    factory { createLoadingDialog() }
}

fun createLoadingDialog(): LoadingDialog {
    return LoadingDialog(Common.currentActivity)
}

