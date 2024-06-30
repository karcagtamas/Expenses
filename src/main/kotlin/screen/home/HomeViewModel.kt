package screen.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

class HomeViewModel() : ScreenModel {

    private var _expenses = mutableStateOf(emptyList<Expense>())

    var expenses: State<List<Expense>> = _expenses

    init {
        screenModelScope.launch {
            _expenses.value = listOf(Expense("alma", BigDecimal(12), Category.INCOME, LocalDate.now()))
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

    fun payBack(expense: Expense) {
        if (expense.connection == null && expense.category == Category.DEPOSIT) {
            val newExpense =
                Expense(UUID.randomUUID().toString(), expense.value, Category.INCOME, LocalDate.now(), expense.id)
            expense.connection = newExpense.id
            edit(expense)
            add(newExpense)
        }
    }

    fun remove(expense: Expense) {
        screenModelScope.launch {
            val connection = expense.connection
            val removeOther = expense.category == Category.DEPOSIT
            _expenses.value = _expenses.value.filter { it.id != expense.id && (!removeOther || it.id != connection) }.map {
                if (it.id == connection) {
                    it.connection = null
                }

                it
            }
        }
    }

    fun getTotal(): BigDecimal {
        return _expenses.value
            .map {
                when (it.category) {
                    Category.INCOME -> it.value
                    Category.OUTCOME -> it.value.multiply(BigDecimal(-1))
                    Category.DEPOSIT -> it.value.multiply(BigDecimal(-1))
                }
            }
            .sumOf { it }
    }

    fun getTotalIncome(): BigDecimal {
        return getTotalByCategory(Category.INCOME)
    }

    fun getTotalOutCome(): BigDecimal {
        return getTotalByCategory(Category.OUTCOME)
    }

    fun getTotalDeposit(): BigDecimal {
        return getTotalByCategory(Category.DEPOSIT)
    }

    private fun getTotalByCategory(category: Category): BigDecimal {
        return _expenses.value
            .filter { it.category == category }
            .sumOf { it.value }
    }
}