package com.sangam.trading.model;

/**
 *
 * @author Sangam
 */
public class PriceModel {

	private Double min;
	private Double max;

    public PriceModel(Double min, Double max) {
        this.min = min;
        this.max = max;
    }

        
	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

}
