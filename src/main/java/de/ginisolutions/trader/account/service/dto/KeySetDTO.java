package de.ginisolutions.trader.account.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;
import de.ginisolutions.trader.account.domain.enumeration.MARKET;

/**
 * A DTO for the {@link de.ginisolutions.trader.account.domain.KeySet} entity.
 */
@ApiModel(description = "The KeySet entity.\n@author A true hipster")
public class KeySetDTO implements Serializable {
    
    private String id;

    @NotNull
    private String owner;

    @NotNull
    private MARKET market;

    @NotNull
    private String apiKey;

    @NotNull
    private String apiSecret;


    private String keyCollectionId;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public MARKET getMarket() {
        return market;
    }

    public void setMarket(MARKET market) {
        this.market = market;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getKeyCollectionId() {
        return keyCollectionId;
    }

    public void setKeyCollectionId(String keyCollectionId) {
        this.keyCollectionId = keyCollectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeySetDTO)) {
            return false;
        }

        return id != null && id.equals(((KeySetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeySetDTO{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            ", market='" + getMarket() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", apiSecret='" + getApiSecret() + "'" +
            ", keyCollectionId='" + getKeyCollectionId() + "'" +
            "}";
    }
}
