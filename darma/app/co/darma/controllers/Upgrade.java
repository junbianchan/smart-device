package co.darma.controllers;

import co.darma.constant.ErrorCode;
import co.darma.models.view.AppVersionModel;
import co.darma.models.view.ResponseModel;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Upgrade extends Controller {

    public Result getLatestVersion() throws Exception {
        try {
            return ok(Json.toJson(new AppVersionModel()));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, e.getMessage())));
        }
    }

}
