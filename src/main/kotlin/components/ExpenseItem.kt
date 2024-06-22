package components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.Expense

@Composable
fun ExpenseItem(expense: Expense, onClick: (Expense) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(4.dp, MaterialTheme.colors.primary, RoundedCornerShape(4.dp))
            .clickable { onClick(expense) },
    ) {
        Text(expense.value.toPlainString())
    }
}