package sample.Data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Users {


    public Users(int id, String name, String surname) {
        setId(id);
        setFirstName(name);
        setLastName(surname);
    }

    public Users(int id, String name, String surname,int level,int time) {
        setId(id);
        setFirstName(name);
        setLastName(surname);
        setComfortTime(time);
        setUserLevelOfLanguage(level);
    }


    public Users() {
    }

    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty firstName = new SimpleStringProperty(this,"firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final IntegerProperty userLevelOfLanguage = new SimpleIntegerProperty(0);
    private final IntegerProperty comfortTime = new SimpleIntegerProperty(100);

    public int getUserLevelOfLanguage() {
        return userLevelOfLanguage.get();
    }

    public IntegerProperty userLevelOfLanguageProperty() {
        return userLevelOfLanguage;
    }

    public void setUserLevelOfLanguage(int userLevelOfLanguage) {
        this.userLevelOfLanguage.set(userLevelOfLanguage);
    }

    public int getComfortTime() {
        return comfortTime.get();
    }

    public IntegerProperty comfortTimeProperty() {
        return comfortTime;
    }

    public void setComfortTime(int comfortTime) {
        this.comfortTime.set(comfortTime);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }



}
