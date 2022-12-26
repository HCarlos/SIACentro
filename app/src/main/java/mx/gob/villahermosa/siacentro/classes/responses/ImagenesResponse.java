package mx.gob.villahermosa.siacentro.classes.responses;

public class ImagenesResponse {
    protected int id = 0;
    protected String fecha = "";
    protected String filename = "";
    protected String filename_png = "";
    protected String filename_thum = "";
    protected Integer user_id = 0;
    protected Integer denunciamobile_id = 0;
    protected Double latitud = 0.00;
    protected Double longitud = 0.00;
    protected String url = "";
    protected String url_png = "";
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
