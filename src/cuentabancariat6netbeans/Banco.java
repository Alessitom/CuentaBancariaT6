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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The type Banco.
 */
public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private final Map<String, Cuenta> cuentas;

    /**
     * Instantiates a new Banco.
     *
     * @param nombre the nombre
     */
    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new LinkedHashMap<>();
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Agregar cuenta boolean.
     *
     * @param cuenta the cuenta
     * @return the boolean
     */
    public boolean agregarCuenta(Cuenta cuenta) {
        if (!cuentas.containsKey(cuenta.getIBAN())) {
            cuentas.put(cuenta.getIBAN(), cuenta);
            return true;
        }
        return false;
    }

    /**
     * Consultar cuenta string.
     *
     * @param iban the iban
     * @return the string
     */
    public String consultarCuenta(String iban) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            return cuenta.toString();
        }
        return null;
    }

    /**
     * Borrar cuenta boolean.
     *
     * @param iban the iban
     * @return the boolean
     */
    public boolean borrarCuenta(String iban) {
        if (cuentas.containsKey(iban)) {
            cuentas.remove(iban);
            return true;
        }
        return false;
    }

    /**
     * Ingresar boolean.
     *
     * @param iban the iban
     * @param importe the importe
     * @return the boolean
     */
    public boolean ingresar(String iban, double importe) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            cuenta.ingresar(importe);
            return true;
        }
        return false;
    }

    /**
     * Retirar.
     *
     * @param iban the iban
     * @param importe the importe
     */
    public void retirar(String iban, double importe) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            cuenta.retirar(importe);
        }
    }

    /**
     * Existe cuenta boolean.
     *
     * @param iban the iban
     * @return the boolean
     */
    public boolean existeCuenta(String iban) {
        return cuentas.containsKey(iban);
    }

    /**
     * Informa saldo double.
     *
     * @param iban the iban
     * @return the double
     */
    public double informaSaldo(String iban) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            return cuenta.getSaldo();
        }
        return -1;
    }

    /**
     * Listado cuentas string.
     *
     * @return the string
     */
    public String listadoCuentas() {
        double totalSaldo = 0;
        StringBuilder salida = new StringBuilder();
        salida.append("      Cuenta                  Titular             DNI           Saldo        Tipo de Cuenta\n");
        salida.append("=============================================================================================\n");

        for (Cuenta cuenta : cuentas.values()) {
            salida.append(String.format("%15s %20s %20s %15.2f %20s\n",
                    cuenta.getIBAN(), cuenta.getTitular(), cuenta.getDocumento(), cuenta.getSaldo(), cuenta.getTipo()));
            totalSaldo += cuenta.getSaldo();
        }
        salida.append("\n");
        salida.append(String.format("Número total de cuentas: %3d                     %8.2f \n",
                cuentas.size(), totalSaldo));

        return salida.toString();
    }

    private Cuenta buscarCuenta(String iban) {
        return cuentas.get(iban);
    }

    /**
     * Rellenar cuentas.
     */
    public void rellenarCuentas() {
        Random random = new Random();
        String IBAN, titular, dni;
        double saldo;
        for (int i = 0; i < 200; i++) {
            IBAN = String.format("Cuenta%3d", i);
            titular = String.format("Titular %3d", i);
            dni = String.format("%08dA", i);
            saldo = Math.random() * 10000;
            int tipoIndex = random.nextInt(4); // 4 es el número de tipos de cuenta en el enum TipoCuenta
            TipoCuenta tipo = TipoCuenta.values()[tipoIndex];
            Cuenta cuenta = new Cuenta(IBAN, titular, saldo, dni, tipo);
            this.agregarCuenta(cuenta);
        }
    }

    /**
     * Guardar estado.
     *
     * @param nombreArchivo the nombre archivo
     * @throws IOException the io exception
     */
// Método para serializar el curso
    public void guardarEstado(String nombreArchivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(this);
            //System.out.println("Curso serializado correctamente.");
        } catch (IOException e) {
            throw new IOException("Se ha producido un error al leer el archivo:" + nombreArchivo);
        }
    }

    /**
     * Cargar estado banco.
     *
     * @param nombreArchivo the nombre archivo
     * @return the banco
     * @throws IOException the io exception
     * @throws ClassNotFoundException the class not found exception
     */
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

    /**
     * Ordenar cuentas por saldo ascendente.
     */
    public void ordenarCuentasPorSaldoAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        Collections.sort(listaCuentas, (c1, c2) -> c1.compareTo(c2));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    /**
     * Ordenar cuentas por saldo descendente.
     */
    /*
     public void ordenarCuentasPorSaldoAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort(Comparator.comparingDouble(Cuenta::getSaldo));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

     */
    public void ordenarCuentasPorSaldoDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort(Comparator.comparingDouble(Cuenta::getSaldo).reversed());
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    /*
    public void ordenarCuentasPorSaldoDescendente() {
    List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
    Collections.sort(listaCuentas, (c1, c2) -> c2.compareTo(c1));
    this.cuentas.clear();
    for (Cuenta cuenta : listaCuentas) {
        this.cuentas.put(cuenta.getIBAN(), cuenta);
    }
}

     */
    /**
     * Ordenar cuentas por iban ascendente.
     */
    public void ordenarCuentasPorIBANAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort(Comparator.comparing(Cuenta::getIBAN));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    /**
     * Ordenar cuentas por iban descendente.
     */
    public void ordenarCuentasPorIBANDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort((c1, c2) -> c2.getIBAN().compareTo(c1.getIBAN()));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    /*
   public void ordenarCuentasPorIBANDescendente() {
    List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
    listaCuentas.sort(Comparator.comparing(Cuenta::getIBAN).reversed());
    this.cuentas.clear();
    for (Cuenta cuenta : listaCuentas) {
        this.cuentas.put(cuenta.getIBAN(), cuenta);
    }
}

}
     */
    /**
     * Ordenar cuentas por titular yiban.
     */
    public void ordenarCuentasPorTitularYIBAN() {
        if (!cuentas.isEmpty()) {
            List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
            Collections.sort(listaCuentas, new Comparador());
            this.cuentas.clear(); // Limpiar el mapa
            for (Cuenta cuenta : listaCuentas) {
                this.cuentas.put(cuenta.getIBAN(), cuenta); // Volver a insertar, manteniendo el orden por IBAN
            }
        } else {
            System.out.println("No hay cuentas para ordenar.");
        }
    }

}
