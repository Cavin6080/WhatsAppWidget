package com.example.demowidgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.demowidgetapp.ui.theme.DemoWidgetAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DemoWidgetAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Cavin")
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String, modifier: Modifier = Modifier
//        .fillMaxHeight(0.5F)
) {
    Column {
        (Modifier
            .fillMaxHeight(0.5F))
        Row {

            Text(
                text = "1",
                modifier = modifier
            )
            Text(
                text = "2",
                modifier = modifier
            )
            Text(
                text = "3",
                modifier = modifier
            )
        }
        Row {

            Text(
                text = "4",
                modifier = modifier
            )
            Text(
                text = "5",
                modifier = modifier
            )
            Text(
                text = "6",
                modifier = modifier
            )
        }
        Row {

            Text(
                text = "7",
                modifier = modifier
            )
            Text(
                text = "8",
                modifier = modifier
            )
            Text(
                text = "9",
                modifier = modifier
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DemoWidgetAppTheme {
        Greeting("Cavin")
    }
}