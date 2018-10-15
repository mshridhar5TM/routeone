package com.routeone.interview.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.routeone.interview.entities.Components;

@Service
public interface Receipt {
	public String getFormattedTotal(BigDecimal totalPrice);

	public List<String> getOrderedItems(List<Components> componentsList);

	Map<BigDecimal, List<Components>> checkoutOrder(List<String> components, Map<String, Components> store);
}