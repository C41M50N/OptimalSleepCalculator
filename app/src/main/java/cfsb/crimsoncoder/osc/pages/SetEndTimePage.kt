@file:OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class,
    ExperimentalFoundationApi::class
)

package cfsb.crimsoncoder.osc.pages

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import cfsb.crimsoncoder.osc.*
import com.vanpra.composematerialdialogs.*
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun SetEndTimePage () {

    val ctx = LocalContext.current

    var dateTime by rememberSaveable { mutableStateOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(5, 0, 0))) }
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(5, 0, 0))) }

    val setTimeDialogState = rememberMaterialDialogState()
    val setAlarmDialogState = rememberMaterialDialogState()

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9F)
    ) {

        Card (onClick = { setTimeDialogState.show() }, elevation = 30.dp) {
            Text (
                text = dateTime.format(CUSTOM_TIME_FORMATTER),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                modifier = Modifier.padding(10.dp, 4.dp)
            )
        }

        Spacer (modifier = Modifier.height(4.dp))

        Button (onClick = { setAlarm(selectedDateTime, "RISE AND GRIND !!!", ctx) }, colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)) {
            Text (SET_WAKE_UP_ALARM_BTN_TEXT, style = TextStyle.Default.plus(TextStyle(MaterialTheme.colors.onBackground, fontSize = TextUnit(4.5F, TextUnitType.Em))))
        }

        Spacer (modifier = Modifier.height(30.dp))

        LazyVerticalGrid (cells = GridCells.Fixed(3), contentPadding = PaddingValues(8.dp)) {
            val optimalWakeUpDateTimes = listOf(
                dateTime.minusMinutes(90 * 1),
                dateTime.minusMinutes(90 * 2),
                dateTime.minusMinutes(90 * 3),
                dateTime.minusMinutes(90 * 4),
                dateTime.minusMinutes(90 * 5),
                dateTime.minusMinutes(90 * 6),
                dateTime.minusMinutes(90 * 7),
                dateTime.minusMinutes(90 * 8),
                dateTime.minusMinutes(90 * 9)
            )
            itemsIndexed (optimalWakeUpDateTimes) { idx, item ->
                Card (onClick = { selectedDateTime = item; setAlarmDialogState.show() }, elevation = 16.dp, modifier = Modifier.padding(4.dp)) {
                    Text(
                        text = item.format(CUSTOM_DT_FORMATTER_W_NL),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(4.dp, 0.dp)
                    )
                }
            }
        }

        Spacer (modifier = Modifier.height(20.dp))

        MaterialDialog (setTimeDialogState, buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }) {
            timepicker { time -> /* TODO */
                val today = LocalDate.now()
                dateTime = LocalDateTime.of(today, time)
            }
        }

        MaterialDialog (setAlarmDialogState, buttons = {
            positiveButton("Yes", onClick = { setAlarm(selectedDateTime, "GO TO BED !!!", ctx) })
            negativeButton("No")
        }) {
            title(text = "Alarm")
            message(text = "Set Alarm for ${selectedDateTime.format(CUSTOM_DT_FORMATTER)} ?")
        }

    }
}