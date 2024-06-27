package components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DateDataRow(
    caption: String,
    value: LocalDateTime,
    trailing: String? = null,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    DataRow(caption, value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), trailing, fontSize)
}