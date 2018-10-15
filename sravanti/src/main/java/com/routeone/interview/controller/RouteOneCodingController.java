package com.routeone.interview.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.routeone.interview.entities.Components;
import com.routeone.interview.service.Receipt;
import com.routeone.interview.util.RouteOneCodingUtil;

@RestController
@RequestMapping("/route")
public class RouteOneCodingController {

	private final Logger logger = Logger.getLogger(RouteOneCodingController.class);
	
	@Autowired
	private RouteOneCodingUtil routeOneCodingUtil;

	@Autowired
	private Receipt receipt;

	@PostMapping("/receipt")
	public ResponseEntity<?> getInventoryReciept(@RequestBody List<String> components) {
		Map<BigDecimal, List<Components>> result;
		logger.info("Total components: " + components.size());
		logger.info("Loading all inventories from csv:");
		Map<String, Components> componentList=routeOneCodingUtil.loadCsv(new File("resource/sample-inventory.csv"));
		logger.info("Input component list: \n" + components.stream().collect(Collectors.joining("\n")));
		result = receipt.checkoutOrder(components,componentList);
		String totalAmount = null;
		List<String> orderedList =null;
		for (Entry<BigDecimal, List<Components>>  comp : result.entrySet()) {
			totalAmount = receipt.getFormattedTotal(comp.getKey());
			orderedList = receipt.getOrderedItems(comp.getValue());
		}
		
		Map<String, Object> respone = new HashMap<>();
		respone.put("Total Amount", totalAmount);
		respone.put("Ordered List", orderedList);
		logger.info("Total Amount: "+totalAmount);
		logger.info("Ordered List: "+orderedList);
		return ResponseEntity.ok(respone);
	}
}
