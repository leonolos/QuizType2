package quiztype;

import java.io.File;

public class DataCollector {
    public static void main(String[] args) {
        readAndSaveQuizzesData();
    }
    public static void readAndSaveQuizzesData() {
        File file = new File("src/quiztype/sample_data/quizzes/api(0).json");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
    }

}
