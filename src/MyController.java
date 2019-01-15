import java.text.SimpleDateFormat;
import java.util.*;

public class MyController {

    private static MyCollection empStore = MyCollection.getInstance();

    public static void main(String[] args) throws InterruptedException {
        CSVThread csvThread =new CSVThread();
          XMLThread xmlThread=new XMLThread();
       JSONThread jsonThread=new JSONThread();



        Thread t1=new Thread(csvThread);
        Thread t2=new Thread(xmlThread);
       Thread t3=new Thread(jsonThread);



        t1.start();
       t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        MyCollection.getInstance().printCollection();


        try{
            XMLFileHandler xmlFileHandler = XMLFileHandler.getXMLFilehandler();
            xmlFileHandler.write(new Employee("Rishabh","Kochar",null,3));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        try{
            CSVFileHandler jsonFileHandler = CSVFileHandler.getCSVInstance();
            Date dt = new SimpleDateFormat("MM/dd/yyyy").parse("09/25/1996");
            jsonFileHandler.write(new Employee("Rishabh","Kochar",dt,3));
            jsonFileHandler.write(new Employee("Rishabh","Kochar",dt,3));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }



    }
    static class CSVThread implements Runnable{

        @Override
        public void run() {

            CSVFileHandler csvFileHandler = null;
            try {
                csvFileHandler = CSVFileHandler.getCSVInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            int i=0;
            while(i<99){
                //System.out.println(i + " ");
                empStore.add(csvFileHandler.read());
                i++;
            }


        }
    }

    static class XMLThread implements Runnable{

        @Override
        public void run() {

            try{
                XMLFileHandler xmlFileHandler = XMLFileHandler.getXMLFilehandler();
                int i=0;
                while(i<100){

                    empStore.add(xmlFileHandler.read());
                    i++;
                }

            }catch (Exception e){
                System.out.println(e.getMessage());
            }


        }
    }

    static class JSONThread implements Runnable{
        @Override
        public void run() {

            try{
                JSONFileHandler jsonFileHandler = JSONFileHandler.getJSONFilehandler();
                int i=0;
                while(i<99){
                    empStore.add(jsonFileHandler.read());
                    i++;
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
