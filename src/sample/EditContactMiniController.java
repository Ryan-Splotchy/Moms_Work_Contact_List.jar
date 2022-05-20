package sample;

import DataModel.Contact;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.util.Optional;

public class EditContactMiniController {
    private final ValidationSupport companyNameValidation = new ValidationSupport();
    private final ValidationSupport nameValidation = new ValidationSupport();
    private final ValidationSupport phoneNumberValidation = new ValidationSupport();
    @FXML
    private TextField companyNameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private TextField notesField;


    public TextField getCompanyNameField() {
        return this.companyNameField;
    }

    public TextField getNameField() {
        return this.nameField;
    }

    public TextField getPhoneNumberField() {
        return this.phoneNumberField;
    }

    public TextField getNotesField() {
        return this.notesField;
    }

    public void initialize() {
        companyNameValidation.registerValidator(companyNameField, Validator.createEmptyValidator("This Field is Required"));
        nameValidation.registerValidator(nameField, Validator.createEmptyValidator("This Field is Required"));
        phoneNumberValidation.registerValidator(phoneNumberField, Validator.createEmptyValidator("This Field is Required"));
    }


    protected boolean checkNullFields() {
        if(companyNameField.getText().isEmpty() || nameField.getText().isEmpty() || phoneNumberField.getText().isEmpty()) {
            //TODO: Alert if fields are empty
            alertForEmptyFields();
            return false;
        }else {
            return true;
        }
    }

    protected Contact editContact() {
        String companyName;
        String name;
        String phoneNumber;
        String notes;

        boolean check = checkNullFields();
        if(check) {
            companyName = companyNameField.getText().strip();
            name = nameField.getText().trim();
            phoneNumber = phoneNumberField.getText().strip();
            if(notesField.getText().isEmpty()) {
                notes = " ";
            }else {
                notes = notesField.getText().strip();
            }
            return new Contact(companyName, name, phoneNumber, notes);
        }else {
            return null;
        }
    }


    protected void alertForEmptyFields() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error!");
        alert.setHeaderText("Please Fill in ALL Required fields");
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.setContentText("Press OK to return to the Main Window");


        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            // I can't figure out a way to keep the creation dialog open when 'ok' is pressed okay, gimme a break i've only been Coding for 6 months.
            alert.close();
        }

    }
}
