import java.util.ArrayList;

public class MyCollection {

    private static MyCollection collectionInstance = null;
    private ArrayList<Employee> collectionList=new ArrayList<>();
    private int readCounter,writeCounter;

    private MyCollection(){
        readCounter = 0;
        writeCounter = 0;
    }

    synchronized public void add(Employee e){
        collectionList.add(e);
        writeCounter++;
        //System.out.println(writeCounter);
    }

    public void printCollection(){
        for(int i=0;i<collectionList.size();i++){
            System.out.println(collectionList.get(i));
        }
    }

    public static MyCollection getInstance(){
        if(null == collectionInstance){
            synchronized (MyCollection.class) {
                if (null == collectionInstance) {
                    collectionInstance = new MyCollection();
                }
            }
        }
        return collectionInstance;
    }

}
