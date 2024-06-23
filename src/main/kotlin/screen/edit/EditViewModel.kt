package screen.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import models.Category
import models.Expense
import java.math.BigDecimal

class EditViewModel(init: Expense) : ScreenModel {

    private var _expense = mutableStateOf(init)

    var expense: State<Expense> = _expense

    fun setValue(value: BigDecimal) {
        screenModelScope.launch {
            expense.value.value = value
        }
    }

    fun setCategory(category: Category) {
        screenModelScope.launch {
            expense.value.category = category
        }
    }
}