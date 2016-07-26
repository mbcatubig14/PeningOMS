/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Muhammad
 */
public class Person {

    private Integer id;

    private String firstName;

    private String lastName;

    private String firstLineAddress;

    private String postcode;

    private String contactNumber;

    private String gender;

    public Person(Integer id, String firstName, String lastName, String firstLineAddress, String postcode, String contactNumber, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstLineAddress = firstLineAddress;
        this.postcode = postcode;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }

    public Person(String firstName, String lastName, String firstLineAddress, String postcode, String contactNumber, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.firstLineAddress = firstLineAddress;
        this.postcode = postcode;
        this.contactNumber = contactNumber;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstLineAddress() {
        return firstLineAddress;
    }

    public void setFirstLineAddress(String firstLineAddress) {
        this.firstLineAddress = firstLineAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", firstLineAddress=" + firstLineAddress + ", postcode=" + postcode + ", contactNumber=" + contactNumber + ", gender=" + gender + '}';
    }

}
