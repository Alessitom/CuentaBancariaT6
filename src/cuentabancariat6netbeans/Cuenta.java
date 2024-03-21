    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;

/**
 *
 * @author Alex
 */
public class Cuenta {
    
    private String IBAN;
    private String Titular;
    private double saldo;

    public Cuenta(String IBAN, String Titular, double saldo) {
        this.IBAN = IBAN;
        this.Titular = Titular;
        this.saldo = saldo;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getTitular() {
        return Titular;
    }

    public void setTitular(String Titular) {
        this.Titular = Titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public void ingresar(double cantidad){
        saldo+=cantidad;
    }
    
    public boolean retirar(double cantidad){
        if(cantidad<=saldo){
            saldo-=cantidad;
            return true;
        }else
            return false;
    }
      public static boolean validarNIF(String nif) throws DNIException {
        String regex = "\\d{8}[a-zA-Z]";
        if (!nif.matches(regex)) {
            throw new DNIException("El NIF no tiene el formato correcto.");
        }

        int miNIF = Integer.parseInt(nif.substring(0, 8));
           char letra = Character.toUpperCase(nif.charAt(8));

        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
       
        int resto = miNIF % 23;

        if (letra != asignacionLetra[resto]) {
            throw new DNIException("La letra del NIF no es válida.");
        }

        return true;
    }

    public static boolean validarNIE(String nie) throws DNIException {
        String regex = "[XxYyZz]\\d{7}[a-zA-Z]";
        if (!nie.matches(regex)) {
            throw new DNIException("El NIE no tiene el formato correcto.");
        }

        char letraInicial = Character.toUpperCase(nie.charAt(0));
        char letraFinal = Character.toUpperCase(nie.charAt(8));
        char[] asignacionLetra = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};

        if (letraInicial == 'X') {
            nie = "0" + nie.substring(1, 9);
        } else if (letraInicial == 'Y') {
            nie = "1" + nie.substring(1, 9);
        } else if (letraInicial == 'Z') {
            nie = "2" + nie.substring(1, 9);
        }

        int miNIE = Integer.parseInt(nie.substring(0, 8));
        int resto = miNIE % 23;

        if (letraFinal != asignacionLetra[resto]) {
            throw new DNIException("La letra del NIE no es válida.");
        }

        return true;
    }





    @Override
    public String toString() {
        return "Cuenta{" + "IBAN=" + IBAN + ", Titular=" + Titular + ", saldo=" + saldo + '}';
    }
    
    
}
