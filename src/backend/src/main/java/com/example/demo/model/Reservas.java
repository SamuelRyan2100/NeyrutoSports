package com.example.demo.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private int id;

    @Column(name="data", nullable=false)
    private LocalDate data;

    @Column(name="hora_inicio", nullable=false) // Adicionei nullable=false aqui, se for o caso
    private LocalTime horaInicio;

    @Column(name="hora_fim", nullable=false) // Adicionei nullable=false aqui, se for o caso
    private LocalTime horaFim;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name="quadra_id", nullable=false)
    private Quadras quadra;

    public Reservas() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    // CORRIGIDO: Getters e Setters para 'horaInicio'
    public LocalTime getHoraInicio() { // Renomeado de getHorarioInicio
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) { // Renomeado de setHorarioInicio
        this.horaInicio = horaInicio;
    }

    // CORRIGIDO: Getters e Setters para 'horaFim'
    public LocalTime getHoraFim() { // Renomeado de getHorarioFim
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) { // Renomeado de setHorarioFim
        this.horaFim = horaFim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Quadras getQuadra() {
        return quadra;
    }

    public void setQuadra(Quadras quadra) {
        this.quadra = quadra;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", data=" + data +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", usuario=" + (usuario != null ? usuario.getId() : "null") +
                ", quadra=" + (quadra != null ? quadra.getId() : "null") +
                '}';
    }
}