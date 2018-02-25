/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import configuracion.Constantes;
import db.Archivo;
import db.Configuracion;
import db.DatoVariable;
import db.Estrato;
import db.Estudiante;
import db.Ingreso;
import db.Liquidacion;
import db.Patrimonio;
import fachade.ArchivoRepository;
import fachade.DatoVariableRepository;
import fachade.EstratoRepository;
import fachade.EstudianteRepository;
import fachade.IngresoRepository;
import fachade.PatrimonioRepository;
import java.io.Serializable;
import java.util.Date;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.JDBCException;

/**
 *
 * @author jk
 */
@Named
public class LiquidacionService implements Serializable {

    @Inject
    private EstudianteRepository estudianteRepository;
    @Inject
    private ArchivoRepository archivoRepository;
    @Inject
    private DatoVariableRepository datoVariableRepository;
    @Inject
    private EstratoRepository estratoRepository;
    @Inject
    private PatrimonioRepository patrimonioRepository;
    @Inject
    private IngresoRepository ingresoRepository;

    public boolean validarSoportes(String PEOPLE_CODE_ID) {

        Archivo archivo;
        Estudiante estudiante = estudianteRepository.findByPEOPLE_CODE_ID(PEOPLE_CODE_ID);

        if (estudiante == null) {
            return false;
        }

        if (estudiante.getNacionalidad() == null) {
            return false;
        }

        archivo = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_IDENTIFICACION, PEOPLE_CODE_ID);
        if (archivo == null) {
            return false;
        }

        if (estudiante.getNacionalidad().equals("Extrangero")) {
            return true;
        }

        if (estudiante.getEstrato() == 0) {
            return false;
        }
        archivo = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_ESTRATO, PEOPLE_CODE_ID);

        if (archivo == null) {
            return false;
        }

        //validar tipo institución educativa
        if (estudiante.getTipoColegio() == null) {
            return false;
        }

        //validar último año de pago del volante
        if (estudiante.getUltimoAnyoPago() == 0) {
            return false;
        }

        //validar soporte para ultima mensualidad
        archivo = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_MENSUALIDAD, PEOPLE_CODE_ID);
        if (archivo == null) {
            return false;
        }

        //validar el patrimonio
        if (estudiante.getPatrimonio() <= 0) {
            return false;
        }

        //validar soporte para instrumentos públicos
        archivo = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_INSTRUMENTOS, PEOPLE_CODE_ID);

        if (archivo == null) {
            return false;
        }

        //declaracion de renta o balance firmado por un contador
        Archivo archivoDeclaracionRenta = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_DECLARACION, PEOPLE_CODE_ID);
        Archivo archivoBalance = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_BALANCE, PEOPLE_CODE_ID);

        if ((archivoDeclaracionRenta == null) && (archivoBalance == null)) {
            return false;
        }

        //validar ingresos        
        if (estudiante.getIngreso() <= 0) {
            return false;
        }

        Archivo archivoIngresosRetenciones = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_INGRESO_RETENCION, PEOPLE_CODE_ID);
        Archivo archivoPerdidasGanancias = archivoRepository.findByTipoAndPEOPLE_CODE_ID(Constantes.TIPO_PERDIDA_GANANCIA, PEOPLE_CODE_ID);

        //validar soportes para ingreso
        //validar soporte para instrumentos públicos
        if ((archivoDeclaracionRenta == null) && (archivoIngresosRetenciones == null) && (archivoPerdidasGanancias == null)) {

            return false;
        }

        return true;
    }

    public Liquidacion liquidarMatricula(
            Estudiante estudiante,
            Configuracion configuracion,
            double smlv) {

        try {
            Liquidacion liquidacion = new Liquidacion();
            liquidacion.setFecha(new Date());
            //liquidacion.setEncabezadoLiquidacion(encabezadoLiquidacion);
            liquidacion.setPEOPLE_CODE_ID(estudiante.getPEOPLE_CODE_ID());
            liquidacion.setAnyoLiquidacion(configuracion.getAnyo());
            liquidacion.setSemestre(configuracion.getSemestre());
            liquidacion.setApellidos(estudiante.getApellidos());
            liquidacion.setNombres(estudiante.getNombres());
            liquidacion.setNombrePrograma(estudiante.getNombrePrograma());
            liquidacion.setCodigoPrograma(estudiante.getCodigoPrograma());
            //liquidacion.setUsuario(encabezadoLiquidacion.getLiquidadoPor());

            if ("Extrangero".equals(estudiante.getNacionalidad())) {
                liquidacion.setNacionalidad("Extrangero");
                liquidacion.setValorMatricula(smlv * 6);
                return liquidacion;
            }

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
            if ((configuracion.getAnyo() - estudiante.getUltimoAnyoPago()) <= 10) {
                diferenciaAnyos = configuracion.getAnyo() - estudiante.getUltimoAnyoPago();
            } else {
                diferenciaAnyos = 10;
            }

            //promedio IPC
            DatoVariable datoVariable = null;
            double promedioIPC = 0;
            int contador = 0;
            double liquidacionSecundaria = 0;
            double liquidacionFinal = 0;

            if (estudiante.getUltimoAnyoPago() == 0) {
                //preguntar estudiante de colegio oficial

            } else {
                for (int i = 0; i < diferenciaAnyos; i++) {
                    datoVariable = datoVariableRepository.findOptionalByAnyoAndNombre(
                            configuracion.getAnyo() - 1 - i, Constantes.IPC);

                    if (datoVariable == null) {
                        System.out.println("NULL Iteracion: " + (configuracion.getAnyo() - 1 - i));

                    } else {
                        promedioIPC += datoVariable.getValor();
                        System.out.println("Iteracion: " + (configuracion.getAnyo() - 1 - i));
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

            Estrato estratoTemporal = estratoRepository.findOptionalByAnyoAndEstrato(configuracion.getAnyo(), estudiante.getEstrato());
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
            System.out.println("Año liquidacion: " + configuracion.getAnyo());

            Patrimonio patrimonioTemporal = patrimonioRepository.
                    findByEquivalenciaAndAnyo(
                            equivalenciaPatrimonioEntero,
                            configuracion.getAnyo());

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
                            configuracion.getAnyo());
            if (ingresoTemporal != null) {
                porcentajeIngreso = ingresoTemporal.getValorSMLV();
            }

            double liquidacionIngreso = smlv * porcentajeIngreso;

            System.out.println("Ingreso: " + estudiante.getIngreso()
                    + " Porcentaje: " + porcentajeIngreso
                    + " Total" + liquidacionIngreso);

            liquidacionFinal = liquidacionFinal + liquidacionIngreso;

            liquidacion.setValorMatricula(liquidacionFinal);

            liquidacion.setEstado(Constantes.ESTADO_PRE_LIQUIDADO);

            return liquidacion;
        } catch (JDBCException e) {
            System.err.println("Falla al liquidar: " + e.getMessage());
            return null;            
        }
        
    }

}
