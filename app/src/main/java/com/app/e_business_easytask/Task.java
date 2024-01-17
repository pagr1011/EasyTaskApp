package com.app.e_business_easytask;

import java.util.Objects;

public class Task {
    private long id;
    private String serviceType;
    private String jobDetails;
    private String formattedDate;
    private String formattedTime;
    private String street;
    private String houseNumber;
    private String zipCode;
    private int duration;
    private String durationUnit;
    private double budget;

    // Konstruktor
    public Task() {
        // Standardkonstruktor
    }

    // Getter und Setter-Methoden

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getJobDetails() {
        return jobDetails;
    }

    public void setJobDetails(String jobDetails) {
        this.jobDetails = jobDetails;
    }

    public String getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(String formattedDate) {
        this.formattedDate = formattedDate;
    }

    public String getFormattedTime() {
        return formattedTime;
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(String durationUnit) {
        this.durationUnit = durationUnit;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceType, jobDetails, formattedDate, formattedTime, street, houseNumber, zipCode, duration, durationUnit, budget);
    }

    // Equalsmethoden für die Verwendung in Collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                duration == task.duration &&
                Double.compare(task.budget, budget) == 0 &&
                Objects.equals(serviceType, task.serviceType) &&
                Objects.equals(jobDetails, task.jobDetails) &&
                Objects.equals(formattedDate, task.formattedDate) &&
                Objects.equals(formattedTime, task.formattedTime) &&
                Objects.equals(street, task.street) &&
                Objects.equals(houseNumber, task.houseNumber) &&
                Objects.equals(zipCode, task.zipCode) &&
                Objects.equals(durationUnit, task.durationUnit);
    }

    //to String Methode um die Task-Informationen anzuzeigen
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", serviceType='" + serviceType + '\'' +
                ", jobDetails='" + jobDetails + '\'' +
                ", formattedDate='" + formattedDate + '\'' +
                ", formattedTime='" + formattedTime + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", duration=" + duration +
                ", durationUnit='" + durationUnit + '\'' +
                ", budget=" + budget +
                '}';
    }
}
