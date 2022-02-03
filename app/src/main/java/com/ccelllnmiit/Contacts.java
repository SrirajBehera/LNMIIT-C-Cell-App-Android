package com.ccelllnmiit;

public class Contacts
{
    private String pos,name,no,email;

    public Contacts(String name, String no,String email) {
        this.name = name;
        this.no = no;
        this.email = email;
    }

    public Contacts(String pos,String name, String no,String email) {
        this.name = name;
        this.no = no;
        this.email = email;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public String getNo() {
        return no;
    }

    public String getEmail() {
        return email;
    }

    public String getPos() {
        return pos;
    }
}
