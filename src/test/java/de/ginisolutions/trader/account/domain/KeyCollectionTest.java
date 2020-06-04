package de.ginisolutions.trader.account.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.account.web.rest.TestUtil;

public class KeyCollectionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(KeyCollection.class);
        KeyCollection keyCollection1 = new KeyCollection();
        keyCollection1.setId("id1");
        KeyCollection keyCollection2 = new KeyCollection();
        keyCollection2.setId(keyCollection1.getId());
        assertThat(keyCollection1).isEqualTo(keyCollection2);
        keyCollection2.setId("id2");
        assertThat(keyCollection1).isNotEqualTo(keyCollection2);
        keyCollection1.setId(null);
        assertThat(keyCollection1).isNotEqualTo(keyCollection2);
    }
}
