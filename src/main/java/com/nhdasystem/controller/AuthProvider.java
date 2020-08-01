package com.nhdasystem.controller;

import com.nhdasystem.util.ModuleList;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.HashMap;

public class AuthProvider {

    final static int SELECT =1;
    final static int INSERT =2;
    final static int UPDATE =3;
    final static int DELETE =4;

    static Connection connection;

    public static boolean isAuthorized(String username, String password, ModuleList module, int operation){
       try {

           if(username.equals("admin"))
              return isUser("admin",password);

          HashMap<String,Boolean> privilages = getPrivilages(username,password,module.toString());

          if( privilages != null) {

              switch (operation) {
                  case 1: return privilages.get("select");
                  case 2: return privilages.get("add");
                  case 3: return privilages.get("update");
                  case 4: return privilages.get("delete");
                  default: return false;
              }
          }

            else return false;

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }


    public static boolean isUser(String username, String password){
        try {
           setConnection();
            Statement st = connection.createStatement();
            String query = "SELECT * FROM user WHERE username='"+username+"'";
            ResultSet rs = st.executeQuery(query);

            if(rs.next()){
               String salt= rs.getString("salt");
                String pword= rs.getString("password");
               return pword.equals(getHash(password+salt));

            }
                else return false;

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }


    public static boolean isNotUser(String username, String password){
        try {
            setConnection();
            Statement st = connection.createStatement();
            String query = "SELECT * FROM user WHERE NOT username='"+username+"'";
            ResultSet rs = st.executeQuery(query);

            if(rs.next()){
                String salt= rs.getString("salt");
                String pword= rs.getString("password");
                return pword.equals(getHash(password+salt));

            }
            else return false;

        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }


    public static void setConnection() throws Exception{
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nhda", "root", "1234");
        }

}


    public static HashMap<String,Boolean> getPrivilages(String username, String password, String module) {


        if (isUser(username, password)) {
            if (username.equals("admin")) {
                HashMap<String, Boolean> adminprivilages = new HashMap();
                adminprivilages.put("add", true);
                adminprivilages.put("update", true);
                adminprivilages.put("delete", true);
                adminprivilages.put("select", true);
                return adminprivilages;
            } else {

                try {
                    setConnection();
                    Statement st = connection.createStatement();
                    String query2 = "select  bit_or(sel) sel, bit_or(ins) ins, bit_or(upd) upd, bit_or(del) del from privilage where role_id in (select role_id from userrole where user_id=(select id from user where username='" + username + "')) and module_id=(select id from module where name='" + module + "')";
                    ResultSet rs2 = st.executeQuery(query2);
                    rs2.next();
                        HashMap<String, Boolean> privilages = new HashMap();
                        System.out.println(rs2.getBoolean("ins"));
                        privilages.put("add", rs2.getBoolean("ins"));
                        privilages.put("update", rs2.getBoolean("upd"));
                        privilages.put("delete", rs2.getBoolean("del"));
                        privilages.put("select", rs2.getBoolean("sel"));
                        return privilages;
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return null;
                }
            }



        }

        return null;
    }



    public static String getHash(String password) {
        StringBuffer sb = new StringBuffer();
        byte[] hashedPasseword = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes("UTF-8"));
            hashedPasseword = digest.digest();

            for (int i = 0; i < hashedPasseword.length; i++) {
                sb.append(Integer.toString((hashedPasseword[i] & 0xff) + 0x100, 16).substring(1));
            }

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
        return sb.toString();
    }

    /**
     *
     * @return
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);

        String s =  new BigInteger(1, bytes).toString(16);
        return s;
    }




}
