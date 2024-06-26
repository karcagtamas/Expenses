package screen.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.DataRow
import components.DateDataRow
import models.Category
import models.Expense
import java.time.LocalDateTime
import java.time.LocalTime

data class DetailsScreen(val expense: Expense) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel { DetailsViewModel(expense) }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val expense = viewModel.expense.value
                DataRow("Value", expense.value.toPlainString())
                DataRow("Category", expense.category.displayName)
                DateDataRow("Date", LocalDateTime.of(expense.date, LocalTime.of(0, 0, 0)))

                if (expense.isPaidBack()) {
                    Text("Deposit is payed back")
                }

                if (expense.isPayingBackDeposit()) {
                    Text("Income is paying back a deposit")
                }
            }
        }
    }
}