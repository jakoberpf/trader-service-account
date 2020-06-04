package de.ginisolutions.trader.account.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import de.ginisolutions.trader.account.web.rest.TestUtil;

public class UserAccountDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAccountDTO.class);
        UserAccountDTO userAccountDTO1 = new UserAccountDTO();
        userAccountDTO1.setId("id1");
        UserAccountDTO userAccountDTO2 = new UserAccountDTO();
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
        userAccountDTO2.setId(userAccountDTO1.getId());
        assertThat(userAccountDTO1).isEqualTo(userAccountDTO2);
        userAccountDTO2.setId("id2");
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
        userAccountDTO1.setId(null);
        assertThat(userAccountDTO1).isNotEqualTo(userAccountDTO2);
    }
}
