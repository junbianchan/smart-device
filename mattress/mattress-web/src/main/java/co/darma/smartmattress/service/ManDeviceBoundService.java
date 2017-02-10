package co.darma.smartmattress.service;

import co.darma.smartmattress.dao.DeviceDao;
import co.darma.smartmattress.dao.DeviceManRelationDao;
import co.darma.smartmattress.dao.UserDao;
import co.darma.smartmattress.entity.Device;
import co.darma.smartmattress.entity.DeviceManRelation;
import co.darma.smartmattress.entity.UserInfo;
import co.darma.smartmattress.entity.ErrorResponse;
import co.darma.smartmattress.util.ErrorMessageBuilder;
import co.darma.smartmattress.validate.ManDeviceBoundValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by frank on 15/10/28.
 */
//@Component
//@Path("manbinddevice")
public class ManDeviceBoundService {

    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeviceManRelationDao deviceManRelationDao;

    private static ObjectMapper mapper = new ObjectMapper();

    private static Logger logger = Logger.getLogger(ManDeviceBoundService.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String boundDevice(
            @QueryParam("deviceNo") String deviceNo,
            @QueryParam("userName") String userName,
            @QueryParam("userFullName") String userFullName,
            @QueryParam("sex") String sex,
            @QueryParam("email") String email,
            @QueryParam("weightKilo") Double weightKilo
    ) {
        logger.info("entry boundDevice... ");

        Map<String, Object> args = new HashMap<String, Object>();

        args.put("deviceNo", deviceNo);
        args.put("userName", userName);
        args.put("userFullName", userFullName);
        args.put("sex", sex);
        args.put("email", email);
        args.put("weightKilo", weightKilo);

        Map<String, ErrorResponse> errorResponse = ManDeviceBoundValidator.validateArgument(args);

        if (errorResponse != null) {
            String result = ErrorMessageBuilder.getErrorMessage(errorResponse.get(ErrorResponse.ErrorResponse));
            logger.info("errorResponse :" + result);
            return result;
        }

        try {
            Device device = deviceDao.queryDeviceByDeviceNo(deviceNo);

            if (device == null) {
                device = new Device();
                device.setDeviceNo(deviceNo);
                deviceDao.insertDevice(device);
            }

            UserInfo userInfo = userDao.queryUserByUserName(userName);
            if (userInfo == null) {
                String result = ErrorMessageBuilder.getErrorMessage(errorResponse.get(ErrorResponse.ErrorResponse));
                return result;
            }
            List<DeviceManRelation> deviceManList = deviceManRelationDao.queryHeartByMan(device.getId());

            if (CollectionUtils.isNotEmpty(deviceManList)) {
                long currentTime = System.currentTimeMillis() / 1000;
                Iterator<DeviceManRelation> iterators = deviceManList.iterator();
                //把跟其他用户绑定的，都设定成去激活的。
                while (iterators.hasNext()) {
                    DeviceManRelation deviceManRelation = iterators.next();
                    if (userInfo.getId().equals(deviceManRelation.getUserId())) {
                        //重复绑定，那么无须在修改此绑定，删除元素，避免重复绑定
                        iterators.remove();
                        continue;
                    }
                    deviceManRelation.setUnboudTime(currentTime);
                    deviceManRelation.setIsActive(false);
                }

                if (CollectionUtils.isNotEmpty(deviceManList)) {
                    //新的绑定
                    DeviceManRelation relation = new DeviceManRelation();
                    relation.setDeviceId(device.getId());
                    relation.setUserId(userInfo.getId());
                    relation.setBoundTime(System.currentTimeMillis() / 1000);
                    relation.setIsActive(true);
                    deviceManRelationDao.insertDeviceManRelathion(relation);
                    //去激活就的绑定
                    deviceManRelationDao.updateDeviceManRelathionBatch(deviceManList);
                }
            } else {
                //新的绑定
                DeviceManRelation relation = new DeviceManRelation();
                relation.setDeviceId(device.getId());
                relation.setUserId(userInfo.getId());
                relation.setBoundTime(System.currentTimeMillis() / 1000);
                relation.setIsActive(true);
                deviceManRelationDao.insertDeviceManRelathion(relation);
            }


            Map entity = new HashMap<String, Object>();
            Map<String, Object> mattressManbinddevice = new HashMap<String, Object>();
            mattressManbinddevice.put("userId", userInfo.getId());
            mattressManbinddevice.put("userName", userInfo.getUserName());
            mattressManbinddevice.put("deviceNo", deviceNo);
            entity.put("mattress_manbinddevice", mattressManbinddevice);


            String result = mapper.writeValueAsString(entity);
            logger.info("result :" + result);
            return result;
        } catch (JsonProcessingException e) {
            logger.error(e);
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.SERVER_ERROR, "server error.",
                    ErrorResponse.BUILD_RESULT_ERROR, e.getMessage());
        } catch (Exception e) {
            logger.error(e);
            return ErrorMessageBuilder.buildErrorMessage(ErrorResponse.SERVER_ERROR, "server error.",
                    ErrorResponse.SERVER_UNKOWN_ERROR, e.getMessage());
        }
    }


    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }
}
