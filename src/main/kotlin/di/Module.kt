package di

import database.AppDatabase
import database.DriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        AppDatabase(DriverFactory().createDriver())
    }
}