package com.solution.onlinestore.controller;

import com.solution.onlinestore.dto.PurchaseDto;
import com.solution.onlinestore.services.Services;
import com.solution.onlinestore.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@RequestMapping(path = Constants.REQUEST_MAPPING)
@RestController
public class PurchaseController {
 private final Services service;

 @Autowired
 public PurchaseController(Services service) {
   this.service = service;
 }

     @PostMapping(path = "/api/purchase")
     public ResponseEntity<Map<String, Object>> purchaseLaptop(@RequestBody PurchaseDto purchaseDto) {
        return ResponseEntity.ok(service.purchase(purchaseDto));
     }
}
