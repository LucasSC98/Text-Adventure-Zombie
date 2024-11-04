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

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/textadventure", "root", "");

            int idSave = 1;

            String sql = "INSERT INTO saves (id_save, id_cena_atual, localizacao, visitou_local) VALUES (?, ?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE id_cena_atual = VALUES(id_cena_atual), " +
                    "localizacao = VALUES(localizacao), visitou_local = VALUES(visitou_local)";

            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idSave); // Id_save fixo
            stmt.setInt(2, gameState.getCenaAtual().getIdCena());
            stmt.setString(3, gameState.getLocation());
            stmt.setBoolean(4, gameState.isVisitouLocal());

            stmt.executeUpdate();

        } catch (SQLException e) {
            gameState.setMessage("Erro no save:" + e.getMessage());
            throw e;
        }
    }

    public static Save carregarUltimoJogo() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Save save = null;

        try {
            conn = Mysql.getConnection();
            String sql = "SELECT * FROM saves ORDER BY id_save DESC"; // Obtém o último save, já que tem só 1
            assert conn != null;
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                save = new Save();
                save.setIdSave(rs.getInt("id_save"));
                save.setCenaAtual(CenaDAO.findCenaById(rs.getInt("id_cena_atual")));
                save.setLocalizacao(rs.getString("localizacao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
        return save;
    }
}