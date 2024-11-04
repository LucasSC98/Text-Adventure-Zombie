package model;

import repositorio.InventarioDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Inventario {
    private List<Item> itens;
    private final GameState gameState;

    public Inventario(GameState gameState) {
        this.itens = new ArrayList<>();
        this.gameState = gameState;
    }

    public List<Item> getItens() {
        return itens;
    }

    public void adicionarItem(Item item) throws SQLException {
        if (!itemNoInv(item)) {
            itens.add(item);
            InventarioDAO.adicionarItem(item, gameState.getIdSave());
        }
    }

    public void removerItem(Item item, int quantidade) throws SQLException {
        for (Item i : this.itens) {
            if (Objects.equals(i.getId(), item.getId())) {
                int novaQuantidade = i.getQtd() - quantidade;
                if (novaQuantidade <= 0) {
                    this.itens.remove(i);
                    InventarioDAO.removerItem(item, gameState.getIdSave()); // Passa o id e remove do mesmo
                } else {
                    i.setQtd(novaQuantidade);
                    InventarioDAO.adicionarItem(i, gameState.getIdSave()); // vai atualizar a quantidade
                }
                break;
            }
        }
    }

    public List<Item> listarItens() {
        return itens;
    }


    public boolean itemNoInv(Item item) {
        return itens.stream().anyMatch(i -> i.getId().equals(item.getId()));
    }

    public void setItens(List<Item> inventario) {
        this.itens = inventario;
    }
    public void salvarInventario() throws SQLException {
        InventarioDAO.salvarInventario(itens, 1); // aqui o id_save é fixo como 1 para simplificar
    }
    public void carregarInventario(int idSave) throws SQLException {
        List<Item> itensCarregados = InventarioDAO.carregarInventario(idSave);
        this.itens.clear();
        this.itens.addAll(itensCarregados);
    }
}
