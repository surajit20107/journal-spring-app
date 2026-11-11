package dev.surajit.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import dev.surajit.journalApp.entity.JournalEntity;

public interface JournalRepository extends MongoRepository<JournalEntity, ObjectId> {
}
