package sample.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class StudentCourseInfo {
    SimpleIntegerProperty idStudentCourse;
    SimpleStringProperty studentCourseName;
    String studentGrade;

    public StudentCourseInfo(int idStudentCourse, String studentCourseName, String studentGrade)
    {
        this.idStudentCourse = new SimpleIntegerProperty(idStudentCourse);
        this.studentCourseName = new SimpleStringProperty(studentCourseName);
        this.studentGrade = studentGrade;
    }

    public int getIdStudentCourse()
    {
        return this.idStudentCourse.get();
    }

    public void setIdStudentCourse(int idStudentCourse)
    {
        this.idStudentCourse.set(idStudentCourse);
    }

    public String getStudentCourseName()
    {
        return this.studentCourseName.get();
    }

    public void setStudentCourseName(String studentCourseName)
    {
        this.studentCourseName.set(studentCourseName);
    }

    public String getStudentGrade()
    {
        return this.studentGrade;
    }

    public void setStudentGrade(String studentGrade)
    {
        this.studentGrade = studentGrade;
    }
}
