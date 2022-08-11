package Controller;

import Data.Connect;
import Data.TestDao;
import Data.dao.iTestDao;
import Model.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


// @WebServlet(name = "names", urlPatterns = {"/names"})
public class myServlet extends HttpServlet {
    private iTestDao repo;

    public void init() {
        repo = new TestDao(new Connect().getConn());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Test> names = new ArrayList<>();

        // Check if URL contains id
        if (req.getParameterMap().containsKey("id")) {
            int id = Integer.parseInt(req.getParameter("id"));
            names.add(repo.find(id));
        } else if (req.getParameterMap().containsKey("name")) {
            String name = req.getParameter("name");
            names.add(repo.find(name));
        } else {
            names.addAll(repo.findAll());
        }

        // Pass data to jsp page
        req.setAttribute("data", names);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/View/names.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String msg;

        try {
            repo.save(new Test(name));
            msg = "Successfully added!";
        } catch (Exception e) {
            msg = e.getMessage();
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.write("<center><h3>" + msg + "</h3></center>");
        out.close();
    }
}