package com.xm.task.controller;

import com.xm.task.service.CryptoProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class ApplicationController {
    private final CryptoProcessingService processingService;

    @PostMapping("/init")
    public void init(@RequestBody String path) {
        processingService.init(path);
    }
}
