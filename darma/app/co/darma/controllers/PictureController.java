package co.darma.controllers;

import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by frank on 15/11/30.
 */
public class PictureController extends Controller {


    public Result uploadPic(String picName) {
        return notFound();
    }

    public Result downloadPic(String picUUID) {
        return notFound();
    }
}
