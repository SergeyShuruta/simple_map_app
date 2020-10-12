package com.example.lessons

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val numberLiveData = MutableLiveData<String>()
    val symbolLiveData = MutableLiveData<String>()
    val mediatorLiveData = MediatorLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediatorLiveData.addSource(numberLiveData) {
            mediatorLiveData.value = it
        }

        mediatorLiveData.addSource(symbolLiveData) {
            if(it == "stop")
                mediatorLiveData.removeSource(symbolLiveData)
            else
                mediatorLiveData.value = it
        }

        mediatorLiveData.observe(this, Observer<String> {
            //timerTextView.text = it
            Log.d("TEST", "- $it")
        })
    }

    override fun onStart() {
        super.onStart()
        Thread {
            numberLiveData.postValue("1")
            Thread.sleep(100)
            symbolLiveData.postValue("a")
            Thread.sleep(100)
            numberLiveData.postValue("2")
            Thread.sleep(100)
            symbolLiveData.postValue("b")
            Thread.sleep(100)
            numberLiveData.postValue("3")
            Thread.sleep(100)
            symbolLiveData.postValue("c")
            symbolLiveData.postValue("stop")
            Thread.sleep(100)
            numberLiveData.postValue("4")
            Thread.sleep(100)
            symbolLiveData.postValue("d")
            Thread.sleep(100)
            numberLiveData.postValue("5")
            Thread.sleep(100)
            symbolLiveData.postValue("e")

        }.start()
    }
}