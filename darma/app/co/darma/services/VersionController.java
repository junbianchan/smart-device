package co.darma.services;

import co.darma.daos.UserUpdateTimeDao;
import co.darma.daos.implspringjdbc.UserUpdateTimeDaoImpl;

/**
 * Created by frank on 15/12/10.
 */
public class VersionController {

    public static final VersionController INSTANCE = new VersionController();

    private UserUpdateTimeDao userUpdateTimeDao = UserUpdateTimeDaoImpl.INSTANCE;

    public Long getLastVersionbyMemberId(Long memberId) {

        if (memberId != null) {

            return userUpdateTimeDao.queryUserLastUpdateTime(memberId);
        }
        return 0L;
    }

}
