package screen.edit

import Constants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import components.DropdownSelect
import models.Category
import models.Expense
import java.math.BigDecimal

class EditScreen(val expense: Expense, val editing: Boolean, val onSave: (Expense) -> Unit) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = rememberScreenModel { EditViewModel(expense, onSave) }

        Scaffold(
            topBar = {
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    ),
                    title = {
                        Text(if (editing) "Edit Expense" else "Add Expense", color = Color.White)
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navigator.pop()
                }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Cancel")
                }
            }
        ) {
            Column(
                modifier = Modifier.padding(it).fillMaxWidth().padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = { Text("Value (${Constants.CURRENCY})") },
                    placeholder = { Text("Value (${Constants.CURRENCY})") },
                    value = viewModel.value.value.toPlainString(),
                    onValueChange = {
                        try {
                            val newValue = BigDecimal(it)
                            viewModel.setValue(newValue)
                        } catch (ex: NumberFormatException) {
                            viewModel.setValue(viewModel.value.value)
                        }
                    }
                )
                DropdownSelect(
                    label = { Text("Category") },
                    options = listOf(Category.INCOME, Category.OUTCOME, Category.DEPOSIT),
                    stringifier = { it.displayName },
                    value = viewModel.category.value,
                    onValueChange = {
                        viewModel.setCategory(it)
                    }
                )
                OutlinedButton(
                    onClick = {
                        viewModel.save()
                        navigator.pop()
                    }
                ) {
                    Text("Save")
                }
            }
        }
    }
}