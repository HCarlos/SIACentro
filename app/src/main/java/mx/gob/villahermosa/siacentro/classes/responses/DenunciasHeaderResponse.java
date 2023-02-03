package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DenunciasHeaderResponse {
    @SerializedName("status")
    protected int status = 0;
    @SerializedName("msg")
    protected String msg = "";
    @SerializedName("denuncias")
    protected ArrayList<DenunciasResponse> denuncias = null;

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

    public ArrayList<DenunciasResponse> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(ArrayList<DenunciasResponse> denuncias) {
        this.denuncias = denuncias;
    }
}
