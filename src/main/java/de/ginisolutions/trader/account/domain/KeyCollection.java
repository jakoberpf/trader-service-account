package de.ginisolutions.trader.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The KeyCollection entity.\n@author A true hipster
 */
@Document(collection = "key_collection")
public class KeyCollection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("owner")
    private String owner;

    @DBRef
    @Field("keySet")
    private Set<KeySet> keySets = new HashSet<>();

    @DBRef
    @Field("userAccount")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UserAccount userAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public KeyCollection owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Set<KeySet> getKeySets() {
        return keySets;
    }

    public KeyCollection keySets(Set<KeySet> keySets) {
        this.keySets = keySets;
        return this;
    }

    public KeyCollection addKeySet(KeySet keySet) {
        this.keySets.add(keySet);
        keySet.setKeyCollection(this);
        return this;
    }

    public KeyCollection removeKeySet(KeySet keySet) {
        this.keySets.remove(keySet);
        keySet.setKeyCollection(null);
        return this;
    }

    public void setKeySets(Set<KeySet> keySets) {
        this.keySets = keySets;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public KeyCollection userAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyCollection)) {
            return false;
        }
        return id != null && id.equals(((KeyCollection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyCollection{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
