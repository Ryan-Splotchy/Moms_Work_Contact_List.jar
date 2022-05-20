package DataModel;

import javafx.beans.property.SimpleStringProperty;

public class Contact {
    private SimpleStringProperty companyName = new SimpleStringProperty("");
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty phoneNumber  = new SimpleStringProperty("");
    private SimpleStringProperty notes  = new SimpleStringProperty("");

    public Contact() {

    }


    public Contact(String companyName, String name, String phoneNumber, String notes) {
        this.companyName.set(companyName);
        this.name.set(name);
        this.phoneNumber.set(phoneNumber);
        this.notes.set(notes);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public SimpleStringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getNotes() {
        return notes.get();
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes.set(notes);
    }
}
