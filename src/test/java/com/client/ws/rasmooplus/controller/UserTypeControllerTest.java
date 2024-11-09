package com.client.ws.rasmooplus.controller;

import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.service.UserTypeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserTypeService userTypeService;

    @Test
    void given_findall_then_returnAllTypes() throws Exception {
        List<UserType> listUserType = new ArrayList<>();
        UserType userType1 = new UserType(1L,"ADMIN","Usuario  administrador");
        UserType userType2 = new UserType(1L,"PROFESSOR","Usuario  professor");
        UserType userType3 = new UserType(1L,"ALUNO","Usuario  aluno");
        listUserType.add(userType1);
        listUserType.add(userType2);
        listUserType.add(userType3);
        Mockito.when(userTypeService.findAll()).thenReturn(listUserType);
        mockMvc.perform(MockMvcRequestBuilders.get("/user-type"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}