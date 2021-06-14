package edu.nahuelpiguillem.dbentities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDHelper{
	//final String url="jdbc:mysql://localhost:3306/busquedabdtest?zeroDateTimeBehavior=CONVERT_TO_NULL";
        //jdbc:mariadb://localhost:3386/busqueda
        final String url="jbc:mariadb://localhost:3376/busqueda";
        final String user="root";
        final String pass="GerardoSofovich";
        private Connection con;
        private Connection conb;
        private Statement stm;
        private static int cont;
        private Statement stmb;
        public BDHelper(){
            cont=0;
            //System.out.println("Usando BDhelper");
        }
        private static void shwcnt(){
            System.out.println("contador es: "+cont);
        }
        public ResultSet leerDatos(String sql){
            ResultSet rs=null;
            try{
                con=DriverManager.getConnection(url,user,pass);
                stm=con.createStatement();
                rs=stm.executeQuery(sql);
            }
            catch(SQLException ex){
                  System.out.println(ex.getMessage());
            }
            return rs;
        }
        public ResultSet leerLastId(String idCol,String tabla)
        {
            ResultSet rs=null;
            String sql="Select max("+idCol+") AS id from "+tabla;
            try{
                con=DriverManager.getConnection(url,user,pass);
                stm=con.createStatement();
                rs=stm.executeQuery(sql);
            }
            catch(SQLException ex){
                  System.out.println(ex.getMessage());
            }
            rs=null;
            return rs;

        
        }
        public int leerUltimoIdTabla(String tabla,String col){
            ResultSet rs=null;
            int id=-1;
            String sql="select "+col+" as id from "+tabla+" order by "+col+" desc limit 1";
            try{
                con=DriverManager.getConnection(url,user,pass);
                stm=con.createStatement();
                rs=stm.executeQuery(sql);
                if(rs.next())
                {
                    id=rs.getInt("id");
                }
                else{
                    id=0;
                }
                
                
                cerrarConexion(rs);
            }
            catch(SQLException ex){
                  System.out.println(ex.getMessage());
            }


 
            return id;
        }
        public int leerLastIdAlter(){
            ResultSet rs=null;
            int id=-1;
            String sql="select last_insert_id() as id";
            try{
                con=DriverManager.getConnection(url,user,pass);
                stm=con.createStatement();
                rs=stm.executeQuery(sql);
                while(rs.next()){
                    id=rs.getInt("id");
                }
                cerrarConexion(rs);
            }
            catch(SQLException ex){
                  System.out.println(ex.getMessage());
            }


 
            return id;
        }
        public void cerrarConexion(ResultSet rs) throws SQLException{
            stm.close();
            if(rs!=null){
                rs.close();    
            }

            con.close();           
        }
        public int modificarRegistro(String sql)throws SQLException{
            int filas=0;
            try{
                con = DriverManager.getConnection(url,user,pass);
                stm=con.createStatement();
                stm.executeUpdate(sql);
                filas++;
            }
            catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
            finally{
                stm.close();
                con.close();
            }
            return filas;
        }
        public void newBatch()throws SQLException {
            conb=DriverManager.getConnection(url,user,pass);
            stmb=conb.createStatement();
            conb.setAutoCommit(false);
        }
        public void addBatch(String sql)throws SQLException{
            stmb.addBatch(sql);
        }
        public int[] runBatch()throws SQLException{
            int[] count=stmb.executeBatch();
            conb.commit();
            stmb.close();
            conb.close();
            return count;
        }
}