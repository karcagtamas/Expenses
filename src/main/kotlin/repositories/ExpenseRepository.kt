package repositories

import database.ExpenseEntry
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun getAll(): Flow<List<ExpenseEntry>>
}