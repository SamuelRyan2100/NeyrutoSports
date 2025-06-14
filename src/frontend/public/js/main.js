async function carregarLogin() {
  const response = await fetch('../../components/login.html');
  const html = await response.text();
  document.getElementById('app').innerHTML = html;
}

carregarLogin(); // Carrega a tela de login ao iniciar
