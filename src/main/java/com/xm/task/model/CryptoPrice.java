package com.xm.task.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class CryptoPrice implements Comparable<CryptoPrice> {

    private final LocalDateTime timestamp;
    private final BigDecimal price;


    @Override
    public int compareTo(CryptoPrice o) {
        return this.getTimestamp().compareTo(o.getTimestamp());
    }
}
