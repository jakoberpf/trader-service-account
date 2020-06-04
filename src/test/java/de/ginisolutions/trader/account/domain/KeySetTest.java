package de.ginisolutions.trader.account.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.account.web.rest.TestUtil;

public class KeySetTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeySet.class);
        KeySet keySet1 = new KeySet();
        keySet1.setId("id1");
        KeySet keySet2 = new KeySet();
        keySet2.setId(keySet1.getId());
        assertThat(keySet1).isEqualTo(keySet2);
        keySet2.setId("id2");
        assertThat(keySet1).isNotEqualTo(keySet2);
        keySet1.setId(null);
        assertThat(keySet1).isNotEqualTo(keySet2);
    }
}
