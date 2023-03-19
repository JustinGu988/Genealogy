package dataLayer;

import java.io.Serializable;

/***
 * @author Justin
 */

public class User implements Serializable {
    public static final long serialVersionUID = 42L;
    private String account;//acct name
    private String password;
    private Power power;//admin, common user
    public User(String account, String password, boolean editable) {
        this.account = account;
        this.password = password;
        if (editable)
            this.power = Power.admin;
        else
            this.power = Power.common;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
        this.power = Power.common;
    }

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            if (this.getAccount().equals(((User) obj).getAccount())
                    && this.getPassword().equals(((User) obj).getPassword())) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public static boolean ifInfoOK(String userName, String userPassword){
        User temp;
        for (int i = 0; i < Common.userList.size(); i++) {
            temp = Common.userList.get(i);
            if (temp.getAccount().equals(userName)
                    && temp.getPassword().equals(userPassword) ) {
                Common.currentUser = temp; //flag current user (for power check)
                return true;
            }
        }
        return false;
    }

    //check whether user exist
    //return true if exist
    public static boolean ifNameExist(String userName){
        User temp;
        for (int i = 0; i < Common.userList.size(); i++) {
            temp = Common.userList.get(i);
            if (temp.getAccount().equals(userName) ) {
                return true;
            }
        }
        return false;
    }
}


