package models

import database.ExpenseEntry
import java.math.BigDecimal
import java.time.LocalDate

data class Expense(
    val id: String,
    var value: BigDecimal,
    var category: Category,
    var date: LocalDate = LocalDate.now(),
    var connection: String? = null
) {
    companion object {
        fun fromEntry(entry: ExpenseEntry): Expense {
            return Expense(
                entry.id,
                entry.value_.toBigDecimal(),
                Category.entries[entry.category.toInt()],
                LocalDate.ofEpochDay(entry.date.toLong()),
                entry.connection
            )
        }
    }

    fun isPaidBack(): Boolean {
        return category == Category.DEPOSIT && connection != null
    }

    fun isPayingBackDeposit(): Boolean {
        return category == Category.INCOME && connection != null
    }

    fun toEntry(): ExpenseEntry {
        return ExpenseEntry(
            id,
            value.toLong(),
            category.ordinal.toLong(),
            date.toEpochDay().toDouble(),
            connection
        )
    }
}
