/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import configuracion.Constantes;
import db.Archivo;
import db.Configuracion;
import db.Consulta;
import db.DatoVariable;
import db.EncabezadoLiquidacion;
import db.Estrato;
import db.Estudiante;
import db.Ingreso;
import db.Liquidacion;
import db.NombreDatasource;
import db.Patrimonio;
import fachade.ArchivoRepository;
import fachade.ConfiguracionRepository;
import fachade.ConsultaRepository;
import fachade.DatoVariableRepository;
import fachade.EncabezadoLiquidacionRepository;
import fachade.EstratoRepository;
import fachade.EstudianteRepository;
import fachade.IngresoRepository;
import fachade.LiquidacionRepository;
import fachade.PatrimonioRepository;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Transient;
import org.hibernate.JDBCException;
import services.NombreDatasourceService;

/**
 *
 * @author jk
 */
@Named(value = "cargar")
@ViewScoped
public class CargarController implements Serializable {

    @Inject
    private NombreDatasourceService datasourceService;
    @Inject
    private ConsultaRepository consultaRepository;
    @Inject
    private ArchivoRepository archivoRepository;
    @Inject
    private EstudianteRepository er;
    @Inject
    private EncabezadoLiquidacionRepository elr;
    @Inject
    private LiquidacionRepository lr;
    @Inject
    private DatoVariableRepository datoVariableRepository;
    @Inject
    private EstratoRepository estratoRepository;
    @Inject
    private PatrimonioRepository patrimonioRepository;
    @Inject
    private IngresoRepository ingresoRepository;
    @Inject
    private ConfiguracionRepository configuracionRepository;

    private Consulta consulta;

    private List<Consulta> consultaList;

    private List<Estudiante> listadoEstudiantes;

    private List<Integer> anyoList;

    private Configuracion configuracion;

    private int anyoLiquidacion;
    private String semestre;

    public int getAnyoLiquidacion() {
        return anyoLiquidacion;
    }

    public void setAnyoLiquidacion(int anyoLiquidacion) {
        this.anyoLiquidacion = anyoLiquidacion;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public List<Integer> getAnyoList() {
        return anyoList;
    }

    public void setAnyoList(List<Integer> anyoList) {
        this.anyoList = anyoList;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public List<Consulta> getConsultaList() {
        return consultaList;
    }

    public void setConsultaList(List<Consulta> consultaList) {
        this.consultaList = consultaList;
    }

    public List<Estudiante> getListadoEstudiantes() {
        return listadoEstudiantes;
    }

    public void setListadoEstudiantes(List<Estudiante> listadoEstudiantes) {
        this.listadoEstudiantes = listadoEstudiantes;
    }

    public void iniciar() {
        this.listadoEstudiantes = new ArrayList<>();
        this.consultaList = consultaRepository.findAll();
        this.anyoList = new ArrayList<>();
        for (int i = -10; i < 10; i++) {
            this.anyoList.add(Calendar.getInstance().get(Calendar.YEAR) + i);
        }
        this.configuracion = configuracionRepository.findBy(1L);
    }

    public void cargar() {
        System.err.println(this.anyoLiquidacion);
        System.err.println(this.semestre);
        
        listadoEstudiantes = datasourceService.cargarEstudiantesConsulta(
                    consulta.getNombreDatasource().getNombre(), 
                    consulta.getTextoSql(),
                    this.anyoLiquidacion, this.semestre);
    }

    public List<Archivo> soportes(String PEOPLE_CODE_ID) {
        return archivoRepository.findAllByPEOPLE_CODE_ID(PEOPLE_CODE_ID);
    }

    public String liquidar() {
        EncabezadoLiquidacion encabezadoLiquidacion = new EncabezadoLiquidacion();
        encabezadoLiquidacion.setFechaLiquidacion(new Date());
        encabezadoLiquidacion.setAnyoLiquidacion(anyoLiquidacion);
        encabezadoLiquidacion.setSemestre(semestre);
        encabezadoLiquidacion.setLiquidadoPor(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        encabezadoLiquidacion = elr.saveAndFlush(encabezadoLiquidacion);

        Liquidacion liquidacion;
        DatoVariable datoVariable = null;
        double smlv = 1;
        datoVariable = datoVariableRepository.findOptionalByAnyoAndNombre(anyoLiquidacion, "SMLV");
        if (datoVariable != null) {
            smlv = datoVariable.getValor();
        }

        for (Estudiante estudiante : listadoEstudiantes) {

            if (estudiante.getLiquidar()) {

                try {
                    liquidacion = new Liquidacion();
                    liquidacion.setFecha(encabezadoLiquidacion.getFechaLiquidacion());
                    liquidacion.setEncabezadoLiquidacion(encabezadoLiquidacion);
                    liquidacion.setPEOPLE_CODE_ID(estudiante.getPEOPLE_CODE_ID());
                    liquidacion.setAnyoLiquidacion(anyoLiquidacion);
                    liquidacion.setSemestre(semestre);
                    liquidacion.setApellidos(estudiante.getApellidos());
                    liquidacion.setNombres(estudiante.getNombres());
                    liquidacion.setNombrePrograma(estudiante.getNombrePrograma());
                    liquidacion.setCodigoPrograma(estudiante.getCodigoPrograma());
                    liquidacion.setUsuario(encabezadoLiquidacion.getLiquidadoPor());

                    if (estudiante.getId() != null) {

                        if ("Extrangero".equals(estudiante.getNacionalidad())) {
                            liquidacion.setNacionalidad("Extrangero");
                            liquidacion.setValorMatricula(smlv*6);
                        } else {
                            
                            //validar los soportes...
                            liquidacion.setNacionalidad("Colombiano");
                            liquidacion.setEstrato(estudiante.getEstrato());
                            liquidacion.setPatrimonio(estudiante.getPatrimonio());
                            liquidacion.setIngreso(estudiante.getIngreso());
                            liquidacion.setUltimoAnyoPago(estudiante.getUltimoAnyoPago());
                            liquidacion.setUltimoPago(estudiante.getUltimoPago());

                            //Inicio proceso liquidación
                            //Diferencia Años:
                            int diferenciaAnyos = 0;
                            if ((anyoLiquidacion - estudiante.getUltimoAnyoPago()) <= 10) {
                                diferenciaAnyos = anyoLiquidacion - estudiante.getUltimoAnyoPago();
                            } else {
                                diferenciaAnyos = 10;
                            }

                            //promedio IPC
                            datoVariable = null;
                            double promedioIPC = 0;
                            int contador = 0;
                            double liquidacionSecundaria = 0;
                            double liquidacionFinal = 0;

                            if (estudiante.getUltimoAnyoPago() == 0) {
                                //preguntar estudiante de colegio oficial

                            } else {
                                for (int i = 0; i < diferenciaAnyos; i++) {
                                    datoVariable = datoVariableRepository.findOptionalByAnyoAndNombre(
                                            anyoLiquidacion - 1 - i, Constantes.IPC);

                                    if (datoVariable == null) {
                                        System.out.println("NULL Iteracion: " + (anyoLiquidacion - 1 - i));

                                    } else {
                                        promedioIPC += datoVariable.getValor();
                                        System.out.println("Iteracion: " + (anyoLiquidacion - 1 - i));
                                        System.err.println(datoVariable);
                                        contador++;
                                    }
                                }

                                if (contador > 0) {
                                    promedioIPC = (promedioIPC / contador);
                                    System.err.println("calculado PromedioIPC: " + promedioIPC);
                                }

                                liquidacionSecundaria = 4 * estudiante.getUltimoPago() * (Math.pow(1 + promedioIPC, diferenciaAnyos));

                            }

                            liquidacionFinal = 0.4 * liquidacionSecundaria;
                            System.err.println("Liquidacion Secundaria: " + liquidacionSecundaria);

                            //liquidacion por estrato
                            double porcentajeEstrato = 1;

                            datoVariable = datoVariableRepository.findOptionalByAnyoAndNombre(anyoLiquidacion, "SMLV");
                            if (datoVariable != null) {
                                smlv = datoVariable.getValor();
                            }

                            Estrato estratoTemporal = estratoRepository.findOptionalByAnyoAndEstrato(anyoLiquidacion, estudiante.getEstrato());
                            if (estratoTemporal != null) {
                                porcentajeEstrato = estratoTemporal.getValorSMLV();
                            }

                            double liquidacionEstrato = smlv * porcentajeEstrato;

                            System.out.println("Estrato: " + estudiante.getEstrato()
                                    + " Porcentaje: " + porcentajeEstrato
                                    + " Total" + liquidacionEstrato);
                            liquidacionFinal = liquidacionFinal + liquidacionEstrato;

//////////////////////////////////////////////////////////////////////////////
                            //liquidacion por patrimonio
                            double porcentajePatrimonio = 1;

                            double equivalenciaPatrimonio = estudiante.getPatrimonio() / smlv;
                            int equivalenciaPatrimonioEntero = (int) Math.ceil(equivalenciaPatrimonio);

                            System.out.println("Equivalencia patrimonio: " + equivalenciaPatrimonioEntero);
                            System.out.println("Año liquidacion: " + anyoLiquidacion);

                            Patrimonio patrimonioTemporal = patrimonioRepository.
                                    findByEquivalenciaAndAnyo(
                                            equivalenciaPatrimonioEntero,
                                            anyoLiquidacion);

                            System.err.println("Patrimonio Temporal: " + patrimonioTemporal);

                            if (patrimonioTemporal != null) {
                                porcentajePatrimonio = patrimonioTemporal.getValorSMLV();
                            }

                            double liquidacionPatrimonio = smlv * porcentajePatrimonio;

                            System.out.println("Patrimonio: " + estudiante.getPatrimonio()
                                    + " Porcentaje: " + porcentajePatrimonio
                                    + " Total" + liquidacionPatrimonio);

                            liquidacionFinal = liquidacionFinal + liquidacionPatrimonio;

///////////////////////////////////////////////////////////////////
                            //liquidacion por ingreso
                            double porcentajeIngreso = 1;
                            double equivalenciaIngreso = estudiante.getIngreso() / smlv;
                            int equivalenciaIngresoEntero = (int) Math.ceil(equivalenciaIngreso);

                            System.out.println("Equivalencia Ingreso: " + equivalenciaIngresoEntero);

                            Ingreso ingresoTemporal = ingresoRepository.
                                    findByEquivalenciaAndAnyo(
                                            equivalenciaIngresoEntero,
                                            anyoLiquidacion);
                            if (ingresoTemporal != null) {
                                porcentajeIngreso = ingresoTemporal.getValorSMLV();
                            }

                            double liquidacionIngreso = smlv * porcentajeIngreso;

                            System.out.println("Ingreso: " + estudiante.getIngreso()
                                    + " Porcentaje: " + porcentajeIngreso
                                    + " Total" + liquidacionIngreso);

                            liquidacionFinal = liquidacionFinal + liquidacionIngreso;

                            liquidacion.setValorMatricula(liquidacionFinal);
                        }

                        liquidacion.setEstado(Constantes.ESTADO_PRE_LIQUIDADO);
                        estudiante.setLiquidado(Boolean.TRUE);
                        estudiante = er.saveAndFlush(estudiante);

                    } else {
                        //este estudiante no se ha actualizado
                        liquidacion.setEstado(Constantes.ESTADO_SIN_ACTUALIZAR);

                    }

                    liquidacion = lr.saveAndFlush(liquidacion);

                } catch (JDBCException e) {
                    System.err.println("Falla al liquidar: " + e.getMessage());
                }
            }
        }
        return "/pages/reporte?faces-redirect=true&amp&id=" + encabezadoLiquidacion.getId();
    }

    public void seleccionarTodos() {
        listadoEstudiantes.forEach((estudiante) -> {
            estudiante.setLiquidar(!estudiante.getLiquidar());
        });
    }
}
