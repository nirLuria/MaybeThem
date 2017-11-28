package com.maybethem.maybethem.friends;

/**
 * Created by nirlu on 11/09/2017.
 */

public class Friend
{

    String firstNsame;
  //  String lastName;
    String phoneNumber;
    int age;
    String gender;
    String hobbies;
    String redLine;
    byte[] image;
    String hobbiesItems;

    public Friend(String firstNsame, int age, String phoneNumber, String gender, String hobbies, String redLine, byte[] image, String hobbiesItems)
    {
        this.firstNsame = firstNsame;
   //     this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.hobbies = hobbies;
        this.redLine = redLine;
        this.image = image;
        this.hobbiesItems = hobbiesItems;

    }

    public String getFirstName() {
        return firstNsame;
    }

    public void setFirstName(String firstNsame) {
        this.firstNsame = firstNsame;
    }

  /*  public String getLastName() {
        return lastName;
    }

    public void setLastNsame(String lastNsame) {
        this.lastName = lastNsame;
    }
*/
    public int getAge() {return age;}

    public void setAge(int age) {this.age = age;}

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    public String getHobbiesItems() {
        return hobbiesItems;
    }

    public void setHobbiesItems(String hobbiesItems) {
        this.hobbiesItems = hobbiesItems;
    }


    public void printFriend()
    {
        System.out.println("--------------------------------------My details are: "+firstNsame+", "+phoneNumber+", "+gender+", "+hobbies+", "+redLine);
    }

    public void printImage()
    {
        System.out.println("--------------------------------------My image is: "+image);
    }

    public String getPrintFriend()
    {
        return "--------------------------------------My details are: "+firstNsame+", "+phoneNumber+", "+gender+", "+hobbies+", "+redLine;
    }




}
