package screen.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import models.Expense

class DetailsViewModel(expense: Expense) : ScreenModel {

    private var _expense = mutableStateOf(expense)

    var expense: State<Expense> = _expense
}