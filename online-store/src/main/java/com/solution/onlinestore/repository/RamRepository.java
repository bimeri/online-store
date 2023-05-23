package com.solution.onlinestore.repository;

import com.solution.onlinestore.entity.Laptop;
import com.solution.onlinestore.entity.Ram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Bimeri Noel
 * @date 5/23/2023
 */

public interface RamRepository extends JpaRepository<Ram, Long> {
    List<Ram> findAllByLaptop_IdOrderByIdAsc(Long id);
}
