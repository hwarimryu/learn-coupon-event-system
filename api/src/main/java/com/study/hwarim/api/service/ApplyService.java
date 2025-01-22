package com.study.hwarim.api.service;

import com.study.hwarim.api.repository.AppliedUserRepository;
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
    private final AppliedUserRepository appliedUserRepository;

    public void apply(Long userId) {
        Long apply = appliedUserRepository.add(userId);

        if(apply != 1)
            return;

        long count = couponCountRepository.increment();

        if(count > 100) {
            return;
        }

        couponCreateProducer.create(userId);
    }

}
