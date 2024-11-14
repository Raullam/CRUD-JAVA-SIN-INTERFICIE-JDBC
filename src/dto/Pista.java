package dto;

import java.sql.Date;

public class Pista {
    private int id;
    private String nom;
    private String tipus;
    private Date created_at;
    private Date updated_at;

    public Pista(int id, String nombre, String apellido, Date created_at, Date updated_at) {
        this.id = id;
        this.nom = nombre;
        this.tipus = apellido;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Pista() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getTipus() {
        return tipus;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Pista{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", tipus='" + tipus + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
