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
import java.util.ArrayList;
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
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

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
    Double min = 125.0;
    Double max = 12555.0;
    List<Trade> tradeList;

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
        trade = new Trade();
        trade.setTimestamp(new Date());
        trade.setType("buy");
        trade.setSymbol("bed");
        trade.setShares("25");
        trade.setPrice(25000.0);
        Trade buy = new Trade();
        buy.setTimestamp(new Date());
        buy.setType("buy");
        buy.setSymbol("ac");
        buy.setShares("25");
        buy.setPrice(25000.0);
        Trade sell = new Trade();
        sell.setTimestamp(new Date());
        sell.setType("buy");
        sell.setSymbol("bed");
        sell.setShares("25");
        sell.setPrice(25000.0);
        tradeList = new ArrayList<>();
        tradeList.add(trade);
        tradeList.add(buy);
        tradeList.add(sell);
        user.setTradeList(tradeList);
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
    @Test
    public void testGetTradesByUser() {
        System.out.println("getTradesByUser");
        when(userRepositroy.getByUserId(Mockito.any(Long.class)))
                .thenReturn(user);
        TradeService instance = new TradeService();
        List<User> expResult = null;
        User result = tradeService.getTradesByUser(1l);
        assertEquals(user.getTradeList(), result.getTradeList());

    }

    /**
     * Test of findAllTrades method, of class TradeService.
     */
    @Test
    public void testFindAllTrades() {
        System.out.println("findAllTrades");
        when(tradeRepository.findAll())
                .thenReturn(tradeList);
        List<Trade> expResult = null;
        List<Trade> result = tradeService.findAllTrades();
        assertEquals(tradeList, result);
    }

    /**
     * Test of deleteByTrade method, of class TradeService.
     */
    @Test
    public void testdeleteAllTrades() {
        System.out.println("deleteAllTrades");
        Boolean deleteAllTrades = tradeService.deleteAllTrades();
        assertEquals(deleteAllTrades, true);
    }

    /**
     * Test of getTradeByStrockandTradeType method, of class TradeService.
     */
    @Test
    public void testGetTradeByStrockandTradeType() {
        System.out.println("getTradeByStrockandTradeType");
        String symbol = "Ac";
        String type = "sell";
        Date startDate = new Date();
        Date endDate = new Date();
        when(tradeRepository.findBySymbolandType(Mockito.any(String.class),
                Mockito.any(String.class), Mockito.any(Date.class),
                Mockito.any(Date.class)))
                .thenReturn(tradeList);
        List<Trade> result = tradeService.getTradeByStrockandTradeType(symbol,
                type, startDate, endDate);
        assertEquals(tradeList, result);
    }

    /**
     * Test of getTradeByStrockPrice method, of class TradeService.
     */
    @Test
    public void testGetTradeByStrockPrice() {
        System.out.println("getTradeByStrockPrice");
        String symbol = "Ac";
        Date startDate = new Date();
        Date endDate = new Date();
        when(tradeRepository.findByMaxPriceSymbol(Mockito.any(String.class),
                Mockito.any(Date.class),
                Mockito.any(Date.class)))
                .thenReturn(max);
        when(tradeRepository.findByMinPriceSymbol(Mockito.any(String.class),
                Mockito.any(Date.class),
                Mockito.any(Date.class)))
                .thenReturn(min);
        PriceModel result = tradeService.getTradeByStrockPrice(symbol, startDate, endDate);
        assertEquals(max, result.getMax());
        assertEquals(min, result.getMin());
    }

    private ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // modelMapper Configuration
        modelMapper.getConfiguration().setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper;
    }
}
