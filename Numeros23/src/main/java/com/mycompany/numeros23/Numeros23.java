/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.numeros23;

public class Numeros23 {
    public static void main(String[] args) {
        System.out.println("¡Hola desde Numeros23!");
    }

    // Retorna el menor de dos números
    public int menor2(int a, int b) {
        return (a < b) ? a : b;
    }

    // Retorna el mayor de dos números
    public int mayor2(int a, int b) {
        return (a > b) ? a : b;
    }

    // Retorna el menor de tres números
    public int menor3(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    // Retorna el mayor de tres números
    public int mayor3(int a, int b, int c) {
        return Math.max(a, Math.max(b, c));
    }

}
