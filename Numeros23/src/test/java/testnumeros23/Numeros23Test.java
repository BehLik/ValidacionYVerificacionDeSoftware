/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testnumeros23;


import com.mycompany.numeros23.Numeros23;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Numeros23Test {

    Numeros23 op = new Numeros23();

    @Test
    public void testMenor2() {
        int r1 = op.menor2(2, 3);
        int r2 = op.menor2(10, 5);
        int r3 = op.menor2(-1, 7);

        System.out.println("menor2(2, 3) = " + r1);
        System.out.println("menor2(10, 5) = " + r2);
        System.out.println("menor2(-1, 7) = " + r3);

        assertEquals(2, r1);
        assertEquals(5, r2);
        assertEquals(-1, r3);
    }

    @Test
    public void testMayor2() {
        int r1 = op.mayor2(2, 3);
        int r2 = op.mayor2(10, 5);
        int r3 = op.mayor2(-1, -5);

        System.out.println("mayor2(2, 3) = " + r1);
        System.out.println("mayor2(10, 5) = " + r2);
        System.out.println("mayor2(-1, -5) = " + r3);

        assertEquals(3, r1);
        assertEquals(10, r2);
        assertEquals(-1, r3);
    }

    @Test
    public void testMenor3() {
        int r1 = op.menor3(2, 3, 1);
        int r2 = op.menor3(9, 5, 7);
        int r3 = op.menor3(-2, 0, -5);

        System.out.println("menor3(2, 3, 1) = " + r1);
        System.out.println("menor3(9, 5, 7) = " + r2);
        System.out.println("menor3(-2, 0, -5) = " + r3);

        assertEquals(1, r1);
        assertEquals(5, r2);
        assertEquals(-5, r3);
    }

    @Test
    public void testMayor3() {
        int r1 = op.mayor3(2, 3, 1);
        int r2 = op.mayor3(9, 5, 7);
        int r3 = op.mayor3(-2, 0, -5);

        System.out.println("mayor3(2, 3, 1) = " + r1);
        System.out.println("mayor3(9, 5, 7) = " + r2);
        System.out.println("mayor3(-2, 0, -5) = " + r3);

        assertEquals(3, r1);
        assertEquals(9, r2);
        assertEquals(0, r3);
    }
}
