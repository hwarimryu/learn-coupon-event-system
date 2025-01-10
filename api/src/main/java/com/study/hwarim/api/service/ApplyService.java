package com.study.hwarim.api.service;

import com.study.hwarim.api.domain.Coupon;
import com.study.hwarim.api.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ApplyService {
    private final CouponRepository couponRepository;

    public void apply(Long userId) {
        long count = couponRepository.count();

        if(count > 100) {
            return;
        }

        couponRepository.save(new Coupon(userId));
    }

}