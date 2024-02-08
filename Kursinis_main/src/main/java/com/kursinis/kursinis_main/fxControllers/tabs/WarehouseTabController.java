package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.model.Manager;
import com.kursinis.kursinis_main.model.ProductType;
import com.kursinis.kursinis_main.model.User;
import com.kursinis.kursinis_main.model.Warehouse;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class WarehouseTabController {
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public TextField addressWarehouseField;
    @FXML
    public TextField titleWarehouseField;
    @FXML
    public ComboBox managerOfWarehouseComboBox;

    private CustomHib customHib;

    public void loadTabData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;
        loadWarehouseList();
        managerOfWarehouseComboBox.getItems().clear();
        managerOfWarehouseComboBox.getItems().addAll(customHib.getAllRecords(Manager.class));
    }

    private void loadWarehouseList() {
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(customHib.getAllRecords(Warehouse.class));
    }

    public void addNewWarehouse() {
        customHib.create(new Warehouse(titleWarehouseField.getText(), addressWarehouseField.getText(), (Manager) managerOfWarehouseComboBox.getSelectionModel().getSelectedItem()));
        loadWarehouseList();
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        Warehouse warehouse = customHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        warehouse.setTitle(titleWarehouseField.getText());
        warehouse.setAddress(addressWarehouseField.getText());
        customHib.update(warehouse);
        loadWarehouseList();
    }

    public void deleteWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        //Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        customHib.delete(Warehouse.class, selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        titleWarehouseField.setText(selectedWarehouse.getTitle());
        addressWarehouseField.setText(selectedWarehouse.getAddress());
    }

}
