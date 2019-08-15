var serverAddress = "http://127.0.0.1:880";

var json = "";

function validaForm() {
    $("#erros")[0].style.display = "none";
    $("#erros")[0].innerHTML = "";
    if ($('#produto')[0].checkValidity()) {
        for (i = 0; i < $('#produto')[0].length; i++) {
            if ($("label")[i].getAttribute("for") != "codigoUpdate")
                $("label")[i].style.color = "black";
        }
        return true;
    }
    else {
        for (i = 0; i < $('#produto')[0].length; i++) {
            if ($('#produto')[0][i].checkValidity())
                $("label[for=" + $("#produto")[0][i].getAttribute("id") + "]")[0].style.color = "black";
            else
                $("label[for=" + $("#produto")[0][i].getAttribute("id") + "]")[0].style.color = "red";
        }
        alert("Preencha corretamente os campos em vermelho");
        return false;
    }
}

function cadastrar() {
    if (validaForm()) {
        let path = serverAddress + `/produto/add`;

        var requisicao = new XMLHttpRequest();
        requisicao.open("POST", path, true);
        requisicao.timeout = 0;
        requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        requisicao.ontimeout = function (e) { console.log("// XMLHttpRequest timed out."); };
        requisicao.send($(`#produto`).serialize());

        requisicao.onreadystatechange = function (e) {
            if (requisicao.readyState == 4) {
                if (requisicao.status == 201) {
                    alert("Produto salvo com sucesso");
                    $(`#produto`)[0].reset();
                }
                else if (requisicao.status == 400) {
                    var erro = [ "", "", "", "", ""];
                    alert("Preencha corretamente os campos em vermelho");
                    json = JSON.parse(requisicao.response);
                    for (i = 0; i < json.length; i++) {
                        $(`label[for=${json[i]}Pdt]`)[0].style.color = "red";
                        if (json[i] == "codigo")
                            erro[0] = `- O código é obrigatório e seu valor deve ser único para evitar adicionar dois produtos com o mesmo
                             código. Deve ter tamanho máximo de 10 digitos.<br>`;
                        if (json[i] == "nome")
                            erro[1] = `- O nome é obrigatório com tamanho máximo de 15 caracteres.<br>`;
                        if (json[i] == "preco")
                            erro[2] = `- O preco é obrigatório e deve ser maior que 0 (zero).<br>`;
                        if (json[i] == "descricao")
                            erro[3] = `- A descrição deve ter tamanho máximo de 30 caracteres.<br>`;
                        if(json[i] == "tipo")
                            erro[4] = `- O tipo é obrigatório. As opções aceitas são alimentos (A), bebidas (B), sobremesas (S) e entradas (E)`;
                    }
                    for(i=0; i<erro.length; i++)
                        $("#erros")[0].innerHTML += erro[i];
                    $("#erros")[0].style.display = "";
                }
                else if (requisicao.status == 501) {
                    alert("O código de produto deve ser único");
                    $("label[for=codigoPdt]")[0].style.color = "red";
                }
                else {
                    console.error("erro na requisicao. //" + requisicao.statusText);
                }
            }
        }

        requisicao.onerror = function (e) {
            console.error(requisicao.statusText);
        }
    }
}

function listar(opcao) {
    let path = serverAddress + `/produtos/listar`;

    var requisicao = new XMLHttpRequest();
    requisicao.open("GET", path, true);
    requisicao.timeout = 0;
    requisicao.ontimeout = function (e) { console.log("// XMLHttpRequest timed out."); };
    requisicao.send();

    requisicao.onreadystatechange = function (e) {
        if (requisicao.readyState == 4) {
            json = JSON.parse(requisicao.response);
            if (requisicao.status == 200) {
                var tabela = "";
                if (json.length == 0) {
                    $("#tabProdutos")[0].innerHTML = "";
                    alert("Não há produtos cadastrados\nVocê será redirecionado para a página de cadastro");
                    window.location.href = "index.html";
                }
                else {
                    for (i = 0; i < json.length; i++) {
                        tabela += `<tr>
                            <td>${json[i].NOME}</td>
                            <td class="colunaPreco">${parseFloat(json[i].PRECO).toFixed(2)}</td>
                            <td>${json[i].DESCRICAO}</td>
                            <td class="center">
                                <span onclick="alterar(${i})" style="cursor:pointer;">&#9997;</span>
                                <span onclick="deletar(${json[i].CODIGO})" style="cursor:pointer;">&#x1f5d1;</span>
                            </td></tr>`;
                    }
                    $("#tabProdutos")[0].innerHTML = tabela;
                    if (opcao == 0)
                        alert("Produtos recuperados com sucesso");
                }
            }
            else {
                console.error("erro na requisicao. //" + requisicao.statusText);
            }
        }
    }

    requisicao.onerror = function (e) {
        console.error(requisicao.statusText);
    }
}

function alterar(index) {
    deletaLinha();
    var novaLinha2 = $("#tabProdutos")[0].insertRow(index + 1);
    var novaLinha1 = $("#tabProdutos")[0].insertRow(index + 1);
    if ((index) % 2 == 0) {
        novaLinha1.setAttribute("id", "linhaAlteracao");
        novaLinha2.setAttribute("id", "vazia");
        novaLinha1.style.backgroundColor = "rgba(0,0,0,0.05)";
    }
    else {
        novaLinha2.setAttribute("id", "linhaAlteracao");
        novaLinha1.setAttribute("id", "vazia");
    }
    var celula = $("#linhaAlteracao")[0].insertCell(0);
    celula.setAttribute("id", "celulaConteudo");
    celula.setAttribute("colspan", "4");
    $("#linhaAlteracao")[0].firstChild.innerHTML = `<div id="alteraForm" style="display: none">
        <form id="produto" action="">
        </form>
        <button onclick="enviaAlteracao()" class="btn btn-primary">Alterar</button>
        <button onclick="deletaLinha()" class="btn btn-danger">Cancelar</button>
        <div id="erros" style="display:none">
        </div>
        </div>`;
    var formulario = `<label for="codigoUpdate" style="color: gray;font-style: italic">Código</label>
        <input name="codigo" id="codigoUpdate" type="number" min="1" max="9999999999" required readonly style="color: gray;font-style: italic"><br>
        <label for="nomeUpdate">Nome</label>
        <input name="nome" id="nomeUpdate" type="text" maxlength="15" required><br>
        <label for="precoUpdate">Preço (R$)</label>
        <input name="preco" id="precoUpdate" type="number" min="0.01" step="0.01" required><br>
        <label for="descricaoUpdate">Descrição</label>
        <input name="descricao" id="descricaoUpdate" type="text" maxlength="30"><br>
        <label for="tipoUpdate">Tipo</label>
        <select name="tipo" id="tipoUpdate" required>
            <option></option>
            <option value="A">A - alimentos</option>
            <option value="B">B - bebidas</option>
            <option value="S">S - sobremesas</option>
            <option value="E">E - entradas</option>
        </select>`;
    $("#produto")[0].innerHTML = formulario;
    $("#alteraForm")[0].style.display = "";
    var form = $("#produto")[0];
    form[0].value = json[index].CODIGO;
    form[1].value = json[index].NOME;
    form[2].value = json[index].PRECO;
    form[3].value = json[index].DESCRICAO;
    form[4].value = json[index].TIPO;
}

function deletaLinha() {
    if ($("#linhaAlteracao")[0]) {
        $("#principal")[0].deleteRow($("#linhaAlteracao")[0].rowIndex);
        $("#principal")[0].deleteRow($("#vazia")[0].rowIndex);
    }
}

function enviaAlteracao() {
    if (validaForm()) {
        let path = serverAddress + `/produto/update`;

        var requisicao = new XMLHttpRequest();
        requisicao.open("POST", path, true);
        requisicao.timeout = 0;
        requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        requisicao.ontimeout = function (e) { console.log("// XMLHttpRequest timed out."); }
        requisicao.send($(`#produto`).serialize());

        requisicao.onreadystatechange = function (e) {
            if (requisicao.readyState == 4) {
                if (requisicao.status == 200) {
                    alert("Produto atualizado com sucesso");
                    listar(1);
                }
                else if (requisicao.status == 400) {
                    var erro = [ "", "", "", "", ""];
                    alert("Preencha corretamente os campos em vermelho");
                    json = JSON.parse(requisicao.response);
                    for (i = 0; i < json.length; i++) {
                        $(`label[for=${json[i]}Update]`)[0].style.color = "red";
                        if (json[i] == "codigo")
                            erro[0] = `- O código é obrigatório e seu valor deve ser único para evitar adicionar dois produtos com o mesmo
                             código. Deve ter tamanho máximo de 10 digitos.<br>`;
                        if (json[i] == "nome")
                            erro[1] = `- O nome é obrigatório com tamanho máximo de 15 caracteres.<br>`;
                        if (json[i] == "preco")
                            erro[2] = `- O preco é obrigatório e deve ser maior que 0 (zero).<br>`;
                        if (json[i] == "descricao")
                            erro[3] = `- A descrição deve ter tamanho máximo de 30 caracteres.<br>`;
                        if(json[i] == "tipo")
                            erro[4] = `- O tipo é obrigatório. As opções aceitas são alimentos (A), bebidas (B), sobremesas (S) e entradas (E)`;
                    }
                    for(i=0; i<erro.length; i++)
                        $("#erros")[0].innerHTML += erro[i];
                    $("#erros")[0].style.display = "";
                }
                else if (requisicao.status == 404) {
                    alert("Produto não existe");
                    $("label[for=codigoUpdate]")[0].style.color = "red";
                }
                else {
                    console.error("erro na requisicao. //" + requisicao.statusText);
                }
            }
        }

        requisicao.onerror = function (e) {
            console.error(requisicao.statusText);
        }
    }
}

function deletar(codigo) {
    let path = serverAddress + `/produto/delete`;

    var requisicao = new XMLHttpRequest();
    requisicao.open("POST", path, true);
    requisicao.timeout = 0;
    requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    requisicao.ontimeout = function (e) { console.log("// XMLHttpRequest timed out."); };
    requisicao.send(codigo);

    requisicao.onreadystatechange = function (e) {
        if (requisicao.readyState == 4) {
            if (requisicao.status == 200) {
                alert("Produto removido com sucesso");
                listar(1);
            }
            else {
                console.error("erro na requisicao. //" + requisicao.statusText);
            }
        }
    }

    requisicao.onerror = function (e) {
        console.error(requisicao.statusText);
    }
}