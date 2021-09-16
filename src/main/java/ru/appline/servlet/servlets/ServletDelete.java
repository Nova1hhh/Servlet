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
import java.io.UnsupportedEncodingException;

@WebServlet(urlPatterns = "/del")
public class ServletDelete extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        int id = jsonObject.get("id").getAsInt();
        if (id == 0) {
            model.delete();
            pw.println(gson.toJson("Список работников удален"));
        } else if (id > 0) {
            if (model.getFromList().containsKey(id)){
                model.delete(id);
                pw.println(gson.toJson(model.getFromList()));
            } else {
                pw.println(gson.toJson("Сотрудника с таким ID нет"));
            }
        } else {
            pw.println(gson.toJson("ID не может быть меньше нуля"));
        }
    }

}
