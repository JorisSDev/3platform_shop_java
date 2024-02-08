package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.Cart;
import com.kursinis.kursinis_main.model.Customer;
import com.kursinis.kursinis_main.model.Manager;
import com.kursinis.kursinis_main.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import lombok.AccessLevel;

public class OrdersTabController {
    @FXML
    public ListView<Cart> orderList;
    @FXML
    public ListView selectedOrderList;
    @FXML
    public ComboBox statusFilterChoiceBox;
    @FXML
    public ComboBox customerFilterChoiceBox;
    @FXML
    public TextField productCountFilterTextField;
    public Button filterButton;
    public Button clearButton;
    public Button cancelOrderButton;
    public Button confirmOrderButton;
    public PieChart orderStatusPieChart;
    @FXML
    public Text totalItemsSold;
    public Button completeOrderButton;

    private CustomHib customHib;
    private User currentUser;

    public void loadTabData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;
        this.currentUser = currentUser;
        limitAccess();
        loadOrderList();

        statusFilterChoiceBox.getItems().clear();
        customerFilterChoiceBox.getItems().clear();
        statusFilterChoiceBox.getItems().addAll("confirmed", "cancelled", "pending", "delivered");
        customerFilterChoiceBox.getItems().addAll(customHib.getAllRecords(Customer.class));
        loadPieChart();
        loadTotalItemsSold();
    }

    public void limitAccess() {
        if (currentUser instanceof Customer) {
            customerFilterChoiceBox.setVisible(false);
            confirmOrderButton.setVisible(false);
            completeOrderButton.setVisible(false);
        }
    }

    public void loadOrderList() {
        if (currentUser instanceof Customer) {
            orderList.getItems().clear();
            orderList.getItems().addAll(customHib.getOrdersByCustomerId(currentUser.getId()));
            return;
        }
        orderList.getItems().clear();
        orderList.getItems().addAll(customHib.getAllRecords(Cart.class));
        System.out.println("Order list loaded-------------------------------------");
    }

    public void loadSelectedOrder() {
        Cart selectedOrder = orderList.getSelectionModel().getSelectedItem();
        selectedOrderList.getItems().clear();
        selectedOrderList.getItems().addAll(customHib.getProductsByCartId(selectedOrder.getId()));
    }

    public void confirmOrder() {
        Cart selectedOrder = orderList.getSelectionModel().getSelectedItem();
        if (selectedOrder.getStatus().equals("cancelled")) {
            System.out.println("Cannot confirm cancelled order");
            return;
        }
        selectedOrder.setStatus("confirmed");
        customHib.update(selectedOrder);
        loadOrderList();
    }

    public void cancelOrder() {
        Cart selectedOrder = orderList.getSelectionModel().getSelectedItem();
        if (selectedOrder.getStatus().equals("delivered")) {
            System.out.println("Cannot cancel delivered order");
            return;
        }
        if (selectedOrder.getStatus().equals("confirmed")) {
            System.out.println("Cannot cancel confirmed order");
            return;
        }
        selectedOrder.setStatus("cancelled");
        customHib.update(selectedOrder);
        loadOrderList();
    }

    public void completeOrder() {
        Cart selectedOrder = orderList.getSelectionModel().getSelectedItem();
        selectedOrder.setStatus("delivered");
        customHib.update(selectedOrder);
        loadOrderList();
    }

    public void filterOrderList() {
        String status = (String) statusFilterChoiceBox.getSelectionModel().getSelectedItem();
        Customer customer = (Customer) customerFilterChoiceBox.getSelectionModel().getSelectedItem();
        String productCount = productCountFilterTextField.getText();
        orderList.getItems().clear();

        if (productCount.equals("")) {
            productCount = "0";
        }
        if (currentUser instanceof Customer) {
            orderList.getItems().addAll(customHib.getFilteredOrders(status, (Customer) currentUser, Integer.parseInt(productCount)));
        } else {
            orderList.getItems().addAll(customHib.getFilteredOrders(status, customer, Integer.parseInt(productCount)));
        }
        loadPieChart();
        loadTotalItemsSold();
    }

    public void clearFilter() {
        statusFilterChoiceBox.getSelectionModel().clearSelection();
        customerFilterChoiceBox.getSelectionModel().clearSelection();
        productCountFilterTextField.clear();
        loadOrderList();
        loadPieChart();
        loadTotalItemsSold();
    }

    public void loadPieChart() {
        orderStatusPieChart.getData().clear();

        if (currentUser instanceof Customer) {
            orderStatusPieChart.getData().addAll(customHib.getOrderStatusPieChartData((Customer) currentUser));
        } else {
            Customer selectedCustomer = (Customer) customerFilterChoiceBox.getSelectionModel().getSelectedItem();
            orderStatusPieChart.getData().addAll(customHib.getOrderStatusPieChartData(selectedCustomer));
        }

    }

    public void loadTotalItemsSold() {
        long totalItemsSold = 0;
        if (currentUser instanceof Customer) {
            totalItemsSold = customHib.getTotalItemsSold((Customer) currentUser);
            this.totalItemsSold.setText("Total items bought: " + String.valueOf(totalItemsSold));
        } else {
            Customer selectedCustomer = (Customer) customerFilterChoiceBox.getSelectionModel().getSelectedItem();
            totalItemsSold = customHib.getTotalItemsSold(selectedCustomer);
            this.totalItemsSold.setText("Total items sold: " + String.valueOf(totalItemsSold));
        }


    }
}
