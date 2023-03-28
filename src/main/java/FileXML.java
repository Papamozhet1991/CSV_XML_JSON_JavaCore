import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileXML {

    private final String fileName = "data.xml";
    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;
    private Document document;

    public FileXML() {
        try {
            this.factory = DocumentBuilderFactory.newInstance();
            this.builder = factory.newDocumentBuilder();
            this.document = builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void newFileXML(String fileName) {
        Element staff = document.createElement("staff");
        document.appendChild(staff);
        Element employee = document.createElement("employee");
        staff.appendChild(employee);
        Element id = document.createElement("id");
        id.appendChild(document.createTextNode("1"));
        employee.appendChild(id);
        Element firstName = document.createElement("firstName");
        firstName.appendChild(document.createTextNode("Andrey"));
        employee.appendChild(firstName);
        Element lastName = document.createElement("lastName");
        lastName.appendChild(document.createTextNode("Andreev"));
        employee.appendChild(lastName);
        Element country = document.createElement("country");
        country.appendChild(document.createTextNode("RU"));
        employee.appendChild(country);
        Element age = document.createElement("age");
        age.appendChild(document.createTextNode("25"));
        employee.appendChild(age);

        Element employee2 = document.createElement("employee");
        staff.appendChild(employee2);
        Element id2 = document.createElement("id");
        id2.appendChild(document.createTextNode("2"));
        employee2.appendChild(id2);
        Element firstName2 = document.createElement("firstName");
        firstName2.appendChild(document.createTextNode("Inav"));
        employee2.appendChild(firstName2);
        Element lastName2 = document.createElement("lastName");
        lastName2.appendChild(document.createTextNode("Petrov"));
        employee2.appendChild(lastName2);
        Element country2 = document.createElement("country");
        country2.appendChild(document.createTextNode("RU"));
        employee2.appendChild(country2);
        Element age2 = document.createElement("age");
        age2.appendChild(document.createTextNode("23"));
        employee2.appendChild(age2);

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(fileName));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
            transformer.transform(domSource, streamResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> parseXML(String fileName) {
        List<Employee> employee = new ArrayList<>();
        try {
            document = builder.parse(new File(fileName));
            Node root = document.getDocumentElement();
            //System.out.println("Корневой элeмeнт: " + root.getNodeName() + " (" + root.getTextContent() + ")");
            List<String> list = new ArrayList<>();
            read(root, list);
            employee.add(new Employee(Long.parseLong(list.get(1)), list.get(2), list.get(3),
                    list.get(4), Integer.parseInt(list.get(5))));
            employee.add(new Employee(Long.parseLong(list.get(7)), list.get(8), list.get(9),
                    list.get(10), Integer.parseInt(list.get(11))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    private static void read(Node root, List<String> list) {
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                list.add(node.getTextContent());
                //System.out.println("Текущий узел: " + node.getNodeName() + " (" + node.getTextContent() + ")");
                read(node, list);
            }
        }
    }

}