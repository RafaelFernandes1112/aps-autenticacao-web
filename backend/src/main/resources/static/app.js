const telaLogin = document.getElementById("tela-login");
const telaOk = document.getElementById("tela-ok");
const form = document.getElementById("form-login");
const btn = document.getElementById("btn");
const btnVer = document.getElementById("btn-ver");
const btnSair = document.getElementById("btn-sair");
const msg = document.getElementById("mensagem");
const boasVindas = document.getElementById("boas-vindas");

function mostrarErro(texto) {
  msg.textContent = texto;
  msg.className = "erro";
  msg.hidden = false;
}

// mostrar / ocultar a senha
btnVer.addEventListener("click", () => {
  const oculta = form.senha.type === "password";
  form.senha.type = oculta ? "text" : "password";
  btnVer.textContent = oculta ? "Ocultar" : "Mostrar";
});

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const identificador = form.identificador.value.trim();
  const senha = form.senha.value;

  if (!identificador || !senha) {
    mostrarErro("Preencha o login (ou e-mail) e a senha.");
    return;
  }

  btn.disabled = true;
  msg.hidden = true;

  try {
    const resp = await fetch("/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ identificador, senha }),
    });
    const dados = await resp.json();

    if (resp.ok) {
      boasVindas.textContent = `Olá, ${dados.nome} (id ${dados.id}).`;
      telaLogin.hidden = true;
      telaOk.hidden = false;
    } else {
      mostrarErro(dados.erro || "Erro ao autenticar.");
    }
  } catch (err) {
    mostrarErro("Não foi possível falar com a API. Ela está rodando?");
  } finally {
    btn.disabled = false;
  }
});

// "logout": só volta pro início com tudo limpo
btnSair.addEventListener("click", () => {
  form.reset();
  form.senha.type = "password";
  btnVer.textContent = "Mostrar";
  msg.hidden = true;
  telaOk.hidden = true;
  telaLogin.hidden = false;
  form.identificador.focus();
});
