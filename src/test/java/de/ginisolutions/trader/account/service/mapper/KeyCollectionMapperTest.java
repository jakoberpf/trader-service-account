package de.ginisolutions.trader.account.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KeyCollectionMapperTest {

    private KeyCollectionMapper keyCollectionMapper;

    @BeforeEach
    public void setUp() {
        keyCollectionMapper = new KeyCollectionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(keyCollectionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(keyCollectionMapper.fromId(null)).isNull();
    }
}
