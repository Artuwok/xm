package com.xm.task;

import com.xm.task.model.WebOverviewView;
import com.xm.task.service.CryptoProcessingService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
public class CryptoProcessingServiceTest {
    @Autowired
    CryptoProcessingService cryptoProcessingService;


    @Test
    void test_template_should_be_uploaded() throws URISyntaxException {
        String btcValues = resolvePathToTestCSV();
        cryptoProcessingService.init(btcValues);
        int size = cryptoProcessingService.getAllNormalizedByPrice().size();
        Assertions.assertEquals(1, size);
    }

    @Test
    void test_template_should_be_normalized_for_date() throws URISyntaxException {
        String btcValues = resolvePathToTestCSV();
        cryptoProcessingService.init(btcValues);

        // check for Sat Jan 01 2022 04:00:00 see BTC_values.csv
        LocalDate testDate = LocalDate.of(2022, Month.JANUARY, 1);
        WebOverviewView maxNormalizedForDate = cryptoProcessingService.getMaxNormalizedForDate(testDate);
        Assertions.assertEquals(BigDecimal.valueOf(47000.21), maxNormalizedForDate.getMax());
    }

    @Test
    void test_template_should_throw_exception_when_no_crypto_found() throws URISyntaxException {
        String btcValues = resolvePathToTestCSV();
        cryptoProcessingService.init(btcValues);
        Assertions.assertThrowsExactly(CryptoNotFoundException.class, () -> cryptoProcessingService.getAllPricesByName("USDT"));
    }

    private String resolvePathToTestCSV() throws URISyntaxException {
        URL res = getClass().getClassLoader().getResource("BTC_values.csv");
        File file = Paths.get(res.toURI()).toFile();
        return file.getAbsolutePath();
    }
}
