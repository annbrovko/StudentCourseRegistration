package sample;

import sample.models.CboxResource;
import sample.models.CourseInfo;
import sample.models.StudentCourseInfo;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

public class DataBaseModel {

    Connection connection = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    DataBaseModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        connection = getConnection(url);
    }

    public void close() throws SQLException {
        if (connection != null)
            connection.close();
        try {
            String url = "jdbc:sqlite:/Users/annabrovko/Desktop/StudentCourseRegistration/StudentDB.db";
            connection = getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException d) {
                    d.printStackTrace();
                }
            }
        }
    }

    public void CreateStatement() throws SQLException {
        this.stmt = connection.createStatement();
    }

    // retrieve names of students from database
    public ArrayList<CboxResource> SQLQueryStudentNames() {
        ArrayList<CboxResource> StudentNames = new ArrayList<>();
        String sql = "Select\n" +
                "       id_user, " +
                "       case when last_name is not null\n" +
                "           then (first_name || ' ' || last_name)\n" +
                "           else first_name\n" +
                "           end as full_name\n" +
                "From\n" +
                "     user where id_usertype is not 2;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                String fullName = rs.getString(2);
                StudentNames.add(new CboxResource(id, fullName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return StudentNames;
    }

    // get all courses from database
    public ArrayList<CboxResource> SQLQueryCourses() {
        ArrayList<CboxResource> Courses = new ArrayList<>();
        String sql = "Select id_course, c.course_name\n" +
                "    From courses as c;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                int id = Integer.parseInt(rs.getString(1));
                String courseName = rs.getString(2);
                Courses.add(new CboxResource(id, courseName));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Courses;
    }


    public ArrayList<CourseInfo> SQLQueryCourse(int idCourse) {
        ArrayList<CourseInfo> courses = new ArrayList<>();
        String sql = "Select sc.id_course, c.course_name , CASE WHEN avg(sc.grade) IS NULL THEN 0 ELSE avg(sc.grade) END as average\n" +
                "    From courses as c, student_courses as sc\n" +
                "WHERE (sc.id_course) = " + idCourse + ";";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                int courseID = Integer.parseInt(rs.getString(1));
                System.out.println(courseID);
                String courseTitle = rs.getString(2);
                float courseAvg = Float.parseFloat(rs.getString(3));
                courses.add(new CourseInfo(courseID, courseTitle, courseAvg));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    // create method to retrieve data about the student from the database
    public ArrayList<StudentCourseInfo> SQLQueryStudentCourses(int idStudent){
        ArrayList<StudentCourseInfo> courses = new ArrayList<>();

        String sql = "SELECT\n" +
                "    sc.id_studentcourse,\n" +
                "    c.course_name,\n" +
                "    sc.grade\n" +
                "FROM\n" +
                "    user as u\n" +
                "INNER JOIN\n" +
                "    student_courses as sc on u.id_user = sc.id_user\n" +
                "INNER JOIN\n" +
                "    courses as c on sc.id_course = c.id_course\n" +
                "WHERE u.id_user = '"+ idStudent +"'";
        try{
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()){
                int studentCourseId = Integer.parseInt(rs.getString(1));
                String courseName = rs.getString(2);
                String courseGrade = rs.getString(3);
                System.out.println(studentCourseId);
                courses.add(new StudentCourseInfo(studentCourseId, courseName, courseGrade));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    // create method to retrieve data about the student from the database
    public ArrayList<String> SQLQueryStudentNameGrade(String student){
        ArrayList<String> studentNameGrade = new ArrayList<>();
        String sql = "SELECT\n" +
                "    case when u.last_name is not null\n" +
                "             then (u.first_name || ' ' || u.last_name)\n" +
                "         else u.first_name\n" +
                "            end as 'Student',\n" +
                "    avg(sc.grade) as 'Average'\n" +
                "FROM\n" +
                "    user as u\n" +
                "INNER JOIN\n" +
                "    student_courses as sc on u.id_user = sc.id_user\n" +
                "WHERE (u.first_name || ' ' || u.last_name) = '"+ student +"'\n" +
                "GROUP BY\n" +
                "    u.id_user;";
        try{
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()){
                String studentName = rs.getString(1);
                String studentAverage = rs.getString(2);
                studentNameGrade.add(0, studentName);
                studentNameGrade.add(1, studentAverage);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentNameGrade;
    }

    public float SQLQueryStudentCoursesAverage(int userId)
    {
        String sql = "SELECT\n" +
                "    CASE WHEN avg(sc.grade) IS NULL THEN 0 ELSE avg(sc.grade) END as 'Average'\n" +
                "FROM\n" +
                "    user as u\n" +
                "INNER JOIN\n" +
                "    student_courses as sc on u.id_user = sc.id_user\n" +
                "WHERE u.id_user = "+ userId +"\n";
        float average = 0;

        try{
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()){
                average = Float.parseFloat(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return average;
    }

    public void SQLQueryUpdateStudentCourseGrade(int idStudentCourse, float newGrade)
    {
        String sql = "UPDATE student_courses\n" +
                "    SET grade = " + newGrade + "\n" +
                "WHERE id_studentcourse = "+ idStudentCourse +"\n";

        try{
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    // print the requested data from the database in console
    public void PrintFromDB(ArrayList<String> n) {
        for (int i = 0; i < n.size(); i++) {
            System.out.println(n.get(i));
        }
    }
}
