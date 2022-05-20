package sample;

import DataModel.Contact;
import DataModel.ContactData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.Comparator;
import java.util.Optional;

public class DeleteController {

    @FXML
    private ListView<Contact> listForDeleting;
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    @FXML


    public void initialize() {
        contacts = ContactData.getContactDataInstance().getContacts();

        SortedList<Contact> sortedList = new SortedList<>(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getCompanyName().compareTo(o2.getCompanyName());
            }
        });

        listForDeleting.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
                if(newValue != null) {
                    listForDeleting.getSelectionModel().select(newValue);
                }
            }
        });

        listForDeleting.setItems(sortedList);
        listForDeleting.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listForDeleting.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Contact contact, boolean b) {
                        super.updateItem(contact, b);
                        if(b) {
                            setText(null);
                        }else {
                            setText(contact.getCompanyName() + " " + contact.getName() + " : " + contact.getPhoneNumber());
                        }
                    }
                };
            }
        });
    }

    public Contact deleteContact() {
        Contact contact = listForDeleting.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Delete Contact: " + contact.getCompanyName() + " " + contact.getName());
        alert.setContentText("Are you sure? Press OK to confirm, or Cancel to Back out.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getContactDataInstance().deleteContact(contact);
            return contact;
        }else {
            return null;
        }

    }
}
