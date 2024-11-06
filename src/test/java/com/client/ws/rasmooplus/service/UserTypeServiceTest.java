package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
 class UserTypeServiceTest {
    @MockBean
    UserTypeService userTypeService;
    @Test
    void findAll(){
       List<UserType> listUserType = new ArrayList<>();
       UserType userType1 = new UserType(1L,"ADMIN","Usuario  administrador");
       UserType userType2 = new UserType(1L,"PROFESSOR","Usuario  professor");
       UserType userType3 = new UserType(1L,"ALUNO","Usuario  aluno");
       listUserType.add(userType1);
       listUserType.add(userType2);
       listUserType.add(userType3);
       Mockito.when(userTypeService.findAll()).thenReturn(listUserType);
       Assertions.assertThat(userTypeService.findAll()).isNotEmpty();

    }
}
