package bespalhuk.kcoroutines.core.port.input

fun interface DeleteUserPortIn {
    suspend fun delete(id: String)
}
