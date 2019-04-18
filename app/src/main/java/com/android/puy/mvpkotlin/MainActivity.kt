package com.android.puy.mvpkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import coroutines.KotlinAsyncWithCoroutines.Companion.loadAsync
import coroutines.KotlinAsyncWithCoroutines.Companion.ui
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
