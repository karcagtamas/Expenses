package components

import Constants
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import models.Category
import models.Expense

@Composable
fun ExpenseItem(expense: Expense, onEdit: (Expense) -> Unit, onClick: (Expense) -> Unit) {
    val color = when (expense.category) {
        Category.INCOME -> Color(21, 157, 21)
        Category.OUTCOME -> Color(255, 26, 26)
        else -> Color(0, 191, 255)
    }

    Box(
        modifier = Modifier
            .padding(4.dp)
            .clickable { onClick(expense) }
            .background(color, RoundedCornerShape(12.dp))
            .padding(12.dp, 2.dp)
            .fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "${expense.value.toPlainString()} ${Constants.CURRENCY}",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = {
                    onEdit(expense)
                },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Expense")
            }
        }
    }
}