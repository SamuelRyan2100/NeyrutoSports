package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="quadras")
public class Quadras {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private int id;

    @Column(name="nome", nullable=false, length=100) // <--- CONFIRMAR SE VOCÊ TEM ESTE CAMPO
    private String nome;

    @Column(name="tipo", nullable=false, length=100) // Ex: "Futsal", "Basquete"
    private String tipo;

    @Column(name="endereco", nullable=false, length=255)
    private String endereco;

    @Column(name="data", nullable=false)
    private java.time.LocalDate data;

    @Column(name="horario_inicio", nullable=false)
    private java.time.LocalTime horarioInicio;

    @Column(name="horario_fim", nullable=false)
    private java.time.LocalTime horarioFim;

    @Column(name="limite_pessoas", nullable=false)
    private int limitePessoas;

    // Construtor padrão
    public Quadras() {
    }

    // Getters e Setters para todos os campos, incluindo 'nome' e 'limitePessoas'
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; } // <--- GETTER PARA 'NOME'
    public void setNome(String nome) { this.nome = nome; } // <--- SETTER PARA 'NOME'

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public java.time.LocalDate getData() { return data; }
    public void setData(java.time.LocalDate data) { this.data = data; }

    public java.time.LocalTime getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(java.time.LocalTime horarioInicio) { this.horarioInicio = horarioInicio; }

    public java.time.LocalTime getHorarioFim() { return horarioFim; }
    public void setHorarioFim(java.time.LocalTime horarioFim) { this.horarioFim = horarioFim; }

    public int getLimitePessoas() { return limitePessoas; }
    public void setLimitePessoas(int limitePessoas) { this.limitePessoas = limitePessoas; }

    @Override
    public String toString() {
        return "Quadras{" +
                "id=" + id +
                ", nome='" + nome + '\'' + // Incluir no toString
                ", tipo='" + tipo + '\'' +
                ", endereco='" + endereco + '\'' +
                ", data=" + data +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                ", limitePessoas=" + limitePessoas +
                '}';
    }
}