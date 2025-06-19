package modelo;

public class Cliente {
    private long idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String dniCliente;
    private String nroAfiliadoObraSocialCliente;
    private Domicilio domicilio;
    private ObraSocial obraSocial;

    public Cliente() {
    }

    public Cliente(long idCliente, String nombreCliente, String apellidoCliente, String dniCliente, String nroAfiliadoObraSocialCliente, Domicilio domicilio, ObraSocial obraSocial) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.dniCliente = dniCliente;
        this.nroAfiliadoObraSocialCliente = nroAfiliadoObraSocialCliente;
        this.domicilio = domicilio;
        this.obraSocial = obraSocial;
    }

    public long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getNroAfiliadoObraSocialCliente() {
        return nroAfiliadoObraSocialCliente;
    }

    public void setNroAfiliadoObraSocialCliente(String nroAfiliadoObraSocialCliente) {
        this.nroAfiliadoObraSocialCliente = nroAfiliadoObraSocialCliente;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }
}
