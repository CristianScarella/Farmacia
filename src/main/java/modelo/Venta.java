package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Venta {

    private Long idVenta;
    private LocalDate fechaVenta;
    private String numeroTicket;
    private long puntoVenta;
    private double totalVenta;
    private String formaPago;

    private Empleado empleadoAtencion;

    private Empleado empleadoCobro;

    private List<DetalleVenta> detallesVenta;

    public Venta(Long idVenta, LocalDate fechaVenta, String numeroTicket, long puntoVenta, double totalVenta, String formaPago, Empleado empleadoAtencion, Empleado empleadoCobro, List<DetalleVenta> detallesVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.numeroTicket = numeroTicket;
        this.puntoVenta = puntoVenta;
        this.totalVenta = totalVenta;
        this.formaPago = formaPago;
        this.empleadoAtencion = empleadoAtencion;
        this.empleadoCobro = empleadoCobro;
        this.detallesVenta = new ArrayList<>();
    }

    public Venta(){}

    public Long getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Long idVenta) {
        this.idVenta = idVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getNumeroTicket() {
        return numeroTicket;
    }

    public void setNumeroTicket(String numeroTicket) {
        this.numeroTicket = numeroTicket;
    }

    public long getPuntoVenta() {
        return puntoVenta;
    }

    public void setPuntoVenta(long puntoVenta) {
        this.puntoVenta = puntoVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public String getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(String formaPago) {
        this.formaPago = formaPago;
    }

    public Empleado getEmpleadoAtencion() {
        return empleadoAtencion;
    }

    public void setEmpleadoAtencion(Empleado empleadoAtencion) {
        this.empleadoAtencion = empleadoAtencion;
    }

    public Empleado getEmpleadoCobro() {
        return empleadoCobro;
    }

    public void setEmpleadoCobro(Empleado empleadoCobro) {
        this.empleadoCobro = empleadoCobro;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
}
