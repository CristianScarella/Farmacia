package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import modelo.*;
import org.bson.Document;
import org.example.utils.JacksonUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestMongo {
    public static void main(String[] args) {
        //ENTREGA 2

        Provincia provincia = new Provincia(0001, "Buenos Aires");
        Localidad localidad = new Localidad(0001, "La Plata", provincia);

        //obra social
        ObraSocial obraSocial = new ObraSocial(0001, "OSDE");

        //domicilio
        Domicilio domicilioSucursal1 = new Domicilio(0001, "Calle 7", "1234", localidad);
        Domicilio domicilioSucursal2 = new Domicilio(0002, "Calle 8", "5678", localidad);
        Domicilio domicilioSucursal3 = new Domicilio(0003, "Calle 9", "9998", localidad);

        //crear empleado
        Empleado empleadoSucursal1Atencion = getEmpleado1(obraSocial);
        Empleado empleadoSucursal2Atencion = getEmpleado2(obraSocial);
        Empleado empleadoSucursal1Cobro = getEmpleado3(obraSocial);
        Empleado empleadoSucursal2Cobro = getEmpleado4(obraSocial);
        
        //nuevos empleados
        Empleado empleadoSucursal1Encargado = new Empleado(0005, "Homero", "Simpson", "88433246", "20884332469", "AF8111200", null, obraSocial, null);
        Empleado empleadoSucursal2Encargado = new Empleado(0006, "Maria", "Lopez", "55777666", "27557776669", "AF8222200", null, obraSocial, null);
        Empleado empleadoSucursal3Encargado = new Empleado(0007, "Carlos", "Diaz", "43323487", "20433234878", "AF9332200", null, obraSocial, null);
        Empleado empleadoSucursal3Atencion= new Empleado(8l, "Andrea", "Mendez", "99667555", "27996675559", "AF9732200", null, obraSocial, null);
        Empleado empleadoSucursal3Cobro = new Empleado(9l, "Jorge", "Ruiz", "22928663", "20229286639", "AF819270", null, obraSocial, null);

        //sucursal
        Sucursal sucursal1 = new Sucursal(0001, 0001l, domicilioSucursal1, empleadoSucursal1Encargado);
        Sucursal sucursal2 = new Sucursal(0002, 0002l, domicilioSucursal2, empleadoSucursal2Encargado);
        Sucursal sucursal3 = new Sucursal(0003, 0003l, domicilioSucursal3, empleadoSucursal3Encargado);

        empleadoSucursal1Atencion.setSucursal(sucursal1);
        empleadoSucursal2Atencion.setSucursal(sucursal2);
        empleadoSucursal3Atencion.setSucursal(sucursal3);
        empleadoSucursal1Cobro.setSucursal(sucursal1);
        empleadoSucursal2Cobro.setSucursal(sucursal2);
        empleadoSucursal3Cobro.setSucursal(sucursal3);

        //cliente
        Cliente cliente1 = new Cliente(0001, "Juan", "Perez", "30123456", "AF123456", null, obraSocial);
        Cliente cliente3 = new Cliente(0003, "Esteban", "Martinez", "30345678", "AF345678", null, obraSocial);
        Cliente cliente4 = new Cliente(0004, "Sofia", "Fernandez", "30456789", "AF456789", null, obraSocial);
        Cliente cliente5 = new Cliente(0005, "Diego", "Rodriguez", "30567890", "AF567890", null, obraSocial);
        Cliente cliente6 = new Cliente(0006, "Valeria", "Torres", "30678901", "AF678901", null, obraSocial);
        Cliente cliente7 = new Cliente(0007, "Agustin", "Sanchez", "30789012", "AF789012", null, obraSocial);
        Cliente cliente8 = new Cliente(8l, "Camila", "Morales", "30890123", "AF890123", null, obraSocial);
        Cliente cliente9 = new Cliente(9l, "Tomas", "Vega", "30901234", "AF901234", null, obraSocial);
        Cliente cliente10 = new Cliente(0010, "Florencia", "Navarro", "31012345", "AF012345", null, obraSocial);

        // Crear producto
        Producto producto1 = new Producto(1, true, "Ibuprofeno 600mg", "Bagó", "IBU600", 120.0);
        Producto producto2 = new Producto(2, false, "Perfume Dior Sauvage 100ml", "Dior", "DIOR100", 42000.0);
        Producto producto3 = new Producto(3, true, "Ibuprofeno 600 mg x20 caps Actron", "Bayer", "ACTRON600", 11954.0);
        Producto producto4 = new Producto(4, true, "Ibupirac 600 mg x10 comp", "Pfizer", "IBUPIRAC600", 7325.0);
        Producto producto5 = new Producto(5, true, "Sindol 600 mg x20 caps blandas", "Farmalife", "SINDOL600", 14241.0);
        Producto producto6 = new Producto(6, true, "Paracetamol 500 mg x10", "Genfar", "PARA500", 800.0);
        Producto producto7 = new Producto(7, true, "Metformina 850 mg x30", "Bayer", "METF850", 13000.0);
        Producto producto8 = new Producto(8, true, "Omeprazol 20 mg x14", "Elea", "OMEP20", 1100.0);
        Producto producto9 = new Producto(9, false, "Crema Nivea Soft 200 ml", "Nivea", "NIV200", 5600.0);
        Producto producto10 = new Producto(10, false, "Shampoo Pantene 400 ml", "Pantene", "PANT400", 11000.0);

        DetalleVenta detalleVenta1 = new DetalleVenta(producto1, 2, producto1.getPrecioProducto());
        DetalleVenta detalleVenta2 = new DetalleVenta(producto2, 1, producto2.getPrecioProducto());
        DetalleVenta detalleVenta3 = new DetalleVenta(producto3, 1, producto3.getPrecioProducto());
        DetalleVenta detalleVenta4 = new DetalleVenta(producto4, 2, producto4.getPrecioProducto());
        DetalleVenta detalleVenta5 = new DetalleVenta(producto5, 1, producto5.getPrecioProducto());
        DetalleVenta detalleVenta6 = new DetalleVenta(producto4, 2, producto4.getPrecioProducto());        
        DetalleVenta detalleVenta7 = new DetalleVenta(producto5, 1, producto5.getPrecioProducto());
        DetalleVenta detalleVenta8 = new DetalleVenta(producto6, 4, producto6.getPrecioProducto());        
        DetalleVenta detalleVenta9 = new DetalleVenta(producto4, 2, producto4.getPrecioProducto());        
        DetalleVenta detalleVenta10 = new DetalleVenta(producto7, 1, producto7.getPrecioProducto());
        DetalleVenta detalleVenta11 = new DetalleVenta(producto8, 1, producto8.getPrecioProducto());        
        DetalleVenta detalleVenta12 = new DetalleVenta(producto9, 3, producto9.getPrecioProducto());        
        DetalleVenta detalleVenta13 = new DetalleVenta(producto10, 2, producto10.getPrecioProducto());
        DetalleVenta detalleVenta14 = new DetalleVenta(producto1, 1, producto1.getPrecioProducto());
        
        List<Venta> ventasTotales = new ArrayList<>();
        
        ventasTotales.add(generarVenta((long) 0001, LocalDate.of(2025,06,1),sucursal1.getPuntoDeVenta()+"-11111111", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta1, detalleVenta2)));
        ventasTotales.add(generarVenta((long) 0002, LocalDate.of(2025,06,2),sucursal1.getPuntoDeVenta()+"-11111112", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta3)));
        ventasTotales.add(generarVenta((long) 0003, LocalDate.of(2025,06,3),sucursal1.getPuntoDeVenta()+"-11111113", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta4, detalleVenta5)));
        ventasTotales.add(generarVenta((long) 0004, LocalDate.of(2025,06,4),sucursal1.getPuntoDeVenta()+"-11111114", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0005, LocalDate.of(2025,06,5),sucursal1.getPuntoDeVenta()+"-11111115", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta7, detalleVenta8)));
        ventasTotales.add(generarVenta((long) 0006, LocalDate.of(2025,06,6),sucursal1.getPuntoDeVenta()+"-11111116", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta10)));
        ventasTotales.add(generarVenta((long) 0007, LocalDate.of(2025,06,7),sucursal1.getPuntoDeVenta()+"-11111117", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta10, detalleVenta11)));
        ventasTotales.add(generarVenta((long) 8, LocalDate.of(2025,06,8),sucursal1.getPuntoDeVenta()+"-11111118", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta4)));
        ventasTotales.add(generarVenta((long) 9, LocalDate.of(2025,06,9),sucursal1.getPuntoDeVenta()+"-11111119", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta7, detalleVenta13)));
        ventasTotales.add(generarVenta((long) 0010, LocalDate.of(2025,06,10),sucursal1.getPuntoDeVenta()+"-11111120", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta10)));
        ventasTotales.add(generarVenta((long) 0011, LocalDate.of(2025,06,11),sucursal1.getPuntoDeVenta()+"-11111121", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta9, detalleVenta12)));
        ventasTotales.add(generarVenta((long) 0012, LocalDate.of(2025,06,12),sucursal1.getPuntoDeVenta()+"-11111122", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta8)));
        ventasTotales.add(generarVenta((long) 0013, LocalDate.of(2025,06,13),sucursal1.getPuntoDeVenta()+"-11111123", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta14, detalleVenta5)));
        ventasTotales.add(generarVenta((long) 0014, LocalDate.of(2025,06,14),sucursal1.getPuntoDeVenta()+"-11111124", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta14)));
        ventasTotales.add(generarVenta((long) 0015, LocalDate.of(2025,06,15),sucursal1.getPuntoDeVenta()+"-11111125", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta10, detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0016, LocalDate.of(2025,06,16),sucursal1.getPuntoDeVenta()+"-11111126", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta8)));
        ventasTotales.add(generarVenta((long) 0017, LocalDate.of(2025,06,17),sucursal1.getPuntoDeVenta()+"-11111127", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta6, detalleVenta13)));
        ventasTotales.add(generarVenta((long) 18, LocalDate.of(2025,06,18),sucursal1.getPuntoDeVenta()+"-11111128", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta1)));
        ventasTotales.add(generarVenta((long) 19, LocalDate.of(2025,06,19),sucursal1.getPuntoDeVenta()+"-11111129", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta1, detalleVenta7)));
        ventasTotales.add(generarVenta((long) 0020, LocalDate.of(2025,06,20),sucursal1.getPuntoDeVenta()+"-11111130", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta4)));
        ventasTotales.add(generarVenta((long) 0021, LocalDate.of(2025,06,21),sucursal1.getPuntoDeVenta()+"-11111131", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta4, detalleVenta14)));
        ventasTotales.add(generarVenta((long) 0022, LocalDate.of(2025,06,22),sucursal1.getPuntoDeVenta()+"-11111132", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta1)));
        ventasTotales.add(generarVenta((long) 0023, LocalDate.of(2025,06,23),sucursal1.getPuntoDeVenta()+"-11111133", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta2, detalleVenta13)));
        ventasTotales.add(generarVenta((long) 0024, LocalDate.of(2025,06,24),sucursal1.getPuntoDeVenta()+"-11111134", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0025, LocalDate.of(2025,06,25),sucursal1.getPuntoDeVenta()+"-11111135", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta14, detalleVenta13)));
        ventasTotales.add(generarVenta((long) 0026, LocalDate.of(2025,06,26),sucursal1.getPuntoDeVenta()+"-11111136", sucursal1, empleadoSucursal1Atencion, empleadoSucursal1Cobro, List.of(detalleVenta11)));

        ventasTotales.add(generarVenta((long) 0027, LocalDate.of(2025,06,1),sucursal2.getPuntoDeVenta()+"-22222222", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta1)));
        ventasTotales.add(generarVenta((long) 28l, LocalDate.of(2025,06,1),sucursal2.getPuntoDeVenta()+"-22222223", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta1, detalleVenta2)));
        ventasTotales.add(generarVenta((long) 29l, LocalDate.of(2025,06,2),sucursal2.getPuntoDeVenta()+"-22222224", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta3)));
        ventasTotales.add(generarVenta((long) 0030, LocalDate.of(2025,06,3),sucursal2.getPuntoDeVenta()+"-22222225", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta4, detalleVenta5)));
        ventasTotales.add(generarVenta((long) 0031, LocalDate.of(2025,06,4),sucursal2.getPuntoDeVenta()+"-22222226", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta4)));
        ventasTotales.add(generarVenta((long) 0032, LocalDate.of(2025,06,5),sucursal2.getPuntoDeVenta()+"-22222227", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta6, detalleVenta14)));
        ventasTotales.add(generarVenta((long) 0033, LocalDate.of(2025,06,6),sucursal2.getPuntoDeVenta()+"-22222228", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta1)));
        ventasTotales.add(generarVenta((long) 0034, LocalDate.of(2025,06,6),sucursal2.getPuntoDeVenta()+"-22222229", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta14, detalleVenta3)));
        ventasTotales.add(generarVenta((long) 0035, LocalDate.of(2025,06,7),sucursal2.getPuntoDeVenta()+"-22222230", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta4)));
        ventasTotales.add(generarVenta((long) 0036, LocalDate.of(2025,06,8),sucursal2.getPuntoDeVenta()+"-22222231", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta5, detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0037, LocalDate.of(2025,06,9),sucursal2.getPuntoDeVenta()+"-22222232", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta2)));
        ventasTotales.add(generarVenta((long) 38l, LocalDate.of(2025,06,10),sucursal2.getPuntoDeVenta()+"-22222233", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta7, detalleVenta8)));
        ventasTotales.add(generarVenta((long) 39l, LocalDate.of(2025,06,11),sucursal2.getPuntoDeVenta()+"-22222234", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0040, LocalDate.of(2025,06,12),sucursal2.getPuntoDeVenta()+"-22222235", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta8, detalleVenta9)));
        ventasTotales.add(generarVenta((long) 0041, LocalDate.of(2025,06,13),sucursal2.getPuntoDeVenta()+"-22222236", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta9)));
        ventasTotales.add(generarVenta((long) 0042, LocalDate.of(2025,06,14),sucursal2.getPuntoDeVenta()+"-22222237", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta14, detalleVenta10)));
        ventasTotales.add(generarVenta((long) 0043, LocalDate.of(2025,06,15),sucursal2.getPuntoDeVenta()+"-22222238", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta8)));
        ventasTotales.add(generarVenta((long) 0044, LocalDate.of(2025,06,16),sucursal2.getPuntoDeVenta()+"-22222239", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta10, detalleVenta11)));
        ventasTotales.add(generarVenta((long) 0045, LocalDate.of(2025,06,17),sucursal2.getPuntoDeVenta()+"-22222240", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta12)));
        ventasTotales.add(generarVenta((long) 0046, LocalDate.of(2025,06,18),sucursal2.getPuntoDeVenta()+"-22222241", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta4, detalleVenta12)));
        ventasTotales.add(generarVenta((long) 0047, LocalDate.of(2025,06,19),sucursal2.getPuntoDeVenta()+"-22222242", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta12)));
        ventasTotales.add(generarVenta((long) 48l, LocalDate.of(2025,06,20),sucursal2.getPuntoDeVenta()+"-22222243", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta13, detalleVenta14)));
        ventasTotales.add(generarVenta((long) 49l, LocalDate.of(2025,06,21),sucursal2.getPuntoDeVenta()+"-22222244", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta5)));
        ventasTotales.add(generarVenta((long) 0050, LocalDate.of(2025,06,22),sucursal2.getPuntoDeVenta()+"-22222245", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta13, detalleVenta14)));
        ventasTotales.add(generarVenta((long) 0051, LocalDate.of(2025,06,23),sucursal2.getPuntoDeVenta()+"-22222246", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta2)));
        ventasTotales.add(generarVenta((long) 0052, LocalDate.of(2025,06,24),sucursal2.getPuntoDeVenta()+"-22222247", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta2, detalleVenta5)));
        ventasTotales.add(generarVenta((long) 0053, LocalDate.of(2025,06,25),sucursal2.getPuntoDeVenta()+"-22222248", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta11)));
        ventasTotales.add(generarVenta((long) 0054, LocalDate.of(2025,06,26),sucursal2.getPuntoDeVenta()+"-22222249", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta5, detalleVenta1)));
        ventasTotales.add(generarVenta((long) 0055, LocalDate.of(2025,06,26),sucursal2.getPuntoDeVenta()+"-22222250", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta7)));
        ventasTotales.add(generarVenta((long) 0056, LocalDate.of(2025,06,26),sucursal2.getPuntoDeVenta()+"-22222251", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta11, detalleVenta6)));
        ventasTotales.add(generarVenta((long) 0057, LocalDate.of(2025,06,27),sucursal2.getPuntoDeVenta()+"-22222252", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta6)));
        ventasTotales.add(generarVenta((long) 58l, LocalDate.of(2025,06,27),sucursal2.getPuntoDeVenta()+"-22222253", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta11, detalleVenta7)));
        ventasTotales.add(generarVenta((long) 59l, LocalDate.of(2025,06,28),sucursal2.getPuntoDeVenta()+"-22222254", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta7)));
        ventasTotales.add(generarVenta((long) 0060, LocalDate.of(2025,06,29),sucursal2.getPuntoDeVenta()+"-22222255", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta11, detalleVenta7)));
        ventasTotales.add(generarVenta((long) 0061, LocalDate.of(2025,06,30),sucursal2.getPuntoDeVenta()+"-22222256", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta14)));
        ventasTotales.add(generarVenta((long) 0062, LocalDate.of(2025,06,30),sucursal2.getPuntoDeVenta()+"-22222257", sucursal2, empleadoSucursal2Atencion, empleadoSucursal2Cobro, List.of(detalleVenta2)));
        

        // Fin de carga de datos

        // Convertir a JSON con Jackson
        ObjectMapper mapper = JacksonUtils.getMapper();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("miFarmaciaDB");
            MongoCollection<Document> coleccionVentas = database.getCollection("ventas");

            // Convertir la venta a JSON y luego a Document (Mongo)
            List<Document> documentosVentas = ventasTotales.stream()
                    .map(venta -> {
                        try {
                            String json = mapper.writeValueAsString(venta);
                            return Document.parse(json);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();

            coleccionVentas.insertMany(documentosVentas);

            System.out.println("Venta insertada exitosamente en MongoDB");

            realizarConsulta1(coleccionVentas);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



        // ENTREGA 1

        /*
        // Crear el cliente
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        // Conectarse a la base "test" (si no existe, Mongo la crea)
        MongoDatabase database = mongoClient.getDatabase("test");

        // Insertar un documento de ejemplo en la colección "myCollection"
        MongoCollection<Document> collection = database.getCollection("myCollection");
        Document doc = new Document("name", "Grupo M")
                .append("role", "BD2");
        collection.insertOne(doc);

        System.out.println("Conexión exitosa a MongoDB y documento insertado!");

        // Siempre cerrar la conexión al terminar
        mongoClient.close();
        */



    }

    private static Venta generarVenta(Long idVenta, LocalDate fechaVenta, String nroTicket, Sucursal sucursal, Empleado empleadoAtencion, Empleado empleadoCobro, List<DetalleVenta> detallesVenta) {
        // Crear venta
        Venta venta1 = new Venta();
        venta1.setIdVenta(idVenta);
        venta1.setFechaVenta(fechaVenta);
        venta1.setNumeroTicket(nroTicket);
        venta1.setPuntoVenta(sucursal.getPuntoDeVenta());
        
        double total = detallesVenta.stream()
                .mapToDouble(DetalleVenta::getSubTotal)
                .sum();

        venta1.setTotalVenta(total);
        venta1.setFormaPago("EFECTIVO");
        venta1.setEmpleadoAtencion(empleadoAtencion);
        venta1.setEmpleadoCobro(empleadoCobro);
        venta1.setDetallesVenta(detallesVenta);
        return venta1;
    }

    private static Empleado getEmpleado4(ObraSocial obraSocial) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(0004);
        empleado.setNombreEmpleado("Ricardo");
        empleado.setApellidoEmpleado("Fort");
        empleado.setDniEmpleado("99888777");
        empleado.setCuil("20998887773");
        empleado.setNroAfiliadoObraSocialEmpleado("AF829402");
        empleado.setObraSocial(obraSocial);
        return empleado;
    }

    private static Empleado getEmpleado3(ObraSocial obraSocial) {
        Empleado empleado3 = new Empleado();
        empleado3.setIdEmpleado(0003);
        empleado3.setNombreEmpleado("Roberto");
        empleado3.setApellidoEmpleado("Garcia");
        empleado3.setDniEmpleado("7845653252");
        empleado3.setCuil("2352568522");
        empleado3.setNroAfiliadoObraSocialEmpleado("AF124512");
        empleado3.setObraSocial(obraSocial);
        return empleado3;
    }

    private static Empleado getEmpleado2(ObraSocial obraSocial) {
        Empleado empleado2 = new Empleado();
        empleado2.setIdEmpleado(0002);
        empleado2.setNombreEmpleado("Oscar");
        empleado2.setApellidoEmpleado("Gómez");
        empleado2.setDniEmpleado("12345678");
        empleado2.setCuil("2365788745");
        empleado2.setNroAfiliadoObraSocialEmpleado("AF652845");
        empleado2.setObraSocial(obraSocial);
        return empleado2;
    }

    private static Empleado getEmpleado1(ObraSocial obraSocial) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(0001);
        empleado.setNombreEmpleado("Lucía");
        empleado.setApellidoEmpleado("Gómez");
        empleado.setDniEmpleado("29333444");
        empleado.setCuil("2245454545");
        empleado.setNroAfiliadoObraSocialEmpleado("AF789012");
        empleado.setObraSocial(obraSocial);
        return empleado;
    }

    private static void realizarConsulta1(MongoCollection<Document> coleccionVentas) {
        var pipeline = Arrays.asList(
                Aggregates.match(Filters.and(
                        Filters.gte("fechaVenta", "2025-06-01"),
                        Filters.lte("fechaVenta", "2025-06-06")
                )),
                Aggregates.group(
                        "$puntoVenta",
                        Accumulators.sum("totalSucursal", "$totalVenta"),
                        Accumulators.sum("cantidadVentas", 1),
                        Accumulators.push("detalles", "$detallesVenta")
                ),
                Aggregates.group(
                        null,
                        Accumulators.sum("totalGeneral", "$totalSucursal"),
                        Accumulators.push("porSucursal", new Document("puntoVenta", "$_id")
                                .append("totalSucursal", "$totalSucursal")
                                .append("cantidadVentas", "$cantidadVentas")
                                .append("detalles", "$detalles"))
                )
        );

        coleccionVentas.aggregate(pipeline).forEach(doc -> {
            System.out.println(doc.toJson());
        });
    }


}