package models

import java.math.BigDecimal

data class Expense(val id: String, val value: BigDecimal, val category: Category)
