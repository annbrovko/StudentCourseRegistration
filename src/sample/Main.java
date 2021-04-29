package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "jdbc:sqlite:/Users/annabrovko/Desktop/StudentCourseRegistration/StudentDB";
        DataBaseModel DBM = new DataBaseModel(url);
//        Controller control = new Controller(DBM);
//        control.setView(view);
        primaryStage.setTitle("Course finder");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            Controller control = loader.getController();

            control.setDbConnection(DBM);
            View view = new View(control);

            primaryStage.setScene(new Scene(root, 800, 700));
            primaryStage.show();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:sqlite:/Users/annabrovko/Desktop/StudentCourseRegistration/StudentDB";
        DataBaseModel DBM = new DataBaseModel(url);
        try {
            DBM.connect();
            DBM.CreateStatement();
            /*
            ArrayList<String> StudentNames = DBM.SQLQueryStudentNames();
            DBM.PrintFromDB(StudentNames);
            ArrayList<String> CourseList = DBM.SQLQueryCourses();
            DBM.PrintFromDB(CourseList);
             */
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                DBM.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        launch(args);
    }
}
