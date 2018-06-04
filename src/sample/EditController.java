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

public class EditController implements Initializable {

    UserDataAccessor ua;
    private Users editableUser;


    public void setText(int id, String userName, String userLastName, int level, int time) {
        setUserID(id);
        setUserName(userName);
        setUserLastName(userLastName);
        setUserLevelOfLanguage(level);
        setUserComfortTime(time);
    }


    public TextField getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.setText(userName);
    }

    public TextField getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName.setText(userLastName);
    }

    public TextField getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID.setText(String.valueOf(userID));
    }

    public TextField getUserLevelOfLanguage() {
        return userLevelOfLanguage;
    }

    public void setUserLevelOfLanguage(int userLevelOfLanguage) {
        this.userLevelOfLanguage.setText(String.valueOf(userLevelOfLanguage));
    }

    public TextField getUserComfortTime() {
        return userComfortTime;
    }

    public void setUserComfortTime(int userComfortTime) {
        this.userComfortTime.setText(String.valueOf(userComfortTime));
    }

    @FXML
    private TextField userName;

    @FXML
    private TextField userLastName;

    @FXML
    private TextField userID;

    @FXML
    private Button buttonUpdateUser;

    @FXML
    private TextField userLevelOfLanguage;

    @FXML
    private TextField userComfortTime;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ua = new UserDataAccessor();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        buttonUpdateUser.setOnAction(event -> {
            Users tempUser = new Users(Integer.valueOf(userID.getText()),userName.getText(),userLastName.getText(),Integer.valueOf(userLevelOfLanguage.getText()),
                    Integer.valueOf(userComfortTime.getText()));
            ua.updateUserInfoWithPreferences(tempUser);
            Stage stage = (Stage) buttonUpdateUser.getScene().getWindow();
            stage.close();
        });
//        editableUser = ua.getPreferences(editableUser.getId());
//        userComfortTime.setText(String.valueOf(editableUser.getComfortTime()));
//        userLevelOfLanguage.setText(String.valueOf(editableUser.getUserLevelOfLanguage()));

//        ua.getPreferences(10);


    }
}
