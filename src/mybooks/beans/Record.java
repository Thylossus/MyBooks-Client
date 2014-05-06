/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybooks.beans;

import java.sql.Date;

/**
 * Record bean.
 *
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class Record {
    /**
     * Id of the balance sheet, which contains the record.
     */
    private int balanceSheet;
    /**
     * Id of the record within the balance sheet.
     */
    private int id;
    /**
     * The record's title.
     */
    private String title;
    /**
     * The record's description.
     */
    private String description;
    /**
     * The record's amount.
     */
    private double amount;
    /**
     * The record's date.
     */
    private String recordDate;
    /**
     * The id of the record's category.
     */
    private int catId;
    /**
     * The name of the record's category.
     */
    private String catName;
    /**
     * The colour (hex code) of the record's category.
     */
    private String catColour;

    //Getter & Setter
    /**
     * Get the id of the balance sheet, which contains the record.
     *
     * @return the balance sheet's id.
     */
    public int getBalanceSheet() {
        return balanceSheet;
    }

    /**
     * Get the record's id.
     *
     * @return the record's id.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the record's title.
     *
     * @return the record's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get the record's description.
     *
     * @return the record's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the record's amount.
     *
     * @return the record's amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get the record's date.
     *
     * @return the record's date.
     */
    public String getRecordDate() {
        return recordDate;
    }

    /**
     * Get the id of the record's category.
     *
     * @return the id of the record's category.
     */
    public int getCatId() {
        return catId;
    }

    /**
     * Get the name of the record's category.
     *
     * @return the name of the record's category.
     */
    public String getCatName() {
        return catName;
    }

    /**
     * Get the colour of the record's category.
     *
     * @return the colour of the record's category.
     */
    public String getCatColour() {
        return catColour;
    }

    public void setBalanceSheet(int balanceSheet) {
        this.balanceSheet = balanceSheet;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public void setCatColour(String catColour) {
        this.catColour = catColour;
    }
    
    

}
