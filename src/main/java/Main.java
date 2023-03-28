import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        FileCSV fileCSV = new FileCSV();
        FileXML fileXML = new FileXML();
        FileJSON fileJSON = new FileJSON();

        fileCSV.newFileCSV(fileCSV.getList(), fileCSV.getFileName());
        List<Employee> listEmployeeCSV = fileCSV.parseCSV(fileCSV.getColumnMapping(), fileCSV.getFileName());
        listToJson("data.json", listEmployeeCSV);

        fileXML.newFileXML(fileXML.getFileName());
        List<Employee> listEmployeeXML = fileXML.parseXML(fileXML.getFileName());
        listToJson("data2.json", listEmployeeXML);

        String json = fileJSON.readString("data.json");
        List<Employee> listEmployeeJSON = fileJSON.jsonToList(json);
        System.out.println(listEmployeeJSON.get(0) + "\n" + listEmployeeJSON.get(1));
    }

    public static void listToJson(String fileNameJSON, List<Employee> listEmployee) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        System.out.println(gson.toJson(listEmployee, listType));
        try (FileWriter file = new FileWriter(fileNameJSON)) {
            file.write(gson.toJson(listEmployee, listType));
            file.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}