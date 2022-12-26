package mx.gob.villahermosa.siacentro.classes.responses;

import java.util.List;

public class DenunciasHeaderResponse {
    protected int status = 0;
    protected String msg = "";
    protected List<DenunciasResponse> denuncias = null;

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

    public List<DenunciasResponse> getDenuncias() {
        return denuncias;
    }

    public void setDenuncias(List<DenunciasResponse> denuncias) {
        this.denuncias = denuncias;
    }
}
