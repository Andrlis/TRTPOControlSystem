package Data;

import Resources.MD5Hash;

public class User {

    private Long id;
    private String username;
    private String password;

    public User(){
        this.username = null;
        this.password = null;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return this.password;
    }

    public void setMD5Password(String md5Password){
        this.password = MD5Hash.getHash(md5Password);
    }
}