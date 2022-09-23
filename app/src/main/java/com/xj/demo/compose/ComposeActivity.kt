package com.xj.demo.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import kotlin.random.Random


class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        setContent {
            ShowText()
        }
    }

    @Preview()
    @Composable
    fun ShowText() {
        val state = remember { mutableStateOf(0) }
        Button(onClick = {
            state.value = Random.nextInt(2)}
        ) {
            Text(text = "测试!", color = if(state.value == 1) Color.Red else Color.Blue,
            )
        }
    }

}
