/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybooks.client;

import java.util.ArrayList;
import java.util.HashMap;
import mybooks.beans.BalanceSheet;
import mybooks.beans.Data;
import mybooks.beans.Record;
import mybooks.beans.User;
import mybooks.types.UserType;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Parses incomming XML into an array list.
 *
 * @author Tobias Kahse <tobias.kahse@outlook.com>
 */
public class XmlParser extends DefaultHandler {

    private ArrayList<Data> data = null;
    private String currentTag;
    private Object currentValue;
    private Class currentObjectClass = null;

    public ArrayList<Data> getData() {
        return this.data;
    }

    @Override
    public void startDocument() throws SAXException {
        this.data = new ArrayList<>();
        this.data.add(new Data());
        this.data.get(0).setIdentifier("dictionary");
        this.data.get(0).setValue(new HashMap<String, Integer>());
    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        if (this.currentObjectClass == null && !qName.equals("data")) {
            this.data.add(new Data());

            int index = this.data.size() - 1;

            ((HashMap) this.data.get(0).getValue()).put(qName, index);
            this.data.get(index).setIdentifier(qName);

            switch (qName) {
                case "balanceSheetList":
                    this.data.get(index).setValue(new ArrayList<BalanceSheet>());
                    this.currentObjectClass = ArrayList.class;
                    break;
                case "recordList":
                    this.data.get(index).setValue(new ArrayList<Record>());
                    this.currentObjectClass = ArrayList.class;
                    break;
                case "user":
                    this.currentValue = new User();
                    this.currentObjectClass = User.class;
                    break;
            }
        } else {
            Integer index;
            if (this.currentObjectClass == ArrayList.class) {
                switch (qName) {
                    case "balanceSheet":
                        index = ((HashMap<String, Integer>) this.data.get(0).getValue()).get("balanceSheetList");
                        this.currentValue = new BalanceSheet();
                        this.currentObjectClass = BalanceSheet.class;
                        if (index != null) {
                            ((ArrayList<BalanceSheet>) this.data.get(index).getValue()).add((BalanceSheet) this.currentValue);
                        }
                        break;
                    case "record":
                        index = ((HashMap<String, Integer>) this.data.get(0).getValue()).get("recordList");
                        this.currentValue = new Record();
                        this.currentObjectClass = Record.class;
                        if (index != null) {
                            ((ArrayList<Record>) this.data.get(index).getValue()).add((Record) this.currentValue);
                        }
                        break;
                }
            }
        }

        this.currentTag = qName;

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        Integer index = ((HashMap<String, Integer>) this.data.get(0).getValue()).get(qName);

        if (index != null) {
            switch (qName) {
                case "balanceSheetList":
                    break;
                case "recordList":
                    break;
                default:
                    this.data.get(index).setValue(this.currentValue);
            }

            this.currentObjectClass = null;
        } else {
            switch (qName) {
                case "balanceSheet":
                    this.currentObjectClass = ArrayList.class;
                    break;
                case "record":
                    this.currentObjectClass = ArrayList.class;
                    break;

            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = (new String(ch, start, length)).trim();

        if (this.currentObjectClass == null) {
            this.currentValue = s;
        } else {
            if (this.currentObjectClass == BalanceSheet.class) {
                switch (this.currentTag) {
                    case "id":
                        ((BalanceSheet) this.currentValue).setId(Integer.parseInt(s));
                        break;
                    case "title":
                        ((BalanceSheet) this.currentValue).setTitle(s);
                        break;
                    case "dateOfLastChange":
                        ((BalanceSheet) this.currentValue).setDateOfLastChange(s);
                        break;
                    case "dateOfCreation":
                        ((BalanceSheet) this.currentValue).setDateOfCreation(s);
                        break;
                }
            } else if (this.currentObjectClass == User.class) {
                switch (this.currentTag) {
                    case "mail":
                        ((User) this.currentValue).setMail(s);
                        break;
                    case "lastname":
                        ((User) this.currentValue).setLastname(s);
                        break;
                    case "firstname":
                        ((User) this.currentValue).setFirstname(s);
                        break;
                    case "userType":
                        ((User) this.currentValue).setUserType(UserType.getUserTypeByIdentifier(s));
                        break;
                }
            } else if (this.currentObjectClass == Record.class) {
                switch (this.currentTag) {
                    case "id":
                        ((Record) this.currentValue).setId(Integer.parseInt(s));
                        break;
                    case "title":
                        ((Record) this.currentValue).setTitle(s);
                        break;
                    case "description":
                        ((Record) this.currentValue).setDescription(s);
                        break;
                    case "amount":
                        ((Record) this.currentValue).setAmount(Double.parseDouble(s));
                        break;
                    case "recordDate":
                        ((Record) this.currentValue).setRecordDate(s);
                        break;
                    case "catName":
                        ((Record) this.currentValue).setCatName(s);
                        break;
                }
            }
        }
    }

    @Override
    public void error(SAXParseException spe) throws SAXException {
        System.out.print("[XML parse error: " + spe.getMessage() + "]\n");
    }
}
