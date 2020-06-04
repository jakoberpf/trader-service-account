package de.ginisolutions.trader.account.repository;

import de.ginisolutions.trader.account.domain.UserAccount;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
}
