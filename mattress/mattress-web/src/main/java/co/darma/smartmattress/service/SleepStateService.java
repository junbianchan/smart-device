package co.darma.smartmattress.service;

import co.darma.smartmattress.analysis.dao.SleepStateDao;
import co.darma.smartmattress.analysis.entity.SleepState;
import co.darma.smartmattress.analysis.service.AnalysisSleepService;
import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.entity.ValueAndTime;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.util.LogUtil;
import co.darma.smartmattress.validate.ManDeviceBoundValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by frank on 16/1/4.
 */
@Component
@Path("sleepstate")
public class SleepStateService {

    public static final String LASTEST_UPDATE_TIME = "lastest_update_time";

    @Autowired
    private SleepStateDao sleepStateDao;

    private final static String INTERFACE_NAME = "sleepstate";

    private final static String SLEEP_DATA = "sleep_data";

    private static Logger logger = Logger.getLogger(SleepStateService.class);

    @Autowired
    private DeviceDao deviceDao;

    /**
     * 用于格式化数据
     */
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private AnalysisSleepService analysisSleepService;

    @GET
    @Path("/{device}/{last_update_time}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSleepState(
            @HeaderParam("access_token") String accessToken,
            @HeaderParam("v") String version,
            @PathParam("device") String devcieNo,
            @PathParam("last_update_time") String lastUpdateTime
    ) {
        if (lastUpdateTime != null && !isNumber(lastUpdateTime.trim())) {

            return Response.status(Response.Status.BAD_REQUEST).
                    entity(ErrorMessageBuilder.buildErrorResponse(ErrorResponse.CLIENT_ERROR,
                            ManDeviceBoundValidator.CLIENT_ERROR_MESSAGE,
                            ErrorResponse.ARGUMENT_ILLEAGEL,
                            "Argument [last_update_time: " + lastUpdateTime + " ] is not a valid Number.")).build();
        }

        try {

            Device devcie = deviceDao.queryDeviceByDeviceNo(devcieNo);

            if (devcie == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(
                        ErrorMessageBuilder.buildErrorMessage(ErrorResponse.CLIENT_ERROR,
                                ManDeviceBoundValidator.CLIENT_ERROR_MESSAGE,
                                ErrorResponse.ARGUMENT_ILLEAGEL, "Argument [device: " + devcie + " ] is not valid device No. ")).build();
            }

            Integer devcieId = devcie.getId();

            //旧数据
            List<SleepState> sleepStateList = sleepStateDao.querySleepState(devcieId, Long.valueOf(lastUpdateTime));
            Long lastestUpdateTime = getLastUpdateTimeFromData(sleepStateList);
            Long currentTime;
            if (lastestUpdateTime > 0) {
                currentTime = System.currentTimeMillis() / 1000;
            } else {
                currentTime = Long.valueOf(lastUpdateTime);
            }

            //临时数据
            List<SleepState> tmpData = analysisSleepService.analysisSleepData(devcie, currentTime);
            if (CollectionUtils.isNotEmpty(tmpData)) {
                sleepStateList.addAll(tmpData);
            }

            Map<String, Object> sleepData = new HashMap<String, Object>(2);
            sleepData.put(SLEEP_DATA, sleepStateList);
            sleepData.put(LASTEST_UPDATE_TIME, lastestUpdateTime);
            Map<String, Object> interfaceResult = new HashMap<String, Object>();
            interfaceResult.put(INTERFACE_NAME, sleepData);
            return Response.status(Response.Status.OK).entity(mapper.writeValueAsString(interfaceResult)).build();
        } catch (Exception e) {
            //TODO
            e.printStackTrace();

            logger.error(LogUtil.logException(e));

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(
                    ErrorMessageBuilder.buildErrorResponse(ErrorResponse.SERVER_ERROR, "server error.",
                            ErrorResponse.PASE_JSON_ERROR, "Unknown Error.")
            ).build();
        }

    }

    private List<ValueAndTime> buildSleepData(List<SleepState> sleepStatesList) {
        if (CollectionUtils.isNotEmpty(sleepStatesList)) {
            List<ValueAndTime> result = new LinkedList<ValueAndTime>();
            for (SleepState data : sleepStatesList) {
                result.add(new ValueAndTime(data.getSleepState(), data.getStartTime()));
            }
            return result;
        }

        return null;
    }

    private Long getLastUpdateTimeFromData(List<SleepState> sleepStatesList) {

        if (CollectionUtils.isNotEmpty(sleepStatesList)) {

            long maxValue = -1;

            for (SleepState data : sleepStatesList) {

                if (data.getUpdateTime() > maxValue) {
                    maxValue = data.getUpdateTime();
                }
            }

            return maxValue;
        }
        return -1L;

    }

    public void setAnalysisSleepService(AnalysisSleepService analysisSleepService) {
        this.analysisSleepService = analysisSleepService;
    }

    public void setSleepStateDao(SleepStateDao sleepStateDao) {
        this.sleepStateDao = sleepStateDao;
    }

    private static Pattern pat = Pattern.compile("^-?[0-9]+$");

    private boolean isNumber(String str) {
        Matcher mat = pat.matcher(str);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }
}
