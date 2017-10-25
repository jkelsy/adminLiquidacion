/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import configuracion.Constantes;
import configuracion.Inicio;
import db.Estudiante;
import db.NombreDatasource;
import fachade.NombreDatasourceRepository;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class NombreDatasourceService implements Serializable{
    
    @Inject
    private NombreDatasourceRepository nombreDatasourceRepository;
    
    public List<NombreDatasource> getDatasources(){
        return nombreDatasourceRepository.findAll();
    }
    
    public List<Estudiante> cargarEstudiantes(String datasource, String sql){
        
        Estudiante e;
        List<Estudiante> estudianteList = new ArrayList();
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");            
            DataSource ds = (DataSource) envCtx.lookup(datasource);
            
            try {
                Connection con = ds.getConnection();
                ResultSet rs = con.createStatement().executeQuery(sql);
                
                List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                while(rs.next()){
                    e = new Estudiante();
                    e.setPEOPLE_CODE_ID(rs.getString(Constantes.PEOPLE_CODE_ID));
                    e.setApellidos(rs.getString(Constantes.NOMBRES));
                    e.setNombres(rs.getString(Constantes.APELLIDOS));   
                    
                    e.setCodigoPrograma(rs.getString(Constantes.CODIGO_PROGRAMA));
                    e.setNombrePrograma(rs.getString(Constantes.NOMBRE_PROGRAMA));
                    e.setAnyoLiquidacion(rs.getInt(Constantes.ANYO));
                    e.setSemestre(rs.getString(Constantes.SEMESTRE));
                    
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
    
    public void getDataSources2() {
        
        try {
            Context ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:jboss/datasources");            
            DataSource ds = (DataSource) envCtx.lookup("AgendaDS");
            
            try {
                Connection con = ds.getConnection();                
                System.err.println(con.toString());    
                
                ResultSet rs = con.createStatement().executeQuery("select * from tarea;");
                
                while(rs.next()){
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
