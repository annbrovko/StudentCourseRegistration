package sample.models;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CourseInfo {
    SimpleIntegerProperty idCourse;
    SimpleStringProperty courseName;
    SimpleFloatProperty courseAvg;

    public CourseInfo(int idCourse, String courseName, float courseAvg)
    {
        this.idCourse = new SimpleIntegerProperty(idCourse);
        this.courseName = new SimpleStringProperty(courseName);
        this.courseAvg = new SimpleFloatProperty(courseAvg);
    }

    // setters and getters for the information about the course

    public int getIdCourse()
    {
        return this.idCourse.get();
    }

    public void setIdCourse(int idStudentCourse)
    {
        this.idCourse.set(idStudentCourse);
    }

    public String getCourseName()
    {
        return this.courseName.get();
    }

    public void setCourseName(String studentCourseName)
    {
        this.courseName.set(studentCourseName);
    }

    public float getCourseAvg()
    {
        return this.courseAvg.get();
    }

    public void setCourseAvg(float studentGrade)
    {
        this.courseAvg.set(studentGrade);
    }
}
