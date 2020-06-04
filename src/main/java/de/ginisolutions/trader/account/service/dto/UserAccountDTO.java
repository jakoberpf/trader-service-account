package de.ginisolutions.trader.account.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.ginisolutions.trader.account.domain.UserAccount} entity.
 */
@ApiModel(description = "The UserAccount entity.\n@author A true hipster")
public class UserAccountDTO implements Serializable {
    
    private String id;

    @NotNull
    private String owner;


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
        if (!(o instanceof UserAccountDTO)) {
            return false;
        }

        return id != null && id.equals(((UserAccountDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAccountDTO{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            ", keyCollectionId='" + getKeyCollectionId() + "'" +
            "}";
    }
}
