package com.client.ws.rasmooplus.repository.jpa;

import com.client.ws.rasmooplus.Model.jpa.SubscriptionType;
import com.client.ws.rasmooplus.controller.AuthenticationController;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@WebMvcTest(SubscriptionTypeRepository.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles(profiles = "test")
@TestPropertySource(properties = {
        "spring.flyway.enabled=false"
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionTypeRepositoryTest {

    @Autowired
    private  SubscriptionTypeRepository subscriptionTypeRepository;

    @BeforeAll
    public  void loadSubscriptiosType(){
        List<SubscriptionType> listaSubscriptionType = new ArrayList<>();
        SubscriptionType subscriptionType1 = new SubscriptionType();
        subscriptionType1.setId(null);
        subscriptionType1.setName("ANUAL");
        subscriptionType1.setAccessMonths(12L);
        subscriptionType1.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType1.setProductKey("PRODANO");
        listaSubscriptionType.add(subscriptionType1);
        SubscriptionType subscriptionType2 = new SubscriptionType();
        subscriptionType2.setId(null);
        subscriptionType2.setName("VITALICIO");
        subscriptionType2.setAccessMonths(12L);
        subscriptionType2.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType2.setProductKey("VITALICIO");
        listaSubscriptionType.add(subscriptionType2);
        SubscriptionType subscriptionType3 = new SubscriptionType();
        subscriptionType3.setId(null);
        subscriptionType3.setName("MENSAL");
        subscriptionType3.setAccessMonths(12L);
        subscriptionType3.setPrice(BigDecimal.valueOf(2000.00));
        subscriptionType3.setProductKey("MENSAL");
        listaSubscriptionType.add(subscriptionType3);

        subscriptionTypeRepository.saveAll(listaSubscriptionType);
    }

    @Test
    void given_findByProductKey_when_getProductKey_then_returnCorrectSubscriptionType(){
        Assertions.assertEquals("VITALICIO",subscriptionTypeRepository.findByProductKey("VITALICIO").get().getName());
        Assertions.assertEquals("ANUAL",subscriptionTypeRepository.findByProductKey("PRODANO").get().getName());
        Assertions.assertEquals("MENSAL",subscriptionTypeRepository.findByProductKey("MENSAL").get().getName());

    }
    @AfterAll
    public void dropDataBase() {
        subscriptionTypeRepository.deleteAll();
    }


}