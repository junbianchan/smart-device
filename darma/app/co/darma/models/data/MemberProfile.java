package co.darma.models.data;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Date;

public class MemberProfile {

    public MemberProfile() {

    }

    public MemberProfile(Long memberId,
                         String firstName,
                         String lastName,
                         Long birthday,
                         int height,
                         int weight,
                         String gender,
                         String imgLarge,
                         String imgMedium,
                         String imgSmall) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.imgLarge = imgLarge;
        this.imgMedium = imgMedium;
        this.imgSmall = imgSmall;
    }

    public MemberProfile(Long memberId,
                         String firstName,
                         String lastName,
                         Long birthday,
                         int height,
                         int weight,
                         String gender,
                         String imgLarge,
                         String imgMedium,
                         String imgSmall,
                         int weightkg,
                         int weightlbs,
                         int heightcm,
                         int heightinch
    ) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.imgLarge = imgLarge;
        this.imgMedium = imgMedium;
        this.imgSmall = imgSmall;
        this.weightkg = weightkg;
        this.weightlbs = weightlbs;
        this.heightcm = heightcm;
        this.heightinch = heightinch;

    }

    public Long memberId;
    public String firstName;
    public String lastName;
    public Long birthday;
    public int height;
    public int weight;
    public String gender;
    public String imgLarge;
    public String imgMedium;
    public String imgSmall;

    public int weightkg = -1;

    public int weightlbs = -1;

    public int heightcm = -1;

    public int heightinch = -1;

    @Override
    public String toString() {
        return "MemberProfile{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", height=" + height +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", imgLarge='" + imgLarge + '\'' +
                ", imgMedium='" + imgMedium + '\'' +
                ", imgSmall='" + imgSmall + '\'' +
                ", weightkg=" + weightkg +
                ", weightlbs=" + weightlbs +
                ", heightcm=" + heightcm +
                ", heightinch=" + heightinch +
                '}';
    }
}
