package com.xm.task.service;

import com.xm.task.CryptoNotFoundException;
import com.xm.task.converter.WebOverviewViewConverter;
import com.xm.task.model.CryptoCurrencyHolder;
import com.xm.task.model.CryptoPrice;
import com.xm.task.model.WebOverviewView;
import com.xm.task.service.file.CryptoReader;
import com.xm.task.service.file.FileSearcher;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CryptoProcessingService {

    private final FileSearcher fileSearcher;
    private final CryptoReader cryptoReader;
    private final CryptoQuotesHolder quotesHolder;

    @SneakyThrows
    public void init(String path) {
        List<String> files = fileSearcher.search(Path.of(path));
        files.forEach(this::updateQuotesHolder);
    }

    public Set<CryptoPrice> getAllPricesByName(String name) throws CryptoNotFoundException {
        return Optional.ofNullable(quotesHolder.getQuotes().get(name))
            .map(CryptoCurrencyHolder::getPrices)
            .orElseThrow(() -> new CryptoNotFoundException("Crypto is not found for: " + name));
    }

    public CryptoCurrencyHolder getOverview(String name) {
        return quotesHolder.getQuotes().getOrDefault(name, new CryptoCurrencyHolder(name));
    }

    public List<WebOverviewView> getAllNormalizedByPrice() {
        return quotesHolder.getQuotes().values().stream()
            .sorted(new CryptoCurrencyHolderComparator().reversed()).map(WebOverviewViewConverter::convert)
            .collect(Collectors.toList());
    }

    private void updateQuotesHolder(String file) {
        quotesHolder.add(cryptoReader.readFromFile(file));
    }

    public WebOverviewView getMaxNormalizedForDate(LocalDate date) throws CryptoNotFoundException {

        List<CryptoCurrencyHolder> maxPriceCandidatesForDay = new ArrayList<>();

        for (CryptoCurrencyHolder holder : quotesHolder.getQuotes().values()) {
            List<CryptoPrice> pricesForDay = holder.getPrices().stream()
                .filter(cryptoPrice -> cryptoPrice.getTimestamp().toLocalDate().equals(date)).toList();

            CryptoCurrencyHolder cryptoCurrencyHolder = new CryptoCurrencyHolder(holder.getName());
            pricesForDay.forEach(cryptoCurrencyHolder::addPrice);
            maxPriceCandidatesForDay.add(cryptoCurrencyHolder);
        }

        return maxPriceCandidatesForDay.stream().max(new CryptoCurrencyHolderComparator()).map(WebOverviewViewConverter::convert)
            .orElseThrow(() -> new CryptoNotFoundException("No crypto found for provided day: " + date));
    }
}
