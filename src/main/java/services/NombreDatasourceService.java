/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import configuracion.Constantes;
import configuracion.Inicio;
import db.Estudiante;
import db.Liquidacion;
import db.NombreDatasource;
import fachade.EstudianteRepository;
import fachade.NombreDatasourceRepository;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author jk
 */
@Named(value = "ndsService")
public class NombreDatasourceService implements Serializable {

    @Inject
    private NombreDatasourceRepository nombreDatasourceRepository;
    @Inject
    private EstudianteRepository estudianteRepository;

    public List<NombreDatasource> getDatasources() {
        return nombreDatasourceRepository.findAll();
    }

    //Método que busca los estudiantes al momento de crear las consultas
    public List<Estudiante> cargarEstudiantes(String datasource, String sql) {
        Estudiante e;
        List<Estudiante> estudianteList = new ArrayList();
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup(datasource);

            try {
                Connection con = ds.getConnection();
                ResultSet rs = con.createStatement().executeQuery(sql);

                while (rs.next()) {
                    e = new Estudiante();
                    e.setPEOPLE_CODE_ID(rs.getString(Constantes.PEOPLE_CODE_ID));

                    e.setApellidos(rs.getString(Constantes.APELLIDOS));
                    e.setNombres(rs.getString(Constantes.NOMBRES));

                    e.setCodigoPrograma(rs.getString(Constantes.CODIGO_PROGRAMA));
                    e.setNombrePrograma(rs.getString(Constantes.NOMBRE_PROGRAMA));

                    estudianteList.add(e);
                }

                rs.close();
                con.close();
                return estudianteList;

            } catch (SQLException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    //Método que carga los estudiantes de un datasource seleccionado para luego preliquidarlos
    public List<Estudiante> cargarEstudiantesConsulta(String datasource, String sql) {
        Estudiante e;
        Estudiante actualizado; //estudiante que entró al formulario de actualizacion y actualizó sus datos
        List<Estudiante> estudianteList = new ArrayList();
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup(datasource);

            try {
                Connection con = ds.getConnection();
                ResultSet rs = con.createStatement().executeQuery(sql);

                while (rs.next()) {
                    e = new Estudiante();
                    e.setPEOPLE_CODE_ID(rs.getString(Constantes.PEOPLE_CODE_ID));

                    e.setApellidos(rs.getString(Constantes.APELLIDOS));
                    e.setNombres(rs.getString(Constantes.NOMBRES));

                    e.setCodigoPrograma(rs.getString(Constantes.CODIGO_PROGRAMA));
                    e.setNombrePrograma(rs.getString(Constantes.NOMBRE_PROGRAMA));

                    actualizado = estudianteRepository.findByPEOPLE_CODE_ID(e.getPEOPLE_CODE_ID());

                    if (actualizado != null) {
                        e.setId(actualizado.getId());
                        e.setNacionalidad(actualizado.getNacionalidad());
                        e.setEstrato(actualizado.getEstrato());
                        e.setUltimoAnyoPago(actualizado.getUltimoAnyoPago());
                        e.setUltimoPago(actualizado.getUltimoPago());
                        e.setPatrimonio(actualizado.getPatrimonio());
                        e.setIngreso(actualizado.getIngreso());
                    }

                    estudianteList.add(e);

                }
                rs.close();
                con.close();
                return estudianteList;

            } catch (SQLException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (NamingException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Estudiante> buscarEstudiantes(
            String identificacion, String apellidos, String nombres,
            int anyo, String semestre) {
        System.err.println("Estoy buscando estudiates");
        Estudiante e;
        List<Estudiante> estudianteList = new ArrayList();
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup("SQLServerUnicorDS");

            try {
                Connection con = ds.getConnection();

                String consulta
                        = "SELECT P.NICKNAME,P.PEOPLE_CODE_ID , "
                        + "P.FIRST_NAME +' '+P.MIDDLE_NAME as Nombres, "
                        + "P.LAST_NAME as Apellidos, A.CURRICULUM CodigoPrograma, "
                        + "cc.LONG_DESC NombrePrograma, A.ACADEMIC_YEAR as Anyo, "
                        + "A.ACADEMIC_TERM  as Semestre \n"
                        + " FROM PEOPLE P, ACADEMIC A , CODE_CURRICULUM cc \n"
                        + " WHERE A.PEOPLE_CODE_ID = P.PEOPLE_CODE_ID \n"
                        + "  and A.CURRICULUM = cc.CODE_VALUE_KEY \n"
                        + "  and A.ACADEMIC_SESSION = 'PREG01' \n"
                        + "  and a.ADMIT_YEAR = '" + anyo + "' \n"
                        + "  and a.ADMIT_TERM = '" + semestre + "' ";

                if ((identificacion != null) && (!identificacion.isEmpty())) {
                    consulta = consulta + "  and p.NICKNAME = '" + identificacion + "' ";
                }

                if ((apellidos != null) && (!apellidos.isEmpty())) {
                    consulta = consulta + "  and p.LAST_NAME like '%" + apellidos + "%' ";
                }

                if ((nombres != null) && (!nombres.isEmpty())) {
                    consulta = consulta + "  and P.FIRST_NAME like '%" + nombres + "%' ";
                }
                //System.err.println(consulta);

                ResultSet rs;

                try {
                    rs = con.createStatement().executeQuery(consulta);
                    while (rs.next()) {
                        e = new Estudiante();
                        e.setPEOPLE_CODE_ID(rs.getString(Constantes.PEOPLE_CODE_ID));

                        e.setApellidos(rs.getString(Constantes.APELLIDOS));
                        e.setNombres(rs.getString(Constantes.NOMBRES));

                        e.setCodigoPrograma(rs.getString(Constantes.CODIGO_PROGRAMA));
                        e.setNombrePrograma(rs.getString(Constantes.NOMBRE_PROGRAMA));

                        estudianteList.add(e);
                    }
                    rs.close();

                } catch (Exception e1) {
                    System.err.println("Error al ejecutar consulta");
                    System.err.println(e1.getMessage());
                }

                con.close();
                return estudianteList;

            } catch (SQLException ex) {
                System.err.println("Error Sql");
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (NamingException ex) {
            System.err.println("Error de nombre");
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String buscarPrimaryFlag(Liquidacion liquidacion) {
        String temporal = null;
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup("SQLServerUnicorDS");

            try {
                Connection con = ds.getConnection();

                String consulta
                        = "Select a.PRIMARY_FLAG \n"
                        + "from Campus.dbo.ACADEMIC a \n"
                        + "Where a.Academic_Year = '" + liquidacion.getAnyoLiquidacion() + "' "
                        + "and a.Academic_Term = '" + liquidacion.getSemestre() + "' "
                        + "and a.People_Code_Id = '" + liquidacion.getPEOPLE_CODE_ID() + "' "
                        + "and a.CURRICULUM = '" + liquidacion.getCodigoPrograma() + "' ";

                //System.err.println(consulta);
                ResultSet rs;

                try {
                    rs = con.createStatement().executeQuery(consulta);
                    rs.next();
                    temporal = rs.getString("PRIMARY_FLAG");
                    rs.close();

                } catch (Exception e1) {
                    System.err.println("Error al ejecutar consulta");
                    System.err.println(e1.getMessage());
                    return null;
                }

                con.close();
                return temporal;

            } catch (SQLException ex) {
                System.err.println("Error Sql");
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (NamingException ex) {
            System.err.println("Error de nombre");
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String buscarRefVolante(String flag, Liquidacion liquidacion) {
        String temporal = null;
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup("SQLServerUnicorDS");

            try {
                Connection con = ds.getConnection();

                String consulta
                        = "Select rv.NumRef \n"
                        + "From Campus_Complemento.dbo.Referencia_Volante rv \n "
                        + "Where rv.Academic_Year = '" + liquidacion.getAnyoLiquidacion() + "' \n "
                        + "and rv.Academic_Term = '" + liquidacion.getSemestre() + "' \n "
                        + "and rv.People_Code_Id = '" + liquidacion.getPEOPLE_CODE_ID() + "' \n "
                        + "and rv.Principal = '" + flag + "'";

                //System.err.println(consulta);
                ResultSet rs;

                try {
                    rs = con.createStatement().executeQuery(consulta);
                    rs.next();
                    temporal = rs.getString("NumRef");
                    rs.close();

                } catch (Exception e1) {
                    System.err.println("Error al buscar referencia de volante");
                    System.err.println(e1.getMessage());
                    return null;
                }

                con.close();
                return temporal;

            } catch (SQLException ex) {
                System.err.println("Error Sql");
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (NamingException ex) {
            System.err.println("Error de nombre");
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void actualizarDerechoMatricula(String refVolante, Liquidacion liquidacion) {

        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup("SQLServerUnicorDS");

            try {
                Connection con = ds.getConnection();

                String consulta
                        = "Update campus.dbo.CHARGECREDIT \n"
                        + "set AMOUNT = " + liquidacion.getValorMatricula() + " \n"
                        + "Where ACADEMIC_YEAR = '" + liquidacion.getAnyoLiquidacion() + " '\n"
                        + "and ACADEMIC_TERM = '" + liquidacion.getSemestre() + " '\n"
                        + "and PEOPLE_ORG_CODE_ID = '" + liquidacion.getPEOPLE_CODE_ID() + "' \n"
                        + "and NOTE = " + refVolante + " \n"
                        + "and CHARGE_CREDIT_CODE in (	'0042ODESPr', \n"
                        + " '0042ODESSc',\n"
                        + " '0037E1y2Pr',\n"
                        + " '0037E1y2Sc',\n"
                        + " '0037Es03Pr',\n"
                        + " '0037Es03Sc',\n"
                        + " '0037Es04Pr',\n"
                        + " '0037Es04Sc',\n"
                        + " '0037Es05Pr',\n"
                        + " '0037Es05Sc',\n"
                        + " '0037Es06Pr',\n"
                        + " '0037Es06Sc')";

                //System.err.println(consulta);
                try {
                    con.createStatement().executeUpdate(consulta);

                } catch (Exception e1) {
                    System.err.println("Error al actualizar la liquidacion de matrícula");
                    System.err.println(e1.getMessage());

                }

                con.close();

            } catch (SQLException ex) {
                System.err.println("Error Sql");
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);

            }
        } catch (NamingException ex) {
            System.err.println("Error de nombre");
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    public void getDataSources2() {

        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");
            DataSource ds = (DataSource) envCtx.lookup("AgendaDS");

            try {
                Connection con = ds.getConnection();
                System.err.println(con.toString());

                ResultSet rs = con.createStatement().executeQuery("select * from tarea;");

                while (rs.next()) {
                    System.out.println(rs.getString("tar_descripcion"));
                }

            } catch (SQLException ex) {
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (NamingException ex) {
            System.err.println("chanfle");
            System.err.println(ex.getMessage());
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
