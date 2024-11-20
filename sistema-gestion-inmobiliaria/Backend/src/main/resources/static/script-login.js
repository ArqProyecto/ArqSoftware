const loginForm = document.getElementById('loginForm');
const messageDiv = document.getElementById('message');

loginForm.addEventListener('submit', async (event) => {
  event.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;

  messageDiv.style.display = 'none';

  try {
    const response = await fetch('http://localhost:8080/api/users/login?username=' + username + '&password=' + password, {
      method: 'POST',
    });

    if (response.ok) {
      messageDiv.textContent = 'Inicio de sesión exitoso. Redirigiendo...';
      messageDiv.className = 'success';
      messageDiv.style.display = 'block';
      setTimeout(() => {
        window.location.href = 'home.html';
      }, 2000);
    } else {
      const errorMessage = await response.text();
      messageDiv.textContent = errorMessage || 'Usuario o contraseña incorrectos';
      messageDiv.className = 'error';
      messageDiv.style.display = 'block';
    }
  } catch (error) {
    console.error('Error al iniciar sesión:', error);
    messageDiv.textContent = 'Hubo un problema al conectar con el servidor.';
    messageDiv.className = 'error';
    messageDiv.style.display = 'block';
  }
});