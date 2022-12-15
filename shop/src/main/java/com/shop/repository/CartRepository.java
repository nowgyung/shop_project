package com.shop.repository;

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
//    Cart 엔티티를 찾기위해서 메소드 추가
    Cart findByMemberId(Long memberId);
}
