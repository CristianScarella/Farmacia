package modelo;

public class Producto {
    private int idProducto;
    private boolean esMedicamento;
    private String descripcionProducto;
    private String laboratorio;
    private String codigoProducto;
    private double precioProducto;

    public Producto(int idProducto, boolean esMedicamento, String descripcionProducto, String laboratorio, String codigoProducto, double precioProducto) {
        this.idProducto = idProducto;
        this.esMedicamento = esMedicamento;
        this.descripcionProducto = descripcionProducto;
        this.laboratorio = laboratorio;
        this.codigoProducto = codigoProducto;
        this.precioProducto = precioProducto;
    }

    public Producto(){}

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public boolean isEsMedicamento() {
        return esMedicamento;
    }

    public void setEsMedicamento(boolean esMedicamento) {
        this.esMedicamento = esMedicamento;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(double precioProducto) {
        this.precioProducto = precioProducto;
    }
}
