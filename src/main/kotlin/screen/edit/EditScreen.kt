package screen.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import models.Expense
import java.math.BigDecimal

class EditScreen(val expense: Expense, val editing: Boolean) : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { EditViewModel(expense) }

        Column {
            OutlinedTextField(
                placeholder = { Text("Value") },
                value = viewModel.expense.value.value.toPlainString(),
                onValueChange = {
                    viewModel.setValue(BigDecimal(it))
                }
            )
        }
    }
}