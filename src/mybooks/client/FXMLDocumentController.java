/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mybooks.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mybooks.beans.BalanceSheet;
import mybooks.beans.Data;
import mybooks.beans.Record;
import mybooks.beans.User;

/**
 * Controller for the application.
 *
 * @author Tobias
 */
public class FXMLDocumentController implements Initializable {

    //MenuItem
    @FXML
    private MenuItem menuItemAuth;
    @FXML
    private MenuItem menuItemExit;
    @FXML
    private MenuItem menuItemAllBalanceSheets;
    @FXML
    private MenuItem menuItemOneBalanceSheet;
    @FXML
    private MenuItem menuItemMonthOverview;
    @FXML
    private MenuItem menuItemYearOverview;
    @FXML
    private MenuItem menuItemTopTenExpenses;
    @FXML
    private MenuItem menuItemTopTenIncomes;
    //Menu
    @FXML
    private Menu menuBalanceSheet;
    @FXML
    private Menu menuBasicReports;
    @FXML
    private Menu menuExtendedReports;
    //Label
    @FXML
    private Label labelStatus;
    //Pane
    @FXML
    private Pane paneLogin;
    @FXML
    private Pane paneBalanceSheetList;
    @FXML
    private Pane paneBalanceSheet;
    @FXML
    private Pane paneMonthOverview;
    @FXML
    private Pane paneYearOverview;
    @FXML
    private Pane paneTopTenExpenses;
    @FXML
    private Pane paneTopTenIncomes;
    //Input Fields
    @FXML
    private TextField textFieldMail;
    @FXML
    private PasswordField passwordFieldPassword;
    //TableView Balance Sheets
    @FXML
    private TableView<BalanceSheet> tableViewBalanceSheets;
    @FXML
    private TableColumn<BalanceSheet, Integer> tableColumnBalanceSheetId;
    @FXML
    private TableColumn<BalanceSheet, String> tableColumnBalanceSheetTitle;
    @FXML
    private TableColumn<BalanceSheet, String> tableColumnBalanceSheetEditDate;
    @FXML
    private TableColumn<BalanceSheet, String> tableColumnBalanceSheetCreationDate;
    //TableView Balance Sheets
    @FXML
    private TableView<Record> tableViewRecords;
    @FXML
    private TableColumn<Record, Integer> tableColumnRecordId;
    @FXML
    private TableColumn<Record, String> tableColumnRecordDate;
    @FXML
    private TableColumn<Record, String> tableColumnRecordTitle;
    @FXML
    private TableColumn<Record, Double> tableColumnRecordAmount;
    @FXML
    private TableColumn<Record, String> tableColumnRecordDescription;
    @FXML
    private TableColumn<Record, String> tableColumnRecordCategory;

    //Attributes
    private User user;
    private ArrayList<BalanceSheet> balanceSheetList;
    private Pane currentPane;

    @FXML
    private void handleMenu(ActionEvent event) {
        if (event.getSource() instanceof MenuItem) {
            //this.labelStatus.setText(((MenuItem)event.getSource()).getId());

            this.currentPane.setVisible(false);

            switch (((MenuItem) event.getSource()).getId()) {
                case "menuItemAuth":
                    this.currentPane = this.paneLogin;
                    if (this.menuItemAuth.getText().equals("Logout")) {
                        this.user = null;
                        this.menuItemAuth.setText("Login");
                    }
                    break;
                case "menuItemExit":
                    ((Stage) this.currentPane.getScene().getWindow()).close();
                    break;
                case "menuItemAllBalanceSheets":
                    this.currentPane = this.paneBalanceSheetList;
                    break;
                case "menuItemOneBalanceSheet":
                    this.currentPane = this.paneBalanceSheet;
                    BalanceSheet selectedBalanceSheet = this.tableViewBalanceSheets.getSelectionModel().getSelectedItem();

                    HashMap<String, String> params = new HashMap<>();
                    params.put("id", Integer.toString(selectedBalanceSheet.getId()));

                    try {
                        ArrayList<Data> result = WebServiceConnector.getInstance().getData("/bsm/balancesheetrecords", params);
                        
                        HashMap<String, Integer> dictionary = (HashMap<String, Integer>) result.get(0).getValue();
                        boolean success = Boolean.valueOf((String) result.get(dictionary.get("success")).getValue());
                        
                        if (success) {
                            this.tableViewRecords.getItems().clear();
                            this.tableViewRecords.getItems().setAll((ArrayList<Record>)result.get(dictionary.get("recordList")).getValue());
                        }
                        
                    } catch (ConnectionException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                case "menuItemMonthOverview":
                    this.currentPane = this.paneMonthOverview;
                    break;
                case "menuItemYearOverview":
                    this.currentPane = this.paneYearOverview;
                    break;
                case "menuItemTopTenExpenses":
                    this.currentPane = this.paneTopTenExpenses;
                    break;
                case "menuItemTopTenIncomes":
                    this.currentPane = this.paneTopTenIncomes;
                    break;
            }

            this.currentPane.setVisible(true);

        }
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String mail = this.textFieldMail.getText();
        String password = this.passwordFieldPassword.getText();

        if (!mail.isEmpty() && !password.isEmpty()) {
            this.labelStatus.setText("Signing in...");

            WebServiceConnector wsc = WebServiceConnector.getInstance();
            HashMap<String, String> params = new HashMap<>();
            params.put("mail", mail);
            params.put("password", password);
            params.put("submit", "signin");
            try {
                ArrayList<Data> result = wsc.getData("/auth/signin", params);

                if (result != null && !result.isEmpty()) {

                    HashMap<String, Integer> dictionary = (HashMap<String, Integer>) result.get(0).getValue();
                    boolean success = Boolean.valueOf((String) result.get(dictionary.get("success")).getValue());

                    if (success) {
                        this.user = (User) result.get(dictionary.get("user")).getValue();
                        if (this.user != null) {
                            this.labelStatus.setText("Welcome " + this.user.getFirstname() + " " + this.user.getLastname() + " (" + this.user.getMail() + ")");

                            //Enable balance sheet menu item
                            this.menuBalanceSheet.setDisable(false);
                            //Change login menu item:
                            this.menuItemAuth.setText("Logout");

                            //Load balance sheets
                            result = wsc.getData("/bsm/balancesheets");
                            dictionary = (HashMap<String, Integer>) result.get(0).getValue();
                            success = Boolean.valueOf((String) result.get(dictionary.get("success")).getValue());

                            if (success) {

                                this.balanceSheetList = (ArrayList<BalanceSheet>) result.get(dictionary.get("balanceSheetList")).getValue();
                                this.tableViewBalanceSheets.getItems().clear();
                                this.tableViewBalanceSheets.getItems().addAll(this.balanceSheetList);
                                this.menuItemOneBalanceSheet.setDisable(false);

                            } else {
                                this.labelStatus.setText("Could not load balance sheets.");
                            }

                            //Change pane
                            this.paneLogin.setVisible(false);
                            this.paneBalanceSheetList.setVisible(true);
                            this.currentPane = this.paneBalanceSheetList;
                        } else {
                            this.labelStatus.setText("Failed to sign in. Please try again.");
                        }
                    } else {
                        this.labelStatus.setText("The entered credentials are incorrect!");
                    }
                } else {
                    this.labelStatus.setText("Failed to sign in. Please try again.");
                }
            } catch (ConnectionException ex) {
                this.labelStatus.setText("Failed to sign in. Please try again.");
            }

        } else {
            this.labelStatus.setText("Please enter e-mail address and password.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.user = null;
        this.balanceSheetList = null;
        this.currentPane = this.paneLogin;

        //Initialise tables
        this.tableColumnBalanceSheetId.setCellValueFactory(new PropertyValueFactory<BalanceSheet, Integer>("id"));
        this.tableColumnBalanceSheetTitle.setCellValueFactory(new PropertyValueFactory<BalanceSheet, String>("title"));
        this.tableColumnBalanceSheetEditDate.setCellValueFactory(new PropertyValueFactory<BalanceSheet, String>("dateOfLastChange"));
        this.tableColumnBalanceSheetCreationDate.setCellValueFactory(new PropertyValueFactory<BalanceSheet, String>("dateOfCreation"));

        this.tableColumnRecordId.setCellValueFactory(new PropertyValueFactory<Record, Integer>("id"));
        this.tableColumnRecordDate.setCellValueFactory(new PropertyValueFactory<Record, String>("recordDate"));
        this.tableColumnRecordTitle.setCellValueFactory(new PropertyValueFactory<Record, String>("title"));
        this.tableColumnRecordAmount.setCellValueFactory(new PropertyValueFactory<Record, Double>("amount"));
        this.tableColumnRecordDescription.setCellValueFactory(new PropertyValueFactory<Record, String>("description"));
        this.tableColumnRecordCategory.setCellValueFactory(new PropertyValueFactory<Record, String>("catName"));
    }

}
