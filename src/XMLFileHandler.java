import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLFileHandler implements FileHandler {

    private static final String FILE_NAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/employee.xml";
    private static final String WRITE_FILENAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/newemployee.xml";
    Document doc;
    private static int n=0;
    private static XMLFileHandler fileReader = null;

    private XMLFileHandler() throws Exception {
        File fXmlFile = new File(FILE_NAME);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
    }

    public static XMLFileHandler getXMLFilehandler() throws Exception {

        if(fileReader == null){
            synchronized (CSVFileHandler.class){
                if(fileReader==null){
                    fileReader = new XMLFileHandler();
                }
            }
        }
        return fileReader;

    }

    @Override
    public Employee read() throws Exception {
        Employee employee = null;
        try {
            NodeList nList = doc.getElementsByTagName("employee");

            Node nNode = nList.item(n++);

            if(nNode != null){
                Element eElement = (Element) nNode;
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(eElement.getElementsByTagName("dateOfBirth").item(0).getTextContent());
                employee = new Employee(eElement.getElementsByTagName("firstName").item(0).getTextContent(),
                        eElement.getElementsByTagName("lastName").item(0).getTextContent(), date, Double.parseDouble(eElement.getElementsByTagName("experience").item(0).getTextContent()));
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void write(Employee employee) {

        try {

            FileWriter file = new FileWriter(WRITE_FILENAME,true);
            JAXBContext jaxbContext = JAXBContext.newInstance(Employee.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(employee, file);
            //jaxbMarshaller.marshal(employee, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
