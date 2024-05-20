import React, { useState, useRef, useEffect } from 'react';
import '../hoja-de-estilos/Consultar.css';
import $, { data } from 'jquery';

function Consultar({ tipoConsulta }) {
  const [radioSeleccionado, setRadioSeleccionado] = useState('todos'); // Estado para almacenar el tipo de consulta seleccionado
  const [clave, setClave] = useState(''); // Estado para almacenar la clave ingresada
  const [datosConsultados, setDatosConsultados] = useState([]); // Estado para almacenar los datos consultados
  const [error, setError] = useState(null); // Estado para almacenar el error
  const inputClaveRef = useRef(null); // Referencia al input de texto

  const consultaTypeChange = (event) => {
    setRadioSeleccionado(event.target.value); // Actualizar el estado con el valor del radio button seleccionado
    setDatosConsultados([]); // Limpiar los datos consultados cuando se cambia el radio button
    setError(null);
  };

  const handleConsultarClick = () => {
    if (radioSeleccionado === 'todos') {
      let url;
      if (tipoConsulta === "Producto"){
        url = 'http://localhost:8080/api/listarProductos';
      } else if (tipoConsulta === "Estado"){
        url = 'http://localhost:8080/api/listarEstados';
      }
      // Realizar la petición al backend para consultar todos los productos
      $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
          // Verificar si la respuesta es un array o un objeto
          setDatosConsultados(Array.isArray(data) ? data : [data]); // Almacenar los datos consultados en el estado
          setError(null); // Reiniciar el estado de error
        },
        error: function(xhr, status, error) {
          setError(error); // Almacenar el mensaje de error
        }
      });
    } else if (radioSeleccionado === 'byKey') {
      // Verificar que se haya ingresado una clave
      if (!clave) {
        setError('Por favor ingrese una clave.');
        return;
      }
  
      // Construir el objeto JSON con la clave
      const id = parseInt(clave);
      if (isNaN(id)) {
        // La clave no es un número
        setError('Por favor ingrese un valor numérico para la clave.');
        return;
      }
      let url;
      if (tipoConsulta === "Producto"){
        url = `http://localhost:8080/api/buscarProducto/${id}`
      } else if (tipoConsulta === "Estado"){
        url = `http://localhost:8080/api/buscarEstado/${id}`
      }
      // Realizar la petición al backend para consultar por clave
      $.ajax({
        url: url,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
          setDatosConsultados([data]);
          setError(null);
        },
        error: function(xhr, status, error) {
          if (xhr.status === 404) {
            // El recurso no se encontró (error 404), maneja el error adecuadamente
            setError(`No exite el producto con la clave: ${clave}`);
        } else {
            // Otro tipo de error, establece el mensaje de error recibido
            setError(error);
        }
          setDatosConsultados([]);
        }
      });
    }
  };
  

  useEffect(() => {
    if (radioSeleccionado === 'byKey' && inputClaveRef.current) {
      inputClaveRef.current.focus(); // Enfocar el input de texto
    }
  }, [radioSeleccionado]);

  return (
    <div className='consulta-producto-principal'>
      <h2>{tipoConsulta}</h2>
      <div>
        <div className='contenedor-izquierdo'>
          <div className='contenedor-ingresa-datos'>
            <h2>Consultar:</h2>
            <div className='opciones-de-consulta'>
              <div>
                <input type="radio" name="consultaType" value="todos" id="radTodos" checked={radioSeleccionado === 'todos'} onChange={consultaTypeChange} />
                <label htmlFor="radTodos">Todos</label>
              </div>
              <div>
                <input type="radio" name="consultaType" value="byKey" id="radPorClave" checked={radioSeleccionado === 'byKey'} onChange={consultaTypeChange} />
                <label htmlFor="radPorClave">Por Clave:</label>
              </div>
            </div>
            <div className='contenedor-caja-por-clave'>
              {radioSeleccionado === 'byKey' && <input className='caja-text-por-clave' type="text" ref={inputClaveRef} placeholder="Ingrese la clave" value={clave} onChange={(e) => setClave(e.target.value)} />}
            </div>
            <div>
              <button id="btnConsultarProductos" className="boton-consultar" type="button" onClick={handleConsultarClick}>Consultar</button>
            </div>
          </div>
        </div>
        <div className='contenedor-muestra-datos'>
          {tipoConsulta === "Producto" && (
            <div className='contenedor-datos'>
              {error && <p className='contenedor-error-404'>{error}</p>}
              {datosConsultados.length > 0 && (
                datosConsultados.map((dato, index) => (
                  <div key={index} className='estructura-de-datos'>
                    <p>Clave de producto: {dato.intIdProducto} </p>
                    <p>Nombre: {dato.cvNombre} </p>
                    <p>Precio: {dato.decPrecioUnitario} </p>
                    <p>Antigüedad por mes: {dato.intAntiguedadXMes}</p>
                  </div>
                ))
              )}
            </div>
          )}
          {tipoConsulta === "Estado" && (
            <div className='contenedor-datos'>
              {error && <p className='contenedor-error-404'>{error}</p>}
              {datosConsultados.length > 0 && (
                datosConsultados.map((dato, index) => (
                  <div key={index} className='estructura-de-datos'>
                    <p>Clave de estado: {dato.cCveEstado} </p>
                    <p>Nombre: {dato.cvNombre} </p>
                  </div>
                ))
              )}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default Consultar;
