package dto;

import java.sql.Date;

public class Membre {
    private int id;
    private String nom;
    private String adreca;
    private int telefono;
    private String email;
    private Date created_at;
    private Date updated_at;

    public Membre(int id, String nom, String adreca, int telefono, String email, Date created_at, Date updated_at) {
        this.id = id;
        this.nom = nom;
        this.adreca = adreca;
        this.telefono = telefono;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Membre() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getAdreca() {
        return adreca;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
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

    public void setAdreca(String adreca) {
        this.adreca = adreca;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Membre{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adreca='" + adreca + '\'' +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
