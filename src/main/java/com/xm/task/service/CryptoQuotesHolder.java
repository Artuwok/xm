package com.xm.task.service;

import com.xm.task.model.CryptoCurrencyHolder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Data
public class CryptoQuotesHolder {

    private Map<String, CryptoCurrencyHolder> quotes = new ConcurrentHashMap<>();

    public void add(CryptoCurrencyHolder holder) {
        quotes.put(holder.getName(), holder);
    }
}
