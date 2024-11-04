package model;


public class Save {
    private int idSave;
    private Cena cenaAtual;
    private String localizacao;
    private boolean visitouLocal;

    public boolean isVisitouLocal() {
        return visitouLocal;
    }

    public void setVisitouLocal(boolean visitouLocal) {
        this.visitouLocal = visitouLocal;
    }

    public Integer getIdSave() {
        return idSave;
    }

    public void setIdSave(Integer idSave) {
        this.idSave = idSave;
    }

    public Cena getCenaAtual() {
        return cenaAtual;
    }

    public void setCenaAtual(Cena cenaAtual) {
        this.cenaAtual = cenaAtual;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Save{" +
                "idSave=" + idSave +
                ", cenaAtual=" + cenaAtual +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}
