package screen.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ExpenseItem
import models.Category
import models.Expense
import screen.ExpenseScreen
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel { HomeViewModel() }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Button(onClick = {
                    /*viewModel.expenses.plus(
                        Expense(
                            UUID.randomUUID().toString(),
                            BigDecimal(Random.nextInt()),
                            Category.INCOME
                        )
                    )*/
                }) {
                    Text("Add")
                }
            }

            items(viewModel.expenses.value.size) { index ->
                ExpenseItem(viewModel.expenses.value[index]) {
                    navigator.push(ExpenseScreen(it.id))
                }
            }
        }
    }
}