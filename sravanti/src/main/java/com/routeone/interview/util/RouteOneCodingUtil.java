package com.routeone.interview.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import com.routeone.interview.entities.Components;

@Configuration
public class RouteOneCodingUtil {

	private final Logger logger = Logger.getLogger(RouteOneCodingUtil.class);

	public String convertDecimalToCurrency(BigDecimal amount, Locale locale) {
		return NumberFormat.getCurrencyInstance(locale).format(amount).toString();
	}

	public Map<String, Components> loadCsv(File inventoryFile) {
		Map<String, Components> storeInventory = new HashMap<String, Components>();
		BufferedReader br = null;
		String newLine = "";
		String commaSplitter = ",";
		logger.info("Reading CSV to Bean");
		try {
			br = new BufferedReader(new FileReader(inventoryFile));
			while ((newLine = br.readLine()) != null) {
				String[] lines = newLine.split(commaSplitter);
				if (lines.length == 3) {
					Components component = new Components(lines[0], new BigDecimal(lines[1]), lines[2]);
					storeInventory.put(lines[0], component);
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return storeInventory;
	}
}
