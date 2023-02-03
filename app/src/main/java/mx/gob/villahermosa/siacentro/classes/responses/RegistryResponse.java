package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

public class RegistryResponse {
    @SerializedName("status")
    public int status = 0;
    @SerializedName("msg")
    public String msg = "";
    @SerializedName("access_token")
    public String access_token = "";
    @SerializedName("token_type")
    public String token_type = "";
    @SerializedName("username")
    public String username = "";
    @SerializedName("password")
    public String password = "";
    @SerializedName("email")
    public String email = "";
    @SerializedName("ap_paterno")
    public String ap_paterno = "";
    @SerializedName("ap_materno")
    public String ap_materno = "";
    @SerializedName("nombre")
    public String nombre = "";
    @SerializedName("domicilio")
    public String domicilio = "";

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAp_paterno() {
        return ap_paterno;
    }

    public void setAp_paterno(String ap_paterno) {
        this.ap_paterno = ap_paterno;
    }

    public String getAp_materno() {
        return ap_materno;
    }

    public void setAp_materno(String ap_materno) {
        this.ap_materno = ap_materno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
