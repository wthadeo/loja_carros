package server.classes;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    public String username;
    public String pass;
    public String function;

    public User(){}

    public User(String username, String pass, String function){

        this.username = username;
        this.pass = pass;
        this.function = function;
    }


}

