package com.xe.controller;

import com.xe.service.TestApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/testapi")
public class TestApiController {

    private final TestApiService testApiService;

    public TestApiController(TestApiService testApiService) {
        this.testApiService = testApiService;
    }

    /**
     * http://localhost:8081/testapi
     */
    @GetMapping
    public String get_rates() {
        log.info("Get -> rates obtained");
        return testApiService.obtain_rates();
    }

}
