public class Cuenta {
    
    private String IBAN;
    private String titular;
    private double saldo;
    
    public Cuenta(String iBAN, String titular) {
        IBAN = iBAN;
        this.titular = titular;
      
    }

    public Cuenta() {
        IBAN = " ";
        this.titular =" ";
        this.saldo = 0;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
   

    public void ingresar(double cantidad)
    {
       saldo =+cantidad;
    }
    
    public void retirar(double cantidad)
    {
        saldo-=cantidad;
    }

}
