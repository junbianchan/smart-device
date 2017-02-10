package co.darma.smartmattress.ccb.dao;

import co.darma.smartmattress.ccb.entity.AccessContext;

import java.util.List;

/**
 * Created by frank on 15/11/13.
 */
public interface AccessTokenDao {

    public void updateAccessToken(AccessContext context);

    public List<AccessContext> queryActiveAccessContextByUserName(String userName);

    public List<AccessContext> queryActiveAccessContext(String accessToken);
}
