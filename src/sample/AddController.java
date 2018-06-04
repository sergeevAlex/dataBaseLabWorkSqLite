package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Data.Users;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddController implements Initializable {

    UserDataAccessor ua;


    private Users newUser;

    @FXML
    private TextField userName;

    @FXML
    private TextField userLastName;

    @FXML
    private TextField userID;

    @FXML
    private Button buttonAddUser;

    @FXML
    private TextField userLevelOfLanguage;

    @FXML
    private TextField userComfortTime;

    @FXML
    private Label labelErrorAdd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        labelErrorAdd.setVisible(false);
        try {
            ua = new UserDataAccessor();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        buttonAddUser.setOnAction(event -> {
            if(!userID.getText().isEmpty() && !userComfortTime.getText().isEmpty() && !userName.getText().isEmpty() && !userLastName.getText().isEmpty() && !userLevelOfLanguage.getText().isEmpty()){
                newUser = new Users(Integer.parseInt(userID.getText()),userName.getText(),userLastName.getText(),Integer.parseInt(userLevelOfLanguage.getText()),Integer.parseInt(userComfortTime.getText()));
                try {
                    ua.addNewUser(newUser);
                } catch (SQLException e) {
                    System.out.println("Error in adding new user...");
                    e.printStackTrace();
                }
                Stage stage = (Stage) buttonAddUser.getScene().getWindow();
                stage.close();
            }
            else {
                labelErrorAdd.setTextFill(Color.web("#FF0000"));
                labelErrorAdd.setVisible(true);
            }
        });
    }
}
