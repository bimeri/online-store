package com.solution.onlinestore.services;

import com.solution.onlinestore.dto.PurchaseDto;

import java.util.Map;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

public interface Services {
    Map<String, Object> purchase(PurchaseDto purchaseDto);

}
