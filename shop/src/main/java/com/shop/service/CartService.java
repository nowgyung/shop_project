package com.shop.service;

import com.shop.dto.CartItemDto;
import com.shop.entity.CarItem;
import com.shop.entity.Cart;
import com.shop.entity.Item;
import com.shop.entity.Member;
import com.shop.repository.CartItemRepository;
import com.shop.repository.CartRepository;
import com.shop.repository.ItemRepository;
import com.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Long addCart(CartItemDto cartItemDto, String email){
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);

        Cart cart = cartRepository.findByMemberId(member.getId());
//        상품을 처음으로 장바구니에 담을 경우
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }
        CarItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

//        장바구니에 이미 있던 상품일 경우 기존 수량에 현재 장바구니에 담을 수량 만큼을 더해줌
        if(savedCartItem != null){
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        }else {
            CarItem cartItem = CarItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }
}
