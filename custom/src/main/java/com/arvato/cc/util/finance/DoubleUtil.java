package com.arvato.cc.util.finance;

import com.arvato.cc.util.Validate;

public class DoubleUtil {
    private Double d;

    public DoubleUtil(Double first) {
        if (Validate.isNullOrEmpty(first)) {
            d = 0.0;
        } else {
            d = new Double(first);
        }
    }

    public DoubleUtil add(Double value) {
        if (!Validate.isNullOrEmpty(value)) {
            d += value;
            return this;
        } else {
            return this;
        }
    }

    public DoubleUtil sub(Double value) {
        if (!Validate.isNullOrEmpty(value)) {
            d = d - value;
            return this;
        } else {
            return this;
        }
    }

    public DoubleUtil mul(Double value) {
        if (!Validate.isNullOrEmpty(value)) {
            d = d * value;
            return this;
        } else {
            d = 0.0;
            return this;
        }
    }

    public DoubleUtil div(Double value) {
        if (!Validate.isNullOrEmpty(value)) {
            d = d / value;
            return this;
        } else {
            return this;
        }
    }

    public Double result() {
        return d;
    }

//	public static void main(String[] args){
//		DoubleUtil du = new DoubleUtil();
//		System.out.println(du.add(null).sub(2d).result());
//	}
}
