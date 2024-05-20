import React, { useEffect, useState } from 'react';
import '../hoja-de-estilos/Actualizar.css';
import $ from 'jquery'; // Asegúrate de tener jQuery instalado

function Actualizar({ tipoActualiza }) {
  const [productos, setProductos] = useState([]);
  const [error, setError] = useState(null);
  const [isClaveSeleccionada, setIsClaveSeleccionada] = useState(false);
  const [actualizacionExitosa, setActualizacionExitosa] = useState(false);
  const [formData, setFormData] = useState({
    intIdProducto: '',
    cvNombre: '',
    decPrecioUnitario: '',
    intAntiguedadXMes: ''
  });

  useEffect(() => {
    cargarClaves();
  }, []); // Esto se ejecutará una vez al cargar el componente

  function cargarClaves() {
    // Realizar la solicitud AJAX para obtener los productos
    $.ajax({
      url: 'http://localhost:8080/api/listarProductos', // Cambia la URL según tu endpoint
      method: 'GET',
      success: function(data) {
        // Verificar si la respuesta contiene productos
        if (Array.isArray(data)) {
          setProductos(data);
        } else {
          setError('La respuesta del servidor no contiene productos.');
        }
      },
      error: function(xhr, status, error) {
        setError('Error al cargar los productos: ' + error);
      }
    });
  }

  function consultarDatos(idProducto) {
    // Realizar la solicitud AJAX para obtener los datos del producto
    $.ajax({
      url: `http://localhost:8080/api/buscarProducto/${idProducto}`, // URL para obtener los datos del producto por su ID
      method: 'GET',
      success: function(data) {
        // Manejar la respuesta de la solicitud AJAX
        console.log('Datos del producto:', data);
        // Actualizar el estado del formulario con los datos recibidos
        setFormData(data);
        setIsClaveSeleccionada(true);
      },
      error: function(xhr, status, error) {
        console.error('Error al consultar los datos del producto:', error);
      }
    });
  }

  function handleChange(event) {
    const { name, value } = event.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  }

  function handleSubmit(event) {
    event.preventDefault(); // Previene el comportamiento por defecto del formulario
    // Realizar la solicitud AJAX para actualizar los datos del producto
    $.ajax({
      url: `http://localhost:8080/api/actualizar${tipoActualiza}`, // URL para actualizar el producto
      method: 'PUT',
      contentType: 'application/json',
      data: JSON.stringify(formData), // Enviar los datos del formulario en formato JSON
      success: function(response) {
        console.log('Producto actualizado:', response);
        setActualizacionExitosa(true);
        // Realizar acciones adicionales después de la actualización exitosa
      },
      error: function(xhr, status, error) {
        console.error('Error al actualizar el producto:', error);
      }
    });
  }

  return (
    <div className='consulta-actualiza-principal'>
      <h2>Actualizar - {tipoActualiza}</h2>
      <div className='contenedor-div'>
        <div className='izquierdo-actualiza'>
          <div className='claves-actualiza'>
            <h3>Productos existentes:</h3>
            <div className="lista-scroll">
              <ul>
                {productos.length > 0 ? (
                  productos.map(producto => (
                    <li key={producto.intIdProducto} onClick={() => consultarDatos(producto.intIdProducto)}>Clave: {producto.intIdProducto}</li>
                  ))
                ) : (
                  <li>No se encontraron productos.</li>
                )}
              </ul>
            </div>
          </div>
        </div>
        <form className='derecho-actualiza' id="registerForm" onSubmit={handleSubmit}>
          <>
            <div className="form-group-actualiza">
              <label htmlFor="intIdProducto">Clave:</label>
              <input
                type="text"
                id="intIdProducto"
                name="intIdProducto"
                value={formData.intIdProducto}
                onChange={handleChange}
                readOnly
                disabled={!isClaveSeleccionada}
                required
              />
            </div>
            <div className="form-group-actualiza">
              <label htmlFor="cvNombre">Nombre:</label>
              <input
                type="text"
                id="cvNombre"
                name="cvNombre"
                value={formData.cvNombre}
                onChange={handleChange}
                required
                disabled={!isClaveSeleccionada}
              />
            </div>
            <div className="form-group-actualiza">
              <label htmlFor="decPrecioUnitario">Precio:</label>
              <input
                type="text"
                id="decPrecioUnitario"
                name="decPrecioUnitario"
                value={formData.decPrecioUnitario}
                onChange={handleChange}
                required
                disabled={!isClaveSeleccionada}
              />
            </div>
            <div className="form-group-actualiza">
              <label htmlFor="intAntiguedadXMes">Antigüedad por mes:</label>
              <input
                type="text"
                id="intAntiguedadXMes"
                name="intAntiguedadXMes"
                value={formData.intAntiguedadXMes}
                onChange={handleChange}
                required
                disabled={!isClaveSeleccionada}
              />
            </div>
          </>
          <button className='btn-actualizar' type="submit" disabled={!isClaveSeleccionada}>Registrar</button>
        </form>
      </div>
      <div className="estatus-actualizar">
        {actualizacionExitosa && <p className="registro-exitoso">Registro exitoso.</p>}
      </div>
    </div>
  );
}

export default Actualizar;
