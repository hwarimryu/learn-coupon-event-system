package com.study.hwarim.api;

import com.study.hwarim.api.producer.CouponCreateProducer;
import com.study.hwarim.api.repository.CouponRepository;
import com.study.hwarim.api.service.ApplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private CouponRepository couponRepository;

	@Test
	void 한명만응모() {
		applyService.apply(1L);

		long count = couponRepository.count();

		assertThat(count).isEqualTo(1L);
	}

	@Test
	public void 여러명응모() throws InterruptedException {
		int threadCount = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		// ExecutorService 병렬작업을 간단하게 할 수 있게 도와주는 자바 api
		CountDownLatch latch = new CountDownLatch(threadCount);
		// 다른 스레드가 종료될 때 까지 기다리는 용도

		for (int i = 0; i < threadCount; i++) {
			long userId = i;
			executorService.submit(() -> {
				try {
					applyService.apply(userId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);

		long count = couponRepository.count();

		assertThat(count).isEqualTo(100);
	}

	@Test
	public void 한명당_한개의쿠폰만_발급() throws InterruptedException {
		int threadCount = 100;
		ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
		CountDownLatch latch = new CountDownLatch(threadCount);

		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					applyService.apply(1L);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Thread.sleep(10000);

		long count = couponRepository.count();

		assertThat(count).isEqualTo(1);

	}

}
