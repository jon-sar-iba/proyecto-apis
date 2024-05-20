import React, { useState } from "react";
import '../hoja-de-estilos/Inicio.css';

function Inicio() {
  const [archivoSeleccionado, setArchivoSeleccionado] = useState(null);
  const [mensaje, setMensaje] = useState(null);

  const handleArchivoSeleccionado = (event) => {
    const archivo = event.target.files[0];
    setArchivoSeleccionado(archivo);
  };

  const enviarArchivo = async () => {
    if (!archivoSeleccionado) {
      return;
    }

    const formData = new FormData();
    formData.append('file', archivoSeleccionado);

    try {
      const response = await fetch('http://localhost:8080/api/file/load', {
        method: 'POST',
        body: formData
      });

      if (response.ok) {
        const data = await response.text();
        alert('El archivo se envio correctamente. ');
        //setMensaje("El archivo se envió correctamente.");
      } else {
        alert('Error al enviar archivo: ' + response.statusText);
        //setMensaje("Hubo un error al enviar el archivo.");
      }
    } catch (error) {
      alert('Error al enviar archivo: ' + error);
      //setMensaje("Hubo un error al enviar el archivo.");
    }
  };

  return (
    <div className="container-load-file-principal">
      <h1>Procesamiento ETL</h1>
      <div className="container-load-file">
        <div className="contenedor-h2">
          <h2>Procesar archivo</h2>
        </div>
        <div className="procesar-archivo">
          <input type="file" accept=".txt" onChange={handleArchivoSeleccionado} />
        </div>
        <div className="container-boton">
          {archivoSeleccionado && <button className="boton-iniciar-etl" onClick={enviarArchivo}>Iniciar proceso ETL</button>}
        </div>
        {mensaje && <p>{mensaje}</p>}
      </div>
    </div>
  );
}

export default Inicio;
