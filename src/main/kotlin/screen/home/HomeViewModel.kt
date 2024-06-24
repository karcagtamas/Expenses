package screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import java.math.BigDecimal

class HomeViewModel : ScreenModel {

    private var _expenses = mutableStateOf(emptyList<Expense>())

    var expenses: State<List<Expense>> = _expenses

    init {
        screenModelScope.launch {
            _expenses.value = listOf(Expense("alma", BigDecimal(12), Category.INCOME))
        }
    }

    fun add(expense: Expense) {
        screenModelScope.launch {
            _expenses.value = _expenses.value.plus(expense)
        }
    }

    fun edit(expense: Expense) {
        screenModelScope.launch {
            _expenses.value = _expenses.value.map { if (it.id != expense.id) it else expense }
        }
    }
}