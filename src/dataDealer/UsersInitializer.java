package dataDealer;

import dataDealer.IODealer;
import dataLayer.User;
import java.util.ArrayList;

/***
 * @author Justin
 */
public class UsersInitializer {

  public static void main(String[] args) {
    String userInfoPath = "src/data/user.data";
    ArrayList<User> d = new ArrayList<User>();
    d.add(new User("admin", "123456", true));
    IODealer.writeObjectToFile(d, userInfoPath);
  }
}
