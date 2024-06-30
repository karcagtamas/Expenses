package repositories

import database.ExpenseEntry
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun getAll(): Flow<List<ExpenseEntry>>

    suspend fun insert(expense: ExpenseEntry)

    suspend fun update(expense: ExpenseEntry)

    suspend fun delete(expense: ExpenseEntry)
}