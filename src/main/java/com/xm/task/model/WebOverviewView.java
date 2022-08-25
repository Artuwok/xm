package com.xm.task.model;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class WebOverviewView {
    private String name;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal first;
    private BigDecimal last;
}
