package com.client.ws.rasmooplus.service;

import com.client.ws.rasmooplus.Model.jpa.User;
import com.client.ws.rasmooplus.Model.jpa.UserType;
import com.client.ws.rasmooplus.dto.UserDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;
import com.client.ws.rasmooplus.repository.jpa.SubscriptionTypeRepository;
import com.client.ws.rasmooplus.repository.jpa.UserRepository;
import com.client.ws.rasmooplus.repository.jpa.UserTypeRepository;
import com.client.ws.rasmooplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;



    private UserDto userDto;

    @BeforeEach
    public void loadUser(){
        userDto = new UserDto();
        userDto.setEmail("email@email.com");
        userDto.setNome("Osvaldo da silva");
        userDto.setCpf("11111111111");
        userDto.setPhone("3198989898");
        userDto.setUserTypeId(1L);

    }
    @Test
    void given_create_when_idisNullAndUserIdFound_then_returnUserCreated(){
        userDto.setId(null);
        UserType userType = getUserType();
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(userType));

        User user = getUser();

        when(userRepository.save(user)).thenReturn(user);

       Assertions.assertEquals(user,userService.create(userDto));
       verify(userTypeRepository,times(1)).findById(1L);
       verify(userRepository,times(1)).save(user);

    }
    @Test
    void given_create_when_idisNotNullAndUserIdFound_then_throwBadResquestException(){

        userDto.setId(1L);
        userDto.setEmail("email@email.com");
        userDto.setNome("Osvaldo da silva");
        userDto.setCpf("11111111111");
        userDto.setPhone("3198989898");
        userDto.setUserTypeId(1L);
        Assertions.assertThrows(BadRequestException.class,() ->userService.create(userDto));
        verify(userTypeRepository,times(0)).findById(any());
        verify(userRepository,times(0)).save(any());

    }

    @Test
    void given_create_when_idisNullAndUserTypeisNotFound_then_thownNotFoundException(){
        userDto.setId(null);
        when(userTypeRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundException.class,() ->userService.create(userDto) );
        verify(userTypeRepository,times(1)).findById(any());
        verify(userRepository,times(0)).save(any());

    }
    @Test

    void given_uploadPhoto_thereIsUserAndFileItsPNGOrJPEG_then_UpdatePhotoAndReturnUser() throws Exception{
        FileInputStream fis = new FileInputStream("src/test/resources/static/teste.png");
        MockMultipartFile file = new MockMultipartFile("file","teste.png", MediaType.MULTIPART_FORM_DATA_VALUE,fis);
        User user = getUser();
        user.setId(2L);
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        User userReturn = userService.uploadPhoto(2L,file);
        Assertions.assertNotNull(userReturn);
        Assertions.assertNotNull(userReturn.getPhoto());
        Assertions.assertEquals("teste.png",userReturn.getPhotoName());
        Mockito.verify(userRepository, Mockito.times(1)).findById(2L);

    }
    @Test
    void given_uploadPhoto_thereIsUserAndFileItsNotPNGOrJPEG_then_throwBadRequest() throws Exception{
        FileInputStream fis = new FileInputStream("src/test/resources/static/teste.png");
        MockMultipartFile file = new MockMultipartFile("file","teste.txt", MediaType.MULTIPART_FORM_DATA_VALUE,fis);

       Assertions.assertThrows(BadRequestException.class,()->userService.uploadPhoto(2L,file)) ;

        Mockito.verify(userRepository, Mockito.times(0)).findById(any());

    }

    private UserType getUserType(){
        UserType userType = new UserType(1L,"aluno","aluo rasmoo");
        return userType;

    }
    private User getUser(){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setNome(userDto.getNome());
        user.setCpf(userDto.getCpf());
        user.setPhone(userDto.getPhone());
        user.setUserType(getUserType());
        user.setDtExpiration(userDto.getDtExpiration());
        user.setDtSubscription(userDto.getDtSubscription());
        return user;

    }

}