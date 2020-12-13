package com.sangam.trading.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sangam.trading.dao.TradeRepository;
import com.sangam.trading.dao.UsersRepository;
import com.sangam.trading.entity.Trade;
import com.sangam.trading.entity.User;
import com.sangam.trading.exception.TradeException;
import com.sangam.trading.model.PriceModel;
import com.sangam.trading.model.TradeModel;
import com.sangam.trading.model.UserModel;

import java.lang.reflect.Type;
import java.util.Optional;

/**
 *
 * @author Sangam
 */
@Service
public class TradeService {

    @Autowired
    TradeRepository tradeRepository;
    @Autowired
    UsersRepository userRepositroy;
    @Autowired
    ModelMapper modelMapper;

    /**
     * User create trade
     */
    public TradeModel createTrade(TradeModel tradeModel) {
        if (tradeModel.getTradeId() == 0) {
            throw new TradeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    " trade id is Invalid trade id should not be zero ",
                    " trade id is Invalid trade id should not be zero ");
        }
        Optional<UserModel> checkUser = Optional.ofNullable(tradeModel.getUser());
        if (checkUser.isPresent() && tradeModel.getUser().getUserId() == 0) {
            throw new TradeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    " userId is Invalid userId should not be null or zero",
                    " userId is Invalid userId should not be null or zero");
        }
        Trade tradeId = tradeRepository.getByTradeId(tradeModel.getTradeId());
        Optional<Trade> checkTrade = Optional.ofNullable(tradeId);
        TradeModel tmReturn = null;
        if (checkTrade.isPresent()) {
            throw new TradeException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(),
                    " trade id already present, create trades wirh different id",
                    " trade id should be unique");
        }
//        ModelMapper modelMapper = getModelMapper();
        Trade trade = modelMapper.map(tradeModel, Trade.class);
        User user = modelMapper.map(tradeModel.getUser(), User.class);
        if (trade != null) {
            User byEmail = userRepositroy.getByEmail(user.getEmail());
            if (byEmail == null) {
                user = userRepositroy.save(user);
            } else {
                System.out.println("byEmail.getUserId()"+byEmail.getUserId());
                System.out.println("user.getUserId()"+byEmail.getUserId());
                if (byEmail.getUserId().equals( user.getUserId())) {
                    user = byEmail;
                }else{
                       throw new TradeException(HttpStatus.BAD_REQUEST, 
                               HttpStatus.BAD_REQUEST.value(),
                    "email ID already exists, try with different email iD",
                    "email ID shold be unique for each user");
                }
            }
            trade.setUserId(user);
            UserModel userModel = modelMapper.map(user, UserModel.class);
            Trade returnval = tradeRepository.save(trade);
            if (returnval == null) {
                throw new TradeException(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value(), "unable To Create Trade",
                        "Kindly Check Your Payload");
            } else {
                tmReturn = modelMapper.map(returnval, TradeModel.class);
                tmReturn.setUser(userModel);
            }
        }
        return tmReturn;
    }

    /**
     * this method to get trade details based on userId
     */
    public User getTradesByUser(long userId) {
        User byUserId = userRepositroy.getByUserId(userId);
        Optional<User> user = Optional.ofNullable(byUserId);
        if (!user.isPresent()) {
            throw new TradeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    "unable get trade Data",
                    "record not found for this userid");
        } else {
            return byUserId;
        }
    }

    /**
     * this method to get trade all details
     */
    public List<Trade> findAllTrades() {

        List<Trade> getTrades = tradeRepository.findAll();
        if (getTrades != null) {
            return getTrades;
        } else {
            throw new TradeException(HttpStatus.NOT_FOUND, " check with request");
        }

    }

    /**
     * this method to delete trade details based on userId
     */
    public Boolean deleteAllTrades() {
        try {
            tradeRepository.deleteAll();
        } catch (Exception ex) {
            throw new TradeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    "exception occureed while deleting trade",
                    ex.getMessage());
        }
        return true;
    }

    /**
     * this method to get trade details based on stock
     */
    public List<Trade> getTradeByStrockandTradeType(String symbol,
            String type, Date startDate, Date endDate) {
        List<Trade> findAll = null;
        // Create Conversion Type
        Type listType = new TypeToken<List<Trade>>() {
        }.getType();
        findAll = tradeRepository.findBySymbolandType(symbol.toUpperCase(),
                type.toUpperCase(), startDate, endDate);
        if (findAll.isEmpty()) {
            throw new TradeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    "No trades found",
                    "Given date range");
        }
        return findAll;

    }

    /**
     * this method to get trade details based on price of lowest and highest
     * price
     */
    public PriceModel getTradeByStrockPrice(String symbol, Date startDate, Date endDate) {
        PriceModel price = tradeRepository.findByMinMaxPriceSymbol(symbol.toUpperCase(),
                startDate, endDate);
        if (price.getMin() != null && price.getMax() != null) {
            return price;
        } else {
            throw new TradeException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(),
                    "trade not found", "there are no trades in the given date range");
        }
    }

}
