package components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelect(label: @Composable () -> Unit, value: LocalDateTime, onValueChange: (LocalDateTime) -> Unit) {
    val state = rememberDatePickerState(value.atOffset(ZoneOffset.UTC).toInstant().toEpochMilli())
    val openDialog = remember { mutableStateOf(false) }

    OutlinedTextField(
        readOnly = true,
        value = toLocalDateTime(state.selectedDateMillis)?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            ?: "N/A",
        onValueChange = {},
        label = label,
        trailingIcon = {
            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(Icons.Filled.DateRange, "Calendar")
            }
        },
    )
    if (openDialog.value) {
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    val selected = toLocalDateTime(state.selectedDateMillis)
                    if (selected != null) {
                        onValueChange(
                            selected
                        )
                    }
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("CANCEL")
                }
            }
        ) {
            DatePicker(state = state)
        }
    }
}

private fun toLocalDateTime(value: Long?): LocalDateTime? {
    if (value == null) {
        return null
    }

    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(value),
        ZoneId.systemDefault()
    )
}