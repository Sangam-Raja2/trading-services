/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sangam.trading.controller;

import com.sangam.trading.dao.UsersRepository;
import com.sangam.trading.model.TradeModel;
import com.sangam.trading.model.UserModel;
import com.sangam.trading.service.TradeService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 *
 * @author user
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    TradeService tradeService;

    @MockBean
    UsersRepository userRepositroy;
    String url = "/trades";

    public TradeControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createTrades method, of class TradeController.
     */
    @Test
    public void testCreateTrades() throws Exception {
        System.out.println("createTrades");
        UserModel user = new UserModel(1, "sangam", "sangam@gmail.com", "india");
        TradeModel mockTrade = new TradeModel(1, "buy", "bed", "25", 100, user);
        String JsonPayload = "{\r\n"
                + "    \"tradeId\": \"1\",\r\n"
                + "    \"type\": \"buy\",\r\n"
                + "    \"symbol\": \"bed\",\r\n"
                + "    \"shares\": \"25\",\r\n"
                + "    \"price\": \"100\",\r\n"
                + "    \"timestamp\": \"2020-09-11 10:09:09\",\r\n"
                + "    \"user\": {\r\n"
                + "        \"userId\": \"1\",\r\n"
                + "        \"userName\": \"sangam\",\r\n"
                + "        \"email\": \"sangam@gmail.com\",\r\n"
                + "        \"address\": \"india\"\r\n"
                + "    }\r\n"
                + "}";

        // tradeService.createTrade to respond back with mockTrade
        Mockito.when(
                tradeService.createTrade(mockTrade)).thenReturn(mockTrade);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(url)
                .accept(MediaType.APPLICATION_JSON).content(JsonPayload)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        
    }

    /**
     * Test of gettradesByUser method, of class TradeController.
     */
    @Test
    public void testGettradesByUser() throws Exception {
        System.out.println("gettradesByUser");
        long userId = 2L;
        RequestBuilder request = MockMvcRequestBuilders
                .get(url + "/user/" + userId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
       
    }

    /**
     * Test of getAllRecords method, of class TradeController.
     */
    @Test
    public void testGetAllRecords() throws Exception {
        System.out.println("getAllRecords");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                url ).accept(
                        MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
       
    }

    /**
     * Test of getStocks method, of class TradeController.
     */
    @Test
    public void testGetStocks() throws Exception {
        System.out.println("getStocks");
        String symbol = "AC";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                url + "/stocks/" + symbol + "/trades").param("type", "shell")
                .param("start", "2020-11-18")
                .param("end", "2020-12-19").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
       
    }

    /**
     * Test of getStocksByPrice method, of class TradeController.
     */
    @Test
    public void testGetStocksByPrice() throws Exception {
        System.out.println("getStocksByPrice");
        String symbol = "AC";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                url + "/stocks/" + symbol + "/price")
                .param("start", "2020-11-18")
                .param("end", "2020-12-19").accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
       
    }

}
