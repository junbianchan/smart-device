package co.darma.controllers;

import co.darma.common.AgentAnalysisor;
import co.darma.common.EncryptionHelper;
import co.darma.constant.ErrorCode;
import co.darma.constant.ErrorMessageEN;
import co.darma.exceptions.*;
import co.darma.forms.CredentialForm;
import co.darma.forms.ResetPasswordForm;
import co.darma.forms.ThirdPartySignInForm;
import co.darma.forms.UpdatePasswordForm;
import co.darma.models.data.Member;
import co.darma.models.data.ThirdParty;
import co.darma.models.returns.Argument;
import co.darma.models.returns.SignInResult;
import co.darma.models.returns.ValidateResult;
import co.darma.models.view.AccessTokenModel;
import co.darma.models.view.ResponseModel;
import co.darma.services.AuthService;
import co.darma.services.EmailService;
import co.darma.services.ThirdPartyMemberService;
import co.darma.services.impl.AuthServiceImpl;
import co.darma.services.impl.EmailServiceImpl;
import co.darma.services.impl.ThirdPartyMemberServiceImpl;
import co.darma.validate.ValidatorHandler;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Authentication extends Controller {

    public static final String SIGUP = "sigup";


    private Form<CredentialForm> credentialForm = Form
            .form(CredentialForm.class);

    private AuthService authSvc = AuthServiceImpl.createInstance();
    private ThirdPartyMemberService thirdPartySvc = ThirdPartyMemberServiceImpl
            .createInstance();
    private EmailService emailSvc = EmailServiceImpl.createInstance();

    /**
     * 注册
     *
     * @return
     */
    public Result signUp() {

        String version = request().getHeader("Version");
        Logger.info(" version is :" + version);
        String agent = AgentAnalysisor.paseToGetAgent(request());
        try {
            Form<CredentialForm> bindedForm = credentialForm.bindFromRequest();
            ValidatorHandler handler = ValidatorHandler.getValidatorHandlerByName(SIGUP);
            //注解只做必填校验
            if (bindedForm.hasErrors()) {
                String key = bindedForm.errors().keySet().iterator().next();
                return ok(Json.toJson(new ResponseModel(
                        ErrorCode.PARAMTER_REQUIRED,
                        key + " is required.")));
            }

            CredentialForm form = bindedForm.get();
            Argument arg = new Argument(form);
            ValidateResult validateResult = handler.validate(arg);
            if (validateResult != null) {
                return ok(Json.toJson(new ResponseModel(validateResult.getErrorCode(), validateResult.getErrorMessage())));
            }
            return signUpHelp(form, agent);
        } catch (Exception e) {
            Logger.error("Sigin up error :" + e.getMessage());
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }


    private Result signUpHelp(CredentialForm form, String agent) throws Exception {
        try {
            SignInResult result = authSvc.signUp(form.email, form.password, agent);
            AccessTokenModel model = new AccessTokenModel(result.memberId,
                    result.accessToken, result.isNewMember);
            return ok(Json.toJson(model));
        } catch (UserAlreadyExistException e) {
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.USER_EXSIT,
                    "User with email already exists.")));
        }
    }

    /**
     * 登录
     *
     * @return
     * @throws Exception
     */
    public Result signIn() throws Exception {

        String version = request().getHeader("Version");
        Logger.info("signIn version is :" + version);
        try {
            String agent = AgentAnalysisor.paseToGetAgent(request());
            Form<CredentialForm> bindedForm = credentialForm.bindFromRequest();
            if (bindedForm.hasErrors()) {
                Logger.error(bindedForm.errors().toString());
                return ok(Json.toJson(new ResponseModel(
                        ErrorCode.INVALID_ARGUMENT,
                        "Invalid parameters provided.")));
            }
            CredentialForm form = bindedForm.get();
            SignInResult result = authSvc.signIn(form.email, form.password, agent);
            AccessTokenModel model = new AccessTokenModel(result.memberId,
                    result.accessToken, result.isNewMember);
            return ok(Json.toJson(model));
        } catch (InvalidCredentialException e) {
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.AUTH_FAILED,
                    "Email or password is Invalid.")));

        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    /**
     * 退出
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Result signOut(String accessToken) throws Exception {
        try {
            String agent = request().getHeader("agent");
            Long memberId = authSvc.verifyAccessToken(accessToken);
            authSvc.signOut(memberId, agent);
            return ok(accessToken);
        } catch (InvalidAccessTokenException e) {
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    private Form<UpdatePasswordForm> updatePassForm = Form
            .form(UpdatePasswordForm.class);

    /**
     * 更新密码
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Result updatePassword(String accessToken) throws Exception {
        try {
            Long memberId = authSvc.verifyAccessToken(accessToken);
            UpdatePasswordForm form = updatePassForm.bindFromRequest().get();
            authSvc.updatePassword(memberId, form.oldPassword, form.newPassword);
            return ok(Json.toJson(new ResponseModel()));
        } catch (InvalidAccessTokenException e) {
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, ErrorMessageEN.TOKEN_INVALID);
            return ok(Json.toJson(model));
        } catch (InvalidCredentialException e) {
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID,
                    "Provided old password is incorrect.");
            return ok(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    private Form<ThirdPartySignInForm> thirdPartySignInForm = Form
            .form(ThirdPartySignInForm.class);

    /**
     * 第三方登录
     *
     * @return
     */
    public Result thirdPartySignIn() {
        try {
            String agent = AgentAnalysisor.paseToGetAgent(request());
            ThirdPartySignInForm form = thirdPartySignInForm.bindFromRequest()
                    .get();
            ThirdParty thirdParty = ThirdParty.fromString(form.thirdParty);
            SignInResult result = thirdPartySvc.signIn(form.thirdPartyId,
                    thirdParty, form.authToken, agent);
            AccessTokenModel model = new AccessTokenModel(result.memberId,
                    result.accessToken, result.isNewMember);

            Logger.info("thirdPartySignIn:" + model);
            return ok(Json.toJson(model));
        } catch (InvalidParameterException e) {
            e.printStackTrace();
            ResponseModel model = new ResponseModel(
                    ErrorCode.TOKEN_INVALID, e.getMessage());
            return ok(Json.toJson(model));
        } catch (InvalidThirdPartyAuthTokenException e) {
            e.printStackTrace();
            ResponseModel model = new ResponseModel(
                    ErrorCode.INVALID_THIRD_PARTY_AUTH_TOKEN,
                    e.getMessage());
            return ok(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    /**
     * 忘记密码
     *
     * @param email
     * @return
     */
    public Result forgetPassword(String email) {
        try {
            Member member = authSvc.getMemberFromEmail(email);
            if (member == null) {
                return ok(Json.toJson(new ResponseModel(
                        ErrorCode.EMAIL_NOT_EXSIT,
                        "Provided email is not registered " + email)));
            } else {
                emailSvc.sendPasswordResetEmail(member.id, member.email);
                Result result = ok(Json.toJson(new ResponseModel()));
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    /**
     * 展示重置密码页面
     *
     * @param resetToken
     * @return
     */
    public Result displayResetPasswordPage(String resetToken) {
        try {
            Long currentTime = System.currentTimeMillis();
            Long memberId = authSvc
                    .extractMemberIdFromPasswordResetToken(resetToken);
            String updatePasswordToken = EncryptionHelper
                    .createAccessToken(memberId, currentTime);
            return ok(views.html.passwordreset.render(updatePasswordToken));
        } catch (ExpiredPasswordResetTokenException e) {
            e.printStackTrace();
            ResponseModel model = new ResponseModel(
                    ErrorCode.EXPIRED_PASSWORD_RESET_TOKEN, e.getMessage());
            return ok(Json.toJson(model));
        } catch (Exception e) {
            e.printStackTrace();
            return ok(Json.toJson(new ResponseModel(
                    ErrorCode.SERVER_ERROR, "System error.")));
        }
    }

    private Form<ResetPasswordForm> resetForm = Form
            .form(ResetPasswordForm.class);

    /**
     * 重置密码
     *
     * @param updateToken
     * @return
     */
    public Result resetPassword(String updateToken) {
        Logger.info("update :" + updateToken);
        String failureTitle = "Failure";
        String failureMessage = "Failed to reset your password, please try again.";

        try {
            Long memberId = EncryptionHelper.decryptAccessToken(updateToken);
            Form<ResetPasswordForm> form = resetForm.bindFromRequest();
            if (!form.hasErrors()) {
                ResetPasswordForm rf = form.get();
                if (!rf.password.trim().equals(rf.confirmPassword.trim())) {
                    ok(views.html.success.render(failureTitle,
                            "Password and confirmation do not match!", false));
                }
                authSvc.resetPassword(memberId, rf.password.trim());
                return ok(views.html.success.render("Success",
                        "Your password has been reset successfully!", true));
            } else {
                return ok(views.html.success.render(failureTitle,
                        failureMessage, false));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ok(views.html.success.render(failureTitle, failureMessage,
                    false));
        }
    }
}
