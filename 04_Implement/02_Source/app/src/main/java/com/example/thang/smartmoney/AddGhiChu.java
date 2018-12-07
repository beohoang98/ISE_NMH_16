package com.example.thang.smartmoney;

public class AddGhiChu {
    private int Id;
    private String NameGC;
    private String DateGC;

    public AddGhiChu(int id, String nameGC, String dateGC) {
        Id = id;
        NameGC = nameGC;
        DateGC = dateGC;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameGC() {
        return NameGC;
    }

    public void setNameGC(String nameGC) {
        NameGC = nameGC;
    }

    public String getDateGC() {
        return DateGC;
    }

    public void setDateGC(String dateGC) {
        DateGC = dateGC;
    }
}
