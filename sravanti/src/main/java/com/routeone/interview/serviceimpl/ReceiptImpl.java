package com.routeone.interview.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routeone.interview.entities.Components;
import com.routeone.interview.exception.ItemNotFoundException;
import com.routeone.interview.service.Receipt;
import com.routeone.interview.util.RouteOneCodingUtil;

@Service
public class ReceiptImpl implements Receipt {

	private final Logger logger = Logger.getLogger(ReceiptImpl.class);

	@Autowired
	private RouteOneCodingUtil routeOneCodingUtil;
	
	public String getFormattedTotal(BigDecimal totalPrice) {
		return routeOneCodingUtil.convertDecimalToCurrency(totalPrice, Locale.US);
	}

	@Override
	public List<String> getOrderedItems(List<Components> componentsList) {
		List<String> sortedOrderList = new ArrayList<String>();
		Collections.sort(componentsList);
		Iterator<Components> listItr = componentsList.iterator();
		logger.info("Sorting the Items:");
		while (listItr.hasNext()) {
			Components c = listItr.next();
			sortedOrderList.add(c.getComponentName());
		}
		logger.info("Items Sorted");
		return sortedOrderList;
	}
	@Override
	public Map<BigDecimal,List<Components>> checkoutOrder(List<String> components, Map<String, Components> store) {
		BigDecimal orderTotal = new BigDecimal("0");
		Map<BigDecimal, List<Components>> res=new HashMap<>();
		List<Components> componentsList = new ArrayList<Components>();
		try {
			logger.info("Getting Price List");
			for (String checkOutComponentName : components) {
				if (checkOutComponentName != null) {
					Components inventoryComponent = store.get(checkOutComponentName);
					if (inventoryComponent != null) {
						componentsList.add(inventoryComponent);
						orderTotal = orderTotal.add(inventoryComponent.getPrice());
					} else {
						throw new ItemNotFoundException(checkOutComponentName + " No Item with this name");
					}
				}
			}
		} catch (ItemNotFoundException ex) {
			ex.printStackTrace();
		}
		res.put(orderTotal, componentsList);
		return res;
//		return new ReceiptImpl(orderTotal, componentsList);
	}
}
