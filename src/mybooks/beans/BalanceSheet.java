/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybooks.beans;

import javafx.event.EventType;
import javafx.scene.control.Button;

/**
 * BalanceSheet bean.
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class BalanceSheet {

    //Attributes
    /**
     * The balance sheet's id.
     */
    private int id;
    /**
     * The balance sheet's title.
     */
    private String title;
    /**
     * The balance sheet's date of last change.
     */
    private String dateOfLastChange;
    /**
     * The balance sheet's date of creation.
     */
    private String dateOfCreation;
    
    //Getter & Setter
    /**
     * Get the balance sheet's id.
     * @return the balance sheet's id.
     */
    public int getId() {
        return id;
    }
    

    /**
     * Get the balance sheet's title.
     * @return title as a string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get edit date.
     * @return edit date.
     */
    public String getDateOfLastChange() {
        return dateOfLastChange;
    }

    /**
     * Get creation date.
     * @return creation date.
     */
    public String getDateOfCreation() {
        return dateOfCreation;
    }
    
    /**
     * Set the balance sheet's id.
     * @param id the balance sheet's id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the balance sheet's title.
     * @param title the balance sheet's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set the date of last change.
     * @param dateOfLastChange the date of last change.
     */
    public void setDateOfLastChange(String dateOfLastChange) {
        this.dateOfLastChange = dateOfLastChange;
    }

    /**
     * Set the date of creation.
     * @param dateOfCreation the date of creation.
     */
    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }
    
    
}
