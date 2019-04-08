package controller;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try{
            JSONObject json = new JSONObject();
            json.put("ResponseCode", response.getStatus());

            try {
                JSONObject innerJson = new JSONObject();
                innerJson.put("QueryParam", Integer.valueOf(request.getParameter("queryParam")));
                json.put("InnerJson", innerJson);
            } catch (Exception e){
                json.put("ErrorDetail",e.getMessage());
            }
            String output = json.toString();

            PrintWriter writer = response.getWriter();
            writer.write(output);
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
