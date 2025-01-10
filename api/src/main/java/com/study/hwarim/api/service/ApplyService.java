package com.study.hwarim.api.service;

import com.study.hwarim.api.repository.CouponCountRepository;
import com.study.hwarim.api.producer.CouponCreateProducer;
import com.study.hwarim.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;

    public void apply(Long userId) {
        long count = couponCountRepository.increment();

        if(count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

}
