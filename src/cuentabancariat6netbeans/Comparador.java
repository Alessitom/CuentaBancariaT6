/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cuentabancariat6netbeans;

import java.util.Comparator;

/**
 *
 * @author usumaniana
 */
public class Comparador implements Comparator<Cuenta>{

    @Override
    public int compare(Cuenta c1, Cuenta c2) {
        int comparacionPorTitular = c1.getTitular().compareToIgnoreCase(c2.getTitular());

        if (comparacionPorTitular != 0) {
            // Si los titulares son diferentes, devolver la comparación por titular
            return comparacionPorTitular;
        } else {
            // Si los titulares son iguales, comparar por IBAN
            return c1.compareToPorIBAN(c2);
        }
    }   
}
