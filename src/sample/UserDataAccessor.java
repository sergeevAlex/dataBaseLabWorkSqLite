package sample;
import sample.Data.Users;

import java.sql.*;
import java.util.List ;
import java.util.ArrayList ;
import java.util.Random;

public class UserDataAccessor {

    private Connection connection ;

    public UserDataAccessor() throws SQLException, ClassNotFoundException {

        try {
            connection = SqliteHelper.getConn();
//            Class.forName("org.sqlite.JDBC");
//            connection = DriverManager.getConnection("jdbc:sqlite:/Users/alexey/IdeaProjects/DataBaseLabWork3SQlite/src/sample/Learning.sqlite");
        } catch (Exception e) {
            System.out.println("Connection problems");
            e.printStackTrace();
        }
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public DataResult getAllData(String table) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        try (
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("select * from " + table)) {

            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1 ; i <= columnCount ; i++) {
                columnNames.add(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1 ; i <= columnCount ; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }
        }
        return new DataResult(columnNames, data);
    }



    public DataResult searchData(int id, String name, String surname) throws SQLException {
        List<List<Object>> data = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();


        String sqlRequest = "select * from Users where ";

        if(id != -1){
            sqlRequest += "Users.id = " + id;
        }
        else {
            if(!name.equals("")) {
                sqlRequest += " Users.name = '" + name + "'";
            }
            else {
                if(!surname.equals("")){
                    sqlRequest+= " Users.surname = '" + surname + "'";
                }
            }
        }
        if(!name.equals("")) {
            sqlRequest += " and Users.name = '" + name + "'";
        }
        else {
            if(!surname.equals("")){
                sqlRequest+= " and Users.surname = '" + surname + "'";
            }
        }
        if(!surname.equals("")){
            sqlRequest+= " and Users.surname = '" + surname + "'";
        }

        try (
                Statement stmt = connection.createStatement();

                ResultSet rs = stmt.executeQuery(sqlRequest)) {

            int columnCount = rs.getMetaData().getColumnCount();

            for (int i = 1 ; i <= columnCount ; i++) {
                columnNames.add(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                List<Object> row = new ArrayList<>();
                for (int i = 1 ; i <= columnCount ; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }
        }
        return new DataResult(columnNames, data);
    }

    public void deleteRowsFromTable(String table, int id) throws SQLException {
        Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from " + table);
            Statement stmt1 = connection.createStatement();
               // String s = "delete from " + table + " where " + rs.getMetaData().getColumnName(1) + " = " + id;
            stmt1.executeUpdate("delete from " + table + " where " + rs.getMetaData().getColumnName(1) + " = " + id );
            stmt.close();
            stmt1.close();
            rs.close();
    }

    public void addNewUser(Users user) throws SQLException {
        PreparedStatement stmtForUsers = connection.prepareStatement("insert into Users values (?,?,?)");
        stmtForUsers.setInt(1, user.getId());
        stmtForUsers.setString(2,user.getFirstName());
        stmtForUsers.setString(3,user.getLastName());
        stmtForUsers.executeUpdate();
        Random rd = new Random();
        PreparedStatement stmtForPreferences = connection.prepareStatement("insert into Preferences values (?,?,?,?)");
        stmtForPreferences.setInt(1,user.getId());
        stmtForPreferences.setInt(2,user.getUserLevelOfLanguage());
        stmtForPreferences.setInt(3,user.getComfortTime());
        stmtForPreferences.setInt(4,rd.nextInt(3));
        stmtForPreferences.executeUpdate();
//        stmtForUsers.executeUpdate("insert into Users values (?,?,?)",)
//        ResultSet rs = stmt.executeQuery("select * from " + table);
//        Statement stmt1 = connection.createStatement();
//        // String s = "delete from " + table + " where " + rs.getMetaData().getColumnName(1) + " = " + id;
//        stmt1.executeUpdate("delete from " + table + " where " + rs.getMetaData().getColumnName(1) + " = " + id );
//        stmt.close();
//        stmt1.close();
//        rs.close();
    }

    public Users getPreferences(int userID) {
        Users tempUser = new Users(0,null,null,0,0);

        try {
            //PreparedStatement statement = connection.prepareStatement("select l_part_id,comfort_time from Preferences where person_id = ?");
           // statement.setInt(1,userID);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select lang_part_id,comfort_time from Preferences where user_id = " + userID);
            boolean r = rs.next();
            Object object = rs.getObject(1);
            tempUser.setUserLevelOfLanguage((int) rs.getObject(1));
            tempUser.setComfortTime((int) rs.getObject(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tempUser;
    }


    public void updateUserInfoWithPreferences(Users user){
        try {
            PreparedStatement psUsers = connection.prepareStatement("UPDATE Users SET name = ?, surname = ? where id = ?");
            psUsers.setString(1, user.getFirstName());
            psUsers.setString(2,user.getLastName());
            psUsers.setInt(3,user.getId());
            PreparedStatement psPref = connection.prepareStatement("UPDATE Preferences SET lang_part_id = ?, " +
                    "comfort_time = ? where user_id = ?");
            psPref.setInt(1,user.getUserLevelOfLanguage());
            psPref.setInt(2,user.getComfortTime());
            psPref.setInt(3,user.getId());
            psUsers.execute();
            psPref.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}