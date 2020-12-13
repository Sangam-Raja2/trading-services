package com.sangam.trading.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Sangam
 */
public class TradeModel {

    @NotNull(message = "tradeId Can't be Null")
    @Min(value = 1, message = "tradeId must be grater than Zero")
    private Long tradeId;
    @NotBlank(message = "type is Mandatory")
    @NotNull(message = "type Can't be Null")
    @Size(min = 2, message = "type must have atleast two characters")
    private String type;
    @NotBlank(message = "symbol is Mandatory")
    @NotNull(message = "symbol Can't be Null")
    @Size(min = 2, message = "symbol must have atleast two characters")
    private String symbol;
    @NotBlank(message = "shares is Mandatory")
    @NotNull(message = "shares Can't be Null")
    @Size(min = 2, message = "shares must have atleast two characters")
    private String shares;
    @NotNull(message = "price Can't be Null")
    private double price;
    @NotNull(message = "shares Can't be Null")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date timestamp;
    private @Valid
    UserModel user;

    public TradeModel(long tradeId, String type, String symbol, String shares, double price, UserModel user) {

        this.tradeId = tradeId;
        this.type = type;
        this.symbol = symbol;
        this.shares = shares;
        this.price = price;
        this.user = user;
    }

    public TradeModel() {

    }

    public Long getTradeId() {
        return tradeId;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

}
