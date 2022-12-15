package com.shop.controller;

import com.shop.dto.CartItemDto;
import com.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for(FieldError fieldError :fieldErrors){
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String email = principal.getName();
        Long cartItemId;

//        장바구니에 상품을 담는 로직 호출, 상품정보와 회원 이메일정보를 이용
        try{
            cartItemId = cartService.addCart(cartItemDto,email);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
//        결과값으로 요청이 성공하였다는 HTTP 응답 상태 코드를 반환
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
