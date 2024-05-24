package com.youngwoomerged.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {
    private Integer productId;
    private Integer quantity;
    private Integer cartId;
}