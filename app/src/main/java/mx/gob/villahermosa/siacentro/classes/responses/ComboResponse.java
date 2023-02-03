package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

public class ComboResponse {
    @SerializedName("status")
    public int status = 0;
    @SerializedName("msg")
    public String msg = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
