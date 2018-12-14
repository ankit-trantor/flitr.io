package com.lyriad.flitrio.Classes;

import java.time.LocalDate;

public class Person {

    private long id;
    private String givenName;
    private String middleName;
    private String lastName;
    private LocalDate birthDate;
    private String countryOfOrigin;
    private char gender;
    private byte[] personImage;

    public Person(String givenName, String middleName, String lastName, LocalDate birthDate,
                  String countryOfOrigin, char gender){
        super();
        this.givenName = givenName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.countryOfOrigin = countryOfOrigin;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public byte[] getPersonImage() {
        return personImage;
    }

    public void setPersonImage(byte[] personImage) {
        this.personImage = personImage;
    }

    /*
    * LocalDate dateTemp = dateChooser.getDate().toInstant().atZone(ZoneId.systemDefault()).
    * toLocalDate();
    *
    * String birthDate = String.format("%02d", dateTemp.getDayOfMonth()) + "/" +
    * String.format("%02d", dateTemp.getMonthValue()) + "/" + dateTemp.getYear();
    */
}