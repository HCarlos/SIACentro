package mx.gob.villahermosa.siacentro.classes.responses;

import java.util.List;

public class DenunciasResponse {

    protected int id = 0;
    protected String denuncia = "";
    protected String fecha = "";
    protected Double latitud = 0.00;
    protected Double longitud = 0.00;
    protected String ubicacion = "";
    protected String ubicacion_google = "";
    protected String servicio = "";
    protected List<ImagenesResponse> imagenes = null;

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

    public void setUbicacion_google(String ubicacion_google) {
        this.ubicacion_google = ubicacion_google;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public List<ImagenesResponse> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenesResponse> imagenes) {
        this.imagenes = imagenes;
    }




}
