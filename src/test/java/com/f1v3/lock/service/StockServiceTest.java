package com.f1v3.lock.service;

import com.f1v3.lock.domain.Stock;
import com.f1v3.lock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    private final int threadCount = 1000;
    private final long productId = 1000L;
    private final long quantity = 1L;
    private final long initQuantity = 1000L;

    private ExecutorService executorService;
    private CountDownLatch countDownLatch;

    @BeforeEach
    public void beforeEach() {
        stockRepository.save(new Stock(productId, initQuantity));

        executorService = Executors.newFixedThreadPool(threadCount);
        countDownLatch = new CountDownLatch(threadCount);
    }

    @AfterEach
    public void afterEach() {
        stockRepository.deleteAll();
    }

    @DisplayName("synchronized test")
    @Test
    void synchronized_test() throws InterruptedException {

        // when
        IntStream.range(0, threadCount)
                .forEach(e -> executorService.submit(() -> {
                    try {
                        stockService.decrease(productId, quantity);
                    } finally {
                        countDownLatch.countDown();
                    }
                }
        ));

        countDownLatch.await();

        // then
        final Long afterQuantity = stockRepository.findByProductId(productId).getQuantity();
        System.out.println("### SYNCHRONIZED 동시성 처리 이후 수량 ###" + afterQuantity);
        assertThat(afterQuantity).isZero();
    }

}