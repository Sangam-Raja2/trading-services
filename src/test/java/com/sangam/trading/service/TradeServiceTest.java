/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sangam.trading.service;

import com.sangam.trading.dao.TradeRepository;
import com.sangam.trading.dao.UsersRepository;
import com.sangam.trading.entity.Trade;
import com.sangam.trading.entity.User;
import com.sangam.trading.model.PriceModel;
import com.sangam.trading.model.TradeModel;
import com.sangam.trading.model.UserModel;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author Wildjasmine
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;
    @Mock
    UsersRepository userRepositroy;
    @InjectMocks
    TradeService tradeService;
    User user;
    Trade trade;
    public TradeServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
         user = new User();
         user.setUserId(1l);
         user.setUserName("sangam");
         user.setEmail("sangam@gmail.com");
         user.setAddress("bangalore");
         trade=new Trade();
         trade.setPrice(123.0);
         trade.setSymbol("bed");
         trade.setShares("25");
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createTrade method, of class TradeService.
     */
    @Test
    public void testCreateTrade() throws Exception {
         UserModel muckUser = new UserModel(1, "sangam", "sangam@gmail.com", "india");
        TradeModel mockTrade = new TradeModel(1, "buy", "bed", "25", 100, muckUser);
        System.out.println("createTrade");
         when(tradeRepository.getByTradeId(Mockito.any(Long.class)))
                .thenReturn(null);
        when(userRepositroy.save(Mockito.any(User.class)))
                .thenReturn(user);
        when(tradeRepository.save(Mockito.any(Trade.class)))
                .thenReturn(trade);
        TradeModel result = tradeService.createTrade(mockTrade);

        System.out.println("result>>" + result);
         assertEquals(user.getUserName(), result.getUser().getUserName());
        assertEquals(trade.getShares(), result.getShares());
        assertEquals(trade.getSymbol(), result.getSymbol());
    }

    /**
     * Test of getTradesByUser method, of class TradeService.
     */
//    @Test
//    public void testGetTradesByUser() {
//        System.out.println("getTradesByUser");
//        long userId = 0L;
//        TradeService instance = new TradeService();
//        List<User> expResult = null;
//        List<User> result = instance.getTradesByUser(userId);
//    }
//
//    /**
//     * Test of findAllTrades method, of class TradeService.
//     */
//    @Test
//    public void testFindAllTrades() {
//        System.out.println("findAllTrades");
//        TradeService instance = new TradeService();
//        List<Trade> expResult = null;
//        List<Trade> result = instance.findAllTrades();
//    }
//
//    /**
//     * Test of deleteByTrade method, of class TradeService.
//     */
//    @Test
//    public void testDeleteByTrade() {
//        System.out.println("deleteByTrade");
//        long userId = 0L;
//        TradeService instance = new TradeService();
//    }
//
//    /**
//     * Test of getTradeByStrockandTradeType method, of class TradeService.
//     */
//    @Test
//    public void testGetTradeByStrockandTradeType() {
//        System.out.println("getTradeByStrockandTradeType");
//        String symbol = "";
//        String type = "";
//        Date startDate = null;
//        Date endDate = null;
//        TradeService instance = new TradeService();
//        List<Trade> expResult = null;
//        List<Trade> result = instance.getTradeByStrockandTradeType(symbol, type, startDate, endDate);
//    }
//
//    /**
//     * Test of getTradeByStrockPrice method, of class TradeService.
//     */
//    @Test
//    public void testGetTradeByStrockPrice() {
//        System.out.println("getTradeByStrockPrice");
//        String symbol = "";
//        Date startDate = null;
//        Date endDate = null;
//        TradeService instance = new TradeService();
//        PriceModel expResult = null;
//        PriceModel result = instance.getTradeByStrockPrice(symbol, startDate, endDate);
//    }

}
