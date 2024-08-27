package com.f1v3.lock.service;

/**
 * Stock Business.
 *
 * @author 정승조
 * @version 2024. 08. 27.
 */
public interface StockBusiness {

    void decrease(Long productId, Long quantity);
}
