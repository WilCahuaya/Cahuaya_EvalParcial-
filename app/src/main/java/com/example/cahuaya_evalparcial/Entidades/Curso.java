package com.example.cahuaya_evalparcial.Entidades;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Curso {
    private int id;
    private String nombre;
    private String descripcion;
    private String nivel;
    private String horaTeoria;
    private String horaPractica;
    private String proyecto;
    private String imagen_ruta;
    private String dato;
    private Bitmap imagen;

    public Curso() {
    }

    public Curso(int id, String nombre, String descripcion, String nivel, String horaTeoria, String horaPractica, String proyecto, String imagen_ruta, String dato, Bitmap imagen) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.nivel = nivel;
        this.horaTeoria = horaTeoria;
        this.horaPractica = horaPractica;
        this.proyecto = proyecto;
        this.imagen_ruta = imagen_ruta;
        this.dato = dato;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getHoraTeoria() {
        return horaTeoria;
    }

    public void setHoraTeoria(String horaTeoria) {
        this.horaTeoria = horaTeoria;
    }

    public String getHoraPractica() {
        return horaPractica;
    }

    public void setHoraPractica(String horaPractica) {
        this.horaPractica = horaPractica;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getImagen_ruta() {
        return imagen_ruta;
    }

    public void setImagen_ruta(String imagen_ruta) {
        this.imagen_ruta = imagen_ruta;
    }

    public String getDato() {
        return dato;
    }
    public void setDataImagen(String data){
        try{
            byte[] bytecode=Base64.decode(data,Base64.DEFAULT);
            this.imagen= BitmapFactory.decodeByteArray(bytecode,0, bytecode.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void setDato(String dato) {
        this.dato = dato;
        try {
            byte[] bytecode= Base64.decode(dato,Base64.DEFAULT);
            int alto=100;
            int ancho=100;
            Bitmap imagenO= BitmapFactory.decodeByteArray(bytecode,0,bytecode.length);
            imagen=Bitmap.createScaledBitmap(imagenO,alto,ancho,true);
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", nivel='" + nivel + '\'' +
                ", horaTeoria='" + horaTeoria + '\'' +
                ", horaPractica='" + horaPractica + '\'' +
                ", proyecto='" + proyecto + '\'' +
                ", imagen_ruta='" + imagen_ruta + '\'' +
                ", dato='" + dato + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
