package com.youngwoomerged.demo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartTotalPriceDto {
    private Integer totalPrice;

    public CartTotalPriceDto(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

}
