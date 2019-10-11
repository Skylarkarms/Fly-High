package com.example.flyhigh.data;

public class ItineraryFlightInfo {

    private String _aeropuerto;
    private String _compania;
    private String _fecha;
    private String _numeroDeVuelo;
    private String _time;

    public ItineraryFlightInfo() {
    }

    public ItineraryFlightInfo(String _aeropuerto,
                               String _compania,
                               String _fecha,
                               String _numeroDeVuelo,
                               String _time) {
        this._aeropuerto = _aeropuerto;
        this._compania = _compania;
        this._fecha = _fecha;
        this._numeroDeVuelo = _numeroDeVuelo;
        this._time = _time;
    }

    public String get_aeropuerto() {
        return _aeropuerto;
    }

    public String get_compania() {
        return _compania;
    }

    public String get_fecha() {
        return _fecha;
    }

    public String get_numeroDeVuelo() {
        return _numeroDeVuelo;
    }

    public String get_time() {
        return _time;
    }
}
