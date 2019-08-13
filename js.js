var serverAddress = "http://127.0.0.1:880";

function validaForm() {
    if ($('#produto')[0].checkValidity()) {
        for (i = 0; i < $('#produto')[0].length; i++) {
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
        alert("Preencha o formulário corretamente");
        return false;
    }
}

/*function dadosFormParaJSON() {
    let codigo = $('#produto')[0][0].value;
    let nome = $('#produto')[0][1].value;
    let preco = $('#produto')[0][2].value;
    let descricao = $('#produto')[0][3].value;
    if (descricao == "")
    descricao = null;
    let tipo = $('#produto')[0][4].value;
    let produto = {
        "codigo":codigo,
        "nome":nome,
        "preco":preco,
        "descricao":descricao,
        "tipo":tipo
    };
    produto = JSON.stringify(produto);
    $("div").text(produto);
    return produto;
}

function cadastrar() {
    if(validaForm()) {
        let dadosRequisicao = dadosFormParaJSON();
        console.log(dadosRequisicao);
        let path = serverAddress + `/produto/add`;
        console.log(path);

        var requisicao = new XMLHttpRequest();
        requisicao.open("POST", path, true);
        requisicao.timeout = 0;
        requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        requisicao.ontimeout = function (e) {
            console.log("// XMLHttpRequest timed out.");
        }
        $(".colNome").removeClass("d-none");
        requisicao.send($(`form`).serialize());
//        requisicao.send(dadosRequisicao);
        console.log("Conteudo do Form: \n" + dadosRequisicao);
        $(".colNome").addClass("d-none");

        requisicao.onreadystatechange = function (e) {
            if (requisicao.readyState == 4) {
                if (requisicao.status >= 200) {
                    console.log("XMLHttp OK. \n" + requisicao.response);
                    // sessionStorage.setItem("dadosXMLHTTP", xmlhttp.response);
                    dadosRequisicao = JSON.parse(requisicao.response);
                    if (funcao == "read")
                        mostrarPainel(modulo, dadosRequisicao);
                    else if (funcao == "create" || funcao == "update" || funcao == "delete")
                        alerta(funcao, modulo);
                }
                else {
                    console.error("erro na requisicao. //" + requisicao.statusText);
                }
            }
        }
        console.log("apos o onreadystatechange");

        requisicao.onerror = function (e) {
            console.error(requisicao.statusText);
        }
    }
}
*/
function cadastrar() {
    if (validaForm()) {
        let path = serverAddress + `/produto/add`;
        console.log(path);

        var requisicao = new XMLHttpRequest();
        requisicao.open("POST", path, true);
        requisicao.timeout = 0;
        requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        requisicao.ontimeout = function (e) {
            console.log("// XMLHttpRequest timed out.");
        }
        requisicao.send($(`#produto`).serialize());
        console.log("Conteudo do Form: \n" + $(`#produto`).serialize());

        requisicao.onreadystatechange = function (e) {
            if (requisicao.readyState == 4) {
                if (requisicao.status == 201) {
                    console.log("XMLHttp OK. \n" + requisicao.response);
                    dadosRequisicao = requisicao.response;
                    alert("Produto salvo com sucesso");
                    $(`#produto`)[0].reset();
                }
                else if (requisicao.status == 501) {
                    console.log("XMLHttp OK. Produto já cadastrado\n" + requisicao.response);
                    alert("O código de produto deve ser único");
                    $("label[for=codigoPdt]")[0].style.color = "red";
                }
                else {
                    console.error("erro na requisicao. //" + requisicao.statusText);
                }
            }
        }
        console.log("apos o onreadystatechange");

        requisicao.onerror = function (e) {
            console.error(requisicao.statusText);
        }
    }
}

function listar() {
    let path = serverAddress + `/produtos/listar`;
    console.log(path);

    var requisicao = new XMLHttpRequest();
    requisicao.open("GET", path, true);
    requisicao.timeout = 0;
    //        requisicao.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    requisicao.ontimeout = function (e) {
        console.log("// XMLHttpRequest timed out.");
    }
    requisicao.send();

    requisicao.onreadystatechange = function (e) {
        if (requisicao.readyState == 4) {
            var json = JSON.parse(requisicao.response);
            console.log("resultado da requisição " + requisicao.status);
            if (requisicao.status == 200) {
                if(json.length == 0)
                    alert("Não há produtos cadastrados");
                else {
                    for(i=0; i<json.length; i++) {
                        var tabela = ************* CONTINUA AQUI *****************;
                    }

                    console.log("o tamanho é: " + .length);
                    console.log("XMLHttp OK. \n" + requisicao.response);
                    dadosRequisicao = requisicao.response;
                    alert("Produtos recuperados com sucesso");
                }
            }
            else {
                console.error("erro na requisicao. //" + requisicao.statusText);
            }
        }
    }
    console.log("apos o onreadystatechange");

    requisicao.onerror = function (e) {
        console.error(requisicao.statusText);
    }
}