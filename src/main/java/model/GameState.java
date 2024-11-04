package model;

import repositorio.CenaDAO;

import java.sql.SQLException;
import java.util.List;

public class GameState {
    private String location;
    private String message;
    private final Inventario inventario;
    private Integer cenaId;
    private String help;
    private Integer idSave;
    private Cena cenaAtual;
    private List<Item> inventory;
    private boolean visitouLocal;

    public GameState() {
        this.location = "start";
        this.message = "";
        this.inventario = new Inventario(this);
        this.cenaId = null;
        this.help = "";
        this.idSave = 1; // Id do save fixo
        this.cenaAtual = null;
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
        return cenaId;
    }

    public void setCenaId(Integer cenaId) {
        this.cenaId = cenaId;
    }

    public Cena getCenaAtual() {
        return cenaAtual;
    }

    public void setCenaAtual(Cena cenaAtual) {
        this.cenaAtual = cenaAtual; // Define a cena atual
        this.cenaId = cenaAtual.getIdCena(); // Atualiza o ID da cena atual
    }

    // metodo para carregar cena
    public void carregarCena(int cenaId) throws SQLException {
        Cena cena = CenaDAO.findCenaById(cenaId);
        this.setMessage(cena.getDescricao());
        this.setHelp(cena.getHelp_cena());
        this.setCenaAtual(cena);
    }

}
