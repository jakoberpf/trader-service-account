package de.ginisolutions.trader.account.repository;

import de.ginisolutions.trader.account.domain.KeySet;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the KeySet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KeySetRepository extends MongoRepository<KeySet, String> {
}
