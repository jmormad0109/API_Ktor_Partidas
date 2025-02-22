package data.persistence

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> suspendTransaction (code: Transaction.() -> T) : T = newSuspendedTransaction(Dispatchers.IO, statement = code)
