package com.youngwoomerged.demo.repository;

import com.youngwoomerged.demo.domain.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {
}
