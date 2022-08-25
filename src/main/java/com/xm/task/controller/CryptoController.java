package com.xm.task.controller;

import com.xm.task.converter.WebOverviewViewConverter;
import com.xm.task.model.CryptoPrice;
import com.xm.task.model.WebOverviewView;
import com.xm.task.service.CryptoProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoProcessingService processingService;

    @GetMapping("/{name}/overview")
    public WebOverviewView getOverview(@PathVariable String name) {
        return WebOverviewViewConverter.convert(processingService.getOverview(name));
    }

    @GetMapping("/{name}/prices")
    public Set<CryptoPrice> getPrices(@PathVariable String name) {
        return processingService.getAllPricesByName(name);
    }

    @GetMapping("/all-normalized")
    public List<WebOverviewView> getPricesNormalized() {
        return processingService.getAllNormalizedByPrice();
    }

    @GetMapping("/max-normalized-for-date")
    public WebOverviewView getMaxNormalizedForDate(@RequestParam("localDate")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate) {
        return processingService.getMaxNormalizedForDate(localDate);
    }
}
