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

    @Column(name="nome", nullable=false, length=100)
    private String nome;

    @Column(name="tipo", nullable=false, length=100)
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

    // NOVO CAMPO: Para contar as pessoas que já reservaram
    @Column(name="pessoas_atuais", nullable=false) // Deve ser NOT NULL no BD
    private int pessoasAtuais = 0; // Valor inicial para novas quadras

    // Você não incluiu 'descricao' na sua entidade, mas está no HTML.
    // Se quiser salvar a descrição, adicione:
    // @Column(name="descricao", length=255)
    // private String descricao;


    // Construtor padrão
    public Quadras() {
    }

    // Adicione um construtor que inclui 'pessoasAtuais' se você for criar Quadras programaticamente
    public Quadras(String nome, String tipo, String endereco, LocalDate data, LocalTime horarioInicio, LocalTime horarioFim, int limitePessoas, int pessoasAtuais) {
        this.nome = nome;
        this.tipo = tipo;
        this.endereco = endereco;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.horarioFim = horarioFim;
        this.limitePessoas = limitePessoas;
        this.pessoasAtuais = pessoasAtuais;
    }


    // Getters e Setters para todos os campos, incluindo 'pessoasAtuais'
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

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

    // NOVO: Getters e Setters para pessoasAtuais
    public int getPessoasAtuais() { return pessoasAtuais; }
    public void setPessoasAtuais(int pessoasAtuais) { this.pessoasAtuais = pessoasAtuais; }


    @Override
    public String toString() {
        return "Quadras{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", endereco='" + endereco + '\'' +
                ", data=" + data +
                ", horarioInicio=" + horarioInicio +
                ", horarioFim=" + horarioFim +
                ", limitePessoas=" + limitePessoas +
                ", pessoasAtuais=" + pessoasAtuais + // Incluir no toString
                '}';
    }
}