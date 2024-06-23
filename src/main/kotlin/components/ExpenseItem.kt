package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import models.Category
import models.Expense

@Composable
fun ExpenseItem(expense: Expense, onEdit: (Expense) -> Unit, onClick: (Expense) -> Unit) {
    val color = when (expense.category) {
        Category.INCOME -> Pair(Color(230, 181, 181), Color(65, 10, 10))
        Category.OUTCOME -> Pair(Color(190, 211, 175), Color(30, 48, 17))
        else -> Pair(Color(150, 193, 226), Color(14, 64, 114))
    }

    Box(
        modifier = Modifier
            .clickable { onClick(expense) }
            .padding(4.dp)
            .background(color.first, RoundedCornerShape(8.dp))
            .padding(8.dp, 4.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(expense.value.toPlainString())
            IconButton(onClick = {
                onEdit(expense)
            }) {
                Icon(Icons.Filled.Edit, "Edit Expense")
            }
        }
    }
}