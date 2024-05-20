import React, { useState, useEffect } from "react";
import '../hoja-de-estilos/Registrar.css';
import $ from 'jquery';

function Registrar({ tipoRegistro }) {
    const [registroExitoso, setRegistroExitoso] = useState(false);
    const [formData, setFormData] = useState({});

    useEffect(() => {
        if (tipoRegistro === 'Producto') {
            setFormData({
                intIdProducto: '',
                cvNombre: '',
                decPrecioUnitario: '',
                intAntiguedadXMes: ''
            });
        } else if (tipoRegistro === 'Estado') {
            setFormData({
                cCveEstado: '',
                cvNombre: ''
            });
        } else if (tipoRegistro === 'Estatus') {
            setFormData({
                cCveEstatus: '',
                cvTipo: ''
            });
        }
    }, [tipoRegistro]);

    const resetFormData = () => {
        if (tipoRegistro === 'Producto') {
            setFormData({
                intIdProducto: '',
                cvNombre: '',
                decPrecioUnitario: '',
                intAntiguedadXMes: ''
            });
        } else if (tipoRegistro === 'Estado') {
            setFormData({
                cCveEstado: '',
                cvNombre: ''
            });
        } else if (tipoRegistro === 'Estatus') {
            setFormData({
                cCveEstatus: '',
                cvTipo: ''
            });
        }
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const url = `http://localhost:8080/api/registrar${tipoRegistro}`;

        $.ajax({
            url: url,
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Formulario enviado', response);
                setRegistroExitoso(true);
                resetFormData(); // Limpiar los inputs
            },
            error: function(xhr, status, error) {
                console.error('Error al enviar la solicitud:', error);
                console.log('Estado de la solicitud:', xhr.status);
                console.log('Mensaje de error:', xhr.statusText);
            }
        });
    };

    return (
        <div className="registrar-principal">
            <h2>Registro - {tipoRegistro}</h2>
            <form id="registerForm" onSubmit={handleSubmit}>
                {tipoRegistro === 'Producto' && (
                    <>
                        <div className="form-group">
                            <label htmlFor="intIdProducto">Clave:</label>
                            <input
                                type="text"
                                id="intIdProducto"
                                name="intIdProducto"
                                value={formData.intIdProducto}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="cvNombre">Nombre:</label>
                            <input
                                type="text"
                                id="cvNombre"
                                name="cvNombre"
                                value={formData.cvNombre}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="decPrecioUnitario">Precio:</label>
                            <input
                                type="text"
                                id="decPrecioUnitario"
                                name="decPrecioUnitario"
                                value={formData.decPrecioUnitario}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="intAntiguedadXMes">Antigüedad por mes:</label>
                            <input
                                type="text"
                                id="intAntiguedadXMes"
                                name="intAntiguedadXMes"
                                value={formData.intAntiguedadXMes}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </>
                )}
                {tipoRegistro === 'Estado' && (
                    <>
                        <div className="form-group">
                            <label htmlFor="cCveEstado">Clave:</label>
                            <input
                                type="text"
                                id="cCveEstado"
                                name="cCveEstado"
                                value={formData.cCveEstado}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="cvNombre">Nombre:</label>
                            <input
                                type="text"
                                id="cvNombre"
                                name="cvNombre"
                                value={formData.cvNombre}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </>
                )}
                {tipoRegistro === 'Estatus' && (
                    <>
                        <div className="form-group">
                            <label htmlFor="cCveEstatus">Clave:</label>
                            <input
                                type="text"
                                id="cCveEstatus"
                                name="cCveEstatus"
                                value={formData.cCveEstatus}
                                onChange={handleChange}
                                required
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="cvTipo">Tipo:</label>
                            <input
                                type="text"
                                id="cvTipo"
                                name="cvTipo"
                                value={formData.cvTipo}
                                onChange={handleChange}
                                required
                            />
                        </div>
                    </>
                )}
                <button type="submit">Registrar</button>
            </form>
            <div className="estatus-registro">
                {registroExitoso && <p className="registro-exitoso">Registro exitoso.</p>}
            </div>
        </div>
    );
}

export default Registrar;
