package repositories

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import database.AppDatabase
import database.ExpenseEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class ExpenseRepositoryImpl(private val database: AppDatabase) : ExpenseRepository {

    override fun getAll(): Flow<List<ExpenseEntry>> {
        return database.expenseQueries.getAll()
            .asFlow()
            .mapToList(Dispatchers.IO)
    }
}