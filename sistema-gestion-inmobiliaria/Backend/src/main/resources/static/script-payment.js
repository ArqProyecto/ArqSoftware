// Procesar Pago
document.getElementById("processPaymentForm").addEventListener("submit", function (e) {
    e.preventDefault();
    
    const userId = document.getElementById("userId").value;
    const amount = document.getElementById("amount").value;
    const paymentMethod = document.getElementById("paymentMethod").value;
    
    fetch("http://localhost:8080/api/payments/process", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            userId: parseInt(userId),
            amount: parseFloat(amount),
            paymentMethod: paymentMethod
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        document.getElementById("processPaymentResult").innerHTML = `
            <p>Pago procesado exitosamente:</p>
            <ul>
                <li>ID del Pago: ${data.id}</li>
                <li>Monto: ${data.amount}</li>
                <li>Método: ${data.paymentMethod}</li>
                <li>Estado: ${data.status}</li>
            </ul>
        `;
    })
    .catch(error => {
        document.getElementById("processPaymentResult").innerHTML = `
            <p>Error al procesar el pago: ${error.message}</p>
        `;
    });
});

// Obtener Pago por ID
document.getElementById("getPaymentForm").addEventListener("submit", function (e) {
    e.preventDefault();
    
    const paymentId = document.getElementById("paymentId").value;
    
    fetch(`http://localhost:8080/api/payments/${paymentId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            document.getElementById("getPaymentResult").innerHTML = `
                <p>Pago encontrado:</p>
                <ul>
                    <li>ID del Pago: ${data.id}</li>
                    <li>Monto: ${data.amount}</li>
                    <li>Método: ${data.paymentMethod}</li>
                    <li>Estado: ${data.status}</li>
                </ul>
            `;
        })
        .catch(error => {
            document.getElementById("getPaymentResult").innerHTML = `
                <p>Error al buscar el pago: ${error.message}</p>
            `;
        });
});
