package dataDealer;

import java.io.*;

/***
 * @author Justin
 */
public class IODealer {

    public static void writeObjectToFile(Object obj,String path) {
        File file = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(obj);//write
            objOut.flush();
            objOut.close();
            out.close();
            System.out.println("write object success!");
        } catch (
                IOException e) {
            System.out.println("write object failed");
            e.printStackTrace();
        }
    }

    public static Object readObjectFromFile(String path)
    {
        Object temp = null;
        File file =new File(path);
        try {
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();//read
            objIn.close();
            System.out.println("read object success!");
        } catch (IOException e) {
            System.out.println("read object failed");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return temp;
    }
}
