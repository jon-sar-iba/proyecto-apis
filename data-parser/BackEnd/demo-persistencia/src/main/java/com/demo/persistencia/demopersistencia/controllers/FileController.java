package com.demo.persistencia.demopersistencia.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;

import com.demo.persistencia.demopersistencia.entidades.Bitacoras;
import com.demo.persistencia.demopersistencia.entidades.Facturas;
import com.demo.persistencia.demopersistencia.entidades.FacturasProductos;
import com.demo.persistencia.demopersistencia.services.BitacoraServicio;
import com.demo.persistencia.demopersistencia.services.EmailServicio;
import com.demo.persistencia.demopersistencia.services.FacturaProductoServicio;
import com.demo.persistencia.demopersistencia.services.FacturaServicio;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/file")
public class FileController {

    static String mensajeBitacora = "";

    @Autowired
    private FacturaServicio servicioFactura;
    @Autowired
    private FacturaProductoServicio servicioFacPro;
    @Autowired
    private BitacoraServicio servicioBitacora;
    @Autowired
    private EmailServicio servicioEmail;


    private static boolean validaSumaDeFactura(List<Float> listFloatPrecio, float totalFactura) {
        float sumaPrecios = 0;
        for (float precio : listFloatPrecio) {
            sumaPrecios += precio;
        }
        if (sumaPrecios < totalFactura) {
            mensajeBitacora += "Error S401. La suma de los valores netos (" + sumaPrecios + ") de los productos es menor al total de la factura ("
                    + totalFactura + "). ";
            return true;
        } else if (sumaPrecios > totalFactura) {
            mensajeBitacora += "Error S402. La suma de los valores netos (" + sumaPrecios + ") de los productos es mayor al total de la factura ("
                    + totalFactura + "). ";
            return true;
        }
        return false;
    }

    private static boolean validaCantidadProducto(List<Integer> listIntIdProducto, int intProductosTotales) {
        if (listIntIdProducto.size() != intProductosTotales) {
            mensajeBitacora += "Error C401. La cantidad de tipos de productos (" + listIntIdProducto.size()
                + ") no coincide con el total mostrado en la factura (" + intProductosTotales + "). ";
            return true;
        }
        return false;
    }

    private static boolean ValidaCamposH(int intNumFactura, String StrMetodoPago, int intNumCliente, String StrCveEstado,
            String StrFecha, String StrEstatus) {
        if (intNumFactura == 0) {
            mensajeBitacora += "Error H401, no existe el número de factura. ";
        }
        if (StrMetodoPago.isEmpty()) {
            mensajeBitacora += "Error H402, no existe el método de pago. ";
        }
        if (intNumCliente == 0) {
            mensajeBitacora += "Error H403, no existe el número de cliente. ";
        }
        if (StrCveEstado.isEmpty()) {
            mensajeBitacora += "Error H404, no existe la clave del país donde se emitió la factura. ";
        }
        if (StrFecha.isEmpty()) {
            mensajeBitacora += "Error H405, no existe la fecha de emisión. ";
        }
        if (StrEstatus.isEmpty()) {
            mensajeBitacora += "Error H406, no existe la clave de la moneda. ";
        }
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher;
        matcher = pattern.matcher(String.valueOf(intNumFactura));
        if (matcher.find()) {
            mensajeBitacora += "Error H407, \"" + intNumFactura + "\" número de factura inconsistente. ";
        }
        matcher = pattern.matcher(StrMetodoPago);
        if (matcher.find() || !StrMetodoPago.matches("[a-zA-Z]+")) {
            mensajeBitacora += "Error H408, \"" + StrMetodoPago + "\" método de pago inconsistente. ";
        }
        matcher = pattern.matcher(String.valueOf(intNumCliente));
        if (matcher.find()) {
            mensajeBitacora += "Error H409, \"" + intNumCliente + "\" número de cliente inconsistente. ";
        }
        matcher = pattern.matcher(StrCveEstado);
        if (matcher.find() || !StrCveEstado.matches("[a-zA-Z]+")) {
            mensajeBitacora += "Error H410, \"" + StrCveEstado + "\" clave del país donde se emitió la factura inconsistente. ";
        }
        matcher = pattern.matcher(StrFecha);
        if (matcher.find() || !StrFecha.matches("\\d+")) {
            mensajeBitacora += "Error H411, \"" + StrFecha + "\" fecha de emisión inconsistente. ";
        }
        matcher = pattern.matcher(StrEstatus);
        if (matcher.find() || !StrEstatus.matches("[a-zA-Z]+")) {
            mensajeBitacora += "Error H412, \"" + StrEstatus + "\" clave de la moneda inconsistente. ";
        }
        return !mensajeBitacora.isEmpty();
    }

    private static boolean ValidaCamposI(List<Integer> listId,List<Integer> listAnt, List<Integer> listCan, List<Float> listPre, int totalP) {
        if (listId.size() != listAnt.size() || listAnt.size() != listCan.size()
                || listCan.size() != listPre.size() || listPre.size() != totalP) {
            mensajeBitacora += "Error I401, hace falta un dato (s) en algún (algunos) item (s). ";
            return true;
        }
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher;
        for (int i = 0; i < listId.size(); i++) {
            matcher = pattern.matcher(String.valueOf(listId.get(i)));
            if (matcher.find()) {
                mensajeBitacora += "Error I403, \"" + listId.get(i) + "\" id del producto " + (i + 1)
                        + " inconsistente. ";
            }
            matcher = pattern.matcher(String.valueOf(listAnt.get(i)));
            if (matcher.find()) {
                mensajeBitacora += "Error I404, \"" + listAnt.get(i) + "\" antiguedad del producto " + (i + 1)
                        + " inconsistente. ";
            }
            matcher = pattern.matcher(String.valueOf(listCan.get(i)));
            if (matcher.find()) {
                mensajeBitacora += "Error I405, \"" + listCan.get(i) + "\" cantidad del producto " + (i + 1)
                        + " inconsistente. ";
            }
            matcher = pattern.matcher(String.valueOf(listPre.get(i)));
            if (matcher.find()) {
                mensajeBitacora += "Error I406, \"" + listPre.get(i) + "\" precio del producto " + (i + 1)
                        + " inconsistente. ";
            }
        }
        return !mensajeBitacora.isEmpty();
    }

    private static boolean ValidaCamposT(String cantTipoProducto, String totalFactura) {
        if (cantTipoProducto.isEmpty()) {
            mensajeBitacora += "Error T401, falta el valor de la cantidad de productos. ";
        }
        if (totalFactura.isEmpty()) {
            mensajeBitacora += "Error T402, falta el valor total de la factura. ";
        }
        Pattern pattern = Pattern.compile("\\s");
        Matcher matcher;
        matcher = pattern.matcher(cantTipoProducto);
        if (matcher.find()) {
            mensajeBitacora += "Error T403, \"" + cantTipoProducto + "\" el campo cantidad de productos es inconsistente. ";
        }
        matcher = pattern.matcher(totalFactura);
        if (matcher.find()) {
            mensajeBitacora += "Error T404, \"" + totalFactura
                    + "\" el campo del valor total de la factura es inconsistente. ";
        }
        return !mensajeBitacora.isEmpty();
    }

    

    @PostMapping("/load")
    public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file){
        // Hacer la solicitud al servidor para obtener la fecha
        
        if (file.isEmpty()) {
            return new ResponseEntity<>("Archivo vacío", HttpStatus.BAD_REQUEST);
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            StringBuilder content = new StringBuilder();
            
            File tempFile = File.createTempFile("temp", null);
            file.transferTo(tempFile);

            String intNumFactura = "";
            String StrMetodoPago = "";
            String intNumCliente = "";
            String StrCveEstado = "";
            String StrFecha = "";
            String StrEstatus = "";
            List<Integer> listIntIdProducto = new ArrayList<>();
            List<Integer> listIntAntgProducto = new ArrayList<>();
            List<Integer> listIntCantProducto = new ArrayList<>();
            List<Float> listFloatPrecio = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(tempFile))) {
                String linea;
                Integer contadorFactura = 0;
                while ((linea = br.readLine()) != null) {
                    
                    if (linea.charAt(0) == 'H') { // Headers
                        int contador = 4; // Se inicia en 1 dado que el primer caracter (linea[0]) sólo es un identificador, es decir, si es Header, Item o Total
                        String cadena = "";
                        boolean boolActivaCaptura = false;
                        boolean boolCapturaNumero = true;
                        for (char caracter : linea.substring(contador).toCharArray()) {
                            if (boolCapturaNumero) {
                                if ((!Character.isWhitespace(caracter) && caracter != '0') || boolActivaCaptura) {
                                    boolActivaCaptura = true;
                                    cadena += caracter;
                                }
                                if (contador == 8) {
                                    intNumFactura = cadena;
                                    cadena = "";
                                    boolActivaCaptura = false;
                                    boolCapturaNumero = false;
                                } else if (contador == 13) {
                                    intNumCliente = cadena;
                                    cadena = "";
                                    boolActivaCaptura = false;
                                    boolCapturaNumero = false;
                                } else if (contador == 24) {
                                    StrFecha = cadena;
                                    cadena = "";
                                    boolActivaCaptura = false;
                                    boolCapturaNumero = false;
                                }
                            } else {
                                cadena += caracter;
                                if (contador == 9) {
                                    StrMetodoPago = cadena;
                                    cadena = "";
                                    boolCapturaNumero = true;
                                } else if (contador == 16) {
                                    StrCveEstado = cadena;
                                    cadena = "";
                                    boolCapturaNumero = true;
                                } else if (contador == 25) {
                                    StrEstatus = cadena;
                                    cadena = "";
                                }
                            }
                            contador++;
                        }
                    } else if (linea.charAt(0) == 'I') {
                        int contador = 1;
                        String cadena = "";
                        boolean boolExisteEspacio = false;
                        for (char caracter : linea.substring(contador).toCharArray()) {
                            if (contador >= 1) {
                                if ((!Character.isWhitespace(caracter) && caracter != '0') || boolExisteEspacio) {
                                    boolExisteEspacio = true;
                                    cadena += caracter;
                                }
                                if (contador == 3) { // Se guarda el id del producto
                                    listIntIdProducto.add(Integer.parseInt(cadena));
                                    cadena = "";
                                    boolExisteEspacio = false;
                                } else if (contador == 5) { // Se guarda la antiguedad del producto
                                    listIntAntgProducto.add(Integer.parseInt(cadena));
                                    cadena = "";
                                    boolExisteEspacio = false;
                                } else if (contador == 8) { // Se guarda la cantidad del producto
                                    listIntCantProducto.add(Integer.parseInt(cadena));
                                    cadena = "";
                                    boolExisteEspacio = false;
                                } else if (contador > 9) { // Se guarda el precio
                                    if (contador == linea.length()) {
                                        listFloatPrecio.add(Float.parseFloat(cadena));
                                        cadena = "";
                                        boolExisteEspacio = false;
                                    } else {
                                        if (Character.isWhitespace(linea.charAt(contador + 1))) {
                                            int cuentaEspacio = 0;
                                            int cuentaElementos = 0;
                                            for (char finalCadena : linea.substring(contador + 1).toCharArray()) {
                                                cuentaElementos++;
                                                if (Character.isWhitespace(finalCadena)) {
                                                    cuentaEspacio++;
                                                }
                                            }
                                            if (cuentaElementos == cuentaEspacio) {
                                                listFloatPrecio.add(Float.parseFloat(cadena));
                                                cadena = "";
                                                boolExisteEspacio = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            contador++;
                        }
                    } else { // T
                        
                        String intProductosTotales = "";
                        String totalFactura = "";
                        String cadena = "";
                        boolean boolActivaCaptura = false;
                        int contador = 1;
                        boolean boolExisteEspacio = false;
                        for (char caracter : linea.substring(contador).toCharArray()) {
                            if (!Character.isWhitespace(caracter) || boolExisteEspacio) { // Validamos para evitar guardar un espacio en la cadenaItem
                                if (caracter != '0' || boolActivaCaptura) {
                                    boolExisteEspacio = true;
                                    boolActivaCaptura = true;
                                    cadena += caracter;
                                    if (contador == 3) { // Se guarda la cantidad de tipos de productos vendidos
                                        intProductosTotales = cadena;
                                        cadena = "";
                                        boolActivaCaptura = false;
                                        boolExisteEspacio = false;
                                    } else if (contador > 3) { // Se guarda el total de la factura
                                        if (contador == linea.length() - 1) {
                                            totalFactura = cadena;
                                            cadena = "";
                                            boolActivaCaptura = false;
                                            boolExisteEspacio = false;
                                        } else if (Character.isWhitespace(linea.charAt(contador + 1))) {
                                            int cuentaEspacio = 0;
                                            int cuentaElementos = 0;
                                            for (char finalCadena : linea.substring(contador + 1).toCharArray()) {
                                                cuentaElementos++;
                                                if (Character.isWhitespace(finalCadena)) {
                                                    cuentaEspacio++;
                                                }
                                            }
                                            if (cuentaElementos == cuentaEspacio) {
                                                totalFactura = cadena;
                                                cadena = "";
                                                boolActivaCaptura = false;
                                                boolExisteEspacio = false;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            contador++;
                        }
                        // Valida que existan valores en los campos correspondientes
                        boolean datosValidosH = ValidaCamposH(Integer.parseInt(intNumFactura), StrMetodoPago, 
                        Integer.parseInt(intNumCliente), StrCveEstado, StrFecha, StrEstatus);
                        boolean datosValidosI = ValidaCamposI(listIntIdProducto, listIntAntgProducto, listIntCantProducto, listFloatPrecio, Integer.parseInt(intProductosTotales));
                        boolean datosValidosT = ValidaCamposT(intProductosTotales, totalFactura);
                        //
                        boolean boolSumaCorrecta = false;
                        boolean boolCantProductoCorrecto = false;
                        if (!datosValidosH && !datosValidosI && !datosValidosT) {
                            boolSumaCorrecta = validaSumaDeFactura(listFloatPrecio, Float.parseFloat(totalFactura));
                            boolCantProductoCorrecto = validaCantidadProducto(listIntIdProducto, Integer.parseInt(intProductosTotales));
                        }
                        if (!datosValidosH && !datosValidosI && !datosValidosT && !boolSumaCorrecta && !boolCantProductoCorrecto) {
                            String anio = StrFecha.substring(0, 4);
                            String mes = StrFecha.substring(4, 6);
                            String dia = StrFecha.substring(StrFecha.length() - 2);
                            String fecha = anio + "-" + mes + "-" + dia; 
                            try {
                                //Registrar factura
                                Facturas factura = new Facturas();
                                
                                factura.setIntNumFactura(Integer.parseInt(intNumFactura));
                                factura.setIntNumCliente(Integer.parseInt(intNumCliente));
                                factura.setIntCantTiposProductos(Integer.parseInt(intProductosTotales));
                                factura.setDecTotal(Float.parseFloat(totalFactura));
                                factura.setCCveMetodoPago(StrMetodoPago);
                                factura.setDFecha(fecha);
                                factura.setCCveEstado(StrCveEstado);
                                factura.setCCveEstatus(StrEstatus);
                                
                                servicioFactura.registFactura(factura);
                                
                            } catch (Exception e) {
                                Bitacoras bitacora = new Bitacoras();
                                bitacora.setIntNumFactura(Integer.parseInt(intNumFactura));
                                bitacora.setBRegistroExitoso(false);
                                bitacora.setCvDescripcion(mensajeBitacora);
                                
                                servicioBitacora.registBitacora(bitacora);
                                // Captura cualquier excepción que no sea de SQL
                                e.printStackTrace(); // Opcional: imprime el seguimiento de la pila para obtener más detalles
                            }
                            Bitacoras bitacora = new Bitacoras();
                            bitacora.setIntNumFactura(Integer.parseInt(intNumFactura));
                            bitacora.setBRegistroExitoso(true);
                            bitacora.setCvDescripcion("Registro exitoso.");
                            
                            servicioEmail.sendSimpleMessage(
                                "ibarmient8@gmail.com", 
                                "Su registro ha sido exitoso",
                                mensajeBitacora
                            );
                            
                            servicioBitacora.registBitacora(bitacora);
                            //Registrar factura-producto
                            for (int i = 0; i < listIntIdProducto.size(); i++) {
                                FacturasProductos facturaProducto = new FacturasProductos();
                                
                                Integer idProducto = listIntIdProducto.get(i);
                                Integer cantidad = listIntCantProducto.get(i);

                                facturaProducto.setIntNumFactura(Integer.parseInt(intNumFactura));
                                facturaProducto.setIntIdProducto(idProducto);
                                facturaProducto.setIntCantidad(cantidad);

                                servicioFacPro.registFacturaProducto(facturaProducto);
                            }
                            //
                        } else {
                            Bitacoras bitacora = new Bitacoras();
                            bitacora.setIntNumFactura(Integer.parseInt(intNumFactura));
                            bitacora.setBRegistroExitoso(false);
                            bitacora.setCvDescripcion(mensajeBitacora);
                            
                            servicioBitacora.registBitacora(bitacora);
                        }
                        // Regresando variables a su valor originales
                        mensajeBitacora = "";
                        intNumFactura = "";
                        listIntIdProducto.clear();
                        listIntAntgProducto.clear();
                        listIntCantProducto.clear();
                        listFloatPrecio.clear();
                    }
                    contadorFactura += 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Eliminar el archivo temporal después de su uso
                tempFile.delete();
            }
            reader.close();
            return ResponseEntity.ok(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al leer el archivo");
        }
    }
}
