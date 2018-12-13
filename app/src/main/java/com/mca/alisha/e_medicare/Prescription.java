package com.mca.alisha.e_medicare;

public class Prescription {

    String id;
    String medname;
    String pre_post;
    String bf;
    String lunch;
    String dinner;
    String medtype;
    String quantity;
    String docname;
    String patientname;
    String tea;

    public Prescription(){

    }

    public Prescription(String id, String medname, String pre_post, String bf, String lunch, String dinner, String medtype, String quantity, String docname,String patientname,String tea) {
        this.id = id;
        this.medname = medname;
        this.pre_post = pre_post;
        this.bf = bf;
        this.lunch = lunch;
        this.dinner = dinner;
        this.medtype = medtype;
        this.quantity = quantity;
        this.docname = docname;
        this.patientname = patientname;
        this.tea = tea;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedname() {
        return medname;
    }

    public void setMedname(String medname) {
        this.medname = medname;
    }

    public String getPre_post() {
        return pre_post;
    }

    public void setPre_post(String pre_post) {
        this.pre_post = pre_post;
    }

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getLunch() {
        return lunch;
    }

    public void setLunch(String lunch) {
        this.lunch = lunch;
    }

    public String getDinner() {
        return dinner;
    }

    public void setDinner(String dinner) {
        this.dinner = dinner;
    }

    public String getMedtype() {
        return medtype;
    }

    public void setMedtype(String medtype) {
        this.medtype = medtype;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDocname() {
        return docname;
    }

    public void setDocname(String docname) {
        this.docname = docname;
    }


    public String getPatientname() {
        return patientname;
    }

    public void setPatientname(String patientname) {
        this.patientname = patientname;
    }

    public String getTea() {
        return tea;
    }

    public void setTea(String tea) {
        this.tea = tea;
    }
}
