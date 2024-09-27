package controller;

import static spark.Spark.*;

import config.ConfiguracaoHtml;
import model.Cena;
import model.GameState;
import model.Item;
import repositorio.CenaRepo;
import repositorio.ItemREPO;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    private GameState gameState = new GameState();

    public GameController() throws SQLException {
    }

    public static void main(String[] args) throws SQLException {
        staticFiles.location("/public");

        ThymeleafTemplateEngine thymeleaf = ConfiguracaoHtml.create();

        GameController controller = new GameController();
        controller.setupRoutes();
    }

    private void setupRoutes() {
        get("/game", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("gameState", gameState);
            model.put("Inventario", mostrarInventario());
            return new ModelAndView(model, "game");
        }, new ThymeleafTemplateEngine());

        post("/game", (req, res) -> {
            String input = req.queryParams("input").toLowerCase();
            try {
                processCommand(input);
            } catch (SQLException e) {
                e.printStackTrace();
                gameState.setMessage("Comando invalido");
            }
            Map<String, Object> model = new HashMap<>();
            model.put("gameState", gameState);
            model.put("inventario", mostrarInventario());
            model.put("items", mostrarInventario());
            return new ThymeleafTemplateEngine().render(new ModelAndView(model, "game"));
        });
    }

    private void processCommand(String input) throws SQLException {
        switch (gameState.getLocation()) {
            case "start":
                handleStart(input);
                break;
            case "casa":
                casaCena1(input);
                break;
            case "comodoCasa":
                casaCena1_1(input);
                break;
            case "casaSaida":
                casaCenaJardim(input);
                break;
            case "casaFundo":
                fundoCasa(input);
                break;
            default:
                gameState.setMessage("Comando não reconhecido.");
        }
    }

    private List<Item> mostrarInventario() {
        List<Item> itens = gameState.getInventario().listarItens();
        System.out.println("Itens no inventário: " + itens);
        return itens;
    }

    private void handleStart(String input) {
        try {
            if (input.contains("start")) {
                gameState.setLocation("casa");
                gameState.carregarCena(1, gameState);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            gameState.setMessage("Erro ao iniciar");
        }
    }

    private void casaCena1(String input) throws SQLException {
        if (input.equalsIgnoreCase("go to comodo")) {
            gameState.setLocation("comodoCasa");
            gameState.carregarCena(2, gameState);
        } else if (input.equalsIgnoreCase("go to jardim")) {
            gameState.setLocation("casaSaida");
            gameState.carregarCena(3, gameState);
        } else if (input.equalsIgnoreCase("go to exit")) {
            gameState.setLocation("casaFundo");
            gameState.carregarCena(4, gameState);
        }
    }

    private void casaCena1_1(String input) throws SQLException {
        switch (input) {
            case "get pa":
                Item pa = ItemREPO.findItemByID(3);
                gameState.getInventario().adicionarItem(pa);
                gameState.setMessage(pa.getDescricao());
                break;
            case "no":
                Item negativepa = ItemREPO.findItemByID(3);
                gameState.setMessage(negativepa.getResNegativo());
                gameState.setLocation("casa");
                break;
            default:
                gameState.setMessage("Comando errado");
        }
        if (input.equalsIgnoreCase("voltar")) {
            gameState.carregarCena(1, gameState);
            gameState.setLocation("casa");
        }
    }

    private void casaCenaJardim(String input) throws SQLException {
        switch (input) {
            case "check container":
                Item fios = ItemREPO.findItemByID(4);
                Item chave = ItemREPO.findItemByID(1);
                gameState.getInventario().adicionarItem(fios);
                gameState.getInventario().adicionarItem(chave);
                gameState.setMessage(chave.getDescricao());
                break;
            case "no":
                Item negativecontainer = ItemREPO.findItemByID(1);
                gameState.setMessage(negativecontainer.getResNegativo());
                break;
            default:
                gameState.setMessage("Comando errado");
        }
        if (input.equalsIgnoreCase("voltar")) {
            gameState.carregarCena(1, gameState);
            gameState.setLocation("casa");
        }
    }

    private void fundoCasa(String input) throws SQLException {
        switch (input) {
            case "check gerador":
                gameState.descricaoNeg(2, gameState);
                break;
            case "check cerca":
                gameState.descricaoNeg(3, gameState);
                break;
            default:
                gameState.setMessage("Comando errado");
        }
        if (input.equalsIgnoreCase("voltar")) {
            gameState.carregarCena(1, gameState);
            gameState.setLocation("casa");
        }

    }

}