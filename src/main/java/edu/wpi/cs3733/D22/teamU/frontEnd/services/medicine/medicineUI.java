package edu.wpi.cs3733.D22.teamU.frontEnd.services.medicine;

public class medicineUI {
    private String id;
    private String patientName;
    private String staffName;
    private String medType;
    private int requestAmount;
    private String destination;
    private String date;
    private String time;
    private int priority;
    private int amountMedInUse;
    private int amountMedAvail;
    private int amountMedTotal;

    public medicineUI(String name, int medInUse, int medAvail, int medTotal) {
        this.medType = name;
        this.amountMedInUse = medInUse;
        this.amountMedAvail = medAvail;
        this.amountMedTotal = medTotal;
    }

    public medicineUI(String medID, String patient, String staff, String type,
                      int amount, String medDest, String medDate, String medTime){
        this.id = medID;
        this.patientName = patient;
        this.staffName = staff;
        this.medType = type;
        this.requestAmount = amount;
        this.destination = medDest;
        this.date = medDate;
        this.time = medTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(int requestAmount) {
        this.requestAmount = requestAmount;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getAmountMedInUse() {
        return amountMedInUse;
    }

    public void setAmountMedInUse(int amountMedInUse) {
        this.amountMedInUse = amountMedInUse;
    }

    public int getAmountMedAvail() {
        return amountMedAvail;
    }

    public void setAmountMedAvail(int amountMedAvail) {
        this.amountMedAvail = amountMedAvail;
    }

    public int getAmountMedTotal() {
        return amountMedTotal;
    }

    public void setAmountMedTotal(int amountMedTotal) {
        this.amountMedTotal = amountMedTotal;
    }
}


