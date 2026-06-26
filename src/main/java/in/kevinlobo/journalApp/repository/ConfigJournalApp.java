package in.kevinlobo.journalApp.repository;

import in.kevinlobo.journalApp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalApp extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
