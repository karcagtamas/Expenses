package components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import models.Category
import models.Expense

@Composable
fun ExpenseItem(expense: Expense, onClick: (Expense) -> Unit) {
    val color = when (expense.category) {
        Category.INCOME -> Pair(Color(230, 181, 181), Color(65, 10, 10))
        Category.OUTCOME -> Pair(Color(190, 211, 175), Color(30, 48, 17))
        else -> Pair(Color(150, 193, 226), Color(14, 64, 114))
    }

    Box(
        modifier = Modifier
            .clickable { onClick(expense) }
            .fillMaxWidth()
            .border(3.dp, color.second, RoundedCornerShape(4.dp))
            .padding(4.dp)
            .background(color.first)
            .clip(CircleShape),
    ) {
        Text(expense.value.toPlainString(), modifier = Modifier.background(color.first))
    }
}