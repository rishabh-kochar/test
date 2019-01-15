import com.oracle.javafx.jmx.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.*;

public class JSONFileHandler implements FileHandler{
    private static int n=0;

    private static final String READ_FILENAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/employee.json";
    private static final String WRITE_FILENAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/Newemployee.json";
    private static JSONFileHandler fileReader = null;
    private FileReader reader = null;
    private JSONArray employeeList = null;

    private JSONFileHandler() throws FileNotFoundException,Exception {
        reader =  new FileReader(READ_FILENAME);
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(reader);
        employeeList = (JSONArray)obj;

    }

    public static JSONFileHandler getJSONFilehandler() throws Exception {

        if(fileReader == null){
            synchronized (CSVFileHandler.class){
                if(fileReader==null){
                    fileReader = new JSONFileHandler();
                }
            }
        }
        return fileReader;

    }


    @Override
    public Employee read() throws Exception
    {

    Employee empRecord = null;
        try
        {
            //Read JSON file


            JSONObject employeeData=(JSONObject)employeeList.get(n);
            String str=employeeData.toString();
            //System.out.println(str);
            String firstname=employeeData.get("firstName").toString();
            String lastname=employeeData.get("lastName").toString();
            String dateofbirth=employeeData.get("dateOfBirth").toString();
            String experience=employeeData.get("experience").toString();

            Date dateOfBirth;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            dateOfBirth = df.parse(dateofbirth);
            empRecord = new Employee(firstname, lastname, dateOfBirth, Double.parseDouble(experience));

            n++;
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return empRecord;
    }

    @Override
    public void write(Employee emp) throws Exception{

            JSONObject employeeDetails = new JSONObject();
            employeeDetails.put("First Name",emp.firstName);
            employeeDetails.put("Last Name",emp.lastName);
            employeeDetails.put("Date of Birth",emp.dateOfBirth);
            employeeDetails.put("Experience",emp.experience);

            try (FileWriter file = new FileWriter(WRITE_FILENAME)) {
                file.write(employeeDetails.toJSONString());
            }


    }



}
