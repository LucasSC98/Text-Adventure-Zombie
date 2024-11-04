package model;

public class Item {
    private String nome;
    private Integer id_item;
    private String descricao_item;
    private String resNegativo;
    private String itemNoInventario;
    private int qtd;

    public Item(Integer id_item, String nome, String descricao_item, String resNegativo, String itemNoInventario, int qtd) {
        this.id_item = id_item;
        this.nome = nome;
        this.descricao_item = descricao_item;
        this.resNegativo = resNegativo;
        this.itemNoInventario = itemNoInventario;
        this.qtd = qtd;
    }
    public Item (Integer id_item, String nome, int qtd) {
        this.id_item = id_item;
        this.nome = nome;
        this.qtd = qtd;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public Item() {
    }

    public String getItemNoInventario() {
        return itemNoInventario;
    }

    public void setItemNoInventario(String itemNoInventario) {
        this.itemNoInventario = itemNoInventario;
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

    @Override
    public String toString() {
        return "Item{" +
                "nome='" + nome + '\'' +
                ", id_item=" + id_item +
                ", descricao_item='" + descricao_item + '\'' +
                ", resNegativo='" + resNegativo + '\'' +
                ", itemNoInventario='" + itemNoInventario + '\'' +
                ", qtd=" + qtd +
                '}';
    }
}
