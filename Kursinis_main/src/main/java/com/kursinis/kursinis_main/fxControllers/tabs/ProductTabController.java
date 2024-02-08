package com.kursinis.kursinis_main.fxControllers.tabs;

import com.kursinis.kursinis_main.fxControllers.JavaFxCustomUtils;
import com.kursinis.kursinis_main.hibernateControllers.CustomHib;
import com.kursinis.kursinis_main.hibernateControllers.GenericHib;
import com.kursinis.kursinis_main.model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductTabController {
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public ComboBox<ProductType> productType;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public TextField productManufacturerField;
    @FXML
    public TextField productPriceField;

    @FXML
    public VBox productVBox;
    @FXML
    public VBox CPUVBox;
    @FXML
    public VBox GPUVBox;
    @FXML
    public VBox RAMVBox;
    @FXML
    public VBox MotherboardVBox;

    @FXML
    public TextField CPUArchitectureField;
    @FXML
    public TextField CPUClockSpeedField;
    @FXML
    public TextField CPUCoreCountField;
    @FXML
    public CheckBox CPUAPUField;

    @FXML
    public TextField GPUVRAMSizeField;
    @FXML
    public TextField GPUClockSpeedField;
    @FXML
    public TextField GPUPowerConsumptionField;

    @FXML
    public TextField RAMTypeField;
    @FXML
    public TextField RAMCapacityField;
    @FXML
    public TextField RAMSpeedField;

    @FXML
    public TextField MotherboardSocketTyperField;
    @FXML
    public TextField MotherboardFormFactorField;
    @FXML
    public TextField MotherboardRAMTypeField;

    private CustomHib customHib;

    public void loadTabData(CustomHib customHib, User currentUser) {
        this.customHib = customHib;

        loadProductList();
        warehouseComboBox.getItems().clear();
        warehouseComboBox.getItems().addAll(customHib.getAllRecords(Warehouse.class));
        productType.getItems().clear();
        productType.getItems().addAll(ProductType.values());
    }

    private void loadProductList() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(customHib.getAllRecords(Product.class));
    }

    public void disableProductFields() {
        CPUVBox.setDisable(true);
        GPUVBox.setDisable(true);
        RAMVBox.setDisable(true);
        MotherboardVBox.setDisable(true);
    }

    public void enableProductFields() {
        disableProductFields();
        if (productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            CPUVBox.setDisable(false);
        }
        if (productType.getSelectionModel().getSelectedItem() == ProductType.GPU) {
            GPUVBox.setDisable(false);
        }
        if (productType.getSelectionModel().getSelectedItem() == ProductType.RAM) {
            RAMVBox.setDisable(false);
        }
        if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            MotherboardVBox.setDisable(false);
        }
    }

    public void clearProductSelection() {
        productType.getSelectionModel().select(null);
        warehouseComboBox.getSelectionModel().select(null);

        productTitleField.clear();
        productDescriptionField.clear();
        productManufacturerField.clear();
        productPriceField.clear();

        CPUArchitectureField.clear();
        CPUClockSpeedField.clear();
        CPUCoreCountField.clear();
        CPUAPUField.setSelected(false);

        GPUVRAMSizeField.clear();
        GPUClockSpeedField.clear();
        GPUPowerConsumptionField.clear();

        RAMTypeField.clear();
        RAMCapacityField.clear();
        RAMSpeedField.clear();

        MotherboardSocketTyperField.clear();
        MotherboardFormFactorField.clear();
        MotherboardRAMTypeField.clear();
    }

    public int getSelectedProductTypeIndex(Product selectedProduct) {
        int index = -1;
        switch (selectedProduct.getClass().getName()) {
            case "com.kursinis.kursinis_main.model.CPU":
                index = 0;
                break;
            case "com.kursinis.kursinis_main.model.GPU":
                index = 1;
                break;
            case "com.kursinis.kursinis_main.model.RAM":
                index = 2;
                break;
            case "com.kursinis.kursinis_main.model.Motherboard":
                index = 3;
                break;
            case "com.kursinis.kursinis_main.model.Plant":
                index = 4;
                break;
        }
        return index;
    }

    public void loadSelectedProductData() {
        enableProductFields();
        clearProductSelection();

        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();

        productTitleField.setText(selectedProduct.getTitle());
        productDescriptionField.setText(selectedProduct.getDescription());
        productManufacturerField.setText(selectedProduct.getManufacturer());
        productPriceField.setText(String.valueOf(selectedProduct.getPrice()));

        productType.getSelectionModel().select(getSelectedProductTypeIndex(selectedProduct));
        warehouseComboBox.getSelectionModel().select(selectedProduct.getWarehouse());

        if (selectedProduct.getClass() == CPU.class) {
            CPU cpu = (CPU) selectedProduct;
            CPUArchitectureField.setText(cpu.getArchitecture());
            CPUClockSpeedField.setText(String.valueOf(cpu.getClockspeed_GHz()));
            CPUCoreCountField.setText(String.valueOf(cpu.getCoreCount()));
            CPUAPUField.setSelected(cpu.getIsAPU());
        }
        if (selectedProduct.getClass() == GPU.class) {
            GPU gpu = (GPU) selectedProduct;
            GPUVRAMSizeField.setText(String.valueOf(gpu.getVRAMSize_GB()));
            GPUClockSpeedField.setText(String.valueOf(gpu.getClockSpeed_MHz()));
            GPUPowerConsumptionField.setText(String.valueOf(gpu.getPowerConsumption_W()));
        }
        if (selectedProduct.getClass() == RAM.class) {
            RAM ram = (RAM) selectedProduct;
            RAMTypeField.setText(ram.getType());
            RAMCapacityField.setText(String.valueOf(ram.getCapacity_GB()));
            RAMSpeedField.setText(String.valueOf(ram.getSpeed_MHz()));
        }
        if (selectedProduct.getClass() == Motherboard.class) {
            Motherboard motherboard = (Motherboard) selectedProduct;
            MotherboardSocketTyperField.setText(motherboard.getSocketType());
            MotherboardFormFactorField.setText(motherboard.getFormFactor());
            MotherboardRAMTypeField.setText(motherboard.getRAMType());
        }

    }

    public void addNewProduct() {
        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
        Warehouse warehouse = customHib.getEntityById(Warehouse.class, selectedWarehouse.getId());

        if (productType.getSelectionModel().getSelectedItem() == ProductType.CPU) {
            customHib.create(new CPU(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), Double.parseDouble(productPriceField.getText()), warehouse,
                    CPUArchitectureField.getText(), Double.parseDouble(CPUClockSpeedField.getText()), Integer.parseInt(CPUCoreCountField.getText()), CPUAPUField.isSelected()));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.GPU) {
            customHib.create(new GPU(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), Double.parseDouble(productPriceField.getText()), warehouse,
                    Double.parseDouble(GPUClockSpeedField.getText()), Integer.parseInt(GPUVRAMSizeField.getText()), Integer.parseInt(GPUPowerConsumptionField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.RAM) {
            customHib.create(new RAM(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), Double.parseDouble(productPriceField.getText()), warehouse,
                    RAMTypeField.getText(), Integer.parseInt(RAMCapacityField.getText()), Integer.parseInt(RAMSpeedField.getText())));
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.MOTHERBOARD) {
            customHib.create(new Motherboard(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), Double.parseDouble(productPriceField.getText()), warehouse,
                    MotherboardSocketTyperField.getText(), MotherboardFormFactorField.getText(), MotherboardRAMTypeField.getText()));
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Creation error", "No selection", "No product type selected");
        }

        loadProductList();
    }

    public void updateProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        Product product = customHib.getEntityById(Product.class, selectedProduct.getId());
        product.setTitle(productTitleField.getText());
        product.setDescription(productDescriptionField.getText());
        product.setManufacturer(productManufacturerField.getText());
        product.setPrice(Double.parseDouble(productPriceField.getText()));

        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
        Warehouse warehouse = customHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        product.setWarehouse(warehouse);

        if (product.getClass() == CPU.class) {
            CPU cpu = (CPU) product;
            cpu.setArchitecture(CPUArchitectureField.getText());
            cpu.setClockspeed_GHz(Double.parseDouble(CPUClockSpeedField.getText()));
            cpu.setCoreCount(Integer.parseInt(CPUCoreCountField.getText()));
            cpu.setIsAPU(CPUAPUField.isSelected());
        }
        if (product.getClass() == GPU.class) {
            GPU gpu = (GPU) product;
            gpu.setVRAMSize_GB(Integer.parseInt(GPUVRAMSizeField.getText()));
            gpu.setClockSpeed_MHz(Double.parseDouble(GPUClockSpeedField.getText()));
            gpu.setPowerConsumption_W(Integer.parseInt(GPUPowerConsumptionField.getText()));
        }
        if (product.getClass() == RAM.class) {
            RAM ram = (RAM) product;
            ram.setType(RAMTypeField.getText());
            ram.setCapacity_GB(Integer.parseInt(RAMCapacityField.getText()));
            ram.setSpeed_MHz(Integer.parseInt(RAMSpeedField.getText()));
        }
        if (product.getClass() == Motherboard.class) {
            Motherboard motherboard = (Motherboard) product;
            motherboard.setSocketType(MotherboardSocketTyperField.getText());
            motherboard.setFormFactor(MotherboardFormFactorField.getText());
            motherboard.setRAMType(MotherboardRAMTypeField.getText());
        }

        customHib.update(product);
        loadProductList();
    }

    public void deleteProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        System.out.println("Deleting selectedProduct: " + selectedProduct);
        System.out.println("ID: " + selectedProduct.getId());
        System.out.println("---------------------------------------------------------------------------------------");

        customHib.delete(Product.class, selectedProduct.getId());
        loadProductList();
    }
}
