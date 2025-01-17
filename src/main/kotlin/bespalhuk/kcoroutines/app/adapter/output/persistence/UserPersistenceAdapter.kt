package bespalhuk.kcoroutines.app.adapter.output.persistence

import bespalhuk.kcoroutines.app.adapter.output.persistence.mapper.toDocument
import bespalhuk.kcoroutines.app.adapter.output.persistence.mapper.toDomain
import bespalhuk.kcoroutines.core.common.exception.UserNotFoundException
import bespalhuk.kcoroutines.core.domain.User
import bespalhuk.kcoroutines.core.port.output.DeleteUserPortOut
import bespalhuk.kcoroutines.core.port.output.FindUserPortOut
import bespalhuk.kcoroutines.core.port.output.SaveUserPortOut
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userDocumentRepository: UserDocumentRepository,
) : SaveUserPortOut, FindUserPortOut, DeleteUserPortOut {

    override suspend fun save(user: User): User {
        val document = user.toDocument()
        val saved = userDocumentRepository.save(document)
        return saved.toDomain()
    }

    override suspend fun find(id: String): User {
        val document = userDocumentRepository.findById(id)
        return document?.toDomain() ?: throw UserNotFoundException()
    }

    override suspend fun delete(id: String) {
        userDocumentRepository.deleteById(id)
    }
}
