package bespalhuk.kcoroutines.core.port.output

fun interface DeleteUserPortOut {
    suspend fun delete(id: String)
}
