package model;

public class Localizacao {
    private Integer idlocal;
    private String nome;

    public Localizacao(int idlocal, String nome) {
        this.idlocal = idlocal;
        this.nome = nome;

    }

    public int getIdlocal() {
        return idlocal;
    }

    public void setIdlocal(int idlocal) {
        this.idlocal = idlocal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}