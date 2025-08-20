package supleAndrew.modelo;

public class UsuarioS
{
    private int id;
    private String usuario;
    private String contraseNIA;
    private int activo;

    //Constructor
    public UsuarioS(int id, String usuario, String contraseNIA, int activo) {
        this.id = id;
        this.usuario = usuario;
        this.contraseNIA = contraseNIA;
        this.activo = activo;
    }

    public UsuarioS(String usuario, String contraseNIA, int activo) {
        this.usuario = usuario;
        this.contraseNIA = contraseNIA;
        this.activo = activo;
    }

    // setter y getters
    public int getId() {
        return id;
    }
    public String getUsuario() {
        return usuario;
    }
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    public String getContraseNIA() {
        return contraseNIA;
    }
    public void setContraseNIA(String contraseNIA) {
        this.contraseNIA = contraseNIA;
    }
    public int getActivo() {
        return activo;
    }
    public void setActivo(int activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "id = " + id + ", usuario = " + usuario + ", contraseNIA = " + contraseNIA + ", activo = " + activo;
    }
}
