package com.f1v3.lock.repository;

import com.f1v3.lock.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Stock JPA Repository.
 *
 * @author 정승조
 * @version 2024. 08. 27.
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByProductId(Long productId);
}
