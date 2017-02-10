package co.darma.controllers;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.exceptions.InvalidAccessTokenException;
import co.darma.models.data.*;
import co.darma.models.view.ResponseModel;
import co.darma.services.*;
import co.darma.services.impl.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Created by frank on 15/12/4.
 */
public class DataUpdatesController extends Controller {

    private static int ONE_YEAR = 365 * 24 * 60 * 60 * 1000;

    private PhysicalService physicalService = PhysicalServiceImpl.INSTANCE;

    private SittingService sittingService = SittingServiceImpl.INSTANCE;

    private BehaviorService behaviorService = BehaviorServiceImpl.INSTANCE;

    private SedentaryService sedentaryService = SedentaryServiceImpl.INSTANCE;

    private AuthService authSvc = AuthServiceImpl.createInstance();

    private VersionController versionController = VersionController.INSTANCE;

    private static ObjectMapper mapper = new ObjectMapper();

    public Result queryUpdates(String lastUpdateTime) {

        Logger.info("lastUpdate time is :" + lastUpdateTime);
        String accessToken = request().getHeader("AccessToken");
        try {
            Long memberId = authSvc.verifyAccessToken(accessToken);

            Long lupdateTime = null;
            if (!StringUtils.isNumeric(lastUpdateTime)) {
                lupdateTime = System.currentTimeMillis() - ONE_YEAR;
            } else {
                lupdateTime = Long.valueOf(lastUpdateTime);
                if (lupdateTime < 100000L) {
                    lupdateTime = System.currentTimeMillis() - ONE_YEAR;
                }
            }

            List<PhysicalRecord> physicalRecords = physicalService.queryAllLastestRecord(memberId,lupdateTime);
            List<SittingRecord> sittingRecords = sittingService.queryAllLastestRecord(memberId,lupdateTime);
            List<BehaviorRecord> behaviorsRecors = behaviorService.queryAllLastestRecord(memberId,lupdateTime);
            List<SedentaryRecord> sendentarRecords = sedentaryService.queryAllLastestRecord(memberId,lupdateTime);

            Map finalMap = new HashMap<String, List>(4);
            finalMap.put(PhysicalRecord.KEY, physicalRecords);
            finalMap.put(SittingRecord.KEY, sittingRecords);
            finalMap.put(BehaviorRecord.KEY, behaviorsRecors);
            finalMap.put(SedentaryRecord.KEY, sendentarRecords);

            UpdatesResult result = new UpdatesResult();
            result.setHealthDatas(finalMap);
            result.setLastUpdateTime(versionController.getLastVersionbyMemberId(memberId));
//            byte[] body = mapper.writeValueAsBytes(result);
//            final ByteArrayOutputStream gzip = gzip(body);
//            response().setHeader("Content-Encoding", "gzip");
//            response().setHeader("Content-Length", gzip.size() + "");
//            return ok(gzip.toByteArray());
            return ok(Json.toJson(result));

        } catch (InvalidAccessTokenException e) {
            Logger.error("error token");
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return unauthorized(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return internalServerError(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }

    }

    public static ByteArrayOutputStream gzip(final byte[] body)
            throws IOException {
        final InputStream inputStream = new ByteArrayInputStream(body);
        final ByteArrayOutputStream stringOutputStream = new ByteArrayOutputStream((int) (body.length * 0.75));
        final OutputStream gzipOutputStream = new GZIPOutputStream(stringOutputStream);

        final byte[] buf = new byte[5000];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            gzipOutputStream.write(buf, 0, len);
        }

        inputStream.close();
        gzipOutputStream.close();

        return stringOutputStream;
    }

}
