package de.ginisolutions.trader.account.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

import de.ginisolutions.trader.account.domain.enumeration.MARKET;

/**
 * The KeySet entity.\n@author A true hipster
 */
@Document(collection = "key_set")
public class KeySet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("owner")
    private String owner;

    @NotNull
    @Field("market")
    private MARKET market;

    @NotNull
    @Field("api_key")
    private String apiKey;

    @NotNull
    @Field("api_secret")
    private String apiSecret;

    @DBRef
    @Field("keyCollection")
    @JsonIgnoreProperties(value = "keySets", allowSetters = true)
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

    public KeySet owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public MARKET getMarket() {
        return market;
    }

    public KeySet market(MARKET market) {
        this.market = market;
        return this;
    }

    public void setMarket(MARKET market) {
        this.market = market;
    }

    public String getApiKey() {
        return apiKey;
    }

    public KeySet apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public KeySet apiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
        return this;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public KeyCollection getKeyCollection() {
        return keyCollection;
    }

    public KeySet keyCollection(KeyCollection keyCollection) {
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
        if (!(o instanceof KeySet)) {
            return false;
        }
        return id != null && id.equals(((KeySet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeySet{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            ", market='" + getMarket() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", apiSecret='" + getApiSecret() + "'" +
            "}";
    }
}
