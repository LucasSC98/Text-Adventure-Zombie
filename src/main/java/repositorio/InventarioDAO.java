package repositorio;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {
    public static void adicionarItem(Item item, int idSave) throws SQLException {
        String sql = "INSERT INTO inventario (item_id, quantidade, id_save) VALUES (?, 1, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getId());
            stmt.setInt(2, idSave);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void removerItem(Item item, Integer idSave) throws SQLException {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", ""); PreparedStatement stmt = conn.prepareStatement("DELETE FROM inventario WHERE item_id = ? AND id_save = ?")) {

            stmt.setInt(1, item.getId());
            stmt.setInt(2, idSave);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("Item não encontrado no inventário.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void limparInventario(Integer idSave) throws SQLException {
        // Limpa o inventario pelo id_save caso der start em um novo jogo
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", ""); PreparedStatement stmt = conn.prepareStatement("DELETE FROM inventario WHERE id_save = ?")) {
            stmt.setInt(1, idSave);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    // Salva o inventario no msm id_save, passandp por todos itens
    public static void salvarInventario(List<Item> itens, Integer idSave) throws SQLException {
        String sql = "INSERT INTO inventario (item_id, id_save) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE item_id = VALUES(item_id)";

        try (Connection conn = Mysql.getConnection()) {
            assert conn != null;
            try (
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                for (Item item : itens) {
                    pstmt.setInt(1, item.getId());
                    pstmt.setInt(2, idSave);
                    pstmt.executeUpdate();
                }
            }
        }
    }
    public static List<Item> carregarInventario(int idSave) throws SQLException {
        List<Item> itens = new ArrayList<>();
        String query = "SELECT i.id_item, i.nome_item, inv.quantidade " +
                "FROM inventario inv " +
                "INNER JOIN itens i ON inv.item_id = i.id_item " +
                "WHERE inv.id_save = ?";

        try (Connection conn = Mysql.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, idSave);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    int id_item = rs.getInt("id_item");
                    String nome = rs.getString("nome_item"); // Aqui está pegando o nome da tabela itens
                    int qtd = rs.getInt("quantidade");
                    Item item = new Item(id_item, nome, qtd);
                    itens.add(item);
                }
            }
        }
        return itens;
    }

}

