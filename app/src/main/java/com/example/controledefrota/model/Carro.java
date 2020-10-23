package com.example.controledefrota.model;

public class Carro {
    private String id;
    private String placa;
    private String modelo;

    public Carro(String placa, String modelo) {
        this.placa = placa;
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }
}
