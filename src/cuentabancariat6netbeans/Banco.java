/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;

/**
 *
 * @author Alex
 */
import java.util.ArrayList;

public class Banco {

    private String nombre;
    private final ArrayList<Cuenta> cuentas;
    private static final int MAX_CUENTAS = 100;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        if (cuentas.size() < MAX_CUENTAS) {
            cuentas.add(cuenta);
            return true;
        }
        return false;
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
        for (int i = 0; i < cuentas.size(); i++) {
            if (cuentas.get(i).getIBAN().equals(iban)) {
                cuentas.remove(i);
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
        StringBuilder informe = new StringBuilder();
        double saldoTotal = 0;
        int cuentasPositivas = 0;
        int cuentasNegativas = 0;

        for (Cuenta cuenta : cuentas) {
            informe.append(cuenta.toString()).append("\n");
            saldoTotal += cuenta.getSaldo();
            if (cuenta.getSaldo() > 0) {
                cuentasPositivas++;
            } else {
                cuentasNegativas++;
            }
        }

        informe.append("Saldo total: ").append(saldoTotal).append("\n");
        informe.append("Número de cuentas con saldo positivo: ").append(cuentasPositivas).append("\n");
        informe.append("Número de cuentas con saldo negativo: ").append(cuentasNegativas).append("\n");
        if (!cuentas.isEmpty()) {
            informe.append("Porcentaje de saldo positivo respecto al total: ").append(((double) cuentasPositivas / cuentas.size()) * 100).append("%\n");
            informe.append("Porcentaje de saldo negativo respecto al total: ").append(((double) cuentasNegativas / cuentas.size()) * 100).append("%\n");
            informe.append("Saldo medio por cuenta: ").append(saldoTotal / cuentas.size()).append("\n");
        }

        return informe.toString();
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
    if (cuentas.size() >= MAX_CUENTAS) {
        System.out.println("No se pueden añadir más cuentas");
    } else {
        int cuentasRestantes = MAX_CUENTAS - cuentas.size();
        int contador = 1; // Inicializamos el contador fuera del bucle

        for (int i = 0; i < cuentasRestantes; i++) {
            String iban = "c"+ String.format("%02d",contador); // Convertimos el contador a String
            Cuenta cuentavacia = new Cuenta(iban, "*** ", 0, "*** ");
            cuentas.add(cuentavacia);

            contador++; // Incrementamos el contador para la siguiente cuenta
        }

        System.out.println("Se han añadido " + cuentasRestantes + " cuentas nuevas");
    }
}
}
