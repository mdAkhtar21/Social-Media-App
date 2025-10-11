package com.example.socialmedialapp.android.common.util

private const val CURRENT_BASE_URL="http://192.164.0.108:8080"
fun String.toCurrentUrl():String{
    return "$CURRENT_BASE_URL${this.substring(26)}"
}