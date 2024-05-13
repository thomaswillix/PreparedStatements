package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Thomas Freitas
 */
public class Main {
    /* Este año lo pongo porque el ejercicio debió de hacerse hace unos años por 
      el hecho de que en el enunciado se contempla que los empleados tengan una 
      antigüedad de menos de dos años y las altas más recientes sean en 2018 */
    
    private final static int ANIO_ACTUAL = 2019;
    
    public static void main(String[] args) throws SQLException {
        Connection con;
        con = DriverManager.getConnection("jdbc:mysql://localhost/personal", "root", "");
        
        PreparedStatement pstUpdateSalario = con.prepareStatement("Update empleados set salario = ? where Emp_no = ?");
        PreparedStatement pstDelete = con.prepareStatement("Delete from empleados where Emp_no = ?");
        PreparedStatement pstGetDepartamento = con.prepareStatement("select * from departamentos where Dept_no = ?");
        PreparedStatement pstUpdatePresupuesto = con.prepareStatement("Update departamentos set presupuesto = ? where Dept_no = ?");
        PreparedStatement pstGetEmpleado = con.prepareStatement("select * from empleados where Emp_no = ?");
        PreparedStatement pstMostrarEmpleadosDeDepatamento = con.prepareStatement("select * from empleados where Dept_no = ?");
        Empleado e;
        List<Empleado> empleados = new ArrayList<>();
        String cod, nom;
        int anioAlta, salario, dpto;
        
        
        System.out.println("--------------- LISTA DE EMPLEADOS ANTES DE SER ACTUALIZADA ---------------");
        Statement st; 
        st = con.createStatement();
        ResultSet rs = st.executeQuery("select *  from empleados");
        while(rs.next()){
            cod = rs.getString(1);
            nom = rs.getString(2);
            anioAlta = rs.getInt(3);
            salario = rs.getInt(4);
            dpto = rs.getInt(5);
            e = new Empleado(cod, nom, anioAlta, salario, dpto);
            empleados.add(e);
            System.out.println(e.toString());
        }
        System.out.println("\n\n--------------- LISTA DE DEPARTAMENTOS ANTES DE SER ACTUALIZADA ---------------");
        rs = st.executeQuery("select *  from departamentos");
        Departamento d;
        int codD, presupuesto;
        String ubi;
        while (rs.next()){
            codD = rs.getInt(1);
            nom = rs.getString(2);
            ubi = rs.getString(3);
            presupuesto = rs.getInt(4);
            d = new Departamento(codD, nom, ubi, presupuesto);
            System.out.println(d.toString());
        }
        System.out.println("\n\n..... ACTUALIZACIONES .....");
        for (Empleado empleado : empleados) {
            /* Incrementar el salario con la gratificación. La antigüedad se calculará mediante el
               año de alta y modificar el presupuesto de cada departamento añadiéndole la cantidad 
               total de dinero que se necesita para las gratificaciones.*/
            if(ANIO_ACTUAL - empleado.getAnioAlta() > 10){
                /* Si llevan más de 10 años, la gratificación se incrementará un 3% por cada año (se entiende
                   que el 3% se hará sobre el sueldo actual).*/
                empleado.setSalario((int)(empleado.getSalario() + (empleado.getSalario() * 0.03 * (ANIO_ACTUAL - empleado.getAnioAlta()))));
                // UPDATE DEL SALARIO DEL EMPLEADO.
                pstUpdateSalario.setInt(1, empleado.getSalario());
                pstUpdateSalario.setString(2, empleado.getCodigo());
                pstUpdateSalario.execute();
                // UPDATE DEL PRESUPUESTO DEL DEPARTAMENTO.
                pstGetDepartamento.setInt(1, empleado.getDpto());
                rs = pstGetDepartamento.executeQuery();
                if(rs.next()){
                    presupuesto = rs.getInt(4);
                    pstUpdatePresupuesto.setInt(1, presupuesto + empleado.getSalario());
                    pstUpdatePresupuesto.setInt(2, empleado.getDpto());
                    pstUpdatePresupuesto.execute();
                }else{
                    System.err.println("EL DEPARTAMENTO ESPECIFICADO NO EXISTE");
                }
            } else if (ANIO_ACTUAL- empleado.getAnioAlta() >= 2){
                /* A los empleados que lleven al menos dos años, se les gratificará con 25 € por cada 3 meses. 
                   25 * 4 (trimestres de un año) = 100*/
                empleado.setSalario((int)(empleado.getSalario() + ((100)*(ANIO_ACTUAL - empleado.getAnioAlta()))));
                // UPDATE DEL SALARIO DEL EMPLEADO.
                pstUpdateSalario.setInt(1, empleado.getSalario());
                pstUpdateSalario.setString(2, empleado.getCodigo());
                pstUpdateSalario.execute();
                // UPDATE DEL PRESUPUESTO DEL DEPARTAMENTO.
                pstGetDepartamento.setInt(1, empleado.getDpto());
                rs = pstGetDepartamento.executeQuery();
                if(rs.next()){
                    presupuesto = rs.getInt(4);
                    pstUpdatePresupuesto.setInt(1, presupuesto + empleado.getSalario());
                    pstUpdatePresupuesto.setInt(2, empleado.getDpto());
                    pstUpdatePresupuesto.execute();
                }else{
                    System.err.println("EL DEPARTAMENTO ESPECIFICADO NO EXISTE");
                }
                // Dar de baja aquellos empleados que lleven menos de 2 años
            } else if (ANIO_ACTUAL - empleado.getAnioAlta() < 2){
                pstDelete.setString(1, empleado.getCodigo());
                System.err.println("El empleado: " + empleado.getNombre() +" con código: " +empleado.getCodigo() + " se ha eliminado de la base de datos.");
                pstDelete.execute();
            }
        }
        
        System.out.println("\n\n------------- MOSTRAR LOS DATOS DE UN EMPLEADO -------------");
        Scanner sc = new Scanner(System.in);
        System.out.println("\nDigame el código del empleado de quien quiere visualizar los datos: ");
        cod = sc.nextLine();
        pstGetEmpleado.setString(1, cod);
        rs = pstGetEmpleado.executeQuery();
        if (rs.next()) {
            cod = rs.getString(1);
            nom = rs.getString(2);
            anioAlta = rs.getInt(3);
            salario = rs.getInt(4);
            dpto = rs.getInt(5);
            e = new Empleado(cod, nom, anioAlta, salario, dpto);
            System.out.println(e.toString());
        }else{
            System.err.println("ESE CÓDIGO DE EMPLEADO NO EXISTE EN LA BASE DE DATOS");
        }
        
        System.out.println("\n\n------------- MOSTRAR LOS EMPLEADO QUE HAY EN UN DEPARTAMENTO -------------");
        System.out.println("\nDigame el código del departamento del cual quiere visualizar sus empleados: ");
        codD = sc.nextInt();
        pstMostrarEmpleadosDeDepatamento.setInt(1, codD);
        rs = pstMostrarEmpleadosDeDepatamento.executeQuery();
        while (rs.next()) {            
            cod = rs.getString(1);
            nom = rs.getString(2);
            anioAlta = rs.getInt(3);
            salario = rs.getInt(4);
            dpto = rs.getInt(5);
            e = new Empleado(cod, nom, anioAlta, salario, dpto);
            System.out.println(e.toString());
        }
        System.out.println("\n\n--------------- LISTA DE EMPLEADOS DESPUÉS DE SER ACTUALIZADA ---------------");
        rs = st.executeQuery("select *  from empleados");
        while(rs.next()){
            cod = rs.getString(1);
            nom = rs.getString(2);
            anioAlta = rs.getInt(3);
            salario = rs.getInt(4);
            dpto = rs.getInt(5);
            e = new Empleado(cod, nom, anioAlta, salario, dpto);
            empleados.add(e);
            System.out.println(e.toString());
        }
        System.out.println("\n\n--------------- LISTA DE DEPARTAMENTOS DESPUÉS DE SER ACTUALIZADA ---------------");
        rs = st.executeQuery("select *  from departamentos");
        while (rs.next()){
            codD = rs.getInt(1);
            nom = rs.getString(2);
            ubi = rs.getString(3);
            presupuesto = rs.getInt(4);
            d = new Departamento(codD, nom, ubi, presupuesto);
            System.out.println(d.toString());
        }
        
    }
    
}
