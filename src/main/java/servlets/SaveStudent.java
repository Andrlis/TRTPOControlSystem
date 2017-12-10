package servlets;

import data.Student;
import resources.Hibernate.StudentHibernateShell;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/SaveStudent")
public class SaveStudent extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupNumber = (String) req.getParameter("group");
        String subGroupNumber = (String) req.getParameter("subgroup");
        String studentName = (String) req.getParameter("name");
        studentName += " " + (String) req.getParameter("surname");
        String studentId = (String) req.getParameter("studentId");
        String gitRepo = (String) req.getParameter("git");
        String eMail = (String) req.getParameter("email");

        if (studentId.equals("")) {
            StudentHibernateShell.insertStudent(groupNumber, subGroupNumber, studentName, eMail, gitRepo);
        } else {
            StudentHibernateShell.updateStudent(studentId, studentName, eMail, gitRepo);
        }
    }
}