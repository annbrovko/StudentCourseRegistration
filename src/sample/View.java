package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import sample.models.CboxResource;
import sample.models.CourseInfo;
import sample.models.StudentCourseInfo;

public class View {

    Controller control;
    private GridPane StartView;
    private TableView table = new TableView();

    public View(Controller control) {
        // receive the controller
        this.control = control;
        // execute the functions
        CreateAndConfigure();
    }

    // fills up the scene with student and courses information and set buttons on action
    public void CreateAndConfigure() {
        this.fillStudentsCbox();
        this.fillCoursesCbox();
        this.initStudentCoursesTableColumns();
        this.setFindStudentButtonAction();
        this.initCoursesTableColumns();
        this.setFindCourseButtonAction();
    }

    // method to fill out the comboboxes with data on students retrieved from the database
    public void fillStudentsCbox()
    {
        ObservableList<CboxResource> StudentNames = this.control.getStudents();
        this.control.studentSearch.setItems(StudentNames);
        this.control.studentSearch.getSelectionModel().selectFirst();
        this.control.studentSearch.setStyle("-fx-font: 12px \"Verdana\";");
        this.control.formatComboInfo(this.control.studentSearch);
        System.out.println(this.control.studentSearch.getValue().getID());
    }

    // method to fill out the comboboxes with data on courses retrieved from the database
    public void fillCoursesCbox()
    {
        ObservableList<CboxResource> CourseTitle = this.control.getCourses();
        this.control.courseSearch.setItems(CourseTitle);
        this.control.courseSearch.getSelectionModel().selectFirst();
        this.control.courseSearch.setStyle("-fx-font: 12px \"Verdana\";");
        this.control.formatComboInfo(this.control.courseSearch);
        System.out.println(this.control.courseSearch.getValue().getID());
    }

    // setting button for finding a student on action
    public void setFindStudentButtonAction()
    {
        this.control.showStd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<StudentCourseInfo> data = control.getStudentInfo(control.studentSearch.getValue().getID());
                control.fullName.setText(control.studentSearch.getValue().getName());
                control.avgGrade.setText(String.valueOf(control.getCourseAverage(control.studentSearch.getValue().getID())));
                control.tblStudentCourses.setItems(data);
            }
        });
    }

    // setting button for finding a course on action
    public void setFindCourseButtonAction()
    {
        this.control.showCourse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<CourseInfo> data = control.getCourseInfo(control.courseSearch.getValue().getID());
                control.tblCourses.setItems(data);
                System.out.println(control.courseSearch.getValue().getID());
            }
        });
    }

    // initialize the columns inside og the table for the student courses
    public void initStudentCoursesTableColumns()
    {
        this.control.idStudentCourse.setCellValueFactory(new PropertyValueFactory<>("idStudentCourse"));
        this.control.studentCourseName.setCellValueFactory(new PropertyValueFactory<>("studentCourseName"));
        this.control.studentGrade.setCellValueFactory(new PropertyValueFactory<>("studentGrade"));
        this.control.studentGrade.setCellFactory(TextFieldTableCell.<StudentCourseInfo>forTableColumn());
        this.control.tblStudentCourses.setEditable(true);
        this.control.studentGrade.setEditable(true);
    }

    // initialize the columns inside og the table for the courses
    public void initCoursesTableColumns()
    {
        this.control.idCourse.setCellValueFactory(new PropertyValueFactory<>("idCourse"));
        this.control.courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        this.control.courseAvg.setCellValueFactory(new PropertyValueFactory<>("courseAvg"));
    }
}
