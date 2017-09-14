package com.maybethem.maybethem.friends;

/**
 * Created by nirlu on 11/09/2017.
 */

public class Friend
{

    String firstNsame;
    String lastName;
    String phoneNumber;
    String gender;
    String hobbies;
    String redLine;


    public Friend(String firstNsame, String lastName, String phoneNumber, String gender, String hobbies, String redLine)
    {
        this.firstNsame = firstNsame;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.hobbies = hobbies;
        this.redLine = redLine;
    }

    public String getFirstNsame() {
        return firstNsame;
    }

    public void setFirstNsame(String firstNsame) {
        this.firstNsame = firstNsame;
    }

    public String getLastNsame() {
        return lastName;
    }

    public void setLastNsame(String lastNsame) {
        this.lastName = lastNsame;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbies() {return hobbies;}

    public void setHobbies(String hobbies) {this.hobbies = hobbies;}

    public String getRedLine() {return redLine;}

    public void setRedLine(String redLine) {this.redLine = redLine;}


    public void printFriend()
    {
        System.out.println("--------------------------------------My details are: "+firstNsame+", "+lastName+", "+phoneNumber+", "+gender+", "+hobbies+", "+redLine);
    }




}
