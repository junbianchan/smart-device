package co.darma.daos;

/**
 * Created by frank on 15/12/10.
 */
public interface UserUpdateTimeDao {

    public Long queryUserLastUpdateTime(long memberId);
}
