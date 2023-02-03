package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

public class ImagenesResponse {
    @SerializedName("id")
    protected int id = 0;
    @SerializedName("fecha")
    protected String fecha = "";
    @SerializedName("filename")
    protected String filename = "";
    @SerializedName("filename_png")
    protected String filename_png = "";
    @SerializedName("filename_thum")
    protected String filename_thum = "";
    @SerializedName("user_id")
    protected Integer user_id = 0;
    @SerializedName("denunciamobile_id")
    protected Integer denunciamobile_id = 0;
    @SerializedName("latitud")
    protected Double latitud = 0.00;
    @SerializedName("longitud")
    protected Double longitud = 0.00;
    @SerializedName("url")
    protected String url = "";
    @SerializedName("url_png")
    protected String url_png = "";
    @SerializedName("url_thumb")
    protected String url_thumb = "";

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

    public String getFilename_thum() {
        return filename_thum;
    }

    public void setFilename_thum(String filename_thum) {
        this.filename_thum = filename_thum;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getDenunciamobile_id() {
        return denunciamobile_id;
    }

    public void setDenunciamobile_id(Integer denunciamobile_id) {
        this.denunciamobile_id = denunciamobile_id;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl_png() {
        return url_png;
    }

    public void setUrl_png(String url_png) {
        this.url_png = url_png;
    }

    public String getUrl_thumb() {
        return url_thumb;
    }

    public void setUrl_thumb(String url_thumb) {
        this.url_thumb = url_thumb;
    }

}
