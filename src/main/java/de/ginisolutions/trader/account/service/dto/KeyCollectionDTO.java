package de.ginisolutions.trader.account.service.dto;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link de.ginisolutions.trader.account.domain.KeyCollection} entity.
 */
@ApiModel(description = "The KeyCollection entity.\n@author A true hipster")
public class KeyCollectionDTO implements Serializable {
    
    private String id;

    @NotNull
    private String owner;

    
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KeyCollectionDTO)) {
            return false;
        }

        return id != null && id.equals(((KeyCollectionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyCollectionDTO{" +
            "id=" + getId() +
            ", owner='" + getOwner() + "'" +
            "}";
    }
}
