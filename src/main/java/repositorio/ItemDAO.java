package repositorio;

import model.Item;

import java.sql.*;

public class ItemDAO {
    public static Item findItemByID(int id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");
            stmt = conn.prepareStatement("SELECT * FROM itens WHERE id_item = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer id_item = rs.getInt("id_item");
                String nome = rs.getString("nome_item");
                String descricao_item = rs.getString("descricao_item");
                String resNegativo = rs.getString("descricao_negativa");
                String itemNoInventario = rs.getString("item_pego");
                int qtd = rs.getInt("quantidade");
                return new Item(id_item, nome, descricao_item, resNegativo, itemNoInventario, qtd);
            }else {
                throw new SQLException("Item nao encontrado");
            }
        } catch (SQLException e){
            e.printStackTrace();
            throw e;
        }finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    }
}
