public class Banco {
    private String Nombre;
    private static Cuenta[] cuentas;
    private int numero_cuentas;
    private static final int MAX_CUENTAS = 100;

    public Banco(String Nombre) {
        Nombre = Nombre;
        this.cuentas = new Cuenta[MAX_CUENTAS];
        this.numero_cuentas = 0;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public static Cuenta[] getCuentas() {
        return cuentas;
    }

    public static void setCuentas(Cuenta[] cuentas) {
        Banco.cuentas = cuentas;
    }

    public int getNumero_cuentas() {
        return numero_cuentas;
    }

    public static int getMaxCuentas() {
        return MAX_CUENTAS;
    }

    public void setNumero_cuentas(int numero_cuentas) {
        this.numero_cuentas = numero_cuentas;
    }

    public boolean agregarCuenta(String Codigo, String Nombre) {
        if (numero_cuentas >= MAX_CUENTAS) {
            return false;
        } else {
            cuentas[numero_cuentas] = new Cuenta(Codigo, Nombre);
            numero_cuentas++;
            return true;
        }
    }

    private Cuenta localizar(String Codigo) {
        Cuenta c = new Cuenta();
        return c;
    }

    private String consultarCuenta(Cuenta cuenta) {
       
        String informacionCuenta = "Número de cuenta: " + cuenta.getIBAN() + "\n" +
                "Titular de la cuenta: " + cuenta.getTitular() + "\n" +
                "Saldo disponible: " + cuenta.getSaldo();

     
        return informacionCuenta;
    }

    
}
