package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author RSoloN
 */
public class Student {

    private Integer id;
    private String firstName;
    private String lastName;
    private String mobile;
    private Character gender;
    private String email;
    private String password;

    private static class MetaData {

        public static final String TABLE_NAME = "students";
        public static final String ID = "ID";
        public static final String FIRST_NAME = "firstname";
        public static final String LAST_NAME = "lastname";
        public static final String MOBILE = "mobile";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";

    }

    public Student() {
    }

    public Student(String firstName, String lastName, String mobile, Character gender, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.gender = gender;
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public Character getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", mobile=" + mobile + ", gender=" + gender + ", email=" + email + ", password=" + password + '}';
    }

    //Create table
    public static void createTable() {
        String raw = "CREATE TABLE IF NOT EXISTS %s(\n"
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "%s TEXT,\n"
                + "%s TEXT,\n"
                + "%s TEXT,\n"
                + "%s TEXT,\n"
                + "%s TEXT,\n"
                + "%s TEXT)";
        String query = String.format(raw, MetaData.TABLE_NAME,
                MetaData.ID,
                MetaData.FIRST_NAME,
                MetaData.LAST_NAME,
                MetaData.MOBILE,
                MetaData.EMAIL,
                MetaData.PASSWORD,
                MetaData.GENDER
        );
        System.out.println(query);
        try {
            String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection;
            connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.Student.createTable()");
            System.out.println(b);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//Save student

    public Student save() {
        String raw="INSERT into students (%s,%s,%s,%s,%s,%s)\n"
                + "values(?,?,?,?,?,?);";
        String query=String.format(raw, 
                MetaData.FIRST_NAME,
                MetaData.LAST_NAME,
                MetaData.MOBILE,
                MetaData.EMAIL,
                MetaData.PASSWORD,
                MetaData.GENDER);       
        String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, this.firstName);
                ps.setString(2, this.lastName);
                ps.setString(3, this.mobile);
                ps.setString(4, this.email);
                ps.setString(5, this.password);
                ps.setString(6, String.valueOf(this.gender));
                int i = ps.executeUpdate();
                ResultSet keys=ps.getGeneratedKeys();
                if(keys.next()){
                this.id=keys.getInt(1);
                }                
                
                System.out.println("Update Rows: " + i);
                return this;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
