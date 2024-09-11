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

    Cena start = CenaRepo.findCenaById(0);

    public GameState() throws SQLException {
        this.location = "start";
        this.message = start.getDescricao();
        this.inventario = new Inventario();
        this.cenaId = null;
        this.localizacao = null;
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
        } else {
            gameState.setMessage("Cena não encontrada.");
        }
    }
    public void localizarCena(int cenaId, GameState gameState) throws SQLException {
        Cena cena = CenaRepo.findCenaById(cenaId);
        if (cena != null) {
            gameState.setLocation(cena.getNome_cena());
        }else {
            gameState.setMessage("Não conheço essa localização");
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