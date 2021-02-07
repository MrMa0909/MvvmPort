package com.cfox.appdemo.data.bean

import androidx.databinding.ObservableField

class RegisterBean() {
    val userName = ObservableField<String>("")
    val userPwd = ObservableField<String>("")
}