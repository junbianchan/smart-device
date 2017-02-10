package co.darma.models.view;

public class ResponseModel {

    public String status = "Success";

    public int respCode;

    public String respMsg = "";

    public ResponseModel() {
    }

    public ResponseModel(int errorCode, String errorMsg) {
        this.status = "Failure";
        this.respCode = errorCode;
        this.respMsg = errorMsg;
    }
}
