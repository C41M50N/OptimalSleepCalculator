package cfsb.crimsoncoder.osc

import android.content.Context
import android.content.Intent
import android.provider.AlarmClock
import androidx.core.content.ContextCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun setAlarm (dateTime: LocalDateTime, message: String, ctx: Context) {
    val intent = Intent(AlarmClock.ACTION_SET_ALARM)
    intent.putExtra(AlarmClock.EXTRA_HOUR, dateTime.hour)
    intent.putExtra(AlarmClock.EXTRA_MINUTES, dateTime.minute)
    intent.putExtra(AlarmClock.EXTRA_MESSAGE, message)
    ContextCompat.startActivity(ctx, intent, null)
}

const val USE_CURRENT_TIME_BTN_TEXT = "Use Current Time"
const val SET_WAKE_UP_ALARM_BTN_TEXT = "Set Wake Up Alarm"

val CUSTOM_TIME_FORMATTER = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)!!
val CUSTOM_DT_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a E LLL d")!!
val CUSTOM_DT_FORMATTER_W_NL = DateTimeFormatter.ofPattern("hh:mm a '\n'E LLL d")!!
