import React, { useState } from 'react';
import '../hoja-de-estilos/BarraNav.css';
import Inicio from './Inicio';
import Consultar from './Consultar';
import Registrar from './Registrar';
import Actualizar from './Actualizar';
import Eliminar from './Eliminar';

function BarraNav() {

  const [inicio, setInicio] = useState(true);
  const [consultaSeleccionada, setConsultaSeleccionada] = useState(false);
  const [tipoConsulta, setTipoConsulta] = useState('');
  const [registroSeleccionado, setRegistroSeleccionado] = useState(false);
  const [tipoRegistro, setTipoRegistro] = useState('');
  const [actualizaSeleccion, setActualizaSeleccion] = useState(false);
  const [tipoActualiza, setTipoActualiza] = useState('');
  const [eliminarSeleccionado, setEliminarSeleccionado] = useState(false);
  const [tipoEliminar, setTipoEliminar] = useState('');

  const handleInicioClick = () => {
    setInicio(true);
    setConsultaSeleccionada(false);
    setRegistroSeleccionado(false);
    setActualizaSeleccion(false);
    setEliminarSeleccionado(false);
  };

  const handleConsultaClick = (tipoConsulta) => {
    setInicio(false);
    setTipoConsulta(tipoConsulta);
    setConsultaSeleccionada(true);
    setRegistroSeleccionado(false);
    setActualizaSeleccion(false);
    setEliminarSeleccionado(false);
  };
  const handleRegistrarClick = (tipoRegistro) => {
    setInicio(false);
    setConsultaSeleccionada(false);
    setTipoRegistro(tipoRegistro);
    setRegistroSeleccionado(true);
    setActualizaSeleccion(false);
    setEliminarSeleccionado(false);
  };
  const handleActualizaClick = (tipoActualiza) => {
    setInicio(false);
    setConsultaSeleccionada(false);
    setRegistroSeleccionado(false);
    setTipoActualiza(tipoActualiza);
    setActualizaSeleccion(true);
    setEliminarSeleccionado(false);
  };
  const handleEliminarClick = (tipoEliminar) => {
    setInicio(false);
    setConsultaSeleccionada(false);
    setRegistroSeleccionado(false);
    setActualizaSeleccion(false);
    setTipoEliminar(tipoEliminar);
    setEliminarSeleccionado(true);
  };
  return (
    <div>
      <div className='contenedor-nav'>
        <nav>
          <ul className='menu-inicio'>
            <li><a href='#' onClick={handleInicioClick}>Inicio</a></li>
          </ul>
          <ul className='menu-opciones'>
            <li>
              <a href='#'>Registrar</a>
              <ul>
                <li><a href='#' onClick={() => handleRegistrarClick('Producto')}>Producto</a></li>
                <li><a href='#' onClick={() => handleRegistrarClick('Estado')}>Estado</a></li>
                <li><a href='#'>Método/Pago</a></li>
                <li><a href='#' onClick={() => handleRegistrarClick('Estatus')}>Estatus</a></li>
              </ul>
            </li>
            <li>
              <a href='#'>Consultar</a>
              <ul>
                <li><a href='#' onClick={() => handleConsultaClick('Factura')}>Factura</a></li>
                <li><a href='#' onClick={() => handleConsultaClick('Producto')}>Producto</a></li>
                <li><a href='#' onClick={() => handleConsultaClick('Estado')}>Estado</a></li>
                <li><a href='#' onClick={() => handleConsultaClick('Método de Pago')}>Método/Pago</a></li>
                <li><a href='#' onClick={() => handleConsultaClick('Bitácora')}>Bitácora</a></li>
                <li><a href='#' onClick={() => handleConsultaClick('Estatus')}>Estatus</a></li>
              </ul>
            </li>
            <li>
              <a href='#'>Actualizar</a>
              <ul>
                <li><a href='#' onClick={() => handleActualizaClick('Producto')}>Producto</a></li>
                <li><a href='#'>Estado</a></li>
                <li><a href='#'>Método/Pago</a></li>
                <li><a href='#'>Estatus</a></li>
              </ul>
            </li>
            <li>
              <a href='#'>Eliminar</a>
              <ul>
                <li><a href='#' onClick={() => handleEliminarClick('Producto')}>Producto</a></li>
                <li><a href='#'>Estado</a></li>
                <li><a href='#'>Método/Pago</a></li>
                <li><a href='#'>Estatus</a></li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
      {inicio && (
        <div id='inicioCargaArchivo' className='inicio'>
          <Inicio />
        </div>
      )}
      {consultaSeleccionada && (
        <div className='consultar-producto'>
          <Consultar tipoConsulta={tipoConsulta} />
        </div>
      )}
      {registroSeleccionado && (
        <div className='registrar-producto'>
          <Registrar tipoRegistro={tipoRegistro} />
        </div>
      )}
      {actualizaSeleccion && (
        <div className='actualiza'>
          <Actualizar tipoActualiza={tipoActualiza} />
        </div>
      )}
      {eliminarSeleccionado && (
        <div className='actualiza'>
          <Eliminar tipoEliminar={tipoEliminar} />
        </div>
      )}
    </div>
  );
}

export default BarraNav;