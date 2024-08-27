package com.f1v3.lock.service;

import com.f1v3.lock.domain.Stock;
import com.f1v3.lock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Synchronized Stock Service.
 *
 * @author 정승조
 * @version 2024. 08. 27.
 */
@Service
@RequiredArgsConstructor
public class StockService implements StockBusiness {

    private final StockRepository stockRepository;

    @Override
    public synchronized void decrease(Long productId, Long quantity) {

        Stock stock = stockRepository.findByProductId(productId);
        stock.decrease(quantity);

        stockRepository.save(stock);

        // TODO: 왜 @Transactional 어노테이션이 있으면 오류가 발생할까?
    }
}
