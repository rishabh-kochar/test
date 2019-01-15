public class CSVTEST {

    public static void main(String[] args) {
        try{
            CSVFileHandler csvFileHandler = CSVFileHandler.getCSVInstance();
            Employee e =  csvFileHandler.read();
            csvFileHandler.read();

            csvFileHandler.write(e);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}
