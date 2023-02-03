package mx.gob.villahermosa.siacentro.classes.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DenunciasResponse {

    @SerializedName("id")
    protected int id = 0;
    @SerializedName("denuncia")
    protected String denuncia = "";
    @SerializedName("fecha")
    protected String fecha = "";
    @SerializedName("latitud")
    protected Double latitud = 0.00;
    @SerializedName("longitud")
    protected Double longitud = 0.00;
    @SerializedName("ubicacion")
    protected String ubicacion = "";
    @SerializedName("ubicacion_google")
    protected String ubicacion_google = "";
    @SerializedName("servicio")
    protected String servicio = "";
    @SerializedName("imagenes")
    protected ArrayList<ImagenesResponse> imagenes = null;
    @SerializedName("respuestas")
    protected ArrayList<RespuestasResponse> respuestas = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(String denuncia) {
        this.denuncia = denuncia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUbicacion_google() {
        return ubicacion_google;
    }

    public void setUbicacion_google(String ubicacion_google) { this.ubicacion_google = ubicacion_google; }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public ArrayList<ImagenesResponse> getImagenes() { return imagenes; }

    public void setImagenes(ArrayList<ImagenesResponse> imagenes) { this.imagenes = imagenes; }

    public ArrayList<RespuestasResponse> getRespuestas() { return respuestas; }

    public void setRespuestas(ArrayList<RespuestasResponse> respuestas) { this.respuestas = respuestas; }

}
