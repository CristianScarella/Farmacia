package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import modelo.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Accumulators.*;

public class TestMongo {
    public static void main(String[] args) {
        //ENTREGA 2
                
        //obrasocial
        ObraSocial obraSocial = new ObraSocial();
        obraSocial.setIdObraSocial(0001);
        obraSocial.setNombreObraSocial("OSDE");

        //crear empleado
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(0001);
        empleado.setNombreEmpleado("Lucía");
        empleado.setApellidoEmpleado("Gómez");
        empleado.setDniEmpleado("29333444");
        empleado.setCuil("2245454545");
        empleado.setNroAfiliadoObraSocialEmpleado("AF789012");
        empleado.setObraSocial(obraSocial);

        Empleado empleado2 = new Empleado();
        empleado.setIdEmpleado(0002);
        empleado.setNombreEmpleado("Oscar");
        empleado.setApellidoEmpleado("Gómez");
        empleado.setDniEmpleado("12345678");
        empleado.setCuil("2365788745");
        empleado.setNroAfiliadoObraSocialEmpleado("AF652845");
        empleado.setObraSocial(obraSocial);

        Empleado empleado3 = new Empleado();
        empleado.setIdEmpleado(0003);
        empleado.setNombreEmpleado("Roberto");
        empleado.setApellidoEmpleado("Garcia");
        empleado.setDniEmpleado("7845653252");
        empleado.setCuil("2352568522");
        empleado.setNroAfiliadoObraSocialEmpleado("AF124512");
        empleado.setObraSocial(obraSocial);

        //cliente
        Cliente cliente = new Cliente();
        cliente.setIdCliente(0001);
        cliente.setNombreCliente("Juan");
        cliente.setApellidoCliente("Pérez");
        cliente.setDniCliente("30123456");
        cliente.setNroAfiliadoObraSocialCliente("AF123456");
        cliente.setObraSocial(obraSocial);

        // Crear producto
        Producto producto = new Producto();
        producto.setIdProducto(0001);
        producto.setDescripcionProducto("Ibuprofeno 600mg");
        producto.setLaboratorio("Bagó");
        producto.setCodigoProducto("IBU600");
        producto.setPrecioProducto(120.0);
        producto.setEsMedicamento(true);

        // Crear venta
        Venta venta = new Venta();
        venta.setIdVenta((long)0001);
        venta.setFechaVenta(LocalDate.now());
        venta.setNumeroTicket("TCK123456");
        venta.setPuntoVenta(0002);

        // Crear detalle de venta
        DetalleVenta detalle = new DetalleVenta();
        detalle.setProducto(producto);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(producto.getPrecioProducto());
        detalle.setSubTotal(producto.getPrecioProducto() * detalle.getCantidad());

        venta.setTotalVenta(detalle.getSubTotal());
        venta.setFormaPago("EFECTIVO");
        venta.setEmpleadoAtencion(empleado);
        venta.setEmpleadoCobro(empleado2);
        venta.setDetallesVenta(List.of(detalle));

        //provincia
        Provincia provincia = new Provincia();
        provincia.setIdProvincia(0001);
        provincia.setNombreProvincia("Buenos Aires");

        //localidad
        Localidad localidad = new Localidad();
        localidad.setIdLocalidad(0001);
        localidad.setNombreLocalidad("La Plata");
        localidad.setProvincia(provincia);

      //domicilio
        Domicilio domicilio = new Domicilio();
        domicilio.setIdDomicilio(0001);
        domicilio.setCalle("Calle 7");
        domicilio.setNumero("1234");
        domicilio.setLocalidad(localidad);

        //sucursal
        Sucursal sucursal = new Sucursal();
        sucursal.setIdSucursal(0001);
        sucursal.setDomicilio(domicilio);
        sucursal.setEncargado(empleado3);

        // Convertir a JSON con Jackson
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // para escribir fechas como texto (ISO)

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("miFarmaciaDB");
            MongoCollection<Document> coleccionVentas = database.getCollection("ventas");

            // Convertir la venta a JSON y luego a Document (Mongo)
            String ventaJson = mapper.writeValueAsString(venta);
            Document ventaDoc = Document.parse(ventaJson);

            // Insertar en MongoDB
            coleccionVentas.insertOne(ventaDoc);
 
            var pipeline = Arrays.asList(
                    Aggregates.match(Filters.and(
                            Filters.gte("fechaVenta", "2025-06-01"),
                            Filters.lte("fechaVenta", "2025-06-30")
                    )),
                    Aggregates.group(
                            "$puntoVenta",
                            Accumulators.sum("totalSucursal", "$totalVenta"),
                            Accumulators.sum("cantidadVentas", 1)
                    ),
                    Aggregates.group(
                            null,
                            Accumulators.sum("totalGeneral", "$totalSucursal"),
                            Accumulators.push("porSucursal", new Document("puntoVenta", "$_id")
                                    .append("totalSucursal", "$totalSucursal")
                                    .append("cantidadVentas", "$cantidadVentas"))
                    )
            );

            coleccionVentas.aggregate(pipeline).forEach(doc -> {
                System.out.println(doc.toJson());
            });

            System.out.println("Venta insertada exitosamente en MongoDB");
        } catch (JsonProcessingException e) {
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


}