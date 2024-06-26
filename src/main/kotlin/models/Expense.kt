package models

import java.math.BigDecimal
import java.time.LocalDate

data class Expense(val id: String, var value: BigDecimal, var category: Category, var date: LocalDate)
