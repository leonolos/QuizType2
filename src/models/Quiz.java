package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {

    //Properties
    private Integer quizId;
    private String title;

    public static class MetaData {

        public static final String TABLE_NAME = "quizs";
        public static final String QUIZ_ID = "quiz_id";
        public static final String TITLE = "title";
    }

    //Constructors
    public Quiz() {
    }

    public Quiz(String title) {
        this.title = title;
    }

    //Getters
    public Integer getQuizId() {
        return quizId;
    }

    public String getTitle() {
        return title;
    }

    //Setters
    public void setQuizId(Integer quizId) {
        this.quizId = quizId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Quiz{" + "quizId=" + quizId + ", title=" + title + '}';
    }

    //Other Methods
    public static void createTable() {

        try {
//            String query = "CREATE TABLE quiz (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT);";
            //Ici table_Name --> quizs
            //CREATE TABLE quizs (quiz_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT)

            String raw = "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT);";
            String query = String.format(raw, MetaData.TABLE_NAME, MetaData.QUIZ_ID, MetaData.TITLE);
            System.err.println(query);

            String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";
            Class.forName("org.sqlite.JDBC");
            Connection connection;
            connection = DriverManager.getConnection(connectionUrl);
            PreparedStatement ps = connection.prepareStatement(query);
            boolean b = ps.execute();
            System.out.println("models.quiz.createTable()");
            System.out.println(b);
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int save() {
        String raw = "Insert into %s (%s) values (?)";
        String query = String.format(raw, MetaData.TABLE_NAME, MetaData.TITLE);
        String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, this.title);
                int i = ps.executeUpdate();
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    return keys.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
        return -1;
    }

    public boolean save(ArrayList<Question> questions) {
        boolean flag = true;
        this.quizId = this.save();

        for (Question q : questions) {
            flag=flag && q.save();    
            System.out.println(flag);
        }
        return flag;
    }
}
