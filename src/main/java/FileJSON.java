import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileJSON {

    public FileJSON() {}

    public String readString(String fileName) {
        String json = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            json = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public List<Employee> jsonToList(String json) {
        List<Employee> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            Object obj = parser.parse(json);
            JSONArray array = (JSONArray) obj;
            for (Object object : array) {
                list.add(gson.fromJson(object.toString(), Employee.class));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }
}