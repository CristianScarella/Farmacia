package org.example.utils;

import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;

public class MongoUtils {

	public static void realizarConsulta1(MongoCollection<Document> coleccionVentas, String fechaDesde, String fechaHasta) {
    	System.out.println("========================================");
    	System.out.println("Consulta 1");
    	
        var pipeline = Arrays.asList(
            Aggregates.match(Filters.and(
                Filters.gte("fechaVenta", fechaDesde),
                Filters.lte("fechaVenta", fechaHasta)
            )),
            Aggregates.group(
                "$puntoVenta",
                Accumulators.sum("cantidadVentas", 1)
            ),
            Aggregates.group(
                null,
                Accumulators.sum("totalCadena", "$cantidadVentas"),
                Accumulators.push("porSucursal", new Document("sucursal", "$_id")
                    .append("cantidad", "$cantidadVentas"))
            )
        );

        // Imprime cada uno de los resultados
        coleccionVentas.aggregate(pipeline).forEach(doc -> System.out.println(doc.toJson()));
        
        System.out.println("========================================\n");
    }

	public static void realizarConsulta3(MongoCollection<Document> coleccionVentas, String fechaDesde, String fechaHasta) {
    	System.out.println("========================================");
    	System.out.println("Consulta 3");

        var pipeline = Arrays.asList(
            Aggregates.match(Filters.and(
                Filters.gte("fechaVenta", fechaDesde),
                Filters.lte("fechaVenta", fechaHasta)
            )),
            Aggregates.group(
                "$puntoVenta",
                Accumulators.sum("totalSucursal", "$totalVenta")
            ),
            Aggregates.group(
                null,
                Accumulators.sum("totalCadena", "$totalSucursal"),
                Accumulators.push("porSucursal", new Document("sucursal", "$_id")
                    .append("total", "$totalSucursal"))
            )
        );

        // Imprime cada uno de los resultados
        coleccionVentas.aggregate(pipeline).forEach(doc -> System.out.println(doc.toJson()));
        
    	System.out.println("========================================\n");
    }

	public static void realizarConsulta4(MongoCollection<Document> coleccionVentas, String fechaDesde, String fechaHasta) {
    	System.out.println("========================================");
    	System.out.println("Consulta 4");

        var pipeline = Arrays.asList(
            Aggregates.match(Filters.and(
                Filters.gte("fechaVenta", fechaDesde),
                Filters.lte("fechaVenta", fechaHasta)
            )),
            Aggregates.unwind("$detallesVenta"),
            Aggregates.group(
                new Document("tipoProducto",
                    new Document("$cond", Arrays.asList(
                        "$detallesVenta.producto.esMedicamento",
                        "Farmacia",
                        "Perfumería"
                    ))
                ),
                Accumulators.sum("cantidad", "$detallesVenta.cantidad")
            )
        );

        // Imprime cada uno de los resultados
        coleccionVentas.aggregate(pipeline).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("========================================\n");
    }
    
	public static void realizarConsulta5(MongoCollection<Document> coleccionVentas) {
    	System.out.println("========================================");
    	System.out.println("Consulta 5");

        var pipeline = Arrays.asList(
            Aggregates.unwind("$detallesVenta"),
            Aggregates.group(
                new Document("producto", "$detallesVenta.producto.descripcionProducto")
                    .append("sucursal", "$puntoVenta"),
                Accumulators.sum("montoTotal", "$detallesVenta.subTotal")
            ),
            Aggregates.sort(new Document("montoTotal", -1))
        );

        // Imprime cada uno de los resultados
        coleccionVentas.aggregate(pipeline).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("========================================\n");
    }
    
	public static void realizarConsulta6(MongoCollection<Document> coleccionVentas) {
    	System.out.println("========================================");
    	System.out.println("Consulta 6");

        var pipeline = Arrays.asList(
            Aggregates.unwind("$detallesVenta"),
            Aggregates.group(
                new Document("producto", "$detallesVenta.producto.descripcionProducto")
                    .append("sucursal", "$puntoVenta"),
                Accumulators.sum("cantidadVendida", "$detallesVenta.cantidad")
            ),
            Aggregates.sort(new Document("cantidadVendida", -1))
        );

        // Imprime cada uno de los resultados
        coleccionVentas.aggregate(pipeline).forEach(doc -> System.out.println(doc.toJson()));

        System.out.println("========================================\n");
    }
}
