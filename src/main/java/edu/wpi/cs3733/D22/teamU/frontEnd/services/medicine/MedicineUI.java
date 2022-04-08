package edu.wpi.cs3733.D22.teamU.frontEnd.services.medicine;

public class MedicineUI {
    private String id;
    private String patientName;
    private String staffName;
    private String medType;
    private String medAmount;
    private String requestDate;
    private String requestTime;

    public MedicineUI(
            String id,
            String patientName,
            String staffName,
            String medType,
            String medAmount,
            String requestDate,
            String requestTime) {
        this.id = id;
        this.patientName = patientName;
        this.staffName = staffName;
        this.medType = medType;
        this.medAmount = medAmount;
        this.requestDate = requestDate;
        this.requestTime = requestTime;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getPatientName() {return patientName;}

    public void setPatientName(String patientName) {this.patientName = patientName;}

    public String getStaffName() {return staffName;}

    public void setStaffName(String staffName) {this.staffName = staffName;}

    public String getMedType() {return medType;}

    public void setMedType(String medType) {this.medType  = medType;}

    public String getMedAmount() {return medAmount;}

    public void setMedAmount(String medAmount) {this.medAmount  = medAmount;}

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {this.requestTime = requestTime;
    }
}


