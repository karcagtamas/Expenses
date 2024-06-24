package screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import java.math.BigDecimal

class EditViewModel(val init: Expense, val onSave: (Expense) -> Unit) : ScreenModel {

    private var _value = mutableStateOf(init.value)
    private var _category = mutableStateOf(init.category)

    var value: State<BigDecimal> = _value
    var category: State<Category> = _category

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

    fun save() {
        onSave(Expense(init.id, _value.value, _category.value))
    }
}