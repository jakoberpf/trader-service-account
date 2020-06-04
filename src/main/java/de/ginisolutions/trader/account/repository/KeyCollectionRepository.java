package de.ginisolutions.trader.account.repository;

import de.ginisolutions.trader.account.domain.KeyCollection;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the KeyCollection entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeyCollectionRepository extends MongoRepository<KeyCollection, String> {
}
