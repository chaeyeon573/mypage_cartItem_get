package com.youngwoomerged.demo.controller;

import com.youngwoomerged.demo.domain.CartItem;
import com.youngwoomerged.demo.dto.CartItemDto;
import com.youngwoomerged.demo.dto.CartTotalPriceDto;
import com.youngwoomerged.demo.service.CartService;
import com.youngwoomerged.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDto cartItemDto) {
        cartService.addItemToCart(cartItemDto.getCartId(), cartItemDto.getProductId().intValue(), cartItemDto.getQuantity());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/total-price/{cartId}")
    public ResponseEntity<?> getTotalPrice(@PathVariable Integer cartId) {
        Integer totalPrice = cartService.calculateTotalPrice(cartId);
        return ResponseEntity.ok(new CartTotalPriceDto(totalPrice));
    }

    @GetMapping
    public ResponseEntity<List<CartItemDto>> getAllCartItems() {
        List<CartItem> cartItems = cartService.getAllCartItems();
        List<CartItemDto> cartItemDtos = cartItems.stream()
                .map(item -> {
                    CartItemDto dto = new CartItemDto();
                    dto.setCartId(item.getCartId());
                    dto.setProductId(item.getProduct().getProductId());
                    dto.setQuantity(item.getQuantity());
                    return dto;
                }).collect(Collectors.toList());
        return ResponseEntity.ok(cartItemDtos);
    }
///해당 부분 추가
    
    @GetMapping("shopping_list/{userId}")
    public ResponseEntity<List<CartItem>> getCartItemsByUserId(@PathVariable Integer userId) {
        List<CartItem> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }

    @PostMapping("/order/{cartId}")
    public ResponseEntity<?> orderItemsFromCart(@PathVariable Integer cartId) {
        orderService.createOrdersFromCartId(cartId);
        return ResponseEntity.ok().build();
    }


}
