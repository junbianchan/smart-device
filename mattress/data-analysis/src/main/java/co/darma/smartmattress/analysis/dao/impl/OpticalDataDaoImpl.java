package co.darma.smartmattress.analysis.dao.impl;

import co.darma.smartmattress.analysis.dao.OpticalDataDao;
import co.darma.smartmattress.analysis.entity.OpticalData;
import co.darma.smartmattress.database.DataAccess;

/**
 * Created by frank on 16/1/27.
 */
public class OpticalDataDaoImpl implements OpticalDataDao {

    private DataAccess dataAcessor;

    @Override
    public void saveOpticalData(OpticalData data) {
        if (data != null) {
            dataAcessor.saveOrUpdateObject("co.darma.smartmattress.saveOrUpdateOpticalData", data);
        }
    }


    public DataAccess getDataAcessor() {
        return dataAcessor;
    }

    public void setDataAcessor(DataAccess dataAcessor) {
        this.dataAcessor = dataAcessor;
    }
}
