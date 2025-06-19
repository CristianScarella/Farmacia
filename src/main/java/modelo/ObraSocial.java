package modelo;

public class ObraSocial {
    private long idObraSocial;
    private String nombreObraSocial;

    public ObraSocial() {
    }

    public ObraSocial(int idObraSocial, String nombreObraSocial) {
        this.idObraSocial = idObraSocial;
        this.nombreObraSocial = nombreObraSocial;
    }

    public long getIdObraSocial() {
        return idObraSocial;
    }

    public void setIdObraSocial(long idObraSocial) {
        this.idObraSocial = idObraSocial;
    }

    public String getNombreObraSocial() {
        return nombreObraSocial;
    }

    public void setNombreObraSocial(String nombreObraSocial) {
        this.nombreObraSocial = nombreObraSocial;
    }
}
