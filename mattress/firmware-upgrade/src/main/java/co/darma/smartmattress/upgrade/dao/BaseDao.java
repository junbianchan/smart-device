package co.darma.smartmattress.upgrade.dao;

import co.darma.smartmattress.database.DataAccess;

/**
 * Created by frank on 15/12/29.
 */
public abstract class BaseDao {

    private DataAccess dataAcessor;

    public static final String IBATIS_PROFIX = "co.darma.smartmattress.upgrade.";

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }

    public DataAccess getDataAcessor() {
        return dataAcessor;
    }
}
