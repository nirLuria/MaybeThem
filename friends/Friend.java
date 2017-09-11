package com.maybethem.maybethem.friends;

/**
 * Created by nirlu on 11/09/2017.
 */

public class Friend
{

    String firstNsame;
    String lastNsame;
    String phoneNumber;
    String gender;



    public Friend(String firstNsame, String lastNsame, String phoneNumber, String gender) {
        this.firstNsame = firstNsame;
        this.lastNsame = lastNsame;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }

    public String getFirstNsame() {
        return firstNsame;
    }

    public void setFirstNsame(String firstNsame) {
        this.firstNsame = firstNsame;
    }

    public String getLastNsame() {
        return lastNsame;
    }

    public void setLastNsame(String lastNsame) {
        this.lastNsame = lastNsame;
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

    public void printFriend()
    {
        System.out.println("My details are: "+firstNsame+", "+lastNsame);
    }




}
