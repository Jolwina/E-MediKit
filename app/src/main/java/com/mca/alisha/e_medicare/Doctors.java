package com.mca.alisha.e_medicare;

public class Doctors {

    String id;
    String doc_name;
    String doc_email;
    String doc_phone;
    String doc_Type;
    String doc_clinic;

    public Doctors(){

    }

    public Doctors(String id, String doc_name, String doc_email, String doc_phone, String doc_Type, String doc_clinic) {
        this.id = id;
        this.doc_name = doc_name;
        this.doc_email = doc_email;
        this.doc_phone = doc_phone;
        this.doc_Type = doc_Type;
        this.doc_clinic = doc_clinic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getDoc_email() {
        return doc_email;
    }

    public void setDoc_email(String doc_email) {
        this.doc_email = doc_email;
    }

    public String getDoc_phone() {
        return doc_phone;
    }

    public void setDoc_phone(String doc_phone) {
        this.doc_phone = doc_phone;
    }

    public String getDoc_Type() {
        return doc_Type;
    }

    public void setDoc_Type(String doc_Type) {
        this.doc_Type = doc_Type;
    }

    public String getDoc_clinic() {
        return doc_clinic;
    }

    public void setDoc_clinic(String doc_clinic) {
        this.doc_clinic = doc_clinic;
    }
}
