package com.alura.literatura.service;

public interface IConvierteDatos {
    // El secreto está en estos símbolos <T> T
    <T> T obtenerDatos(String json, Class<T> clase);
}