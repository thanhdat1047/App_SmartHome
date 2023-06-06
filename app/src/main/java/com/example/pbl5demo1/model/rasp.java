package com.example.pbl5demo1.model;

public class rasp {
    private String email;
    private String trangthai1;
    private String trangthai2;
    private String trangthai3;
    private String trangthai4;
    private String nameTB1;
    private String nameTB2;
    private String nameTB3;
    private String nameTB4;
    private String alertmode;

    public  rasp(){}

    public rasp(String email, String trangthai1, String trangthai2, String trangthai3, String trangthai4, String nameTB1, String nameTB2, String nameTB3, String nameTB4,String alertmode) {
        this.email = email;
        this.trangthai1 = trangthai1;
        this.trangthai2 = trangthai2;
        this.trangthai3 = trangthai3;
        this.trangthai4 = trangthai4;
        this.nameTB1 = nameTB1;
        this.nameTB2 = nameTB2;
        this.nameTB3 = nameTB3;
        this.nameTB4 = nameTB4;
        this.alertmode = alertmode;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTrangthai1() {
        return trangthai1;
    }

    public void setTrangthai1(String trangthai1) {
        this.trangthai1 = trangthai1;
    }

    public String getTrangthai2() {
        return trangthai2;
    }

    public void setTrangthai2(String trangthai2) {
        this.trangthai2 = trangthai2;
    }

    public String getTrangthai3() {
        return trangthai3;
    }

    public void setTrangthai3(String trangthai3) {
        this.trangthai3 = trangthai3;
    }

    public String getTrangthai4() {
        return trangthai4;
    }

    public void setTrangthai4(String trangthai4) {
        this.trangthai4 = trangthai4;
    }

    public String getNameTB1() {
        return nameTB1;
    }

    public void setNameTB1(String nameTB1) {
        this.nameTB1 = nameTB1;
    }

    public String getNameTB2() {
        return nameTB2;
    }

    public void setNameTB2(String nameTB2) {
        this.nameTB2 = nameTB2;
    }

    public String getNameTB3() {
        return nameTB3;
    }

    public void setNameTB3(String nameTB3) {
        this.nameTB3 = nameTB3;
    }

    public String getNameTB4() {
        return nameTB4;
    }

    public void setNameTB4(String nameTB4) {
        this.nameTB4 = nameTB4;
    }
    public String getAlertmode() {
        return alertmode;
    }

    public void setAlertmode(String alertmode) {
        this.alertmode = alertmode;
    }

}
