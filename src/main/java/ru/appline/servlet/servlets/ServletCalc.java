package ru.appline.servlet.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/calc")
public class ServletCalc extends HttpServlet {
    double a;
    double b;
    String op;
    private double result;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public void setResult(double a, double b, String op){
        switch (op) {
            case "*" -> result = a * b;
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "/" -> result = a / b;
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String line;
        StringBuilder strBuild = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                strBuild.append(line);
            }
        } catch (IOException e){
            System.out.println("error");
        }

        JsonObject jsonObject = gson.fromJson(String.valueOf(strBuild), JsonObject.class);
        response.setContentType("application/json;charset=utf-8");

        a = jsonObject.get("a").getAsDouble();
        b = jsonObject.get("b").getAsDouble();
        op = jsonObject.get("math").getAsString();

        setResult(a, b, op);

        PrintWriter pw = response.getWriter();

        Map<String, Double> answer = new HashMap<>();
        answer.put("result", result);
        pw.println(gson.toJson(answer));
    }
}
