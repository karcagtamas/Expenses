package screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import components.ExpenseItem
import models.Category
import models.Expense
import screen.details.DetailsScreen
import screen.edit.EditScreen
import java.math.BigDecimal
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
                            false
                        ) {
                            viewModel.add(it)
                        }
                    )
                }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(it).fillMaxWidth().padding(8.dp),
            ) {
                items(viewModel.expenses.value.size) { index ->
                    ExpenseItem(viewModel.expenses.value[index], onEdit = {
                        navigator.push(
                            EditScreen(it, true) {
                                viewModel.edit(it)
                            }
                        )
                    }) {
                        navigator.push(DetailsScreen(it))
                    }
                }
            }
        }
    }
}