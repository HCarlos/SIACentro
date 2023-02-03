package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

public class UsuarioResponse {
    @SerializedName("status")
    public int status           = 0;
    @SerializedName("msg")
    public String msg           = "";
    @SerializedName("access_token")
    public String access_token  = "";
    @SerializedName("token_type")
    public String token_type    = "Bearer";
    @SerializedName("user")
    public UserResponse user = new UserResponse();

    public UsuarioResponse() { }




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

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

}
