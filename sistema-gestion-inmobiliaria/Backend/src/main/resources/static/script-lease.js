// Botón: Ver todos los arrendamientos
document.getElementById('viewLeasesBtn').addEventListener('click', async () => {
  try {
    const response = await fetch('http://localhost:8080/api/leases');
    if (!response.ok) throw new Error('Error al obtener los arrendamientos.');
    
    const leases = await response.json();
    const leasesContainer = document.getElementById('allLeases');
    leasesContainer.innerHTML = ''; // Limpiar contenido previo

    leases.forEach((lease) => {
      const leaseElement = document.createElement('pre');
      leaseElement.textContent = JSON.stringify(lease, null, 2);
      leasesContainer.appendChild(leaseElement);
    });
  } catch (error) {
    document.getElementById('allLeases').textContent = 'Error al obtener los arrendamientos.';
    console.error(error);
  }
});

// Botón: Buscar arrendamiento por ID
document.getElementById('searchLeaseBtn').addEventListener('click', async () => {
  const leaseId = document.getElementById('leaseId').value;
  if (!leaseId) return alert('Por favor, ingrese un ID válido.');
  
  try {
    const response = await fetch(`http://localhost:8080/api/leases/${leaseId}`);
    if (!response.ok) throw new Error('Arrendamiento no encontrado.');

    const lease = await response.json();
    document.getElementById('leaseDetails').textContent = JSON.stringify(lease, null, 2);
  } catch (error) {
    document.getElementById('leaseDetails').textContent = 'No se encontró el arrendamiento.';
    console.error(error);
  }
});

// Botón: Crear nuevo arrendamiento
document.getElementById('createLeaseBtn').addEventListener('click', async () => {
  const tenantId = document.getElementById('tenantId').value;
  const propertyId = document.getElementById('propertyId').value;
  const startDate = document.getElementById('startDate').value;
  const endDate = document.getElementById('endDate').value;
  const rentAmount = document.getElementById('rentAmount').value;

  if (!tenantId || !propertyId || !startDate || !endDate || !rentAmount) {
    return alert('Por favor, complete todos los campos.');
  }

  try {
    const response = await fetch('http://localhost:8080/api/leases', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        tenant: { id: tenantId },
        property: { id: propertyId },
        startDate,
        endDate,
        rentAmount,
      }),
    });

    if (!response.ok) throw new Error('Error al crear el arrendamiento.');

    const newLease = await response.json();
    document.getElementById('createLeaseResponse').textContent = `Arrendamiento creado: ${JSON.stringify(newLease, null, 2)}`;
  } catch (error) {
    document.getElementById('createLeaseResponse').textContent = 'Error al crear el arrendamiento.';
    console.error(error);
  }
});

// Botón: Eliminar arrendamiento por ID
document.getElementById('deleteLeaseBtn').addEventListener('click', async () => {
  const leaseId = document.getElementById('deleteLeaseId').value;
  if (!leaseId) return alert('Por favor, ingrese un ID válido.');

  try {
    const response = await fetch(`http://localhost:8080/api/leases/${leaseId}`, { method: 'DELETE' });
    document.getElementById('deleteLeaseResponse').textContent = response.ok
      ? 'Arrendamiento eliminado con éxito.'
      : 'No se pudo eliminar el arrendamiento.';
  } catch (error) {
    document.getElementById('deleteLeaseResponse').textContent = 'Error al eliminar el arrendamiento.';
    console.error(error);
  }
});