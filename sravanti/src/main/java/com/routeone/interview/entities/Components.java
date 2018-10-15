package com.routeone.interview.entities;

import java.math.BigDecimal;

public class Components implements Comparable<Components> {

	private String componentName;
	private BigDecimal price;
	private String category;
	
	public Components(String componentName, BigDecimal price, String category) {
        super();
        this.componentName = componentName;
        this.price = price;
        this.category = category;
    }

	public String getComponentName()
    {
        return componentName;
    }

    public void setComponentName(String componentName)
    {
        this.componentName = componentName;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

	/**
	 * Sort componentName by price
	 * 
	 * @param other
	 * @return
	 *
	 * @author rayapureddir
	 */
	@Override
	public int compareTo(Components other) {
	    int priceComp = other.price.compareTo(price);
	    return (priceComp == 0? (componentName.compareTo(other.componentName)):priceComp);
	}

}
