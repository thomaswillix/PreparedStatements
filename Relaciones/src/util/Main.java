package util;

import clases.Emparejamiento;
import clases.Hombre;
import clases.Mujer;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas Freitas
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/relaciones", "root", "");
        Statement st = con.createStatement();
        PreparedStatement buscarMujer = con.prepareStatement("select * from Mujeres where codigo = ?");
        PreparedStatement buscarHombre = con.prepareStatement("select * from Hombres where codigo = ?");
        PreparedStatement insertarEmp = con.prepareStatement("insert into Emparejamientos values (?,?,?)");

        PreparedStatement actualizarM = con.prepareStatement("update Mujeres set v1=?, v2=?, v3=?, v4=?, v5=? where codigo = ?");
        PreparedStatement actualizarH = con.prepareStatement("update Hombres set v1=?, v2=?, v3=?, v4=?, v5=? where codigo = ?");

        PreparedStatement borrarM = con.prepareStatement("delete from Mujeres where codigo = ?");
        PreparedStatement borrarH = con.prepareStatement("delete from Hombres where codigo = ?");
        PreparedStatement borrarEmp = con.prepareStatement("delete from Emparejamientos where codigoH = ? and codigoM = ? and grado = ?");

        ResultSet rs;
        ResultSet aux;
        File f = new File("datos.bin");

        int codH, codM;
        Hombre h;
        Mujer m;
        Emparejamiento e;
        List<Emparejamiento> emparejamientos = new ArrayList<>();
        System.out.println("-------------------- Lectura del archivo binario --------------------");
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream(f));
            try {
                while (true) {
                    codM = dis.readInt();
                    codH = dis.readInt();
                    System.out.println("codM: " + codM + ", codH: " + codH);
                    e = new Emparejamiento(codH, codM);
                    emparejamientos.add(e);
                }
            } catch (EOFException ex) {
                System.err.println("End of file");
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        double numerador, denominador, afinidad;
        System.out.println("\n\n------------- Inserción de los emparejamientos a la base de datos -------------");
        for (Emparejamiento emparejamiento : emparejamientos) {
            buscarMujer.setInt(1, emparejamiento.getCodM());
            rs = buscarMujer.executeQuery();
            if (rs.next()) {
                m = new Mujer(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7));
                buscarHombre.setInt(1, emparejamiento.getCodH());
                rs = buscarHombre.executeQuery();
                if (rs.next()) {
                    h = new Hombre(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7));
                    numerador = (h.getV1() * m.getV1()) + (h.getV2() * m.getV2()) + (h.getV3() * m.getV3()) + (h.getV4() * m.getV4()) + (h.getV5() + m.getV5());
                    denominador = Math.sqrt((Math.pow(m.getV1(), 2) + Math.pow(m.getV2(), 2) + Math.pow(m.getV3(), 2) + Math.pow(m.getV4(), 2) + Math.pow(m.getV5(), 2))
                            + (Math.pow(h.getV1(), 2) + Math.pow(h.getV2(), 2) + Math.pow(h.getV3(), 2) + Math.pow(h.getV4(), 2) + Math.pow(h.getV5(), 2)));
                    afinidad = numerador / denominador;
                    emparejamiento.setAfinidad(afinidad);
                    insertarEmp.setInt(1, emparejamiento.getCodH());
                    insertarEmp.setInt(2, emparejamiento.getCodM());
                    insertarEmp.setDouble(3, emparejamiento.getAfinidad());
                    insertarEmp.execute();
                } else {
                    System.err.println("El código del hombre(" + emparejamiento.getCodH() + ")no pertenece a ningún hombre de la base de datos");
                }
            } else {
                System.err.println("El código de la mujer (" + emparejamiento.getCodM() + ") no pertenece a ninguna mujer de la base de datos");
            }
        }
        emparejamientos.removeAll(emparejamientos);
        System.out.println("\n\n----------- Tabla de emparejamientos sin actualizar -----------");
        rs = st.executeQuery("select * from emparejamientos");
        while (rs.next()) {
            e = new Emparejamiento(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
            emparejamientos.add(e);
            System.out.println(e.toString());
        }

        for (Emparejamiento emparejamiento : emparejamientos) {
            if (emparejamiento.getAfinidad() >= 0.8) {
                borrarM.setInt(1, emparejamiento.getCodM());
                borrarM.execute();
                borrarH.setInt(1, emparejamiento.getCodH());
                borrarH.execute();
            } else if (emparejamiento.getAfinidad() >= 0.5) {
                borrarEmp.setInt(1, emparejamiento.getCodH());
                borrarEmp.setInt(2, emparejamiento.getCodM());
                borrarEmp.setDouble(3, emparejamiento.getAfinidad());
                borrarEmp.execute();
            } else {
                borrarEmp.setInt(1, emparejamiento.getCodH());
                borrarEmp.setInt(2, emparejamiento.getCodM());
                borrarEmp.setDouble(3, emparejamiento.getAfinidad());
                borrarEmp.execute();
                buscarHombre.setInt(1, emparejamiento.getCodH());
                rs = buscarHombre.executeQuery();
                if (rs.next()) {
                    h = new Hombre(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7));
                    double increment = 0.1;

                    double[] values = {h.getV1(), h.getV2(), h.getV3(), h.getV4(), h.getV5()};
                    for (int i = 0; i < values.length; i++) {
                        values[i] = Math.min(values[i] + increment, 1);
                    }

                    h.setV1(values[0]);
                    h.setV2(values[1]);
                    h.setV3(values[2]);
                    h.setV4(values[3]);
                    h.setV5(values[4]);

                    actualizarH.setDouble(1, h.getV1());
                    actualizarH.setDouble(2, h.getV2());
                    actualizarH.setDouble(3, h.getV3());
                    actualizarH.setDouble(4, h.getV4());
                    actualizarH.setDouble(5, h.getV5());
                    actualizarH.setInt(6, h.getCod());
                    actualizarH.execute();
                    System.out.println("Hombre con id: " + emparejamiento.getCodH() + " actualizado con éxito");
                }
                buscarMujer.setInt(1, emparejamiento.getCodH());
                aux = buscarMujer.executeQuery();
                if (aux.next()) {
                    m = new Mujer(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7));
                    double increment = 0.1;

                    double[] values = {m.getV1(), m.getV2(), m.getV3(), m.getV4(), m.getV5()};
                    for (int i = 0; i < values.length; i++) {
                        values[i] = Math.min(values[i] + increment, 1);
                    }

                    m.setV1(values[0]);
                    m.setV2(values[1]);
                    m.setV3(values[2]);
                    m.setV4(values[3]);
                    m.setV5(values[4]);

                    actualizarM.setDouble(1, m.getV1());
                    actualizarM.setDouble(2, m.getV2());
                    actualizarM.setDouble(3, m.getV3());
                    actualizarM.setDouble(4, m.getV4());
                    actualizarM.setDouble(5, m.getV5());
                    actualizarM.setInt(6, m.getCod());
                    actualizarM.execute();
                    System.out.println("Mujer con id: " + emparejamiento.getCodM() + " actualizada con éxito");
                }
            }
        }
        System.out.println("\n\n----------- Tabla de emparejamientos actualizada -----------");
        rs = st.executeQuery("select * from emparejamientos");
        while (rs.next()) {
            e = new Emparejamiento(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
            System.out.println(e.toString());
        }
        buscarMujer.close();
        buscarHombre.close();
        insertarEmp.close();
        actualizarH.close();
        actualizarM.close();
        borrarEmp.close();
        borrarM.close();
        borrarH.close();
    }

}
