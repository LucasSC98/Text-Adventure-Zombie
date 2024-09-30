package repositorio;

import model.GameState;
import model.Save;

import java.sql.*;

public class SaveDAO {
    private static GameState gameState;

    public static void setGameState(GameState state) {
        gameState = state;
    }

    public static void salvarJogo() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = Mysql.getConnection();

            // Vai Verifica se tem um jogo salvo
            String sqlCheck = "SELECT id_save FROM saves ORDER BY id_save DESC LIMIT 1";
            stmt = conn.prepareStatement(sqlCheck);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Save existente, atualiza o save do jogo
                int idSave = rs.getInt("id_save");
                String sqlUpdate = "UPDATE saves SET id_cena_atual = ? WHERE id_save = ?";
                stmt = conn.prepareStatement(sqlUpdate);
                stmt.setInt(1, gameState.getCenaAtual().getIdCena());
                stmt.setInt(2, idSave);
                stmt.executeUpdate();
            } else {
                // Se não tiver um save, vai criar um
                String sqlInsert = "INSERT INTO saves(id_cena_atual) VALUES (?)";
                stmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, gameState.getCenaAtual().getIdCena());
                stmt.executeUpdate();

                // Obter a chave gerada do save
                generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    Save save = new Save();
                    save.setIdSave(generatedKeys.getInt(1));
                    save.setCenaAtual(gameState.getCenaAtual());
                }
            }
        } finally {
            if (generatedKeys != null) try { generatedKeys.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static Save carregarUltimoJogo() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Save save = null;

        try {
            conn = Mysql.getConnection();
            String sql = "SELECT * FROM saves ORDER BY id_save DESC LIMIT 1"; // Obtém o último save
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                save = new Save();
                save.setIdSave(rs.getInt("id_save"));
                save.setCenaAtual(CenaREPO.findCenaById(rs.getInt("id_cena_atual")));
            }
        } finally {
            // Fechando recursos
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return save;
    }
}
