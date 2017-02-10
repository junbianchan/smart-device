package co.darma.smartmattress.service;

import co.darma.smartmattress.analysis.dao.BodyMotionDao;
import co.darma.smartmattress.analysis.dao.MeddoHealthDataDao;
import co.darma.smartmattress.analysis.entity.MeddoHealthData;
import co.darma.smartmattress.ccb.AccessTokenManager;
import co.darma.smartmattress.ccb.entity.AccessContext;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.entity.MeddoMetaData;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.validate.ManDeviceBoundValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by frank on 16/1/4.
 */
@Component
@Path("healthdata")
public class HealthDataService {

    @Autowired
    protected MeddoHealthDataDao meddoHealthDataDao;

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private AccessTokenManager manager;

    private static final String HEALTH_DATA = "health_data";

    private static final String INTERFACE = "healthdata";

    private static Logger logger = Logger.getLogger(HealthDataService.class);


    @GET
    @Path("/{device}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHealthData(
            @HeaderParam("access_token") String accessToken,
            @HeaderParam("v") String version,
            @PathParam("device") String devcieNo) {

        logger.info("access_token is :" + accessToken + ", version is :" + version + ", device is :" + devcieNo);

        AccessContext context = manager.authByToken(accessToken);

        logger.info("context is :" + context);
        if (context == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.ACCESS_DENIED,
                            "access_token is invalid or time out.",
                            ErrorResponse.ACCESS_DENIED, "access_token is invalid or expired.")
            ).build();
        }

        Device devcie = deviceDao.queryDeviceByDeviceNo(devcieNo);

        if (devcie == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR,
                            ManDeviceBoundValidator.CLIENT_ERROR_MESSAGE,
                            ErrorResponse.ARGUMENT_ILLEAGEL, "Argument [device: " + devcie + " ] is not valid device No. ")
            ).build();
        }

        Integer devcieId = devcie.getId();
        Long currentTime = System.currentTimeMillis();

        Long lastMinute = ((((currentTime / 1000) / 60) - 1) * 1000 * 60);
        Map<String, Object> data = new HashMap<String, Object>();

        MeddoHealthData healthData = meddoHealthDataDao.queryLastestHealthDataByDevice(devcieId, lastMinute);

        MeddoMetaData meddoMetaData;
        if (healthData != null) {
            meddoMetaData = new MeddoMetaData(healthData);
        } else {
            meddoMetaData = new MeddoMetaData();
            meddoMetaData.setBreath(-1);
            meddoMetaData.setHeart_rate(-1);
            meddoMetaData.setTime(lastMinute / 1000);
        }
        data.put(HEALTH_DATA, meddoMetaData);
        Map<String, Object> interfaceResult = new HashMap<String, Object>();
        interfaceResult.put(INTERFACE, data);
        logger.info("health data :" + interfaceResult);

        return Response.status(Response.Status.OK).entity(interfaceResult).build();
    }

    public void setMeddoHealthDataDao(MeddoHealthDataDao meddoHealthDataDao) {
        this.meddoHealthDataDao = meddoHealthDataDao;
    }

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }


}
