package configuracion;

import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

    @Inject private HttpServletRequest request;
    
    private static final long serialVersionUID = 1L;
    
    private String rutaFisicaArchivos;
    private String rutaWebArchivos;

    public String getRutaFisicaArchivos() {
        return rutaFisicaArchivos;
    }

    public void setRutaFisicaArchivos(String rutaFisicaArchivos) {
        this.rutaFisicaArchivos = rutaFisicaArchivos;
    }

    public String getRutaWebArchivos() {
        return rutaWebArchivos;
    }

    public void setRutaWebArchivos(String rutaWebArchivos) {
        this.rutaWebArchivos = rutaWebArchivos;
    }
    
    @PostConstruct
    public void iniciar(){

        rutaFisicaArchivos = "/home/files/formulario";
        rutaWebArchivos = "http://"+request.getServerName()+":"+request.getServerPort()+"/archivos/formulario";        
    }

}
