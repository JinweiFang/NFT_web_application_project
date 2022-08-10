package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.*;
import Data.Repo.*;
import Model.Test;


// @WebServlet(name = "names", urlPatterns = {"/names"})
public class myServlet extends HttpServlet {
    private iTestRepo repo;

    public void init() throws ServletException {
        repo = new TestRepo(new Connect("postgres").getConn());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Test names[] = null;

        // Check if URL contains id
        if (req.getParameterMap().containsKey("id")) {
            int ID = Integer.parseInt(req.getParameter("id"));
            names = new Test[]{repo.find(ID)};
        } else if (req.getParameterMap().containsKey("name")) {
            String name = req.getParameter("name");
            names = new Test[]{repo.find(name)};
        } else {
            names = repo.findAll();
        }

        // Pass data to jsp page
        req.setAttribute("data", names);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/names.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String msg;

        try {
            repo.add(new Test(name));
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