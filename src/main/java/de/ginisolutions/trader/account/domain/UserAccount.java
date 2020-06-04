package de.ginisolutions.trader.account.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * The UserAccount entity.\n@author A true hipster
 */
@Document(collection = "user_account")
public class UserAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("owner")
    private String owner;

    @DBRef
    @Field("keyCollection")
    private KeyCollection keyCollection;

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

    public UserAccount owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public KeyCollection getKeyCollection() {
        return keyCollection;
    }

    public UserAccount keyCollection(KeyCollection keyCollection) {
        this.keyCollection = keyCollection;
        return this;
    }

    public void setKeyCollection(KeyCollection keyCollection) {
        this.keyCollection = keyCollection;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAccount)) {
            return false;
        }
        return id != null && id.equals(((UserAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccount{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
