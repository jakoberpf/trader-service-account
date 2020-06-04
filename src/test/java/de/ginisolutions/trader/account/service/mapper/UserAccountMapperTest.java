package de.ginisolutions.trader.account.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserAccountMapperTest {

    private UserAccountMapper userAccountMapper;

    @BeforeEach
    public void setUp() {
        userAccountMapper = new UserAccountMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id = "id1";
        assertThat(userAccountMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userAccountMapper.fromId(null)).isNull();
    }
}
