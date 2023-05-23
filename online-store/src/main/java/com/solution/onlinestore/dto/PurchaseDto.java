package com.solution.onlinestore.dto;

import lombok.Data;
import lombok.NonNull;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@Data
public class PurchaseDto {
    @NonNull
    private int quantity;
    @NonNull
    private String modelName;
}
