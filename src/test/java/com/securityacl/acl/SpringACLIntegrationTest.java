package com.securityacl.acl;

import com.securityacl.persistence.entity.NoticeMessage;
import com.securityacl.persistence.repository.NoticeMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringACLIntegrationTest {

    private static Integer FIRST_MESSAGE_ID = 1;
    private static Integer SECOND_MESSAGE_ID = 2;
    private static Integer THIRD_MESSAGE_ID = 3;
    private static String EDITTED_CONTENT = "EDITED";

    @Autowired
    NoticeMessageRepository noticeMessageRepository;

    @Test
    @WithMockUser(username="manager")
    public void givenUserManager_whenFindAllMessage_thenReturnFirstMessage(){
        List<NoticeMessage> details = noticeMessageRepository.findAll();
        assertNotNull(details);
        assertEquals(1,details.size());
        assertEquals(FIRST_MESSAGE_ID,details.get(0).getId());
    }

    @Test
    @WithMockUser(roles={"EDITOR"})
    public void givenRoleEditor_whenFindAllMessage_thenReturn3Message(){
        List<NoticeMessage> details = noticeMessageRepository.findAll();
        assertNotNull(details);
        assertEquals(3,details.size());
    }
}
