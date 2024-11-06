package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserTypeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
 class UserTypeServiceTest {
    @Mock
    private UserTypeRepository userTypeRepository;
    @InjectMocks
    private UserTypeServiceImpl userTypeService;
    @Test
    void given_findAll_when_thereAreDatasInDataBase_then_returnAllDatas(){
       List<UserType> listUserType = new ArrayList<>();
       UserType userType1 = new UserType(1L,"ADMIN","Usuario  administrador");
       UserType userType2 = new UserType(1L,"PROFESSOR","Usuario  professor");
       UserType userType3 = new UserType(1L,"ALUNO","Usuario  aluno");
       listUserType.add(userType1);
       listUserType.add(userType2);
       listUserType.add(userType3);
       Mockito.when(userTypeRepository.findAll()).thenReturn(listUserType);
       Assertions.assertThat(userTypeService.findAll()).isNotEmpty();
       Assertions.assertThat(userTypeService.findAll()).hasSize(3);

    }
}
