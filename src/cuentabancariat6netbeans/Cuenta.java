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
        if(cantidad<saldo){
            saldo-=cantidad;
            return true;
        }else
            return false;
    }

    @Override
    public String toString() {
        return "Cuenta{" + "IBAN=" + IBAN + ", Titular=" + Titular + ", saldo=" + saldo + '}';
    }
    
    
}
