package com.xm.task.service.file;

import com.xm.task.model.CryptoCurrencyHolder;
import com.xm.task.model.CryptoPrice;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class CryptoReader {

    private static final String COMMA_DELIMITER = ",";
    public static final String ZONE_ID = "GMT-0";

    @SneakyThrows
    public CryptoCurrencyHolder readFromFile(String path) {
        CryptoCurrencyHolder holder = null;
        try (Scanner scanner = new Scanner(new File(path))) {
            while (scanner.hasNextLine()) {
                List<String> line = getRecordFromLine(scanner.nextLine());
                LocalDateTime timestamp = toTimestamp(line.get(0));
                String name = line.get(1);
                BigDecimal price = new BigDecimal(line.get(2));
                if (holder == null) {
                    holder = new CryptoCurrencyHolder(name);
                }
                holder.addPrice(new CryptoPrice(timestamp, price));
            }
        }
        return holder;
    }

    private LocalDateTime toTimestamp(String item) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(item)), ZoneId.of(ZONE_ID));
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(COMMA_DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
