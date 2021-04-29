package sample;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.StringConverter;
import sample.models.CboxResource;
import sample.models.CourseInfo;
import sample.models.StudentCourseInfo;

import java.sql.SQLException;
import java.util.ArrayList;

public class Controller {
    DataBaseModel model;
//    View view;
    @FXML
    protected Label fullNameStd;
    @FXML
    protected ComboBox<CboxResource> studentSearch;
    @FXML
    protected ComboBox<CboxResource> courseSearch;
    @FXML
    protected Button showCourse;
    @FXML
    protected TableColumn<CourseInfo, SimpleIntegerProperty> idCourse;
    @FXML
    protected Label avgGradeStd;
    @FXML
    protected TextField fullName;
    @FXML
    protected TableColumn<CourseInfo, SimpleFloatProperty> courseAvg;
    @FXML
    protected Pane userView;
    @FXML
    protected Label findStd;
    @FXML
    protected TableView<CourseInfo> tblCourses;
    @FXML
    protected TableView<StudentCourseInfo> tblStudentCourses;
    @FXML
    protected TableColumn<StudentCourseInfo, SimpleIntegerProperty> idStudentCourse;
    @FXML
    protected TableColumn<StudentCourseInfo, String> studentGrade;
    @FXML
    protected TableColumn<CourseInfo, SimpleStringProperty> courseName;
    @FXML
    protected TextField avgGrade;
    @FXML
    protected Label findCourse;
    @FXML
    protected TableColumn<StudentCourseInfo, SimpleStringProperty> studentCourseName;
    @FXML
    protected Button showStd;

    public Controller() {

    }

    // connect the controller to the database
    public void setDbConnection(DataBaseModel model)
    {
        this.model = model;
        try {
            model.connect();
            model.CreateStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    // get the student names from the SQL query
    public ObservableList<CboxResource> getStudents() {
        ArrayList<CboxResource> fullNames = model.SQLQueryStudentNames();
        ObservableList<CboxResource> StudentNames = FXCollections.observableList(fullNames);
        return StudentNames;
    }

    public ObservableList<CboxResource> getCourses() {
        ArrayList<CboxResource> courses = model.SQLQueryCourses();
        ObservableList<CboxResource> CourseList = FXCollections.observableList(courses);
        return CourseList;
    }

    public ObservableList<StudentCourseInfo> getStudentInfo(int idStudent) {
        ArrayList<StudentCourseInfo> studentCourses = model.SQLQueryStudentCourses(idStudent);
        ObservableList<StudentCourseInfo> courseList = FXCollections.observableList(studentCourses);
        return courseList;
    }

    public ObservableList<CourseInfo> getCourseInfo(int idCourse) {
        ArrayList<CourseInfo> courseInfo = model.SQLQueryCourse(idCourse);
        ObservableList<CourseInfo> courseList = FXCollections.observableList(courseInfo);
        return courseList;
    }

    public void formatComboInfo(ComboBox<CboxResource> comboBox)
    {
        comboBox.setConverter(new StringConverter<CboxResource>() {

            @Override
            public String toString(CboxResource object) {
                return object.getName();
            }

            @Override
            public CboxResource fromString(String string) {
                return comboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
    }

    public float getCourseAverage(int userId)
    {
       return model.SQLQueryStudentCoursesAverage(userId);
    }

    public void updateStudentCourseGrade(TableColumn.CellEditEvent<StudentCourseInfo, String> studentCourseInfoStringCellEditEvent) {
        int idStudentCourse = studentCourseInfoStringCellEditEvent.getRowValue().getIdStudentCourse();
        model.SQLQueryUpdateStudentCourseGrade(idStudentCourse, Float.parseFloat(studentCourseInfoStringCellEditEvent.getNewValue()));
        this.showStd.fire();
        this.showCourse.fire();
    }
}
