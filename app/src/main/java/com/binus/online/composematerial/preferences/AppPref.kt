package com.binus.online.composematerial.preferences

interface AppPref {
    fun setLogin(isLogin: Boolean)
    fun getLogin(): Boolean
}