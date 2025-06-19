package modelo;

public class Empleado {
    private long idEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String dniEmpleado;
    private String cuil;
    private String nroAfiliadoObraSocialEmpleado;
    private Domicilio domicilio;
    private ObraSocial obraSocial;
    private Sucursal sucursal;



    public Empleado() {
    }

    public Empleado(long idEmpleado, String nombreEmpleado, String apellidoEmpleado, String dniEmpleado, String cuil, String nroAfiliadoObraSocialEmpleado, Domicilio domicilio, ObraSocial obraSocial, Sucursal sucursal) {
        this.idEmpleado = idEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.dniEmpleado = dniEmpleado;
        this.cuil = cuil;
        this.nroAfiliadoObraSocialEmpleado = nroAfiliadoObraSocialEmpleado;
        this.domicilio = domicilio;
        this.obraSocial = obraSocial;
        this.sucursal = sucursal;

    }

    public long getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getDniEmpleado() {
        return dniEmpleado;
    }

    public void setDniEmpleado(String dniEmpleado) {
        this.dniEmpleado = dniEmpleado;
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getNroAfiliadoObraSocialEmpleado() {
        return nroAfiliadoObraSocialEmpleado;
    }

    public void setNroAfiliadoObraSocialEmpleado(String nroAfiliadoObraSocialEmpleado) {
        this.nroAfiliadoObraSocialEmpleado = nroAfiliadoObraSocialEmpleado;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }
}
