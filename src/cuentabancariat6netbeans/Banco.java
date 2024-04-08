/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;

/**
 *
 * @author Alex
 */
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Banco implements Serializable {

    private static final long serialVersion = 1L;
    private String nombre;
    private final Set<Cuenta> cuentas;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new LinkedHashSet<Cuenta>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        return cuentas.add(cuenta);
    }

    public String consultarCuenta(String iban) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIBAN().equals(iban)) {
                return cuenta.toString();
            }
        }
        return null;
    }

    public boolean borrarCuenta(String iban) {
        Iterator<Cuenta> iteCuentas = cuentas.iterator();
        while (iteCuentas.hasNext()) {
            Cuenta c = iteCuentas.next();
            if (c.getIBAN().equals(iban)) {
                iteCuentas.remove();
                return true;
            }
        }
        return false;
    }

    public boolean ingresar(String iban, double importe) {
        Cuenta cuenta = buscarCuenta(iban);
        if (cuenta != null) {
            cuenta.ingresar(importe);
            return true;
        }
        return false;
    }

    public void retirar(String iban, double importe) {
        Cuenta cuenta = buscarCuenta(iban);
        if (cuenta != null) {
            cuenta.retirar(importe);
        }
    }

    public boolean existeCuenta(String iban) {
        return buscarCuenta(iban) != null;
    }

    public double informaSaldo(String iban) {
        Cuenta cuenta = buscarCuenta(iban);
        if (cuenta != null) {
            return cuenta.getSaldo();
        }
        return -1;
    }

    public String listadoCuentas() {
        double totalSaldo = 0;
        StringBuilder salida = new StringBuilder();
        salida.append("      cuenta                  Titular             DNI           Saldo\n");
        salida.append("====================   ====================    ===========   =============\n");

        for (Cuenta cuenta : cuentas) {
            salida.append(String.format("%15s %20s %20s %15.2f\n",
                    cuenta.getIBAN(), cuenta.getTitular(), cuenta.getDocumento(), cuenta.getSaldo()));
            totalSaldo += cuenta.getSaldo();
        }
        salida.append("\n");
        salida.append(String.format("Número total de cuentas: %3d                     %8.2f \n",
                cuentas.size(), totalSaldo));

        return salida.toString();
    }

    private Cuenta buscarCuenta(String iban) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getIBAN().equals(iban)) {
                return cuenta;
            }
        }
        return null;
    }

    public void rellenarCuentas() {
        String IBAN, titular, dni;
        for (int i = 0; i < 200; i++) {
            IBAN = String.format("Cuenta%3d", i);
            titular = String.format("Titular %3d", i);
            dni = String.format("%08dA", i);
            double saldo = Math.random() * 10000;
            Cuenta cuenta = new Cuenta(IBAN, titular, saldo, dni);
            this.agregarCuenta(cuenta);
        }
    }

    // Método para serializar el curso
    public void guardarEstado(String nombreArchivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(this);
            //System.out.println("Curso serializado correctamente.");
        } catch (IOException e) {
            throw new IOException("Se ha producido un error al leer el archivo:" + nombreArchivo);
        }
    }

    // Método para deserializar el curso
    public static Banco cargarEstado(String nombreArchivo) throws IOException, ClassNotFoundException {
        Banco banco;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            banco = (Banco) in.readObject();
            //System.out.println("Curso deserializado correctamente.");
        } catch (IOException e) {
            throw new IOException("Se ha producido un error al leer el archivo:" + nombreArchivo);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Se ha producido un error al abir el archivo:" + nombreArchivo);
        }
        return banco;
    }

    public Set<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Cuenta> ordenarCuentasPorSaldoAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas);
        listaCuentas.sort(Comparator.comparingDouble(Cuenta::getSaldo));
        return listaCuentas;
    }

    public List<Cuenta> ordenarCuentasPorSaldoDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas);
        listaCuentas.sort(Comparator.comparingDouble(Cuenta::getSaldo).reversed());
        return listaCuentas;
    }

    public List<Cuenta> ordenarCuentasPorIBANAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas);
        listaCuentas.sort(Comparator.comparing(Cuenta::getIBAN));
        return listaCuentas;
    }

    public List<Cuenta> ordenarCuentasPorIBANDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas);
        listaCuentas.sort((c1, c2) -> c2.getIBAN().compareTo(c1.getIBAN()));
        return listaCuentas;
    }

}
