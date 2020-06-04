package de.ginisolutions.trader.account.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class KeySetMapperTest {

    private KeySetMapper keySetMapper;

    @BeforeEach
    public void setUp() {
        keySetMapper = new KeySetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(keySetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(keySetMapper.fromId(null)).isNull();
    }
}
