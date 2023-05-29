package com.solution.onlinestore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto extends RAM_MemoryCard {
 private String modelName;
 private String message;
}
