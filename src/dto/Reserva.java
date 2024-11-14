package dto;

import java.sql.Date;
import java.sql.Time;

public class Reserva {
    private int id;
    private int membreId;
    private int pistaId;
    private Date data;
    private Time hora;
    private int durada;
    private Date created_at;
    private Date updated_at;

    public Reserva(int id, int membreId, int pistaId, Date data, Time hora, int durada, Date created_at, Date updated_at) {
        this.id = id;
        this.membreId = membreId;
        this.pistaId = pistaId;
        this.data = data;
        this.hora = hora;
        this.durada = durada;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Reserva() {

    }

    // Getters i setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMembreId() {
        return membreId;
    }

    public void setMembreId(int membreId) {
        this.membreId = membreId;
    }

    public int getPistaId() {
        return pistaId;
    }

    public void setPistaId(int pistaId) {
        this.pistaId = pistaId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", membreId=" + membreId +
                ", pistaId=" + pistaId +
                ", data=" + data +
                ", hora=" + hora +
                ", durada=" + durada +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
