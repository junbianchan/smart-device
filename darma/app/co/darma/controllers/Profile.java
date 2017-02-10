package co.darma.controllers;

import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.constant.InterfaceVersionController;
import co.darma.exceptions.InternalException;
import co.darma.exceptions.InvalidAccessTokenException;
import co.darma.forms.ProfileForm;
import co.darma.models.data.MemberProfile;
import co.darma.models.returns.Argument;
import co.darma.models.returns.ValidateResult;
import co.darma.models.view.ResponseModel;
import co.darma.services.AuthService;
import co.darma.services.MemberService;
import co.darma.services.impl.AuthServiceImpl;
import co.darma.services.impl.MemberServiceImpl;
import co.darma.validate.ValidatorHandler;
import org.apache.commons.lang3.StringUtils;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 个人注册信息
 */
public class Profile extends Controller {

    private Form<ProfileForm> profileForm = Form.form(ProfileForm.class);

    private AuthService authSvc = AuthServiceImpl.createInstance();

    private MemberService memberSvc = MemberServiceImpl.createInstance();

    private String FIRST_NAME = "DARMA_FAKE_FIRST";

    private String LAST_NAME = "DARMA_FAKE_LAST";

    @Deprecated
    public Result createProfile(String accessToken) throws Exception {
        String version = request().getHeader("version");
        Logger.info("Call Profile.createProfile() interface version:" + version);
        try {
            Long memberId = authSvc.verifyAccessToken(accessToken);
            ProfileForm form = profileForm.bindFromRequest().get();

            // 版本1.0需要校验的东西
            if (StringUtils.equalsIgnoreCase(version, InterfaceVersionController.VERSION_V_1_0)) {
                ValidatorHandler handler = ValidatorHandler.getValidatorHandlerByName(ValidatorHandler.PROFILE);
                ValidateResult validateResult = handler.validate(new Argument(form));
                if (validateResult != null) {
                    return ok(Json.toJson(new ResponseModel(validateResult.getErrorCode(), validateResult.getErrorMessage())));
                }
            }

            if (StringUtils.isBlank(form.firstName) || StringUtils.isBlank(form.lastName)) {
                ResponseModel model = new ResponseModel(
                        ErrorCode.INVALID_ARGUMENT,
                        "Missing parameter in request.");
                return ok(Json.toJson(model));
            }

            if (StringUtils.equalsIgnoreCase(version, InterfaceVersionController.VERSION_V_1_0)) {
                MemberProfile profile = new MemberProfile(memberId, form.firstName,
                        form.lastName, form.birthday, form.height, form.weight,
                        form.gender, form.imgLarge, form.imgMedium, form.imgSmall,
                        form.weightkg, form.weightlbs, form.heightcm, form.heightinch
                );
                memberSvc.updateProfile(profile);
            } else {
                MemberProfile profile = new MemberProfile(memberId, form.firstName,
                        form.lastName, form.birthday, form.height, form.weight,
                        form.gender, form.imgLarge, form.imgMedium, form.imgSmall);
                memberSvc.updateProfile(profile);
            }
            return ok(Json.toJson(new ResponseModel()));
        } catch (InvalidAccessTokenException e) {
            Logger.error("error token");
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (InternalException e) {
            Logger.error("error token");
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (IllegalStateException e) {
            Logger.error("IllegalStateException :" + e.getStackTrace()[0]);
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.INVALID_ARGUMENT, e.getMessage())));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    /**
     * 获取用户个人信息
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Result getProfile(String accessToken) throws Exception {

        if (Logger.isDebugEnabled()) {
            Logger.info("begin to getProfile:" + accessToken);
        }

        try {
            Long memberId = authSvc.verifyAccessToken(accessToken);
            MemberProfile p = memberSvc.getProfileByMemberId(memberId);
            if (p == null) {
                return ok(Json.toJson(new ResponseModel(
                        ErrorCode.PROFILE_NOT_FOUND,
                        "Cannot find profile for member.")));
            } else {
                Logger.info("Get Profile Successed :" + p);
                return ok(Json.toJson(p));
            }
        } catch (InvalidAccessTokenException e) {
            Logger.error("Error token :" + e.getMessage());
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (InternalException e) {
            Logger.error("Error token :" + e.getMessage());
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (Exception e) {
            Logger.error(e.getStackTrace()[0] + ", :" + e.getMessage());
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }


    /**
     * 更新个人信息
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Result updateProfile(String accessToken) throws Exception {
        String version = request().getHeader("version");
        try {
            Long memberId = authSvc.verifyAccessToken(accessToken);
            Logger.info("member is :" + memberId);
            ProfileForm form = profileForm.bindFromRequest().get();

            // 版本1.0需要校验的东西
            if (StringUtils.equalsIgnoreCase(version, InterfaceVersionController.VERSION_V_1_0)) {
                ValidatorHandler handler = ValidatorHandler.getValidatorHandlerByName(ValidatorHandler.PROFILE);
                ValidateResult validateResult = handler.validate(new Argument(form));
                if (validateResult != null) {
                    return ok(Json.toJson(new ResponseModel(validateResult.getErrorCode(), validateResult.getErrorMessage())));
                }
            }

            MemberProfile profile = null;
            if (StringUtils.equalsIgnoreCase(version, InterfaceVersionController.VERSION_V_1_0)) {
                profile = new MemberProfile(memberId, form.firstName,
                        form.lastName, form.birthday, form.height, form.weight,
                        form.gender, form.imgLarge, form.imgMedium, form.imgSmall,
                        form.weightkg, form.weightlbs, form.heightcm, form.heightinch
                );
                Logger.info("V1.0 profile information is :" + profile);
            } else {
                profile = new MemberProfile(memberId, form.firstName,
                        form.lastName, form.birthday, form.height, form.weight,
                        form.gender, form.imgLarge, form.imgMedium, form.imgSmall);
                Logger.info("profile information is :" + profile);
            }

            memberSvc.updateProfile(profile);
            return ok(Json.toJson(new ResponseModel()));
        } catch (InvalidAccessTokenException e) {
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (InternalException e) {
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }


}
