package com.mercado.mercadol.Utils;

public class ItemsVo {

    private String id;
    private String nombre;
    private String info;
    private String credit;
    private int foto;
    private String imagen;


    public ItemsVo(String id, String nombre, String info, String credit, String imagen) {
        this.id = id;
        this.nombre = nombre;
        this.info = info;
        this.foto = foto;
        this.credit = credit;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
