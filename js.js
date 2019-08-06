var serverAddress = "http://127.0.0.1:880";

function validaForm() {
    if($('#produto')[0].checkValidity()) {
        for(i=0;i<$('#produto')[0].length;i++) {
            $("label")[i].style.color = "black";
        }
        return true;
    }
    else {
        for(i=0;i<$('#produto')[0].length;i++) {
            if($('#produto')[0][i].checkValidity())
                $("label[for=" + $("#produto")[0][i].getAttribute("id") + "]")[0].style.color = "black";
            else
                $("label[for=" + $("#produto")[0][i].getAttribute("id") + "]")[0].style.color = "red";
        }
        alert("Preencha o formulário corretamente");
        return false;
    }
}

function dadosFormParaJSON() {
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
}

function cadastrar() {
    if(validaForm()) {
        dadosFormParaJSON();
        let path = serverAddress + `/produto/add`;
        console.log(path);
    }
}