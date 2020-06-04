package de.ginisolutions.trader.account.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.account.web.rest.TestUtil;

public class KeyCollectionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyCollectionDTO.class);
        KeyCollectionDTO keyCollectionDTO1 = new KeyCollectionDTO();
        keyCollectionDTO1.setId("id1");
        KeyCollectionDTO keyCollectionDTO2 = new KeyCollectionDTO();
        assertThat(keyCollectionDTO1).isNotEqualTo(keyCollectionDTO2);
        keyCollectionDTO2.setId(keyCollectionDTO1.getId());
        assertThat(keyCollectionDTO1).isEqualTo(keyCollectionDTO2);
        keyCollectionDTO2.setId("id2");
        assertThat(keyCollectionDTO1).isNotEqualTo(keyCollectionDTO2);
        keyCollectionDTO1.setId(null);
        assertThat(keyCollectionDTO1).isNotEqualTo(keyCollectionDTO2);
    }
}
