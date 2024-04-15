/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Cuenta.
 *
 * @author Alex
 */
public class Cuenta implements Serializable, Comparable<Cuenta> {

    private static final long serialVersion = 1L;
    private String IBAN;
    private String Titular;
    private double saldo;
    private String Documento;
    private TipoCuenta Tipo;
    

    /**
     * Instantiates a new Cuenta.
     *
     * @param IBAN      the iban
     * @param Titular   the titular
     * @param saldo     the saldo
     * @param Documento the documento
     */
    public Cuenta(String IBAN, String Titular, double saldo, String Documento,TipoCuenta Tipo) {
        this.IBAN = IBAN;
        this.Titular = Titular;
        this.saldo = saldo;
        this.Documento = Documento;
        this.Tipo = Tipo;
    }

    public TipoCuenta getTipo() {
        return Tipo;
    }

    public void setTipo(TipoCuenta Tipo) {
        this.Tipo = Tipo;
    }


    /**
     * Gets iban.
     *
     * @return the iban
     */
    public String getIBAN() {
        return IBAN;
    }

    /**
     * Sets iban.
     *
     * @param IBAN the iban
     */
    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    /**
     * Gets titular.
     *
     * @return the titular
     */
    public String getTitular() {
        return Titular;
    }

    /**
     * Sets titular.
     *
     * @param Titular the titular
     */
    public void setTitular(String Titular) {
        this.Titular = Titular;
    }

    /**
     * Gets saldo.
     *
     * @return the saldo
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Sets saldo.
     *
     * @param saldo the saldo
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Ingresar.
     *
     * @param cantidad the cantidad
     */
    public void ingresar(double cantidad) {
        saldo += cantidad;
    }

    /**
     * Retirar.
     *
     * @param cantidad the cantidad
     */
    public void retirar(double cantidad) {

        saldo -= cantidad;

    }

    /**
     * Validar nif boolean.
     *
     * @param Documento the documento
     * @return the boolean
     * @throws DNIException the dni exception
     */
    public static boolean validarNIF(String Documento) throws DNIException {
        String regex = "\\d{8}[a-zA-Z]";
        if (!Documento.matches(regex)) {
            throw new DNIException("El NIF no tiene el formato correcto.");
        }

        int miNIF = Integer.parseInt(Documento.substring(0, 8));
        char letra = Character.toUpperCase(Documento.charAt(8));

        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        int resto = miNIF % 23;

        if (letra != asignacionLetra[resto]) {
            throw new DNIException("La letra del NIF no es válida.");
        }

        return true;
    }

    /**
     * Validar nie boolean.
     *
     * @param Documento the documento
     * @return the boolean
     * @throws DNIException the dni exception
     */
    public static boolean validarNIE(String Documento) throws DNIException {
        String regex = "[XxYyZz]\\d{7}[a-zA-Z]";
        if (!Documento.matches(regex)) {
            throw new DNIException("El NIE no tiene el formato correcto.");
        }

        char letraInicial = Character.toUpperCase(Documento.charAt(0));
        char letraFinal = Character.toUpperCase(Documento.charAt(8));
        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        if (letraInicial == 'X') {
            Documento = "0" + Documento.substring(1, 9);
        } else if (letraInicial == 'Y') {
            Documento = "1" + Documento.substring(1, 9);
        } else if (letraInicial == 'Z') {
            Documento = "2" + Documento.substring(1, 9);
        }

        int miNIE = Integer.parseInt(Documento.substring(0, 8));
        int resto = miNIE % 23;

        if (letraFinal != asignacionLetra[resto]) {
            throw new DNIException("La letra del NIE no es válida.");
        }

        return true;
    }

    /**
     * Gets documento.
     *
     * @return the documento
     */
    public String getDocumento() {
        return Documento;
    }

    /**
     * Sets documento.
     *
     * @param Documento the documento
     */
    public void setDocumento(String Documento) {
        this.Documento = Documento;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "IBAN=" + IBAN + ", Titular=" + Titular + ", saldo=" + saldo + ", Documento=" + Documento + ", Tipo=" + Tipo + '}';
    }

    
 /*
    @Override
    public int compareTo(Cuenta o) {
        if (this.saldo > o.saldo) {
            return 1;

        } else if (this.saldo < o.saldo) {
            return -1;
        } else {
            return 0;
        }
    }
    */
   
    @Override
        public int compareTo(Cuenta o) {
        return Double.compare(this.saldo, o.saldo);
        }


    /**
     * Compare to por iban int.
     *
     * @param otraCuenta the otra cuenta
     * @return the int
     */
    public int compareToPorIBAN(Cuenta otraCuenta) {
        return otraCuenta.IBAN.compareTo(this.IBAN);
    }

    @Override
    public int hashCode() {
        
         return Objects.hashCode(this.IBAN);
        
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cuenta other = (Cuenta) obj;
        return Objects.equals(this.IBAN, other.IBAN);
    }

}
