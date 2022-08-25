package com.xm.task.service;

import com.xm.task.model.CryptoCurrencyHolder;

import java.math.RoundingMode;
import java.util.Comparator;

public class CryptoCurrencyHolderComparator implements Comparator<CryptoCurrencyHolder> {


    @Override
    public int compare(CryptoCurrencyHolder o1, CryptoCurrencyHolder o2) {
        return (o1.getMax().subtract(o1.getMin()).divide(o1.getMin(), RoundingMode.HALF_UP)
            .compareTo(o2.getMax().subtract(o2.getMin()).divide(o2.getMin(), RoundingMode.HALF_UP)));
    }
}
