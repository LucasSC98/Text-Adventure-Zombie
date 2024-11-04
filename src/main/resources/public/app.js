document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const input = document.querySelector('input[name="input"]');
    const output = document.getElementById('output');
    const helpTexto = document.getElementById('helpTexto');
    const inventarioLista = document.getElementById('inventario-lista');
    const inventario = document.getElementById('inventory');
    const ajuda = document.getElementById('help');

    //Primeira msg a aparecer, sem comando
    addMessageToOutput("O mundo enfrenta uma invasão de mortos-vivos, que começou há duas semanas e tem piorado. Após um ataque quase fatal, " +
        "eu me abriguei em uma casa abandonada para descansar. No entanto, " +
        "uma horda de zumbis se aproxima e preciso encontrar comida antes que o sol se ponha. " +
        "<br>Start - Começar jogo <br> Load - Carregar Save <br>");

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        const userInput = input.value.trim().toLowerCase();
        if (userInput === '') return;

        addMessageToOutput(`Jogador: ${userInput}`);
        if (userInput === 'inventory') {
            inventario.style.display = 'block';
            input.value = '';
        } else if (userInput === 'close inventory') {
            inventario.style.display = 'none';
            input.value = '';
        } else if (userInput === 'help') {
            ajuda.style.display = 'block';
            input.value = '';
        } else if (userInput === 'close help') {
            ajuda.style.display = 'none';
            input.value = '';
        } else {
            fetch('/game', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({ input: userInput })
            })
                .then(response => {
                    console.log("Resposta do servidor:", response);
                    return response.json();
                })
                .then(data => {
                    addMessageToOutput(`Jogo: ${data.message}`);
                    mostrarHelp(data.helpTexto);
                    atualizarInventario(data.inventario);
                    mostrarCenaLocal(data.local); // Cena nome
                    input.value = ''; // Limpar campo
                })
                .catch(error => {
                    console.error('Erro:', error);
                    addMessageToOutput('Comando Inválido, tente outro');
                    input.value = '';
                });
        }
    });

    async function addMessageToOutput(message) {
        console.log("Mensagem recebida:", message);
        const messageElement = document.createElement('div');
        output.appendChild(messageElement);

        const parts = message.split('<br>').map(part => part.trim());

        for (const part of parts) {
            if (part) {
                for (const char of part) {
                    messageElement.innerHTML += char;
                    await new Promise(resolve => setTimeout(resolve, 3)); // 3 ms entre cada caractere
                }
                messageElement.innerHTML += '<br>'; // Adiciona quebra de linha
            }
        }
        output.scrollTop = output.scrollHeight; // Rola para o final
    }

    function mostrarCenaLocal(meuLocal) {
        const localizacao = document.getElementById('local');
        localizacao.textContent = `Cena Local: ${meuLocal}`;
    }

    function mostrarHelp(helpText) {
        helpTexto.innerHTML = '';
        const comandoSeparado = helpText.split(','); // Separar os comandos quando tiver vírgula
        comandoSeparado.forEach(comando => {
            const comandoElement = document.createElement('div');
            comandoElement.textContent = comando.trim().toUpperCase();
            helpTexto.appendChild(comandoElement);
        });
    }

    function atualizarInventario(itens) {
        inventarioLista.innerHTML = '';
        itens.forEach(item => {
            const listaItens = document.createElement('li');
            listaItens.textContent = `${item.nome}`;
            inventarioLista.appendChild(listaItens);
        });
    }
});
