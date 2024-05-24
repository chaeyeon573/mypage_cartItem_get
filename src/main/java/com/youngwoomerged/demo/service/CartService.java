package com.youngwoomerged.demo.service;

import com.youngwoomerged.demo.domain.Cart;
import com.youngwoomerged.demo.domain.CartItem;
import com.youngwoomerged.demo.domain.Product;
import com.youngwoomerged.demo.dto.CartItemDto;
import com.youngwoomerged.demo.repository.CartItemRepository;
import com.youngwoomerged.demo.repository.CartRepository;
import com.youngwoomerged.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // 장바구니에 아이템 추가
    public void addItemToCart(Integer cartId, Integer productId, Integer quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCartId(cartId);
        Product product = productRepository.findById(productId) // or .findById(productId) if your productId is already Integer
                .orElseThrow(() -> new RuntimeException("Product not found"));
        cartItem.setProduct(product); // 여기를 수정
        cartItem.setQuantity(quantity);
        cartItem.setCreatedAt(new Date());
        cartItem.setUpdatedAt(new Date());

        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getAllCartItems() {
        return cartItemRepository.findAll();
    }

    // 장바구니 아이템 총 가격 계산
    public Integer calculateTotalPrice(Integer cartId) {
        List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);
        int totalPrice = 0;

        for (CartItem item : cartItems) {
            totalPrice += item.getProduct().getPrice() * item.getQuantity(); // 여기도 수정
        }

        return totalPrice;
    }

    public void updateCartItem(Integer cartId, CartItemDto cartItemDto) {
        CartItem cartItem = cartItemRepository.findByCartId(cartId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        // productId와 quantity가 제공되면 업데이트합니다.
        if(cartItemDto.getProductId() != null) {
            Product product = productRepository.findById(cartItemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            cartItem.setProduct(product);
        }
        if(cartItemDto.getQuantity() != null) {
            cartItem.setQuantity(cartItemDto.getQuantity());
        }

        cartItem.setUpdatedAt(new Date());
        cartItemRepository.save(cartItem);
    }



    public List<CartItem> getCartItemsByUserId(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId).stream().findFirst().orElse(null);
        if (cart == null) {
            return new ArrayList<>();
        }
        return cartItemRepository.findByCartId(cart.getCartId());
    }
}