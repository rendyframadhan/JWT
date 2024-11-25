package com.technicaltest.fruitservice.controller;

import com.technicaltest.fruitservice.dto.DeleteFruitRequest;
import com.technicaltest.fruitservice.dto.FruitBaseResponse;
import com.technicaltest.fruitservice.dto.StoreFruitRequest;
import com.technicaltest.fruitservice.dto.UpdateFruitRequest;
import com.technicaltest.fruitservice.model.Fruit;
import com.technicaltest.fruitservice.service.management.FruitManagementService;
import com.technicaltest.fruitservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FruitServiceController {

    private final FruitManagementService fruitManagementService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/public/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        if (authentication.isAuthenticated()) {
            return JwtUtil.generateToken(username);
        }

        throw new RuntimeException("Invalid login attempt");
    }

    @PostMapping("/public/test")
    public String testPublicAccess() {
        return "Public Access Success!";
    }

    @PostMapping("/getfruitlist")
    public List<Fruit> getFruitList(){
        return fruitManagementService.getFruitList();
    }

    @PostMapping("/public/storefruit")
    public FruitBaseResponse storeFruitName(@RequestBody StoreFruitRequest request){
        return fruitManagementService.insertFruit(request);
    }

    @PostMapping("/updatefruit")
    public FruitBaseResponse getFruitName(@RequestBody UpdateFruitRequest request){
        return fruitManagementService.updateFruit(request);
    }

    @PostMapping("/deletefruit")
    public FruitBaseResponse deleteFruit(@RequestBody DeleteFruitRequest request){
        return fruitManagementService.deleteFruit(request);
    }
}
