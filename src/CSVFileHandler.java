

import java.io.*;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CSVFileHandler implements FileHandler {

    private static final String READ_FILENAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/employee.csv";
    private static final String WRITE_FILENAME = "/Users/rishabhkochar/IdeaProjects/CSV/src/newemployee.csv";
    private static FileReader instance = null;
    private static BufferedReader br = null;
    private static FileWriter writeinstance = null;
    private static BufferedWriter writebr = null;
    private static CSVFileHandler fileReader = null;

    private CSVFileHandler() throws FileNotFoundException, Exception {
        instance = new FileReader(READ_FILENAME);
        br = new BufferedReader(instance);
        writeinstance = new FileWriter(WRITE_FILENAME,true);
        writebr = new BufferedWriter(writeinstance);
    }

    public static CSVFileHandler getCSVInstance() throws Exception {

        if (fileReader == null) {
            synchronized (CSVFileHandler.class) {
                if (fileReader == null) {
                    fileReader = new CSVFileHandler();
                }
            }
        }
        return fileReader;

    }


    @Override
    public Employee read() {

        Employee empObj = null;
        try {

            String nextRecord = br.readLine();
//            String nextRecord = "";

            String[] empRecord = nextRecord.split(",");
            Date dateOfBirth;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            dateOfBirth = df.parse(empRecord[2]);
            empObj = new Employee(empRecord[0], empRecord[1], dateOfBirth, Double.parseDouble(empRecord[3]));
            //System.out.println(empObj);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return empObj;


    }

    @Override
    public void write(Employee ob) {



        try {

            String fname = ob.getFirstName();
            String lname = ob.getLastName();
            Date dob = ob.getDateOfBirth();
            double experience = ob.getExperience();

            writebr.write(fname);
            writebr.write(",");
            writebr.write(lname);
            writebr.write(",");
            writebr.write(dob.toString());
            writebr.write(",");
            writebr.write(Double.toString(experience));
            writebr.newLine();


        } catch (IOException e) {

            e.printStackTrace();

        }
    }


}
