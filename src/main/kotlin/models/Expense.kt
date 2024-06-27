package models

import java.math.BigDecimal
import java.time.LocalDate

data class Expense(
    val id: String,
    var value: BigDecimal,
    var category: Category,
    var date: LocalDate = LocalDate.now(),
    var connection: String? = null
) {

    fun isPaidBack(): Boolean {
        return category == Category.DEPOSIT && connection != null
    }

    fun isPayingBackDeposit(): Boolean {
        return category == Category.INCOME && connection != null
    }
}
