document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');
    const input = document.querySelector('input[name="input"]');
    const output = document.getElementById('output');
    const helpTexto = document.getElementById('helpTexto');
    const inventarioLista = document.getElementById('inventario-lista');


    addMessageToOutput("O mundo enfrenta uma invasão de mortos-vivos, que começou há duas semanas e tem piorado. Após um ataque quase fatal, eu me abriguei em uma casa abandonada para descansar. No entanto, uma horda de zumbis se aproxima e preciso proteger o local antes do pôr do sol para tentar sobreviver mais um dia. Digite \"start\" para começar o jogo.");

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        const userInput = input.value.trim();
        if (userInput === '') return;

        addMessageToOutput(`Jogador: ${userInput}`);

        fetch('/game', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams({ input: userInput })
        })
            .then(response => response.json())
            .then(data => {
                addMessageToOutput(`Jogo: ${data.message}`);
                mostrarHelp(data.helpTexto);
                atualizarInventario(data.inventario);
                mostrarCenaLocal(data.local); // Cena nome
                input.value = ''; // Limpar campo
            })
            .catch(error => {
                console.error('Erro:', error);
                addMessageToOutput('Erro ao enviar comando.');
            });
    });

    function addMessageToOutput(message) {
        const messageElement = document.createElement('div');
        messageElement.textContent = message;
        output.appendChild(messageElement);
        output.scrollTop = output.scrollHeight;
    }


    function mostrarCenaLocal(meuLocal) {
        const localizacao = document.getElementById('local');
        localizacao.textContent = `Cena Local: ${meuLocal}`;
    }

    function mostrarHelp(helpText){
        helpTexto.innerHTML = '';
        const comandoSeparado = helpText.split(','); // Separar os comando, quando tiver virgula
        comandoSeparado.forEach(comandoSeparado =>{
            const comandoElement = document.createElement('div')
            comandoElement.textContent= comandoSeparado.trim();
            helpTexto.appendChild(comandoElement);
        })
    }
    function atualizarInventario(itens){
        inventarioLista.innerHTML = '';
        itens.forEach(item => {
            const listaItens = document.createElement('li');
            listaItens.textContent = `${item.nome} (Quantidade: ${item.quantidade})`;
            inventarioLista.appendChild(listaItens);
        })
    }
});
