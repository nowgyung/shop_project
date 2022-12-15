package com.shop.repository;

import com.shop.entity.CarItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CarItem, Long> {
    //상품을 저장하거나 조회하기 위함
    CarItem findByCartIdAndItemId(Long cartId, Long itemId);
}
