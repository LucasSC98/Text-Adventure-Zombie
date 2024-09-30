package model;

import repositorio.InventarioREPO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Item> itens;
    private GameState gameState;

    public Inventario(GameState gameState) {
        this.itens = new ArrayList<>();
        this.gameState = gameState;
    }

    public void adicionarItem(Item item, int quantidade) throws SQLException {
        for (Item i : itens) {
            if (i.getId() == item.getId()) {
                i.setQuantidade(i.getQuantidade() + quantidade);
                InventarioREPO.adicionarItem(i, gameState.getIdSave());
                return;
            }
        }
        item.setQuantidade(quantidade);
        itens.add(item);
        InventarioREPO.adicionarItem(item, gameState.getIdSave()); // Passa o id e adiciona nele
    }

    public void removerItem(Item item, int quantidade) throws SQLException {
        for (Item i : this.itens) {
            if (i.getId() == item.getId()) {
                int novaQuantidade = i.getQuantidade() - quantidade;
                if (novaQuantidade <= 0) {
                    this.itens.remove(i);
                    InventarioREPO.removerItem(i, quantidade, gameState.getIdSave()); // Passa o id e remove do mesmo
                } else {
                    i.setQuantidade(novaQuantidade);
                    InventarioREPO.adicionarItem(i, gameState.getIdSave()); // vai atualizar a quantidade
                }
                break;
            }
        }
    }

    public List<Item> listarItens() {
        return itens;
    }

    public Item buscarItemPorNome(String nome) {
        for (Item item : itens) {
            if (item.getNome().equalsIgnoreCase(nome)) {
                return item;
            }
        }
        return null;
    }

    public boolean itemJaPegado(Item item) {
        for (Item i : itens) {
            if (i.getId() == item.getId()) {
                return true;
            }
        }
        return false;
    }

    public void setItens(List<Item> inventario) {
        this.itens = inventario;
    }
}
