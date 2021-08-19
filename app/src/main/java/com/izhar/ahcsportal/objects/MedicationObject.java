package com.izhar.ahcsportal.objects;

public class MedicationObject {
    String id, medication, dosage, description, start_date, end_date;

    public MedicationObject() {
    }

    public MedicationObject(String id, String medication, String dosage, String description, String start_date, String end_date) {
        this.id = id;
        this.medication = medication;
        this.dosage = dosage;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getMedication() {
        return medication;
    }

    public String getDosage() {
        return dosage;
    }

    public String getDescription() {
        return description;
    }
}
