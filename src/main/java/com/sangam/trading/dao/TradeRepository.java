package com.sangam.trading.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sangam.trading.entity.Trade;
import com.sangam.trading.model.PriceModel;

/**
 *
 * @author Sangam
 */

@Repository
public interface TradeRepository extends JpaRepository<Trade, Long> {

	@Query("from Trade where tradeId=:tradeId")
	public Trade getByTradeId(@Param("tradeId")long tradeId);

	public List<Trade> findByTradeId(long tradeId);

	public List<Trade> findByUserId(long userId);

	public List<Trade> findAll();

	@Query( "from Trade  WHERE  UPPER(symbol)=?1  AND  UPPER(type)=?2 AND "
			+ " timestamp BETWEEN ?3 AND ?4")
	List<Trade> findBySymbolandType(String symbol, String type,  Date startDate,
			Date endDate);

	@Query(value = "SELECT MIN(price) from Trade WHERE UPPER(symbol)=?1 AND "
                + "timestamp BETWEEN ?2 AND ?3")
	public Double findByMinPriceSymbol(String symbol,  Date startDate,
			 Date endDate);
        
        @Query(value = "SELECT MAX(price) from Trade WHERE  UPPER(symbol)=?1 AND "
                + "timestamp BETWEEN ?2 AND ?3")
	public Double findByMaxPriceSymbol(String symbol,  Date startDate,
			 Date endDate);

        @Query(value = "SELECT new com.sangam.trading.model.PriceModel( MIN(price) as min,MAX(price) as max) "
                + "from Trade WHERE  UPPER(symbol)=?1 AND timestamp BETWEEN ?2 AND ?3")
	public PriceModel findByMinMaxPriceSymbol(String symbol,  Date startDate,
			 Date endDate);
}
