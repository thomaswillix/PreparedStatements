package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("“jdbc:mysql://localhost/colegio", "root", "");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from alumnos");
        
        HashMap<Integer,Integer> notas = new HashMap<Integer,Integer>();
        while(rs.next()){
            notas.put(rs.getInt(1), rs.getInt(4));
        }
        PreparedStatement update = con.prepareStatement("update alumnos set Nota = ? where id = ?");
        PreparedStatement delete = con.prepareStatement("delete from Alunos where id=?");

        rs = st.executeQuery("select * from cambios");

        String operacion;
        int id, incremento;

        while (rs.next()) {
            operacion = rs.getString(2);
            id = rs.getInt(3);
            incremento = rs.getInt(4);
            switch(operacion){
                case "BJ": 
                    delete.setInt(1, id);
                    delete.execute();
                    break;
                case "MD":
                    update.setInt(1, notas.get(id) + incremento);
                    update.setInt(2, id);
                    update.execute();
                    break;
            }
        }
    }
    
}
