package ru.appline.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.appline.servlet.logic.Model;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/get")
public class ServletList extends HttpServlet {

    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter pw = response.getWriter();
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if (id == 0) {
//            pw.println("<html>" +
//                    "Доступные пользователи: <h3><br>" +
//                    "ID пользователя: " +
//                    "<ul>");
//            for (Map.Entry<Integer, User> entry : model.getFromList().entrySet()) {
//                pw.println("<li>" + entry.getKey() + "</li>"+
//                        "<ul><li>Имя: " + entry.getValue().getName() + "</li>" +
//                        "<li>Фамилия: " + entry.getValue().getSurname() + "</li>" +
//                        "<li>Зарплата: " + entry.getValue().getSalary() + "</li></ul>");
//            }
//            pw.println("</ul><a href =\"index.jsp\">Домой</a></html>");
//        } else if (id > 0) {
//            if (id > model.getFromList().size()){
//                pw.println("<html><h1>Такого пользователя нет =(</h1>");
//                pw.println("</ul><a href =\"index.jsp\">Домой</a></html>");
//            } else {
//                pw.println("<html><h3>запрошенный пользователь: </h3><br>" +
//                        "Имя: " + model.getFromList().get(id).getName() + "<br>" +
//                        "Фамилия: " + model.getFromList().get(id).getSurname() + "<br>" +
//                        "Зарплата: " + model.getFromList().get(id).getSalary() + "<br></h3>" +
//                        "<a href =\"index.jsp\">Домой</a></html>");
//            }
//        } else {
//            pw.println("<html><h1>ID пользователя должен быть больше нуля</h1></html>");
//        }
//    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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


        PrintWriter pw = response.getWriter();
       // int id = jsonObject.get("id").getAsInt();
        int id = Integer.parseInt(request.getParameter("id"));
        if (id == 0){
            pw.print(gson.toJson(model.getFromList()));
        } else if (id > 0) {
            if (id > model.getFromList().size()){
                pw.print(gson.toJson("Нет такого пользователя"));
            } else {
                pw.print(gson.toJson(model.getFromList().get(id)));
            }
        } else {
            pw.print(gson.toJson("Не может быть пользователя с отрицательным ID"));
        }
    }
}
