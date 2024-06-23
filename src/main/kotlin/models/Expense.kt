package models

import java.math.BigDecimal

data class Expense(val id: String, var value: BigDecimal, var category: Category)
