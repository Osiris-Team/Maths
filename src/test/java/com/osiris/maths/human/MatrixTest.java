package com.osiris.maths.human;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatrixTest {
    Matrix m2x2 = new Matrix(2, 2);
    Matrix m5x5 = new Matrix(5, 5);

    public MatrixTest() {
        m2x2.setRow(0, 5.0, 2.0);
        m2x2.setRow(1, 3.0, 1.0);

        m5x5.setRow(0, 10.2, 2.0, 22.1, 4.90, 19.2);
        m5x5.setRow(1, 10.2, 1.0, 22.1, 4.90, 19.2);
        m5x5.setRow(2, 10.2, 0.0, 22.1, 4.90, 19.2);
        m5x5.setRow(3, 10.2, 5.0, 22.1, 4.90, 19.);
        m5x5.setRow(4, 10.2, 6.0, 22.1, 4.90, 19.2);
    }

    @Test
    void getColumns() {
        System.out.println(m5x5.asString());
        List<List<Double>> columns = m5x5.getColumns();
        String[] rows = new String[columns.size()];
        Arrays.fill(rows, "");
        for (List<Double> column : columns) {
            for (int j = 0; j < rows.length; j++) {
                rows[j] = rows[j] + column.get(j) + " ";
            }
        }
        for (String row :
                rows) {
            System.out.println(row);
        }
        System.out.println(m5x5.asString(columns));
    }

    @Test
    void asString() {
    }

    @Test
    void testAsString() {
    }

    @Test
    void add() throws Matrix.NotEqual {
        Matrix m = new Matrix(2, 2);
        m.setRow(0, 1.0, 2.0);
        m.setRow(1, 1.0, 2.0);
        m = m.add(m);
        assertEquals(2.0, m.rows.get(0).get(0));
        assertEquals(4.0, m.rows.get(0).get(1));
        assertEquals(2.0, m.rows.get(1).get(0));
        assertEquals(4.0, m.rows.get(1).get(1));
    }

    @Test
    void subtract() throws Matrix.NotEqual {
        Matrix m = new Matrix(2, 2);
        m.setRow(0, 1.0, 2.0);
        m.setRow(1, 1.0, 2.0);
        m = m.subtract(m);
        assertEquals(0.0, m.rows.get(0).get(0));
        assertEquals(0.0, m.rows.get(0).get(1));
        assertEquals(0.0, m.rows.get(1).get(0));
        assertEquals(0.0, m.rows.get(1).get(1));
    }

    @Test
    void determinant() {
    }

    @Test
    void laplaceExpansion() {
    }

    @Test
    void at() {
    }

    @Test
    void resize() {
        Matrix m = new Matrix(2, 2);
        m.setRow(0, 0.0, 3.0);
        m.setRow(1, 1.0, 2.0);
        m.resize(4, 4);
        assertEquals(4, m.rows.size());
        assertEquals(4, m.getColumns().size());
    }

    @Test
    void setRow() {
    }

    @Test
    void testSetRow() {
    }

    @Test
    void multiply() throws Matrix.NotEqual {
        Matrix result = m2x2.multiply(2.0);
        assertEquals(10.0, result.at(0, 0));
        assertEquals(4.0, result.at(0, 1));
        assertEquals(6.0, result.at(1, 0));
        assertEquals(2.0, result.at(1, 1));
    }

    @Test
    void testMultiply() throws Matrix.NotEqual {
        Matrix m = new Matrix(2, 2);
        m.setRow(0, 0.0, 3.0);
        m.setRow(1, 1.0, 2.0);
        Matrix result = m2x2.multiply(m);
        assertEquals(2.0, result.at(0, 0));
        assertEquals(19.0, result.at(0, 1));
        assertEquals(1.0, result.at(1, 0));
        assertEquals(11.0, result.at(1, 1));
    }

    @Test
    void gaussianElimination() throws Matrix.NotQuadratic {
        Matrix m = new Matrix(4, 4);
        m.set("1 0 2 3\n" +
                "0 1 3 4\n" +
                "2 1 -1 3\n" +
                "3 0 12 7\n");
        m.determinantByGaussianElimination(true);
    }
}