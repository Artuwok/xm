package com.xm.task.converter;

import com.xm.task.model.CryptoCurrencyHolder;
import com.xm.task.model.WebOverviewView;

public class WebOverviewViewConverter {

    public static WebOverviewView convert(CryptoCurrencyHolder holder){
        return new WebOverviewView()
                .withName(holder.getName())
                .withFirst(holder.getFirst())
                .withLast(holder.getLast())
                .withMin(holder.getMin())
                .withMax(holder.getMax());
    }
}
