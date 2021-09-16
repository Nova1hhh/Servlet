package ru.appline.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.servlet.logic.Model;
import ru.appline.servlet.logic.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@WebServlet(urlPatterns = "/put")
public class ServletPut extends HttpServlet {
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Model model = Model.getInstance();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        response.setContentType("application/json;charset=utf-8");
        request.setCharacterEncoding("utf-8");
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("error");
        }
        JsonObject jsonObject = gson.fromJson(String.valueOf(stringBuffer), JsonObject.class);

        int id = jsonObject.get("id").getAsInt();

        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user, id);

        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(model.getFromList()));
    }
}
