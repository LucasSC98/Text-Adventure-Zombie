package model;

import repositorio.AcoesDAO;
import repositorio.CenaRepo;

import java.sql.SQLException;

public class GameState {
    private String location;
    private String message;
    private Inventario inventario;
    private Long cenaId;
    private Localizacao localizacao;
    private String help;


    public GameState() {
        this.location = "start";
        this.message = "";
        this.inventario = new Inventario();
        this.cenaId = null;
        this.localizacao = null;
        this.help = "";
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

    public Long getCenaId() {
        return cenaId;
    }

    public void setCenaId(Long cenaId) {
        this.cenaId = cenaId;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    public void carregarCena(int cenaId, GameState gameState) throws SQLException {
        Cena cena = CenaRepo.findCenaById(cenaId);
        if (cena != null) {
            gameState.setMessage(cena.getDescricao());
            gameState.setHelp(cena.getHelp_cena());
        } else {
            gameState.setMessage("Cena não encontrada.");
            gameState.setHelp("");
        }
    }

    public void descricaoNeg(int Id, GameState gameState) throws SQLException {
        Movimentos movimento = AcoesDAO.findAcaoById(Id);
        if (movimento != null) {
            gameState.setMessage(movimento.getDescricao());
        } else {
            gameState.setMessage("Ação não encontrada.");
        }
    }
}