package com.shop.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격

    private int count; // 수량

//    private LocalDateTime regTime;
//
//    private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count); // 상품과 수량 세팅
        orderItem.setOrderPrice(item.getPrice()); //현재 시간 기준으로

        item.removeStock(count); // 주문 수량만큼 재고 수량 감소
        return orderItem;
    }
    public int getTotalPrice(){
        return orderPrice*count; // 가격과 수량 곱해서 가격계산
    }

    public void cancel(){
        this.getItem().addStock(count);
    }

}
