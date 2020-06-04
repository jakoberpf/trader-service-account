package de.ginisolutions.trader.account.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.account.web.rest.TestUtil;

public class KeySetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeySetDTO.class);
        KeySetDTO keySetDTO1 = new KeySetDTO();
        keySetDTO1.setId("id1");
        KeySetDTO keySetDTO2 = new KeySetDTO();
        assertThat(keySetDTO1).isNotEqualTo(keySetDTO2);
        keySetDTO2.setId(keySetDTO1.getId());
        assertThat(keySetDTO1).isEqualTo(keySetDTO2);
        keySetDTO2.setId("id2");
        assertThat(keySetDTO1).isNotEqualTo(keySetDTO2);
        keySetDTO1.setId(null);
        assertThat(keySetDTO1).isNotEqualTo(keySetDTO2);
    }
}
