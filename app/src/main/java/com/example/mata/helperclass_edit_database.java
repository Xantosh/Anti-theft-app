package com.example.mata;

public class helperclass_edit_database {

    String Full_name,Phone_number,password,secret_code,emergency1,emergency2;

    public helperclass_edit_database() {
    }

    public helperclass_edit_database(String full_name, String phone_number, String password, String secret_code, String emergency1, String emergency2) {
        Full_name = full_name;
        Phone_number = phone_number;
        this.password = password;
        this.secret_code = secret_code;
        this.emergency1 = emergency1;
        this.emergency2 = emergency2;
    }

    public String getFull_name() {
        return Full_name;
    }

    public void setFull_name(String full_name) {
        Full_name = full_name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public void setPhone_number(String phone_number) {
        Phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecret_code() {
        return secret_code;
    }

    public void setSecret_code(String secret_code) {
        this.secret_code = secret_code;
    }

    public String getEmergency1() {
        return emergency1;
    }

    public void setEmergency1(String emergency1) {
        this.emergency1 = emergency1;
    }

    public String getEmergency2() {
        return emergency2;
    }

    public void setEmergency2(String emergency2) {
        this.emergency2 = emergency2;
    }
}
