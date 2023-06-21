package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

public class RespuestasResponse {
    @SerializedName("id")
    protected int id = 0;
    @SerializedName("fecha")
    protected String fecha = "";
    @SerializedName("respuesta")
    protected String respuesta = "";
    @SerializedName("roleuser")
    protected String roleuser = "";
    @SerializedName("username")
    protected String username = "";
    @SerializedName("observaciones")
    protected String observaciones = "";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getRoleuser() { return roleuser; }

    public void setRoleuser(String roleuser) { this.roleuser = roleuser; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

}
