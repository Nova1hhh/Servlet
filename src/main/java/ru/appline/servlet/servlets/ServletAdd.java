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
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class ServletAdd extends HttpServlet {

    private AtomicInteger count = new AtomicInteger(4);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//        request.setCharacterEncoding("utf-8");
//        PrintWriter pw = response.getWriter();
//
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        User user = new User(name, surname, salary);
//        model.add(user, count.getAndIncrement());
//
//        pw.println("<html>" +
//                "<h1>Пользовалель" + name + " " + surname + " с зп = " + salary + " успешно создан<br>" +
//                "<a href =\"addUser.html\">Добавить еще одного пользователя</a><br/>" +
//                "<a href =\"index.jsp\">Домой</a>" +
//                "</html>");
//    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        StringBuilder strBuff = new StringBuilder();
        String line;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                strBuff.append(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("error");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(strBuff), JsonObject.class);

        request.setCharacterEncoding("utf-8");

        String name = jsonObject.get("name").getAsString();
        String surname = jsonObject.get("surname").getAsString();
        double salary = jsonObject.get("salary").getAsDouble();

        User user = new User(name, surname, salary);

        model.add(user, count.getAndIncrement());

        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(gson.toJson(model.getFromList()));
    }
}
