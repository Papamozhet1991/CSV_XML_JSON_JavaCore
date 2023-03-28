import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileCSV {

    private final List<String> list = new ArrayList<>();
    private final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
    private final String fileName = "data.csv";

    public FileCSV() {
        this.list.add("1, Andrey, Andreev, RU, 25");
        this.list.add("2, Inav, Petrov, RU, 23");
    }

    public List<String> getList() {
        return list;
    }

    public String[] getColumnMapping() {
        return columnMapping;
    }

    public String getFileName() {
        return fileName;
    }

    public void newFileCSV(List<String> list, String fileName) {
        for (int i = 0; i < list.size(); i++) {
            String[] employee = list.get(i).split(", ");
            try (CSVWriter writer = new CSVWriter(new FileWriter(fileName, true))) {
                writer.writeNext(employee);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> employee = null;
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();
            employee = csv.parse();
            //employee.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employee;
    }
}