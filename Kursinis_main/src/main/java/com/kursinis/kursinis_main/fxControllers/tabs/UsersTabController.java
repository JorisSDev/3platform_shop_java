package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.StartGui;
import com.kursinis.kursinis_main.fxControllers.JavaFxCustomUtils;
import com.kursinis.kursinis_main.fxControllers.RegistrationController;
import com.kursinis.kursinis_main.fxControllers.tableParameters.CustomerTableParameters;
import com.kursinis.kursinis_main.fxControllers.tableParameters.ManagerTableParameters;
import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.Customer;
import com.kursinis.kursinis_main.model.Manager;
import com.kursinis.kursinis_main.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class UsersTabController implements Initializable {
    @FXML
    public TableView customerTable;
    @FXML
    public TableView managerTable;

    @FXML
    public TableColumn<CustomerTableParameters, Integer> idColumnCustomer;
    @FXML
    public TableColumn<CustomerTableParameters, String> loginColumnCustomer;
    @FXML
    public TableColumn<CustomerTableParameters, String> passwordColumnCustomer;
    @FXML
    public TableColumn<CustomerTableParameters, String> addressColumnCustomer;
    @FXML
    public TableColumn<CustomerTableParameters, Void> dummyColumnCustomer;
    @FXML
    public TableColumn<ManagerTableParameters, Integer> idColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, String> loginColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, String> passwordColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, Integer> EmployeeIdColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, String> employmentDateColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, Boolean> isAdminColumnManager;
    @FXML
    public TableColumn<ManagerTableParameters, Void> dummyColumnManager;
    public Button addUserButton;

    private ObservableList<CustomerTableParameters> dataCustomer = FXCollections.observableArrayList();
    private ObservableList<ManagerTableParameters> dataManager = FXCollections.observableArrayList();

    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;
    private User currentUser;

    public void loadTabData(CustomHib customHib, User currentUser, EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.customHib = customHib;
        this.currentUser = currentUser;
        limitAccess();
        loadTables();
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            if (!manager.isAdmin()) {
                managerTable.setDisable(true);
            }
        } else if (currentUser.getClass() == Customer.class) {
            Customer customer = (Customer) currentUser;
            //tabPane.getTabs().remove(usersTab);
            //tabPane.getTabs().remove(warehouseTab);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "WTF", "WTF", "WTF");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Allows data to be displayed
        idColumnCustomer.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumnCustomer.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumnCustomer.setCellValueFactory(new PropertyValueFactory<>("password"));
        addressColumnCustomer.setCellValueFactory(new PropertyValueFactory<>("address"));

        idColumnManager.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumnManager.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumnManager.setCellValueFactory(new PropertyValueFactory<>("password"));
        EmployeeIdColumnManager.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employmentDateColumnManager.setCellValueFactory(new PropertyValueFactory<>("employmentDate"));
        isAdminColumnManager.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

        allowFieldEditing();

        dummyColumnCustomer.setCellFactory(createCustomerDeleteButton());
        dummyColumnManager.setCellFactory(createManagerDeleteButton());
    }

    public void limitAccess() {
        if (!((Manager) currentUser).isAdmin()) {
            //dont show managerTable and addUserButton
            managerTable.setVisible(false);
            addUserButton.setVisible(false);
        }
    }

    private Callback<TableColumn<CustomerTableParameters, Void>, TableCell<CustomerTableParameters, Void>> createCustomerDeleteButton() {
        return param -> {
            final TableCell<CustomerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        CustomerTableParameters row = getTableView().getItems().get(getIndex());
                        customHib.delete(Customer.class, row.getId());

                        // Update the table after deletion
                        loadTables();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
    }

    private Callback<TableColumn<ManagerTableParameters, Void>, TableCell<ManagerTableParameters, Void>> createManagerDeleteButton() {
        return param -> {
            final TableCell<ManagerTableParameters, Void> cell = new TableCell<>() {
                private final Button deleteButton = new Button("Delete");

                {
                    deleteButton.setOnAction(event -> {
                        ManagerTableParameters row = getTableView().getItems().get(getIndex());
                        customHib.delete(Manager.class, row.getId());

                        // Update the table after deletion
                        loadTables();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                    }
                }
            };
            return cell;
        };
    }


    private void allowFieldEditing() {
        customerTable.setEditable(true);
        managerTable.setEditable(true);
        idColumnCustomer.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumnCustomer.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setId(event.getNewValue());
            Customer customer = customHib.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setId(event.getNewValue());
            customHib.update(customer);
        });
        loginColumnCustomer.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumnCustomer.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
            Customer customer = customHib.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setLogin(event.getNewValue());
            customHib.update(customer);
        });
        passwordColumnCustomer.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumnCustomer.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            Customer customer = customHib.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setPassword(event.getNewValue());
            customHib.update(customer);
        });
        addressColumnCustomer.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumnCustomer.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setAddress(event.getNewValue());
            Customer customer = customHib.getEntityById(Customer.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            customer.setAddress(event.getNewValue());
            customHib.update(customer);
        });

        idColumnManager.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        idColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setId(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setId(event.getNewValue());
            customHib.update(manager);
        });
        loginColumnManager.setCellFactory(TextFieldTableCell.forTableColumn());
        loginColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setLogin(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setLogin(event.getNewValue());
            customHib.update(manager);
        });
        passwordColumnManager.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setPassword(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setPassword(event.getNewValue());
            customHib.update(manager);
        });
        EmployeeIdColumnManager.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        EmployeeIdColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmployeeId(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setEmployeeId(String.valueOf(event.getNewValue()));
            customHib.update(manager);
        });
        employmentDateColumnManager.setCellFactory(TextFieldTableCell.forTableColumn());
        employmentDateColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setEmploymentDate(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setEmploymentDate(LocalDate.parse(event.getNewValue()));
            customHib.update(manager);
        });
        isAdminColumnManager.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        isAdminColumnManager.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setIsAdmin(event.getNewValue());
            Manager manager = customHib.getEntityById(Manager.class, event.getTableView().getItems().get(event.getTablePosition().getRow()).getId());
            manager.setAdmin(event.getNewValue());
            customHib.update(manager);
        });
    }

    private void loadTables() {
        clearTableFields();
        List<Customer> customerList = customHib.getAllRecords(Customer.class);
        for (Customer c : customerList) {
            System.out.println("Customer: " + c);
            CustomerTableParameters customerTableParameters = new CustomerTableParameters();

            customerTableParameters.setId(c.getId());
            customerTableParameters.setLogin(c.getLogin());
            customerTableParameters.setPassword(c.getPassword());
            customerTableParameters.setAddress(c.getAddress());

            dataCustomer.add(customerTableParameters);
        }
        customerTable.setItems(dataCustomer);

        List<Manager> managerList = customHib.getAllRecords(Manager.class);
        for (Manager m : managerList) {
            System.out.println("Manager: " + m);
            ManagerTableParameters managerTableParameters = new ManagerTableParameters();

            managerTableParameters.setId(m.getId());
            managerTableParameters.setLogin(m.getLogin());
            managerTableParameters.setPassword(m.getPassword());
            managerTableParameters.setEmployeeId(Integer.parseInt(m.getEmployeeId()));
            managerTableParameters.setEmploymentDate(String.valueOf(m.getEmploymentDate()));
            managerTableParameters.setIsAdmin(m.isAdmin());

            dataManager.add(managerTableParameters);
        }
        managerTable.setItems(dataManager);
    }

    private void clearTableFields() {
        customerTable.getItems().clear();
        managerTable.getItems().clear();
    }

    public void addUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartGui.class.getResource("registration.fxml"));
        Parent parent = fxmlLoader.load();
        RegistrationController formController = fxmlLoader.getController();

        // Pass the EntityManagerFactory to the FormController
        formController.setData(this.entityManagerFactory, true);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Add user");
        stage.setScene(scene);
        stage.setOnHidden(event -> {
            loadTables();
        });
        stage.show();
    }
}
