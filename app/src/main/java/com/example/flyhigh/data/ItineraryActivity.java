package com.example.flyhigh.data;

public class ItineraryActivity {

    private String _actividad;
    private String _detalles;
    private String _fecha;
    private String _time;
    private String _ubicacion;

    public ItineraryActivity() {
    }

    public ItineraryActivity(String _actividad,
                             String _detalles,
                             String _fecha,
                             String _time,
                             String _ubicacion) {
        this._actividad = _actividad;
        this._detalles = _detalles;
        this._fecha = _fecha;
        this._time = _time;
        this._ubicacion = _ubicacion;
    }

    public String get_actividad() {
        return _actividad;
    }

    public String get_detalles() {
        return _detalles;
    }

    public String get_fecha() {
        return _fecha;
    }

    public String get_time() {
        return _time;
    }

    public String get_ubicacion() {
        return _ubicacion;
    }
}
