package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // init database model
        String url = "jdbc:sqlite:/Users/annabrovko/Desktop/StudentCourseRegistration/StudentDB";
        DataBaseModel DBM = new DataBaseModel(url);
        // give a title to the program
        primaryStage.setTitle("Course finder");
        try {
            // connect the database with the fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
            Parent root = loader.load();
            Controller control = loader.getController();
            control.setDbConnection(DBM);
            View view = new View(control);
            // set a scene for the project and assign the root and render everything from fxml file in this scene and show it
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
