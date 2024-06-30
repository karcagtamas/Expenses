package screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import repositories.ExpenseRepository
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

class HomeViewModel(val expenseRepository: ExpenseRepository) : ScreenModel {

    private var _expenses = mutableStateOf(emptyList<Expense>())

    var expenses: State<List<Expense>> = _expenses

    init {
        screenModelScope.launch {
            expenseRepository.getAll().collect {
                _expenses.value = it.map {
                    Expense.fromEntry(it)
                }
            }
        }
    }

    fun add(expense: Expense) {
        screenModelScope.launch {
            _expenses.value = _expenses.value.plus(expense)
            expenseRepository.insert(
                expense.toEntry()
            )
        }
    }

    fun edit(expense: Expense) {
        screenModelScope.launch {
            _expenses.value = _expenses.value.map { if (it.id != expense.id) it else expense }
            expenseRepository.update(expense.toEntry())
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

            val other = _expenses.value.filter { it.id == connection }.firstOrNull()

            _expenses.value =
                _expenses.value.filter { it.id != expense.id && (!removeOther || it.id != connection) }.map {
                    if (it.id == connection) {
                        it.connection = null
                    }

                    it
                }

            expenseRepository.delete(expense.toEntry())

            if (removeOther && other != null) {
                expenseRepository.delete(expense.toEntry())
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