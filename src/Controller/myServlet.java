package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Data.*;
import Model.Test;


@WebServlet(name = "HelloServlet1", urlPatterns = {"/hello"})
public class myServlet extends HttpServlet {
    private iTest repo;

    public void init() throws ServletException {
        Connect db = new Connect();
        repo = new TestContext(db.getConn());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String name = req.getParameter("name");
        name = Utils.StringUtils.encodeHtml(name);

        String message;

        try {
            repo.addName(new Test(1, name));
            message = "Successfully added!";
        } catch (Exception e) {
            message = e.getMessage();
        }

        PrintWriter out = resp.getWriter();
        out.write("<center><h3>" + message + "</h3></center>");
        out.close();
    }
}