package mx.gob.villahermosa.siacentro.classes.databases;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import mx.gob.villahermosa.siacentro.classes.AppConfig;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey
    @ColumnInfo(name = "id")
    public Integer id;
    @ColumnInfo(name = "user_id")
    public Integer user_id;
    @ColumnInfo(name = "username")
    public String username = "";
    @ColumnInfo(name = "email")
    public String email = "";
    @ColumnInfo(name = "nombre")
    public String nombre = "";
    @ColumnInfo(name = "ap_paterno")
    public String ap_paterno = "";
    @ColumnInfo(name = "ap_materno")
    public String ap_materno = "";
    @ColumnInfo(name = "curp")
    public String curp = "";
    @ColumnInfo(name = "emails")
    public String emails = "";
    @ColumnInfo(name = "celulares")
    public String celulares = "";
    @ColumnInfo(name = "telefonos")
    public String telefonos = "";
    @ColumnInfo(name = "fecha_nacimiento")
    public String fecha_nacimiento = "";
    @ColumnInfo(name = "genero")
    public int genero = 0;
    @ColumnInfo(name = "root")
    public String root = "";
    @ColumnInfo(name = "filename")
    public String filename = "";
    @ColumnInfo(name = "filename_png")
    public String filename_png = "";
    @ColumnInfo(name = "filename_thumb")
    public String filename_thumb = "";
    @ColumnInfo(name = "admin")
    public Boolean admin = false;
    @ColumnInfo(name = "alumno")
    public Boolean alumno = false;
    @ColumnInfo(name = "delegado")
    public Boolean delegado = false;
    @ColumnInfo(name = "session_id")
    public int session_id = 0;
    @ColumnInfo(name = "status_user")
    public int status_user = 0;
    @ColumnInfo(name = "empresa_id")
    public int empresa_id = 0;
    @ColumnInfo(name = "ip")
    public String ip = "";
    @ColumnInfo(name = "host")
    public String host = "";
    @ColumnInfo(name = "logged")
    public String logged = "";
    @ColumnInfo(name = "logged_at")
    public String logged_at = "";
    @ColumnInfo(name = "logout_at")
    public String logout_at = "";
    @ColumnInfo(name = "email_verified_at")
    public String email_verified_at = "";
    @ColumnInfo(name = "created_at")
    public String created_at = "";
    @ColumnInfo(name = "updated_at")
    public String updated_at = "";
    @ColumnInfo(name = "user_mig_id")
    public int user_mig_id = 0;
    @ColumnInfo(name = "searchtext")
    public String searchtext = "";
    @ColumnInfo(name = "ubicacion_id")
    public int ubicacion_id = 0;
    @ColumnInfo(name = "imagen_id")
    public int imagen_id = 0;
    @ColumnInfo(name = "uuid")
    public String uuid = "";
    @ColumnInfo(name = "token")
    public String token = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    public String getCelulares() {
        return celulares;
    }

    public void setCelulares(String celulares) {
        this.celulares = celulares;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(String fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilename_png() {
        return filename_png;
    }

    public void setFilename_png(String filename_png) {
        this.filename_png = filename_png;
    }

    public String getFilename_thumb() {
        return filename_thumb;
    }

    public void setFilename_thumb(String filename_thumb) {
        this.filename_thumb = filename_thumb;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getAlumno() {
        return alumno;
    }

    public void setAlumno(Boolean alumno) {
        this.alumno = alumno;
    }

    public Boolean getDelegado() {
        return delegado;
    }

    public void setDelegado(Boolean delegado) {
        this.delegado = delegado;
    }

    public int getSession_id() {
        return session_id;
    }

    public void setSession_id(int session_id) {
        this.session_id = session_id;
    }

    public int getStatus_user() {
        return status_user;
    }

    public void setStatus_user(int status_user) {
        this.status_user = status_user;
    }

    public int getEmpresa_id() {
        return empresa_id;
    }

    public void setEmpresa_id(int empresa_id) {
        this.empresa_id = empresa_id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogged() {
        return logged;
    }

    public void setLogged(String logged) {
        this.logged = logged;
    }

    public String getLogged_at() {
        return logged_at;
    }

    public void setLogged_at(String logged_at) {
        this.logged_at = logged_at;
    }

    public String getLogout_at() {
        return logout_at;
    }

    public void setLogout_at(String logout_at) {
        this.logout_at = logout_at;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_mig_id() {
        return user_mig_id;
    }

    public void setUser_mig_id(int user_mig_id) {
        this.user_mig_id = user_mig_id;
    }

    public String getSearchtext() {
        return searchtext;
    }

    public void setSearchtext(String searchtext) {
        this.searchtext = searchtext;
    }

    public int getUbicacion_id() {
        return ubicacion_id;
    }

    public void setUbicacion_id(int ubicacion_id) {
        this.ubicacion_id = ubicacion_id;
    }

    public int getImagen_id() {
        return imagen_id;
    }

    public void setImagen_id(int imagen_id) {
        this.imagen_id = imagen_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getURLImagenArchivo() {
        if (this.getFilename() == null || this.getFilename() == "") {
            if (this.getGenero() == 0 ){
                return AppConfig.URL_HOME+"images/web/empty_user_female.png";
            }else{
                return AppConfig.URL_HOME+"images/web/empty_user_male.png";
            }
        }else{
            return AppConfig.URL_HOME+"storage/"+this.getRoot()+this.getFilename_png();
        }
    }

    public String getFullName() {
        return this.getAp_paterno()+" "+this.getAp_materno()+" "+this.getNombre();
    }


}
