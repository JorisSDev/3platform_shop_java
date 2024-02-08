package com.kursinis.kursinis_main.fxControllers;

import com.kursinis.kursinis_main.fxControllers.tabs.*;
import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class MainShopController {
    //-----------------------<General>-----------------------------
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab cartTabMain;
    @FXML
    public Tab productsTabMain;
    @FXML
    public Tab warehouseTabMain;
    @FXML
    public Tab ordersTabMain;
    @FXML
    public Tab usersTabMain;
    public Tab reviewsTabMain;

    @FXML
    private CartTabController cartTabController;
    @FXML
    private OrdersTabController ordersTabController;
    @FXML
    private ProductTabController productsTabController;
    @FXML
    private UsersTabController usersTabController;
    @FXML
    private WarehouseTabController warehouseTabController;
    @FXML
    private ReviewsTabController reviewsTabController;

    private EntityManagerFactory entityManagerFactory;
    private CustomHib customHib;
    private User currentUser;

    public void loadTabValues() {
        if (productsTabMain.isSelected()) {
            productsTabController.loadTabData(customHib, currentUser);
        } else if (warehouseTabMain.isSelected()) {
            warehouseTabController.loadTabData(customHib, currentUser);
        } else if (cartTabMain.isSelected()) {
            List<Warehouse> record = customHib.getAllRecords(Warehouse.class);
            cartTabController.loadTabData(customHib, currentUser);
        } else if (ordersTabMain.isSelected()) {
            ordersTabController.loadTabData(customHib, currentUser);
        } else if (usersTabMain.isSelected()) {
            usersTabController.loadTabData(customHib, currentUser, entityManagerFactory);
        } else if (reviewsTabMain.isSelected()) {
            reviewsTabController.loadTabData(customHib, currentUser);
        }
        System.out.println("Loading tab values-----------------------------------");
    }

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        this.customHib = new CustomHib(entityManagerFactory);
        cartTabController.loadTabData(customHib, currentUser);

        limitAccess();
        if (currentUser instanceof Manager) {
            tabPane.getSelectionModel().select(usersTabMain); // Set the default tab for managers
        } else if (currentUser instanceof Customer) {
            tabPane.getSelectionModel().select(cartTabMain); // Set the default tab for customers
        }
    }

    public void limitAccess() {
        if (currentUser instanceof Customer) {
            tabPane.getTabs().remove(usersTabMain);
            tabPane.getTabs().remove(warehouseTabMain);
            tabPane.getTabs().remove(productsTabMain);
            // remove other tabs that are only for managers
        } else if (currentUser instanceof Manager) {
            if (!((Manager) currentUser).isAdmin()) {
                tabPane.getTabs().remove(cartTabMain);
                // remove other tabs that are only for customers & admins
            }
            // if the manager is an admin, do not remove any tabs
        }
    }

}
