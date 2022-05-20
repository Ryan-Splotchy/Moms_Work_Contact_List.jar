package sample;

import DataModel.Contact;
import DataModel.ContactData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Optional;

public class Controller {
    private ObservableList<Contact> contacts = ContactData.getContactDataInstance().getContacts();
    @FXML
    private TableView<Contact> tableView;
    @FXML
    private BorderPane mainWindow;




    public void initialize() {
        //? initial test data

//        String name1 = "Ryan";
//        String name2 = "Shelton";
//        String ph = "123 456 7890";
//        String note = "The First";
//        Contact contact = new Contact(name1, name2, ph, note);
//        tableView.getItems().add(contact);
//
//        name1 = "Jack";
//        name2 = "Simmons";
//        ph = "123 456 7890";
//        note = "";
//        contact = new Contact(name1, name2, ph, note);
//        contact.setNotes("Stay off his lawn");
//        tableView.getItems().add(contact);
//
//        name1 = "John";
//        name2 = "Simmons";
//        ph = "123 456 7890";
//        note = "Hey There! This is a test";
//        contact = new Contact(name1, name2, ph, note);
//        tableView.getItems().add(contact);

        tableView.getItems().addAll(contacts);
    }




    public void dialogWindow() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dialogWindow.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TODO: add things.

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController controller = fxmlLoader.getController();


        Optional<ButtonType> result = dialog.showAndWait();
//        dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty()
//                .bind(Bindings.createBooleanBinding(
//                        () -> controller.getFirstNameField().getText().isEmpty() ||
//                                controller.getLastNameField().getText().isEmpty() ||
//                                controller.getPhoneNumberField().getText().isBlank(),
//                        controller.getFirstNameField().textProperty(),
//                        controller.getLastNameField().textProperty(),
//                        controller.getPhoneNumberField().textProperty()
//                ));

        if(result.isPresent() && result.get() == ButtonType.OK) {
            if(controller.checkNullFields()) {
                Contact contact = controller.processResults();
                tableView.getItems().add(contact);
                ContactData.getContactDataInstance().addContact(contact);
                ContactData.getContactDataInstance().saveContacts();
            }
        }
    }

    public void editDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit a Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("editDialogWindow.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            e.printStackTrace();
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            MainEditWindow con = fxmlLoader.getController();
            // make sure the name of the class you want the controller from is correct (not everything is dialog controller)
            dialog.show();
            con.editContact();
            tableView.getItems().removeAll(contacts);
            tableView.getItems().addAll(ContactData.getContactDataInstance().getContacts());
            dialog.close();
            ContactData.getContactDataInstance().saveContacts();
        }
    }


    public void deleteDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Choose a Contact to delete");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("deleteWindow.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e) {
            e.printStackTrace();
        }


        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            DeleteController controller = fxmlLoader.getController();
            Contact contact = controller.deleteContact();
            if(contact != null) {
                tableView.getItems().remove(contact);
                ContactData.getContactDataInstance().saveContacts();
            }
        }
    }




}
