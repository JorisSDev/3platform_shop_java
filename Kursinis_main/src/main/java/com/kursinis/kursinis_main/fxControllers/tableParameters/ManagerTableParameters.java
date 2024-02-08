package com.kursinis.kursinis_main.fxControllers.tableParameters;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ManagerTableParameters extends UserTableParameters {
    private SimpleIntegerProperty employeeId = new SimpleIntegerProperty();
    private SimpleStringProperty employmentDate = new SimpleStringProperty();
    private SimpleBooleanProperty isAdmin = new SimpleBooleanProperty();

    public ManagerTableParameters(SimpleIntegerProperty id, SimpleStringProperty login, SimpleStringProperty password, SimpleIntegerProperty employeeId, SimpleStringProperty employmentDate, SimpleBooleanProperty isAdmin) {
        super(id, login, password);
        this.employeeId = employeeId;
        this.employmentDate = employmentDate;
        this.isAdmin = isAdmin;
    }

    public ManagerTableParameters() {
    }

    public int getEmployeeId() {
        return employeeId.get();
    }

    public SimpleIntegerProperty employeeIdProperty() {
        return employeeId;
    }

    public String getEmploymentDate() {
        return employmentDate.get();
    }

    public SimpleStringProperty employmentDateProperty() {
        return employmentDate;
    }

    public boolean isIsAdmin() {
        return isAdmin.get();
    }

    public SimpleBooleanProperty isAdminProperty() {
        return isAdmin;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId.set(employeeId);
    }

    public void setEmploymentDate(String employmentDate) {
        this.employmentDate.set(employmentDate);
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin.set(isAdmin);
    }
}
