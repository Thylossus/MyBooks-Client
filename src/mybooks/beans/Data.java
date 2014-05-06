/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybooks.beans;

/**
 * Data bean.
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class Data {

    /**
     * Data identifier.
     */
    private String identifier;
    
    /**
     * Data value.
     */
    private Object value;

    /**
     * Get data identifier.
     * @return data identifier.
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Set data identifier.
     * @param identifier data identifier.
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Get data value.
     * @return data value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Set data value.
     * @param value data value object.
     */
    public void setValue(Object value) {
        this.value = value;
    }
    
    
    
}
