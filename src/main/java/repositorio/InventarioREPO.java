package repositorio;

import model.Item;

import java.sql.*;

public class InventarioREPO {
    public static void adicionarItem(Item item, int idSave) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement checkStmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");

            // Verifica se o item já existe no inventário
            checkStmt = conn.prepareStatement("SELECT quantidade FROM inventario WHERE item_id = ? AND id_save = ?");
            checkStmt.setInt(1, item.getId());
            checkStmt.setInt(2, idSave);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Se tiver o item, atualizar a quantidade dele
                int novaQuantidade = rs.getInt("quantidade") + item.getQuantidade();
                stmt = conn.prepareStatement("UPDATE inventario SET quantidade = ? WHERE item_id = ? AND id_save = ?");
                stmt.setInt(1, novaQuantidade);
                stmt.setInt(2, item.getId());
                stmt.setInt(3, idSave);
            } else {
                // Se o item não existir, insere um novo registro do item
                stmt = conn.prepareStatement("INSERT INTO inventario (item_id, nome_item, quantidade, id_save) VALUES (?, ?, ?, ?)");
                stmt.setInt(1, item.getId());
                stmt.setString(2, item.getNome());
                stmt.setInt(3, item.getQuantidade());
                stmt.setInt(4, idSave);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (checkStmt != null) checkStmt.close();
            if (conn != null) conn.close();
        }
    }
    public static void removerItem(Item item, int quantidade, Integer idSave) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement checkStmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");

            // Verifica a quantidade atual do item
            checkStmt = conn.prepareStatement("SELECT quantidade FROM inventario WHERE item_id = ? AND id_save = ?");
            checkStmt.setInt(1, item.getId());
            checkStmt.setInt(2, idSave); // Verifica pelo id_save
            rs = checkStmt.executeQuery();

            if (rs.next()) {
                int quantidadeAtual = rs.getInt("quantidade");
                if (quantidadeAtual < quantidade) {
                    throw new IllegalArgumentException("Quantidade a remover maior que a quantidade disponível.");
                }
                stmt = conn.prepareStatement("UPDATE inventario SET quantidade = quantidade - ? WHERE item_id = ? AND id_save = ?");
                stmt.setInt(1, quantidade);
                stmt.setInt(2, item.getId());
                stmt.setInt(3, idSave); // Atualiza com id_save
                stmt.executeUpdate();
            } else {
                throw new IllegalArgumentException("Item não encontrado no inventário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (checkStmt != null) checkStmt.close();
            if (rs != null) rs.close();
            if (conn != null) conn.close();
        }
    }

    public static void limparInventario(Integer idSave) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");
            stmt = conn.prepareStatement("DELETE FROM inventario WHERE id_save = ?");
            stmt.setInt(1, idSave); // Limpa pelo id_save
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
