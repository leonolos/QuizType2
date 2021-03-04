package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        return this.title;
    }

    //Other Methods
    public static void createTable() {

        try {
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
            System.out.println(ex.getMessage());
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
            flag = flag && q.save();
            System.out.println(flag);
        }
        return flag;
    }

    public static Map<Quiz, List<Question>> getAll() {
        Map<Quiz, List<Question>> quizes = new HashMap<>();
        Quiz key = null;

//        SELECT quizs.quiz_id, title,id, question,option1, 
//option2,option3, option4,answer FROM quizs join questions 
//on questions.quiz_id=quizs.quiz_id
        String query = String.
                format("SELECT %s.%s, %s,"
                        + "%s, %s,"
                        + "%s, %s,"
                        + "%s, %s,"
                        + "%s "
                        + "FROM %s join %s on %s.%s=%s.%s",
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID,
                        MetaData.TITLE,
                        Question.MetaData.QUESTION_ID,
                        Question.MetaData.QUESTION,
                        Question.MetaData.OPTION1,
                        Question.MetaData.OPTION2,
                        Question.MetaData.OPTION3,
                        Question.MetaData.OPTION4,
                        Question.MetaData.ANSWER,
                        MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUIZ_ID,
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID
                );
        String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";
        System.out.println(query);

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    Quiz temp = new Quiz();
                    temp.setQuizId(result.getInt(1));
                    temp.setTitle(result.getString(2));

                    Question tempQuestion = new Question();
                    tempQuestion.setQuestionId(result.getInt(3));
                    tempQuestion.setQuestion(result.getString(4));
                    tempQuestion.setOption1(result.getString(5));
                    tempQuestion.setOption2(result.getString(6));
                    tempQuestion.setOption3(result.getString(7));
                    tempQuestion.setOption4(result.getString(8));
                    tempQuestion.setAnswer(result.getString(9));

                    if (key != null && key.equals(temp)) {
                        quizes.get(key).add(tempQuestion);
                    } else {
                        ArrayList<Question> value = new ArrayList<>();
                        value.add(tempQuestion);
                        quizes.put(temp, value);
                    }
                    key = temp;
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return quizes;
    }

    public static Map<Quiz, Integer> getAllWithQuestionCount() {
        Map<Quiz, Integer> quizes = new HashMap<>();
        Quiz key = null;
        
        //SELECT quizs.quiz_id, title, 
        //COUNT(*) as question_count 
        //FROM quizs join questions on questions.quiz_id=quizs.quiz_id 
        //GROUP BY quizs.quiz_id
        String query = String.
                format("SELECT %s.%s, %s,"
                        + " COUNT(*) as question_count"
                        + " FROM %s join %s on %s.%s=%s.%s"
                        + " GROUP BY quizs.quiz_id",
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID,
                        MetaData.TITLE,
                        MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.TABLE_NAME,
                        Question.MetaData.QUIZ_ID,
                        MetaData.TABLE_NAME,
                        MetaData.QUIZ_ID
                );
        String connectionUrl = "jdbc:sqlite:src/models/dbKiz2.db";
        System.out.println(query);

        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet result = ps.executeQuery();

                while (result.next()) {
                    Quiz temp = new Quiz();
                    temp.setQuizId(result.getInt(1));
                    temp.setTitle(result.getString(2));
                    int count = result.getInt(3);
                    quizes.put(temp, count);

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return quizes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Quiz)) {
            return false;
        }
        Quiz t = (Quiz) obj;

        if (this.quizId == t.quizId) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quizId, title);
    }
}
