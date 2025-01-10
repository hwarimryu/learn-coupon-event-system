package com.study.hwarim.consumer.consumer;

import com.study.hwarim.consumer.domain.Coupon;
import com.study.hwarim.consumer.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponCreatedConsumer {

    private final CouponRepository couponRepository;

    @KafkaListener(topics = "coupon_create", groupId = "group_id")
    public void listener(Long userId) {
        couponRepository.save(new Coupon(userId));
    }
}
