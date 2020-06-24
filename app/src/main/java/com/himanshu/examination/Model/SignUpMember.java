package com.himanshu.examination.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class SignUpMember {

    private String Name, RollNo, Address, Email, Password, Password2, image;
    private String Gender, Stream, Standard, Division;

    public SignUpMember() {

    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getRollNo() {
        return RollNo;
    }

    public void setRollNo(String RollNo) {
        this.RollNo = RollNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getPassword2() {
        return Password2;
    }

    public void setPassword2(String Password2) {
        this.Password2 = Password2;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public String getStream() {
        return Stream;
    }

    public void setStream(String Stream) {
        this.Stream = Stream;
    }

    public String getStandard() {
        return Standard;
    }

    public void setStandard(String Standard) {
        this.Standard = Standard;
    }

    public String getDivision() {
        return Division;
    }

    public void setDivision(String Division) {
        this.Division = Division;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Exclude
    public static Map<String, Object> updateImageMap(String imageUrl){
        Map<String, Object>map = new HashMap<>();
        map.put("image", imageUrl);
        return map;
    }

}
