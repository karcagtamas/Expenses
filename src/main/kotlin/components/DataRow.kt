package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun DataRow(caption: String, value: String, trailing: String? = null, fontSize: TextUnit = TextUnit.Unspecified) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            "$caption:",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            fontSize = fontSize
        )
        Text(value, fontSize = fontSize)

        if (trailing != null) {
            Text(trailing, fontSize = fontSize)
        }
    }
}