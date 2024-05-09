/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.cuentabancariat6netbeans;

/**
 *
 * @author Alex
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import static java.lang.Math.random;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Banco implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nombre, cuentasIBAN, dniscuentas, nombrescuentas;
    private double saldocuentas;
    private TipoCuenta tipo;
    private final Map<String, Cuenta> cuentas;
    private final List<String> ListaMovimientos = new ArrayList<>();
    private static final String comillas = "\"";

    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new LinkedHashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        if (!cuentas.containsKey(cuenta.getIBAN())) {
            cuentas.put(cuenta.getIBAN(), cuenta);
            return true;
        }
        return false;
    }

    public String consultarCuenta(String iban) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            return cuenta.toString();
        }
        return null;
    }

    public boolean borrarCuenta(String iban) {
        if (cuentas.containsKey(iban)) {
            cuentas.remove(iban);
            return true;
        }
        return false;
    }

    public boolean ingresar(String iban, double importe) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            cuenta.ingresar(importe);
            return true;
        }
        return false;
    }

    public void retirar(String iban, double importe) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            cuenta.retirar(importe);
        }
    }

    public boolean existeCuenta(String iban) {
        return cuentas.containsKey(iban);
    }

    public double informaSaldo(String iban) {
        Cuenta cuenta = cuentas.get(iban);
        if (cuenta != null) {
            return cuenta.getSaldo();
        }
        return -1;
    }

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

    public void guardarEstado(String nombreArchivo) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            out.writeObject(this);
        } catch (IOException e) {
            throw new IOException("Se ha producido un error al leer el archivo:" + nombreArchivo);
        }
    }

    public static Banco cargarEstado(String nombreArchivo) throws IOException, ClassNotFoundException {
        Banco banco;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            banco = (Banco) in.readObject();
        } catch (IOException e) {
            throw new IOException("Se ha producido un error al leer el archivo:" + nombreArchivo);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Se ha producido un error al abir el archivo:" + nombreArchivo);
        }
        return banco;
    }

    public void ordenarCuentasPorSaldoAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        Collections.sort(listaCuentas, Comparator.comparingDouble(Cuenta::getSaldo));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    public void ordenarCuentasPorSaldoDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort(Comparator.comparingDouble(Cuenta::getSaldo).reversed());
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    public void ordenarCuentasPorIBANAscendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort(Comparator.comparing(Cuenta::getIBAN));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    public void ordenarCuentasPorIBANDescendente() {
        List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
        listaCuentas.sort((c1, c2) -> c2.getIBAN().compareTo(c1.getIBAN()));
        this.cuentas.clear();
        for (Cuenta cuenta : listaCuentas) {
            this.cuentas.put(cuenta.getIBAN(), cuenta);
        }
    }

    public void ordenarCuentasPorTitularYIBAN() {
        if (!cuentas.isEmpty()) {
            List<Cuenta> listaCuentas = new ArrayList<>(this.cuentas.values());
            Collections.sort(listaCuentas, new Comparador());
            this.cuentas.clear();
            for (Cuenta cuenta : listaCuentas) {
                this.cuentas.put(cuenta.getIBAN(), cuenta);
            }
        } else {
            System.out.println("No hay cuentas para ordenar.");
        }
    }

    // Método para obtener el límite de saldo de una cuenta
    public double obtenerLimiteSaldo(String iban) {
        Cuenta cuenta = buscarCuenta(iban);
        if (cuenta != null) {
            return cuenta.getLimiteCredito();
        }
        return -1;
    }

    // Método para calcular la liquidación de todas las cuentas hasta una fecha dada
    public void liquidarCuentas(LocalDate fecha) {
        for (Cuenta cuenta : cuentas.values()) {
            LocalDate fechaLiquidacionAnterior = cuenta.getFechaLiquidacion();
            long diasDiferencia = ChronoUnit.DAYS.between(fechaLiquidacionAnterior, fecha);
            double interesDiario = 0;
            if (diasDiferencia > 0) {
                switch (cuenta.getTipo()) {
                    case AHORRO:
                        interesDiario = cuenta.getTipo().getInteresDeudor();
                        break;
                    case NOMINA:
                        interesDiario = cuenta.getTipo().getInteresDeudor();
                        break;
                    case CREDITO:
                        interesDiario = cuenta.getTipo().getInteresDeudor();
                        break;
                    case VALORES:
                        interesDiario = cuenta.getTipo().getInteresDeudor();
                        break;
                }
                double interesAcumulado = interesDiario * diasDiferencia;
                cuenta.retirar(interesAcumulado);
                cuenta.setFechaLiquidacion(fecha);
            }
        }
    }

    public void cargarArchivoCSV(String archivo) {
        int cuentas = 0;
        Random random = new Random();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                cuentasIBAN = partes[0].replaceAll(comillas, "");
                nombrescuentas = partes[1].replaceAll(comillas, "");
                dniscuentas = partes[2].replaceAll(comillas, "");
                saldocuentas = Double.parseDouble(partes[3].replaceAll(comillas, ""));
                int tipoIndex = random.nextInt(4); // 4 es el número de tipos de cuenta en el enum TipoCuenta
                tipo = TipoCuenta.values()[tipoIndex];
                Cuenta nuevaCuenta = new Cuenta(cuentasIBAN, nombrescuentas, saldocuentas, dniscuentas, tipo); // Tipo de cuenta a elegir según tu lógica
                agregarCuenta(nuevaCuenta);
                cuentas++;

            }
            System.out.println("Han sido cargadas " + cuentas + " cuentas nuevas del archivo " + archivo + ".csv");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarMovimientosCSV(String archivo) {
        int movimientosRegistrados = 0;
        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                String numeroCuenta = partes[0].replaceAll(comillas, "");
                String tipoMovimiento = partes[1].replaceAll(comillas, "");
                double cantidad = Double.parseDouble(partes[2].replaceAll(comillas, ""));

                ListaMovimientos.add(String.format("%s - %s: %.2f", numeroCuenta, tipoMovimiento, cantidad));
                movimientosRegistrados++;

            }
            System.out.println("Se han registrado " + movimientosRegistrados + " movimientos desde el archivo " + archivo + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String listadoMovimientos() {
        StringBuilder salida = new StringBuilder();
        salida.append("Lista de Movimientos\n");
        salida.append("====================\n");

        for (String movimiento : ListaMovimientos) {
            salida.append(movimiento).append("\n");
        }

        return salida.toString();
    }

}
