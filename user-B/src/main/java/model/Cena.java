package model;

import java.util.ArrayList;
import java.util.List;

public class Cena {
    private Integer id_cena;
    private String descricao_cena;
    private String id_local;
    private String nome_cena;

    public Cena(int id_cena, String descricao_cena, String id_local, String nome_cena) {
        this.id_cena = id_cena;
        this.descricao_cena = descricao_cena;
        this.id_local = id_local;
        this.nome_cena = nome_cena;
    }

    public Integer getIdCena() {
        return id_cena;
    }

    public String getNome_cena() {
        return nome_cena;
    }

    public void setNome_cena(String nome_cena) {
        this.nome_cena = nome_cena;
    }

    public void setIdCena(Integer idCena) {
        this.id_cena = idCena;
    }

    public String getDescricao() {
        return descricao_cena;
    }

    public void setDescricao(String descricao) {
        this.descricao_cena = descricao_cena;
    }

    public String getId_local() {
        return id_local;
    }

    public void setId_local(String id_local) {
        this.id_local = id_local;
    }

    @Override
    public String toString() {
        return "Cena{" +
                "id_cena=" + id_cena +
                ", descricao='" + descricao_cena + '\'' +
                ", local='" + id_local + '\'' +
                '}';
    }
}
