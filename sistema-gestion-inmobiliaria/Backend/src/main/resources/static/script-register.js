const API_URL = 'http://localhost:8080/api/users/register';

document.getElementById('registerForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const username = document.getElementById('username').value;
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;

  const messageDiv = document.getElementById('message');
  messageDiv.style.display = 'none';

  try {
    const response = await fetch(API_URL, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, email, password }),
    });
    const message = await response.text();

    if (response.ok) {
      messageDiv.textContent = 'Registro exitoso';
      messageDiv.className = 'success';
    } else {
      messageDiv.textContent = message || 'Error al registrar usuario';
      messageDiv.className = 'error';
    }
  } catch (error) {
    messageDiv.textContent = 'Error al conectar con el servidor';
    messageDiv.className = 'error';
  }

  messageDiv.style.display = 'block';
});