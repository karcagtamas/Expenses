package screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ExpenseItem
import models.Category
import models.Expense
import screen.ExpenseScreen
import screen.edit.EditScreen
import java.math.BigDecimal
import java.util.*

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel { HomeViewModel() }

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(2.dp),
                ) {
                    Text(
                        text = "Expenses",
                        fontSize = TextUnit(1.6f, TextUnitType.Em),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(onClick = {
                        navigator.push(
                            EditScreen(
                                Expense(
                                    UUID.randomUUID().toString(),
                                    BigDecimal(0),
                                    Category.INCOME
                                ),
                                false
                            )
                        )
                    }) {
                        Icon(Icons.Filled.Add, "Add new Expense")
                    }
                }
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(viewModel.expenses.value.size) { index ->
                    ExpenseItem(viewModel.expenses.value[index], onEdit = {
                        navigator.push(EditScreen(it, true))
                    }) {
                        navigator.push(ExpenseScreen(it.id))
                    }
                }
            }
        }
    }
}