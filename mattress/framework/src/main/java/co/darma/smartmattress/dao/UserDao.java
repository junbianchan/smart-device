package co.darma.smartmattress.dao;


import co.darma.smartmattress.entity.UserInfo;

/**
 * Created by frank on 15/10/28.
 */
public interface UserDao {

    public UserInfo queryUserByUserName(String userName);

    public boolean insertUserInfo(UserInfo userInfo);

    public UserInfo queryUserInfoById(Integer userId);

}
