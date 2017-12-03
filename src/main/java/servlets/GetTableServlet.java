package servlets;

import data.group.Group;
import data.group.SubGroup;
import resources.HibernateShell;
import resources.TableMaker.JsonMaker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetTableServlet")
public class GetTableServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupNumber = (String) req.getParameter("group");
        String subGroupNumber = (String) req.getParameter("subgroup");
        String tableType = (String) req.getParameter("type");

        try {
            Group group = HibernateShell.getGroupByGroupNumber(groupNumber);
            SubGroup subGroup = group.getSubGroup(subGroupNumber);
            switch (tableType.getBytes()[0]) {
                case 'm':
                    String str = JsonMaker.getJsonSubGroupMarks(subGroup);
                    resp.getWriter().append(str);
                    break;
                case 'p':
                    break;
                case 'e':
                    break;
                default:
                    req
                            .getRequestDispatcher("WEB-INF/pages/NotFound.jsp")
                            .forward(req, resp);
                    break;
            }
        } catch (NullPointerException e) {
            req
                    .getRequestDispatcher("WEB-INF/pages/NotFound.jsp")
                    .forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}