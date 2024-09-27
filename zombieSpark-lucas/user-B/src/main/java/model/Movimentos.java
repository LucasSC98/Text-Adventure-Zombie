package model;

import repositorio.AcoesDAO;
import repositorio.CenaRepo;

import java.sql.SQLException;

public class Movimentos {
    private Integer id_acao;
    private Integer item1_id;
    private Integer item2_id;
    private String descricaoComb;
    private String nome_acao;
    private String descricao_negativa;
    private String descricao;

    public Movimentos(Integer id_acao, Integer item1_id, Integer item2_id, String descricaoComb, String nome_acao, String descricao_negativa, String descricao) {
        this.id_acao = id_acao;
        this.item1_id = item1_id;
        this.item2_id = item2_id;
        this.descricaoComb = descricaoComb;
        this.nome_acao = nome_acao;
        this.descricao_negativa = descricao_negativa;
        this.descricao = descricao;
    }

    public Integer getId_acao() {
        return id_acao;
    }

    public void setId_acao(Integer id_acao) {
        this.id_acao = id_acao;
    }

    public String getDescricao_negativa() {
        return descricao_negativa;
    }

    public void setDescricao_negativa(String descricao_negativa) {
        this.descricao_negativa = descricao_negativa;
    }

    public String getNome_acao() {
        return nome_acao;
    }

    public void setNome_acao(String nome_acao) {
        this.nome_acao = nome_acao;
    }

    public String getDescricaoComb() {
        return descricaoComb;
    }

    public void setDescricaoComb(String descricaoComb) {
        this.descricaoComb = descricaoComb;
    }

    public Integer getItem1_id() {
        return item1_id;
    }

    public void setItem1_id(Integer item1_id) {
        this.item1_id = item1_id;
    }

    public Integer getItem2_id() {
        return item2_id;
    }

    public void setItem2_id(Integer item2_id) {
        this.item2_id = item2_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}


