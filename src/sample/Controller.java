package sample;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.NumberStringConverter;
import sample.Data.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

  private UserDataAccessor ud;

  private String table;

  @FXML private Button saveButton;
  @FXML private Button deleteButton;
  @FXML private Button editButton;
  @FXML private TextField searchName;
  @FXML private TextField searchLastName;
  @FXML private Button buttonSearch;
  @FXML private TextField searchID;

    private void disableAllTables() throws SQLException {
        userView.setVisible(false);
        //SetOff all tables...
    }

    private void hideButtons() {
      editButton.setVisible(false);
    }
    private void setSearch(boolean rule) {
        buttonSearch.setVisible(rule);
        searchID.setVisible(rule);
        searchName.setVisible(rule);
        searchLastName.setVisible(rule);
    }



    private void createDialog(String method) {
        Parent root;
        switch (method){
            case "add": {
                try {
                    root = FXMLLoader.load(getClass().getResource("add.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Adding new user");
                    stage.setScene(new Scene(root));
                    stage.show();

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            } break;
            case "search": {} break;
            case "edit": {} break;
            default:
        }
    }


    private void createEdit(Users user) {
      Parent root;
       Users temp = new Users(user.getId(),user.getFirstName(),user.getLastName(),0,0);
       Users tempNew =  ud.getPreferences(user.getId());

       temp.setUserLevelOfLanguage(tempNew.getUserLevelOfLanguage());
       temp.setComfortTime(tempNew.getComfortTime());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("edit.fxml"));
            loader.load();
            EditController ec = loader.getController();
            ec.setText(temp.getId(),temp.getFirstName(),temp.getLastName(),temp.getUserLevelOfLanguage(),temp.getComfortTime());

            root = loader.getRoot();
            Stage stage = new Stage();
            stage.setTitle("Editing user");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initView(String table) throws SQLException {
        this.table = table;
        disableAllTables();
        userView.setVisible(true);
        userView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        DataResult data = ud.getAllData(table);
        for (int i = 0 ; i < data.getNumColumns() ; i++) {
            TableColumn<List<Object>, Object> column = new TableColumn<>(data.getColumnName(i));
            column.setEditable(true);
            int columnIndex = i;
            column.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
            userView.getColumns().add(column);
        }
        userView.getItems().setAll(data.getData());
    }


    private void initSearchView(int id,String name, String surname) throws SQLException {

        ///////
        this.table = table;
        disableAllTables();
        userView.setVisible(true);
        userView.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );
        DataResult data = ud.searchData(id,name,surname);
        for (int i = 0 ; i < data.getNumColumns() ; i++) {
            TableColumn<List<Object>, Object> column = new TableColumn<>(data.getColumnName(i));
            column.setEditable(true);
            int columnIndex = i;
            column.setCellValueFactory(cellData ->
                    new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
            userView.getColumns().add(column);
        }
        userView.getItems().setAll(data.getData());
    }

    private void clearTableView() {
        userView.getItems().clear();
        userView.getColumns().clear();
    }
    private  ArrayList<String> ch;

    @FXML
    public void deleteButtonClicked() throws SQLException {
        ObservableList<List<Object>> row;
        ObservableList<List<Object>> allRows;
        allRows = userView.getItems();
        row = userView.getSelectionModel().getSelectedItems();
//        allRows.removeAll(row);
        for (int i = 0; i <row.size() ; i++) {
            ud.deleteRowsFromTable(table, Integer.parseInt(String.valueOf(row.get(i).get(0))));
        }
        allRows.removeAll(row);
    }

    @FXML
    public void editButtonClicked() throws SQLException {
        userView.setEditable(true);
        userView.getSelectionModel().getSelectedItems();
    }

    @FXML
    public AnchorPane pane;

    @FXML
    public Label actionLabel;

    @FXML
    private ChoiceBox<String> actionBox;


    //UserRequest
    @FXML private TableView<List<Object>> userView;
    //@FXML private TableColumn<Users,Integer> columnUserId;
    //@FXML private TableColumn<Users,String> columnUserIdUserName;
    //@FXML private TableColumn<Users,String> columnUserIdUserSurname;
   // private ObservableList<Users> usersData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideButtons();
        setSearch(false);
        try {
            ud = new UserDataAccessor();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        ch = new ArrayList<String>(Arrays.asList("Показать всех пользователей",
                "Показать всех наставников",
                "Показать все Аккаунты",
                "Показать все Предпочтения",
                "Найти пользователя",
                "Добавить нового пользователя",
                "Завершить работу"));
        ObservableList<String> choices = FXCollections.observableArrayList(ch);
        actionBox.setItems(choices);

        actionBox.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                      //  actionLabel.setText(ov.getValue().toString());
                        switch (Integer.parseInt(ov.getValue().toString())){
                            case 0:{
                                if(!value.equals(new_value)){
                                    clearTableView();
                                    setSearch(false);
                                }
                                try {
                                    editButton.setVisible(true);
                                    initView("Users");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                                break;
                            case 1:{
                                if(!value.equals(new_value)){
                                    hideButtons();
                                    setSearch(false);
                                    clearTableView();
                                }
                                try {
                                    initView("Supervisors");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                                break;
                            case 2: {
                                if(!value.equals(new_value)){
                                    hideButtons();
                                    setSearch(false);
                                    clearTableView();
                                }
                                try {
                                    initView("Accounts");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }

                            }
                                break;
                            case 3: {
                                if(!value.equals(new_value)){
                                    hideButtons();
                                    clearTableView();
                                    setSearch(false);
                                }
                                try {
                                    initView("Preferences");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                                break;
                            case 4: {
                                if(!value.equals(new_value)){
                                    hideButtons();
                                    clearTableView();
                                }
                                //Searching
                                setSearch(true);
                            }
                            break;
                            case 5: {
                                if(!value.equals(new_value)){
                                    hideButtons();
                                    setSearch(false);
                                    clearTableView();
                                }

                                createDialog("add");
                            }
                                break;
                            case 6: {
                                Stage cur = (Stage) deleteButton.getScene().getWindow();
                                cur.close();
                            } break;
                            default:

                        }
                    }
                });

        editButton.setOnAction(event -> {
            ObservableList<List<Object>> row;
            row = userView.getSelectionModel().getSelectedItems();
            Users tempUser = new Users((int) row.get(0).get(0),String.valueOf(row.get(0).get(1)),String.valueOf(row.get(0).get(2)));
            createEdit(tempUser);
            clearTableView();
        });

        buttonSearch.setOnAction(event -> {
            if(!userView.getColumns().isEmpty()){
                clearTableView();
            }
            int sId;
            if(!searchID.getText().isEmpty()){
                sId = Integer.parseInt(searchID.getText());}
            else {
                sId = -1;
            }
            try {
                initSearchView(sId,searchName.getText(),searchLastName.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }

        });
    }
}