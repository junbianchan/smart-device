package co.darma.smartmattress.entity;

import sun.rmi.runtime.Log;

/**
 * Created by frank on 15/10/12.
 */
public class UserInfo {

    /**
     * 用户Id
     */
    private Integer id;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 加密密码
     */
    private String password;

    /**
     * 用户全名
     */
    private String userFullName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 体重(kg)
     */
    private Double weightKilo;

    /**
     * 体重(磅)
     */
    private Double weightPound;

    private Project project;

    private String salt;

    private boolean onLine;

    private Long registerTime;

    private String tokenType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getWeightKilo() {
        return weightKilo;
    }

    public void setWeightKilo(Double weightKilo) {
        this.weightKilo = weightKilo;
    }

    public Double getWeightPound() {
        return weightPound;
    }

    public void setWeightPound(Double weightPound) {
        this.weightPound = weightPound;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userFullName='" + userFullName + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", weightKilo=" + weightKilo +
                ", weightPound=" + weightPound +
                ", project=" + project +
                ", salt='" + salt + '\'' +
                ", onLine=" + onLine +
                ", registerTime=" + registerTime +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
