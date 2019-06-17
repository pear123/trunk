/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: ExtFilter.java 2011-4-26 20:28:21 Justin $
 */
package com.arvato.platform.util;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * @author Justin
 */
@JsonDeserialize
public class ExtFilter {


    private String type;
    private String comparison;
    private String value;
    private String field;

    public ExtFilter() {
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the comparison
     */
    public String getComparison() {
        return comparison;
    }

    /**
     * @param comparison the comparison to set
     */
    public void setComparison(String comparison) {
        this.comparison = comparison;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(String field) {
        this.field = field;
    }


}
