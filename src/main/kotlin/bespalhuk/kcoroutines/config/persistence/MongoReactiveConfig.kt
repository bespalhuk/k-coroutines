package bespalhuk.kcoroutines.config.persistence

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration
@EnableReactiveMongoRepositories(basePackages = ["bespalhuk.kcoroutines.app.adapter.output.persistence"])
class MongoReactiveConfig
