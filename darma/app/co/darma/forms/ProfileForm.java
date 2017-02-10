package co.darma.forms;

import play.data.validation.Constraints.Required;

public class ProfileForm {

    public String firstName;

    public String lastName;

    @Required
    public long birthday;

    public int weight;

    public int height;

    @Required
    public String gender;

    public String imgLarge;

    public String imgMedium;

    public String imgSmall;

    /**
     * 体重（千克）
     * 10-500
     */
    public int weightkg = -1;

    /**
     * 体重（磅）
     * 10-1000
     */
    public int weightlbs = -1;

    /**
     * 身高（厘米）
     * 60-300
     */
    public int heightcm = -1;

    /**
     * 身高（英寸）
     * 12 - 108
     */
    public int heightinch = -1;

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

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImgLarge() {
        return imgLarge;
    }

    public void setImgLarge(String imgLarge) {
        this.imgLarge = imgLarge;
    }

    public String getImgMedium() {
        return imgMedium;
    }

    public void setImgMedium(String imgMedium) {
        this.imgMedium = imgMedium;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }


    public int getWeightkg() {
        return weightkg;
    }

    public void setWeightkg(int weightkg) {
        this.weightkg = weightkg;
    }

    public int getWeightlbs() {
        return weightlbs;
    }

    public void setWeightlbs(int weightlbs) {
        this.weightlbs = weightlbs;
    }

    public int getHeightcm() {
        return heightcm;
    }

    public void setHeightcm(int heightcm) {
        this.heightcm = heightcm;
    }

    public int getHeightinch() {
        return heightinch;
    }

    public void setHeightinch(int heightinch) {
        this.heightinch = heightinch;
    }
}