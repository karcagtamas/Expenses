package screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import java.math.BigDecimal
import java.time.LocalDate

class EditViewModel(val init: Expense, val onSave: (Expense) -> Unit) : ScreenModel {

    private var _value = mutableStateOf(init.value)
    private var _category = mutableStateOf(init.category)
    private var _date = mutableStateOf(init.date)

    var value: State<BigDecimal> = _value
    var category: State<Category> = _category
    var date: State<LocalDate> = _date

    fun setValue(newValue: BigDecimal) {
        screenModelScope.launch {
            _value.value = newValue
        }
    }

    fun setCategory(newCategory: Category) {
        screenModelScope.launch {
            _category.value = newCategory
        }
    }

    fun setDate(newDate: LocalDate) {
        screenModelScope.launch {
            _date.value = newDate
        }
    }

    fun save() {
        onSave(Expense(init.id, _value.value, _category.value, _date.value))
    }

    fun canPayBack(): Boolean {
        return init.category == Category.DEPOSIT && !isPaidBack()
    }

    fun isPaidBack(): Boolean {
        return init.category == Category.DEPOSIT && init.connection != null
    }

    fun hasConnection(): Boolean {
        return init.connection != null
    }
}