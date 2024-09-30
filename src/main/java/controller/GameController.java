package controller;

import static spark.Spark.*;

import config.ConfiguracaoHtml;
import model.Acoes;
import model.GameState;
import model.Item;
import model.Save;
import repositorio.AcoesDAO;
import repositorio.InventarioDAO;
import repositorio.ItemDAO;
import repositorio.SaveDAO;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import com.google.gson.Gson;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameState gameState = new GameState();
    private Gson gson = new Gson();

    public GameController() {

    }

    public static void main(String[] args) {
        staticFiles.location("/public");
        ThymeleafTemplateEngine thymeleaf = ConfiguracaoHtml.create();
        GameController controller = new GameController();
        controller.setupRoutes();
    }

    private void setupRoutes() {
        get("/game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("gameState", gameState);
            model.put("inventario", mostrarInventario());
            model.put("helpTexto", gameState.getHelp());
            model.put("local", gameState.getLocation());
            return new ModelAndView(model, "game");
        }, new ThymeleafTemplateEngine());

        post("/game", (req, res) -> {
            res.type("application/json");
            String input = req.queryParams("input").toLowerCase();
            processCommand(input);
            Map<String, Object> model = new HashMap<>();
            model.put("message", gameState.getMessage());
            model.put("inventario", mostrarInventario());
            model.put("helpTexto", gameState.getHelp());
            model.put("local", gameState.getLocation());

            return gson.toJson(model);
        });
    }

    private void processCommand(String input) {
        try {
            if (input.equals("restart")) {
                handleRestart();
            } else if (input.equals("save")) {
                SaveDAO.setGameState(gameState);
                SaveDAO.salvarJogo();
                gameState.setMessage("Jogo salvo com sucesso.");
            } else {
                if (gameState.getLocation().equals("start")) {
                    handleStart(input);
                } else {
                    locationsProcess(input);
                }
            }
        } catch (SQLException e) {
            gameState.setMessage("Erro ao salvar o jogo: " + e.getMessage());
        } catch (Exception e) {
            gameState.setMessage("Erro ao processar o comando: " + e.getMessage());
        }
    }

    private void locationsProcess(String input) throws SQLException {
        switch (gameState.getLocation()) {
            case "casa":
                casaCena1(input);
                break;
            case "poraoCasa":
                casaCena1_1(input);
                break;
            case "casaSaida":
                casaCenaJardim(input);
                break;
            case "casaFundo":
                fundoCasa(input);
                break;
            case "quarto":
                quartoCena(input);
                break;
            default:
                gameState.setMessage("Local não reconhecido.");
        }
    }

    private List<Item> mostrarInventario() {
        return gameState.getInventario().listarItens();
    }

    private void handleStart(String input) {
        try {
            if (input.contains("start")) {
                InventarioDAO.limparInventario(gameState.getIdSave());
                gameState.setLocation("casa");
                gameState.carregarCena(1);
            } else if (input.equalsIgnoreCase("load")) {
                Save save = SaveDAO.carregarUltimoJogo();
                gameState.setIdSave(save.getIdSave());
                gameState.carregarCena(save.getCenaAtual().getIdCena());
            } else {
                handleStart(input);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            gameState.setMessage("Erro ao iniciar");
        }
    }

    private void handleRestart() {
        try {
            InventarioDAO.limparInventario(gameState.getIdSave());
            gameState = new GameState();
            gameState.setLocation("casa");
            gameState.carregarCena(1);
        } catch (SQLException e) {
            e.printStackTrace();
            gameState.setMessage("Erro ao reiniciar o jogo.");
        }
    }


    private void casaCena1(String input) throws SQLException {
        boolean visitouPorao = gameState.isVisitouLocal();
        if (input.equalsIgnoreCase("go to porao")) {
            gameState.setLocation("poraoCasa");
            gameState.carregarCena(2);
            gameState.setVisitouLocal(true);
        } else if (input.equalsIgnoreCase("go to jardim")) {
            if (visitouPorao) {
                gameState.setLocation("casaSaida");
                gameState.carregarCena(4);
            } else {
                gameState.setMessage("Acho melhor você visitar o porão primeiro");
            }
        }else{
            gameState.setMessage("Comando Invalido no local");
        }
    }
    private void casaCena1_1(String input) throws SQLException {
        Item lanterna = ItemDAO.findItemByID(3);
        Item pilha = ItemDAO.findItemByID(2);
        Item cartucho = ItemDAO.findItemByID(5);
        Item lanternaComPilhas = ItemDAO.findItemByID(4);
        switch (input) {
            case "get lanterna":
                if (gameState.getInventario().itemJaPegado(lanterna)) {
                    gameState.setMessage(lanterna.getPego());
                } else {
                    gameState.getInventario().adicionarItem(lanterna, 1);
                    gameState.setMessage(lanterna.getDescricao());
                }
                break;
            case "get pilhas":
                if (gameState.getInventario().itemJaPegado(pilha)) {
                    gameState.setMessage(pilha.getPego());
                } else {
                    gameState.getInventario().adicionarItem(pilha, 1);
                    gameState.setMessage(pilha.getDescricao());
                }
                break;
            case "use pilha with lanterna":
                Acoes pilhaLanterna = AcoesDAO.findAcaoById(1);
                boolean tenhoPilha = gameState.getInventario().itemJaPegado(pilha);
                boolean tenhoLanterna = gameState.getInventario().itemJaPegado(lanterna);

                if (tenhoPilha && tenhoLanterna) {
                    gameState.setMessage(pilhaLanterna.getDescricaoComb());
                    gameState.getInventario().removerItem(lanterna, 1);
                    gameState.getInventario().removerItem(pilha, 1);
                    gameState.getInventario().adicionarItem(lanternaComPilhas, 1);
                } else {
                    gameState.setMessage(pilhaLanterna.getDescricao_negativa());
                }
                break;
            case "explorar":
                gameState.carregarCena(3);
                break;
            case "get cartucho":
                if (gameState.getInventario().itemJaPegado(cartucho)) {
                    gameState.setMessage(cartucho.getPego());
                } else {
                    gameState.getInventario().adicionarItem(cartucho, 1);
                    gameState.setMessage(cartucho.getDescricao());
                }
                break;
            default:
                gameState.setMessage("Comando errado");
        }
        if (input.equalsIgnoreCase("voltar")) {
            gameState.carregarCena(1);
            gameState.setLocation("casa");
        }
    }

    private void casaCenaJardim(String input) throws SQLException {
        switch (input) {
            case "check container":
                Item chave = ItemDAO.findItemByID(1);
                gameState.getInventario().adicionarItem(chave, 1);
                gameState.setMessage(chave.getDescricao());
                break;
        }
        if (input.equalsIgnoreCase("voltar")) {
            gameState.carregarCena(5);
            gameState.setLocation("quarto");
        }
    }

    private void quartoCena(String input) throws SQLException {
        Item revolver = ItemDAO.findItemByID(6);
        Item cartucho = ItemDAO.findItemByID(5);
        Item revolverCarregado = ItemDAO.findItemByID(7);
        Item chave = ItemDAO.findItemByID(1);
        boolean tenhoChave = gameState.getInventario().itemJaPegado(chave);
        boolean tenhoCartucho = gameState.getInventario().itemJaPegado(cartucho);
        boolean tenhoRevolver = gameState.getInventario().itemJaPegado(revolver);
        boolean revolverCombala = gameState.getInventario().itemJaPegado(revolverCarregado);
        switch (input) {
            case "use key":
                if (tenhoChave) {
                    gameState.carregarCena(6);
                    gameState.getInventario().removerItem(chave, 1);
                } else {
                    gameState.setMessage("Preciso da chave");
                }
                break;
            case "get revolver":
                gameState.getInventario().adicionarItem(revolver, 1);
                gameState.setMessage(revolver.getDescricao());
                break;
            case "use cartucho with revolver":
                Acoes equiparArma = AcoesDAO.findAcaoById(2);

                if (tenhoCartucho && tenhoRevolver) {
                    gameState.setMessage(equiparArma.getDescricao());
                    gameState.getInventario().removerItem(revolver, 1);
                    gameState.getInventario().removerItem(cartucho, 1);
                    gameState.getInventario().adicionarItem(revolverCarregado, 1);
                } else {
                    gameState.setMessage(equiparArma.getDescricao_negativa());
                }
                break;
            case "check janela":
                if(revolverCombala){
                    gameState.carregarCena(7);
                }else{
                    gameState.setMessage("Melhor carregar a arma antes");
                }
                break;
            case "correr":
                gameState.carregarCena(8);
                break;
            case "go to exit":
                gameState.carregarCena(9);
                gameState.setLocation("casaFundo");
        }
    }

    private void fundoCasa(String input) throws SQLException {
        Item revolverCarregado = ItemDAO.findItemByID(7);
        boolean tenhoRevolver = gameState.getInventario().itemJaPegado(revolverCarregado);
        switch (input) {
            case "lutar":
                if (tenhoRevolver) {
                    gameState.carregarCena(11);
                    gameState.getInventario().removerItem(revolverCarregado, 1);
                }else{
                    gameState.setMessage("Eu não tenho arma para lutar");
                }
                break;
            case "fugir":
                gameState.carregarCena(10);
                break;
            default:
                gameState.setMessage("Comando errado");
        }
    }
}
