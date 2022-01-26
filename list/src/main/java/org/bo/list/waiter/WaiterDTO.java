package org.bo.list.waiter;

public class WaiterDTO {

    private int ci;
    private String name;
    private String birthDate;
    private int phone;
    private String initDate;
    private  String path;

    public WaiterDTO(int ci, String name, String birthDate, int phone, String initDate, String path) {
        this.ci = ci;
        this.name = name;
        this.birthDate = birthDate;
        this.phone = phone;
        this.initDate = initDate;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getInitDate() {
        return initDate;
    }

    public void setInitDate(String initDate) {
        this.initDate = initDate;
    }
}
