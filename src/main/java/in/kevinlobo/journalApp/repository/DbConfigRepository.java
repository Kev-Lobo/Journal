package in.kevinlobo.journalApp.repository;

import in.kevinlobo.journalApp.entity.DbConfigEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DbConfigRepository extends MongoRepository<DbConfigEntity, String> {

    DbConfigEntity findByKey(String key);
}
