package model;

public class Item {
    private String nome;
    private Integer id_item;
    private Integer quantidade;
    private String descricao_item;
    private String resNegativo;
    private String pego;

    public Item(Integer id_item, String nome, String descricao_item, Integer quantidade, String resNegativo, String pego) {
        this.id_item = id_item;
        this.nome = nome;
        this.descricao_item = descricao_item;
        this.quantidade = quantidade;
        this.resNegativo = resNegativo;
        this.pego = pego;

    }
    public Item() {
    }

    public String getPego() {
        return pego;
    }

    public void setPego(String pego) {
        this.pego = pego;
    }

    public String getResNegativo() {
        return resNegativo;
    }

    public void setResNegativo(String resNegativo) {
        this.resNegativo = resNegativo;
    }

    public String getDescricao() {
        return descricao_item;
    }

    public void setDescricao(String descricao_item) {
        this.descricao_item = descricao_item;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getId() {
        return id_item;
    }

    public void setId(Integer id) {
        this.id_item = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
