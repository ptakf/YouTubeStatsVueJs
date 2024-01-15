package com.project.youtubestats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class MainController {
    private double exchangeRate = 2;

    @GetMapping("/main")
    public String mainPage(Model model) {
        return "main";
    }

    @PostMapping("/askInfo")
    @ResponseBody
    public Map<String, Double> askInfo(@RequestBody Map<String, Integer> requestPayload) {
        int receivedNumber = requestPayload.get("someNumber");
        double result = exchangeRate * receivedNumber;

        Map<String, Double> response = new HashMap<>();
        response.put("result", result);

        return response;
    }
}
