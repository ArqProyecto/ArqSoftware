const propertyForm = document.getElementById('propertyForm');
const propertyList = document.getElementById('propertyList');

const apiUrl = 'http://localhost:8080/api/properties';

// Cargar todas las propiedades al inicio
async function loadProperties() {
  try {
    const response = await fetch(apiUrl);
    const properties = await response.json();
    propertyList.innerHTML = '';
    properties.forEach(property => {
      const li = document.createElement('li');
      li.innerHTML = `
        ${property.address} - $${property.price} - ${property.isAvailable ? 'Disponible' : 'No disponible'}
        <button onclick="deleteProperty(${property.id})">Eliminar</button>
        <button onclick="editProperty(${property.id}, '${property.address}', ${property.price}, ${property.isAvailable})">Editar</button>
      `;
      propertyList.appendChild(li);
    });
  } catch (error) {
    alert('Error cargando propiedades');
  }
}

// Guardar o actualizar una propiedad
propertyForm.addEventListener('submit', async (event) => {
  event.preventDefault();
  const id = document.getElementById('propertyId').value;
  const address = document.getElementById('address').value;
  const price = parseFloat(document.getElementById('price').value);
  const isAvailable = document.getElementById('isAvailable').value === 'true';

  const property = { address, price, isAvailable };

  try {
    const response = id 
      ? await fetch(`${apiUrl}/${id}`, { 
          method: 'PUT', 
          headers: { 'Content-Type': 'application/json' }, 
          body: JSON.stringify(property) 
        })
      : await fetch(apiUrl, { 
          method: 'POST', 
          headers: { 'Content-Type': 'application/json' }, 
          body: JSON.stringify(property) 
        });

    if (response.ok) {
      alert(id ? 'Propiedad actualizada' : 'Propiedad creada');
      propertyForm.reset();
      loadProperties();
    } else {
      alert('Error guardando propiedad');
    }
  } catch (error) {
    alert('Error al conectar con el servidor');
  }
});

// Eliminar una propiedad
async function deleteProperty(id) {
  if (!confirm('¿Deseas eliminar esta propiedad?')) return;

  try {
    const response = await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
    if (response.ok) {
      alert('Propiedad eliminada');
      loadProperties();
    } else {
      alert('Error eliminando propiedad');
    }
  } catch (error) {
    alert('Error al conectar con el servidor');
  }
}

// Editar una propiedad
function editProperty(id, address, price, isAvailable) {
  document.getElementById('propertyId').value = id;
  document.getElementById('address').value = address;
  document.getElementById('price').value = price;
  document.getElementById('isAvailable').value = isAvailable.toString();
}

// Inicializar
loadProperties();