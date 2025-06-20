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
        Domicilio domicilio = new Domicilio();
        domicilio.setIdDomicilio(0001);
        domicilio.setCalle("Calle 7");
        domicilio.setNumero("1234");
        domicilio.setLocalidad(localidad);

        Domicilio domicilio2 = new Domicilio();
        domicilio2.setIdDomicilio(0002);
        domicilio2.setCalle("Calle 8");
        domicilio2.setNumero("5678");
        domicilio2.setLocalidad(localidad);

        //crear empleado
        Empleado empleadoSucursal1Atencion = getEmpleado1(obraSocial);
        Empleado empleadoSucursal2Atencion = getEmpleado2(obraSocial);
        Empleado empleadoSucursal1Cobro = getEmpleado3(obraSocial);
        Empleado empleadoSucursal2Cobro = getEmpleado4(obraSocial);

        //sucursal
        Sucursal sucursal1 = new Sucursal();
        sucursal1.setIdSucursal(0001);
        sucursal1.setPuntoDeVenta(0001l);
        sucursal1.setDomicilio(domicilio);
        sucursal1.setEncargado(empleadoSucursal1Cobro);

        Sucursal sucursal2 = new Sucursal();
        sucursal2.setIdSucursal(0002);
        sucursal2.setPuntoDeVenta(0002l);
        sucursal2.setDomicilio(domicilio2);
        sucursal2.setEncargado(empleadoSucursal2Cobro);

        empleadoSucursal1Atencion.setSucursal(sucursal1);
        empleadoSucursal2Atencion.setSucursal(sucursal2);

        //cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente(0001);
        cliente.setNombreCliente("Juan");
        cliente.setApellidoCliente("Pérez");
        cliente.setDniCliente("30123456");
        cliente.setNroAfiliadoObraSocialCliente("AF123456");
        cliente.setObraSocial(obraSocial);

        // Crear producto
        Producto producto1 = new Producto(0001, true, "Ibuprofeno 600mg", "Bagó", "IBU600", 120.0);
        Producto producto2 = new Producto(0002, false, "Perfume Dior Sauvage 100ml", "Dior", "DIOR100", 42000.0);

        Venta venta1 = generarVenta((long) 0001, LocalDate.of(2025,06,16),sucursal2.getPuntoDeVenta()+"-12345678", 2, sucursal2, producto1, empleadoSucursal2Atencion, empleadoSucursal2Cobro);
        Venta venta2 = generarVenta((long) 0002, LocalDate.of(2025,06,15),sucursal2.getPuntoDeVenta()+"-11324312", 1, sucursal2, producto2, empleadoSucursal2Atencion, empleadoSucursal2Cobro);
        Venta venta3 = generarVenta((long) 0003, LocalDate.of(2025,06,16),sucursal1.getPuntoDeVenta()+"-11234323", 3, sucursal1, producto1, empleadoSucursal1Atencion, empleadoSucursal1Cobro);

        // Fin de carga de datos

        // Convertir a JSON con Jackson
        ObjectMapper mapper = JacksonUtils.getMapper();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("miFarmaciaDB");
            MongoCollection<Document> coleccionVentas = database.getCollection("ventas");

            // Convertir la venta a JSON y luego a Document (Mongo)
            List<Venta> ventas = List.of(venta1, venta2, venta3);
            List<Document> documentosVentas = ventas.stream()
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

    private static Venta generarVenta(Long idVenta, LocalDate fechaVenta, String nroTicket, int cantidad, Sucursal sucursal, Producto producto, Empleado empleadoAtencion, Empleado empleadoCobro) {
        // Crear venta
        Venta venta1 = new Venta();
        venta1.setIdVenta(idVenta);
        venta1.setFechaVenta(fechaVenta);
        venta1.setNumeroTicket(nroTicket);
        venta1.setPuntoVenta(sucursal.getPuntoDeVenta());

        // Crear detalle de venta
        DetalleVenta detalle = new DetalleVenta(producto, cantidad, producto.getPrecioProducto());

        venta1.setTotalVenta(detalle.getSubTotal());
        venta1.setFormaPago("EFECTIVO");
        venta1.setEmpleadoAtencion(empleadoAtencion);
        venta1.setEmpleadoCobro(empleadoCobro);
        venta1.setDetallesVenta(List.of(detalle));
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
                        Filters.lte("fechaVenta", "2025-06-30")
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