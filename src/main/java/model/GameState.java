package model;

import repositorio.AcoesREPO;
import repositorio.CenaREPO;

import java.sql.SQLException;
import java.util.List;

public class GameState {
    private String location;
    private String message;
    private Inventario inventario;
    private Integer cenaId; // ID da cena atual
    private Localizacao localizacao;
    private String help;
    private Integer idSave; // ID do save
    private Cena cenaAtual; // Cena atual
    private List<Item> inventory;
    private boolean visitouLocal;

    public GameState() {
        this.location = "start";
        this.message = "";
        this.inventario = new Inventario(this);
        this.cenaId = null; // Inicialmente sem cena
        this.localizacao = null;
        this.help = "";
        this.idSave = 1; // Inicialmente sem ID de save
        this.cenaAtual = null; // Inicialmente sem cena atual
    }

    public Integer getIdSave() {
        return idSave;
    }

    public boolean isVisitouLocal() {
        return visitouLocal;
    }

    public void setVisitouLocal(boolean visitouLocal) {
        this.visitouLocal = visitouLocal;
    }

    public void setIdSave(Integer idSave) {
        this.idSave = idSave;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public Integer getCenaId() {
        return cenaId; // Método para obter o ID da cena atual
    }

    public void setCenaId(Integer cenaId) {
        this.cenaId = cenaId;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public Cena getCenaAtual() {
        return cenaAtual;
    }

    public void setCenaAtual(Cena cenaAtual) {
        this.cenaAtual = cenaAtual; // Define a cena atual
        this.cenaId = cenaAtual.getIdCena(); // Atualiza o ID da cena atual
    }

    // Método para carregar a cena e ajuda no banco de dados
    public void carregarCena(int cenaId) throws SQLException {
        Cena cena = CenaREPO.findCenaById(cenaId);
        if (cena != null) {
            this.setMessage(cena.getDescricao());
            this.setHelp(cena.getHelp_cena());
            this.setCenaAtual(cena); // Atualiza a cena atual
        } else {
            this.setMessage("Cena não encontrada.");
            this.setHelp("");
        }
    }

    public void descricaoNeg(int Id) throws SQLException {
        Acoes movimento = AcoesREPO.findAcaoById(Id);
        if (movimento != null) {
            this.setMessage(movimento.getDescricao());
        } else {
            this.setMessage("Ação não encontrada.");
        }
    }
}
