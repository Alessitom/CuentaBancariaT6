/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.cuentabancariat6netbeans;

/**
 *
 * @author Alex
 */
public enum TipoCuenta {
    AHORRO(2.5, 0, 7.5, 0.05, 0),
    NOMINA(0.5, 0, 7.5, 0.1, 0),
    CREDITO(0.1, 4.5, 9.5, 0.5, -10000),
    VALORES(0, 6.0, 12.0, 0.65, -5000);
    
    private final double interesDeudor;
    private final double interesAcreedor;
    private final double interesDescubierto;
    private final double gastoPorDia;
    private final double limiteCredito;
    
    TipoCuenta(double interesDeudor, double interesAcreedor, double interesDescubierto, double gastoPorDia, double limiteCredito) {
        this.interesDeudor = interesDeudor;
        this.interesAcreedor = interesAcreedor;
        this.interesDescubierto = interesDescubierto;
        this.gastoPorDia = gastoPorDia;
        this.limiteCredito = limiteCredito;
    }
    
    public double getInteresDeudor() {
        return interesDeudor;
    }
    
    public double getInteresAcreedor() {
        return interesAcreedor;
    }
    
    public double getInteresDescubierto() {
        return interesDescubierto;
    }
    
    public double getGastoPorDia() {
        return gastoPorDia;
    }
    
    public double getLimiteCredito() {
        return limiteCredito;
    }
}


