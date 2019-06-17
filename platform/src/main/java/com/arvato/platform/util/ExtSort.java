/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtSort.java 2011-6-8 10:50:06 Justin $
 */
package com.arvato.platform.util;

import com.arvato.jdf.dao.PropertyOrder;

/**
 *
 * @author Justin
 */
public class ExtSort {

    public enum Direction {

        ASC, DESC
    }
    private String property;
    private Direction direction;

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public PropertyOrder toPropertyOrder() {
        if (getDirection().equals(Direction.ASC)) {
            return PropertyOrder.asc(getProperty());
        }
        return PropertyOrder.desc(getProperty());
    }
}
