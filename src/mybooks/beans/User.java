/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mybooks.beans;

import mybooks.types.UserType;

/**
 * User bean.
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class User {
    /**
     * The user's e-mail address
     */
    private String mail;
    /**
     * The user's surname
     */
    private String lastname;
    /**
     * The user's forename
     */
    private String firstname;
    /**
     * The user's type
     */
    private UserType userType;

    /**
     * Get the user's e-mail address.
     *
     * @return The user's e-mail address
     */
    public String getMail() {
        return mail;
    }

    /**
     * Get the user's surname.
     *
     * @return The user's surname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Get the user's forename.
     *
     * @return The user's forname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Get the user's type.
     *
     * @return The user's type
     */
    public UserType getUserType() {
        return userType;
    }

    /**
     * Set user's mail.
     * 
     * @param mail user's mail.
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Set user's lastname.
     * @param lastname user's lastname.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Set user's firstname
     * @param firstname user's firstname.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Set the user's type.
     * @param userType the user's type.
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    
    
}
