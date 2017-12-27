package servlets.test;

import resources.Hibernate.HibernateCore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/TableTestServlet")
public class TableTestServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HibernateCore hibernateCore = HibernateCore.getInstance();

        try {
            request.setAttribute("groups", hibernateCore.getGroupKeeper().getGroupList());
            request
                    .getRequestDispatcher("WEB-INF/pages/test/table_test.jsp")
                    .forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
