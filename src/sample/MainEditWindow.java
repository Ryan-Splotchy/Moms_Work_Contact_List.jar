package sample;

import DataModel.Contact;
import DataModel.ContactData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Comparator;
import java.util.Optional;

public class MainEditWindow {
    @FXML
    private ListView<Contact> listView;
    private static ObservableList<Contact> contacts = FXCollections.observableArrayList();
    @FXML
    private BorderPane editDialogMainWindow;


    public ListView<Contact> getListView() {
        return listView;
    }

    public void initialize() {
        contacts = ContactData.getContactDataInstance().getContacts();

        SortedList<Contact> sortedList = new SortedList<>(contacts, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {
                return o1.getCompanyName().compareTo(o2.getCompanyName());
            }
        });

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observableValue, Contact oldValue, Contact newValue) {
                if(newValue != null) {
                    listView.getSelectionModel().select(newValue);
                }
            }
        });

        listView.setItems(sortedList);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listView.setCellFactory(new Callback<ListView<Contact>, ListCell<Contact>>() {
            @Override
            public ListCell<Contact> call(ListView<Contact> contactListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Contact contact, boolean b) {
                        super.updateItem(contact, b);
                        if(b) {
                            setText(null);
                        }else {
                            setText(contact.getCompanyName() + " " + contact.getName());
                        }
                    }
                };
            }
        });
    }


    public void editContact() {
        Contact contact = listView.getSelectionModel().getSelectedItem();
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(editDialogMainWindow.getScene().getWindow());
        dialog.setTitle("Editing " + contact.getCompanyName() + " " + contact.getName());
        dialog.setHeaderText("Current Details: " + "\n\n" + "Company Name: " + contact.getCompanyName() + "\n" +
                            "Name: " + contact.getName() + "\n" +
                            "Phone Number: " + contact.getPhoneNumber() + "\n" +
                            "Notes: " + contact.getNotes() + "\n" + "\n" + "Enter New Details: ");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("editContact.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        EditContactMiniController controller = fxmlLoader.getController();
        controller.getCompanyNameField().setText(contact.getCompanyName());
        controller.getNameField().setText(contact.getName());
        controller.getPhoneNumberField().setText(contact.getPhoneNumber());
        controller.getNotesField().setText(contact.getNotes());
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {

            if(controller.checkNullFields()) {
               Contact contact1 = controller.editContact();
               contact.setCompanyName(contact1.getCompanyName());
               contact.setName(contact1.getName());
               contact.setPhoneNumber(contact1.getPhoneNumber());
               contact.setNotes(contact1.getNotes());
           }
        }else if(result.isPresent() && result.get() == ButtonType.CANCEL) {
            dialog.close();
        }
    }

}
