package com.product.globie.controller;

import com.product.globie.payload.DTO.RateDTO;
import com.product.globie.payload.request.CreateRateRequest;
import com.product.globie.payload.request.UpdateRateRequest;
import com.product.globie.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.version}/rates")
@Slf4j
@CrossOrigin(origins = "https://globie-front-cgxbtuyd8-dolakiens-projects.vercel.app")

public class RateController {
    @Autowired
    private RateService rateService;

    @PostMapping("/create")
    public ResponseEntity<CreateRateRequest> createRate(@RequestBody CreateRateRequest rateDTO){
        return ResponseEntity.ok(rateService.createRate(rateDTO));
    }

    @PostMapping("/update")
    public ResponseEntity<RateDTO> updateRate(@RequestBody UpdateRateRequest rateDTO){
        return ResponseEntity.ok(rateService.updateRate(rateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RateDTO> getRateById(@PathVariable int id){
        return ResponseEntity.ok(rateService.getRateById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable int id) {
        rateService.deleteRate(id);
        return ResponseEntity.ok("Delete success");
    }

    @GetMapping("/all")
    public ResponseEntity<List<RateDTO>> getAllRates(){
        return ResponseEntity.ok(rateService.getAllRates());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<RateDTO>> getRatesByProductId(@PathVariable int productId){
        return ResponseEntity.ok(rateService.getRatesByProductId(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RateDTO>> getRatesByUserId(@PathVariable int userId){
        return ResponseEntity.ok(rateService.getRatesByUserId(userId));
    }


}
