package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.StartGui;
import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.Product;
import com.kursinis.kursinis_main.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CartTabController {
    @FXML
    private AnchorPane cartTabRoot;

    @FXML
    public ListView<Product> availableProductsList;
    @FXML
    public ListView<Product> currentOrderList;

    private User currentUser;
    private CustomHib customHib;

    public void loadTabData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;
        this.currentUser = currentUser;

        loadProductList();
    }

    public void addToCart() {
        Product selectedProduct = availableProductsList.getSelectionModel().getSelectedItem();
        currentOrderList.getItems().add(selectedProduct);
        availableProductsList.getItems().remove(selectedProduct);
    }

    public void removeFromCart() {
        Product productToRemove = currentOrderList.getSelectionModel().getSelectedItem();
        availableProductsList.getItems().add(productToRemove);
        currentOrderList.getItems().remove(productToRemove);
    }

    public void createOrder() {
        customHib.addToCart(currentUser.getId(), currentOrderList.getItems());
        currentOrderList.getItems().clear();
        //productList.getItems().addAll(customHib.getAvailableProducts());
    }

    public void loadProductList() {
        availableProductsList.getItems().clear();
        availableProductsList.getItems().addAll(customHib.getProductsNotInCart());
    }
}
