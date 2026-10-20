package com.deloitte.flight.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PassengerDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Passenger Name is required")
    private String passengerName;

    @NotNull(message = "Age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age must be valid")
    private Integer age;

    @NotBlank(message = "Gender is required")
    private String gender; // Male, Female, Other

    private String seatNo; // Selected from visual seat map (e.g. 1A, 2B)

    private String mealPreference = "Standard"; // Vegetarian, Non-Vegetarian, Vegan, Jain
    private String baggage = "15 kg";

    public PassengerDto() {
    }

    public PassengerDto(String passengerName, Integer age, String gender) {
        this.passengerName = passengerName;
        this.age = age;
        this.gender = gender;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName != null ? passengerName.trim() : null;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo != null ? seatNo.trim().toUpperCase() : null;
    }

    public String getMealPreference() {
        return mealPreference != null ? mealPreference : "Standard";
    }

    public void setMealPreference(String mealPreference) {
        this.mealPreference = mealPreference;
    }

    public String getBaggage() {
        return baggage != null ? baggage : "15 kg";
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }
}
