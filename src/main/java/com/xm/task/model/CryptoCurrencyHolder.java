package com.xm.task.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@Getter
@RequiredArgsConstructor
public class CryptoCurrencyHolder {

    private final String name;
    private final Set<CryptoPrice> prices = new TreeSet<>();
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal first;
    private BigDecimal last;


    public void addPrice(CryptoPrice price) {
        if (prices.isEmpty()) {
            first = price.getPrice();
        }
        last = price.getPrice();
        prices.add(price);
        updateMin(price.getPrice());
        updateMax(price.getPrice());
    }

    private void updateMax(BigDecimal price) {
        if (max == null) {
            max = price;
        } else {
            if (price.compareTo(max) > 0) {
                max = price;
            }
        }
    }

    private void updateMin(BigDecimal price) {
        if (min == null) {
            min = price;
        } else {
            if (price.compareTo(min) < 0) {
                min = price;
            }
        }
    }
}
