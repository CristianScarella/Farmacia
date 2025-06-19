package modelo;

public class Sucursal {
    private long idSucursal;
    private long puntoDeVenta;
    private Domicilio domicilio;
    private Empleado encargado;

    public Sucursal() {
    }

    public Sucursal(long idSucursal, long puntoDeVenta, Domicilio domicilio, Empleado encargado) {
        this.idSucursal = idSucursal;
        this.puntoDeVenta = puntoDeVenta;
        this.domicilio = domicilio;
        this.encargado = encargado;
    }

    public long getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(long idSucursal) {
        this.idSucursal = idSucursal;
    }

    public long getPuntoDeVenta() {
        return puntoDeVenta;
    }

    public void setPuntoDeVenta(long puntoDeVenta) {
        this.puntoDeVenta = puntoDeVenta;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Empleado getEncargado() {
        return encargado;
    }

    public void setEncargado(Empleado encargado) {
        this.encargado = encargado;
    }
}
