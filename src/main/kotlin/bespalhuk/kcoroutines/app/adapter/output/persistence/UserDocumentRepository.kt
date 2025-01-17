package bespalhuk.kcoroutines.app.adapter.output.persistence

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDocumentRepository : CoroutineCrudRepository<UserDocument, String>
