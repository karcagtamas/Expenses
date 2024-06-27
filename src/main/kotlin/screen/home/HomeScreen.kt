package screen.home

import Constants
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.DataRow
import components.ExpenseItem
import models.Category
import models.Expense
import screen.details.DetailsScreen
import screen.edit.EditScreen
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel { HomeViewModel() }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text("Expenses", color = Color.White)
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navigator.push(
                        EditScreen(
                            Expense(
                                UUID.randomUUID().toString(),
                                BigDecimal(0),
                                Category.INCOME
                            ),
                            false,
                            onSave = {
                                viewModel.add(it)
                            },
                            onPayBack = {},
                            onRemove = {}
                        )
                    )
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val currency = Constants.CURRENCY
                    val textUnit = TextUnit(1.2f, TextUnitType.Em)
                    DataRow("Total", viewModel.getTotal().toPlainString(), trailing = currency, fontSize = textUnit)
                    DataRow(
                        "Total Income",
                        viewModel.getTotalIncome().toPlainString(),
                        trailing = currency,
                        fontSize = textUnit
                    )
                    DataRow(
                        "Total Outcome",
                        viewModel.getTotalOutCome().toPlainString(),
                        trailing = currency,
                        fontSize = textUnit
                    )
                    DataRow(
                        "Total Deposit",
                        viewModel.getTotalDeposit().toPlainString(),
                        trailing = currency,
                        fontSize = textUnit
                    )
                }
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    val orderedItems = viewModel.expenses.value.sortedBy { it.date }

                    items(orderedItems.size) { index ->
                        ExpenseItem(orderedItems[index], onEdit = {
                            navigator.push(
                                EditScreen(it, true, onSave = {
                                    viewModel.edit(it)
                                }, onPayBack = {
                                    viewModel.payBack(it)
                                }, onRemove = {
                                    viewModel.remove(it)
                                })
                            )
                        }) {
                            navigator.push(DetailsScreen(it))
                        }
                    }
                }
            }
        }
    }
}