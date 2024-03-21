/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;



/**
 *
 * @author Alex
 */
public class Banco {
    private String nombre;
    private final Cuenta[] cuentas;
    private int numeroCuentas;
    private static final int MAX_CUENTAS = 100;

    public Banco(String nombre) {
        this.nombre = nombre;
        this.cuentas = new Cuenta [MAX_CUENTAS];
        this.numeroCuentas = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean agregarCuenta(Cuenta cuenta) {
        if (numeroCuentas < MAX_CUENTAS) {
            cuentas[numeroCuentas++] = cuenta;
            return true;
        }
        return false;
    }

    public String consultarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIBAN().equals(iban)) {
                return cuentas[i].toString();
            }
        }
        return null;
    }

    public boolean borrarCuenta(String iban) {
        int posicion = localizarCuenta(iban);
        if (posicion != -1) {
            cuentas[posicion] = cuentas[numeroCuentas - 1];
            cuentas[numeroCuentas - 1] = null;
            numeroCuentas--;
            return true;
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

  public boolean retirar(String iban, double importe) {
    Cuenta cuenta = buscarCuenta(iban);
    if (cuenta != null) {
        if (importe >0) {
            return cuenta.retirar(importe);
        } else {
            System.out.println("El importe a retirar debe ser igual o mayor que 0.");
            return false;
        }
    }
    return false;
}




    public boolean existeCuenta(String iban) {
        return buscarCuenta(iban) != null;
    }

   public double informaSaldo(String iban){
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

        for (int i = 0; i < numeroCuentas; i++) {
            informe.append(cuentas[i].toString()).append("\n");
            saldoTotal += cuentas[i].getSaldo();
            if (cuentas[i].getSaldo() > 0) {
                cuentasPositivas++;
            } else {
                cuentasNegativas++;
            }
        }

        informe.append("Saldo total: ").append(saldoTotal).append("\n");
        informe.append("Número de cuentas con saldo positivo: ").append(cuentasPositivas).append("\n");
        informe.append("Número de cuentas con saldo negativo: ").append(cuentasNegativas).append("\n");
        if (numeroCuentas > 0) {
            informe.append("Porcentaje de saldo positivo respecto al total: ").append(((double) cuentasPositivas / numeroCuentas) * 100).append("%\n");
            informe.append("Porcentaje de saldo negativo respecto al total: ").append(((double) cuentasNegativas / numeroCuentas) * 100).append("%\n");
            informe.append("Saldo medio por cuenta: ").append(saldoTotal / numeroCuentas).append("\n");
        }

        return informe.toString();
    }

    private int localizarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIBAN().equals(iban)) {
                return i;
            }
        }
        return -1;
    }

    private Cuenta buscarCuenta(String iban) {
        for (int i = 0; i < numeroCuentas; i++) {
            if (cuentas[i].getIBAN().equals(iban)) {
                return cuentas[i];
            }
        }
        return null;
    }
}
