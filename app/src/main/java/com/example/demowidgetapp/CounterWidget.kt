package com.example.demowidgetapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.Button
import androidx.glance.ButtonColors
import androidx.glance.ButtonDefaults
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
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxHeight
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.material.ColorProviders
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import java.sql.Struct

object CounterWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MyContent()
        }
    }

    val countKey = stringPreferencesKey("count")

}


@Composable
fun MyContent() {

    val count = currentState(key = CounterWidget.countKey) ?: "Type Number"

    Column(
        modifier = GlanceModifier.background(Color.DarkGray),
        verticalAlignment = Alignment.Vertical.CenterVertically,
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
    ) {
        Spacer(modifier = GlanceModifier.padding(20.dp))
        Text(
            text = count.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Medium,
                color = ColorProvider(Color.White),
                fontSize = 23.sp,
            ),
        )
        buttonDisplay("1", "2", "3")
        buttonDisplay("4", "5", "6")
        buttonDisplay("7", "8", "9")
        buttonDisplay("C", "0", "❮")

        Row(modifier = GlanceModifier.fillMaxWidth().padding(20.dp)) {
            Button(
                text = "Send WhatsApp", onClick = actionRunCallback(
                    SendWhatsppCallback::class.java
                ),
                style = TextStyle(textAlign = TextAlign.Center),
                modifier = GlanceModifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun buttonDisplay(first: String, second: String, third: String) {
    Row(modifier = GlanceModifier.fillMaxWidth().padding(20.dp)) {
        Button(
            text = first, onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to first
                )
            ),
            style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Medium),
            modifier = GlanceModifier.defaultWeight()
        )
        Spacer(modifier = GlanceModifier.padding(10.dp))
        Button(
            text = second, onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to second
                )
            ),
            style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Medium),
            modifier = GlanceModifier.defaultWeight()
        )
        Spacer(modifier = GlanceModifier.padding(10.dp))
        Button(
            text = third, onClick = actionRunCallback(
                IncrementActionCallback::class.java, parameters = actionParametersOf(
                    actionWidgetKey to third
                )
            ),
            style = TextStyle(textAlign = TextAlign.Center, fontWeight = FontWeight.Medium),
            modifier = GlanceModifier.defaultWeight()
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

        Log.d("value ", value.toString())
        updateAppWidgetState(context, glanceId) { prefs ->

            val currentCount = prefs[CounterWidget.countKey]
            if (value == "❮") {
                if (!currentCount.isNullOrEmpty()) {
                    prefs[CounterWidget.countKey] = removeLastChar(currentCount)
                }
            } else if (value == "C") {
                prefs[CounterWidget.countKey] = ""
            } else {
                if (currentCount != null) {
                    prefs[CounterWidget.countKey] = currentCount + value.toString()
                } else {
                    prefs[CounterWidget.countKey] = value.toString()
                }
            }
        }
        CounterWidget.update(context, glanceId)
    }

}

class SendWhatsppCallback() : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {

        updateAppWidgetState(context, glanceId) { prefs ->

            val currentCount = prefs[CounterWidget.countKey]

            Log.d("currentCount", currentCount.toString())
            openWhatsApp(number = currentCount, context = context, pref = prefs)
        }
        CounterWidget.update(context, glanceId)
    }

}

fun removeLastChar(s: String?): String {
    return if (s == null || s.isEmpty()) "" else s.substring(0, s.length - 1)
}

fun openWhatsApp(number: String?, context: Context, pref: MutablePreferences) {

    // on below line we are opening the intent.
    val intent = Intent(
        // on below line we are calling
        // uri to parse the data
        Intent.ACTION_VIEW,
        Uri.parse(
            // on below line we are passing uri,
            // message and whats app phone number.
            java.lang.String.format(
                "https://api.whatsapp.com/send?phone=%s&text=%s",
                number,
                ""
            )
        )
    )

    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
    pref[CounterWidget.countKey] = ""

}