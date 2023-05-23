package com.solution.onlinestore.repository;

import com.solution.onlinestore.entity.Laptop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

public interface LaptopRepository extends JpaRepository<Laptop, Long> {
 Optional<Laptop> findByModelName(String modelName);
}
