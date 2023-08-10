package com.example.demowidgetapp

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.demowidgetapp.ui.theme.DemoWidgetAppTheme

object CounterWidget : GlanceAppWidget() {


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent()
        }
    }

    val countKey = stringPreferencesKey("count")



}

@Preview(showBackground = true)
@Composable
fun GreetingPreview123() {
    DemoWidgetAppTheme {
        MyContent()
    }
}

@Composable
 fun MyContent() {

    val count = currentState(key = CounterWidget.countKey) ?: "0"


    Column(
        modifier = GlanceModifier.fillMaxSize().background(Color.DarkGray),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
    ) {
        Text(
            text = count.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color.White),
                fontSize = 26.sp,
            ),
        )


        Button(
            text = "1", onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to "1"
                )
            )
        )
        Button(
            text = "2", onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to "2"
                )
            )
        )
        Button(
            text = "3", onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to "3"
                )
            )
        )
        Button(
            text = "4", onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to "4"
                )
            )
        )
        Button(
            text = "5", onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to "5"
                )
            )
        )
    }
}

val actionWidgetKey = ActionParameters.Key<String>("action-widget-key")

class SimpleCounterWidgetReciever : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget
}


class IncrementActionCallback() : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        Log.d("CounterWidget", "Item with id $glanceId and params $parameters clicked.")
        val value: String? = parameters.get(key = actionWidgetKey)

        Log.d("value ",value.toString())
        updateAppWidgetState(context, glanceId) { prefs ->


            val currentCount = prefs[CounterWidget.countKey]
            if (currentCount != null) {
                prefs[CounterWidget.countKey] = currentCount + value.toString()
            } else {
                prefs[CounterWidget.countKey] = value.toString()
            }
        }
        CounterWidget.update(context, glanceId)
    }

}