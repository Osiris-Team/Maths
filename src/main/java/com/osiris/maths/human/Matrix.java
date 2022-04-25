package com.osiris.maths.human;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {
    public int rowsCount, columnsCount;
    public List<List<Double>> rows;

    /**
     * Creates a matrix filled with zeros, with
     * the provided row and column counts. <br>
     * Example with rowCount=3 and columnCount=2: <br>
     * [0.0, 0.0] <br>
     * [0.0, 0.0] <br>
     * [0.0, 0.0] <br>
     * Note that quadratic matrices are needed for most calculations. <br>
     */
    public Matrix(int rowsCount, int columnsCount) {
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
        this.rows = new ArrayList<>();
        for (int i = 0; i < rowsCount; i++) {
            List<Double> values = new ArrayList<>(columnsCount);
            Collections.fill(values, 0.0);
            this.rows.add(values);
        }
    }

    /**
     * Returns the value at the provided row and column index.
     *
     * @param iR indexRow
     * @param iC indexColumn
     */
    public Double at(int iR, int iC) {
        return rows.get(iR).get(iC);
    }

    public void resize(int newRowsCount, int newColumnsCount) {
        if (newRowsCount > rowsCount) {
            for (int i = 0; i < newRowsCount - rowsCount; i++) {
                List<Double> values = new ArrayList<>();
                for (int j = 0; j < newColumnsCount; j++) {
                    values.add(0.0);
                }
                this.rows.add(values);
            }
        } else {
            for (int i = 0; i < rowsCount - newRowsCount; i++) {
                this.rows.remove(rowsCount - 1 - i);
            }
        }
        if (newColumnsCount > columnsCount) {
            for (int i = 0; i < newColumnsCount - columnsCount; i++) {
                for (List<Double> row :
                        rows) {
                    if (row.size() < newColumnsCount)
                        row.add(0.0);
                }
            }
        } else {
            for (int i = 0; i < columnsCount - newColumnsCount; i++) {
                for (List<Double> row :
                        rows) {
                    row.remove(row.size() - 1);
                }
            }
        }
        this.columnsCount = newColumnsCount;
    }

    public void setRow(int i, Double... values) {
        List<Double> list = new ArrayList<>(values.length);
        Collections.addAll(list, values);
        setRow(i, list);
    }

    public void setRow(int i, List<Double> values) {
        this.rows.set(i, values);
    }

    public List<List<Double>> getColumns() {
        List<List<Double>> columns = new ArrayList<>(columnsCount);
        for (int i = 0; i < columnsCount; i++) {
            List<Double> column = new ArrayList<>(rows.size());
            columns.add(column);
            for (List<Double> row : rows) {
                column.add(row.get(i));
            }
        }
        return columns;
    }

    @Override
    public String toString() {
        return super.toString() + "  " + rowsCount + "x" + columnsCount + "\n" + asString();
    }

    public String asString() {
        return asString(rows);
    }

    public String asString(List<List<Double>> list) {
        StringBuilder s = new StringBuilder();
        for (List<Double> row :
                list) {
            s.append(row + "\n");
        }
        return s.toString();
    }

    /**
     * @return a new matrix containing the result.
     */
    public Matrix add(Matrix m) throws NotEqual {
        if (rowsCount != m.rowsCount || columnsCount != m.columnsCount)
            throw new NotEqual(this, m);
        Matrix newM = new Matrix(m.rowsCount, m.columnsCount);
        for (int i = 0; i < m.rows.size(); i++) {
            List<Double> row = rows.get(i);
            List<Double> otherRow = m.rows.get(i);
            List<Double> newRow = new ArrayList<>(row.size());
            newM.setRow(i, newRow);
            for (int j = 0; j < row.size(); j++) {
                newRow.add(row.get(j) + otherRow.get(j));
            }
        }
        return newM;
    }

    /**
     * @return a new matrix containing the result.
     */
    public Matrix subtract(Matrix m) throws NotEqual {
        if (rowsCount != m.rowsCount || columnsCount != m.columnsCount)
            throw new NotEqual(this, m);
        Matrix newM = new Matrix(m.rowsCount, m.columnsCount);
        for (int i = 0; i < m.rows.size(); i++) {
            List<Double> row = rows.get(i);
            List<Double> otherRow = m.rows.get(i);
            List<Double> newRow = new ArrayList<>(row.size());
            newM.setRow(i, newRow);
            for (int j = 0; j < row.size(); j++) {
                newRow.add(row.get(j) - otherRow.get(j));
            }
        }
        return newM;
    }

    /**
     * Multiplies this matrix with the provided number.
     *
     * @return a new matrix containing the result.
     */
    public Matrix multiply(Double num) {
        Matrix newM = new Matrix(rowsCount, columnsCount);
        for (int i = 0; i < rows.size(); i++) {
            List<Double> row = rows.get(i);
            List<Double> newRow = new ArrayList<>(row.size());
            newM.setRow(i, newRow);
            for (Double d : row) {
                newRow.add(d * num);
            }
        }
        return newM;
    }

    /**
     * Multiplies this matrix with the provided matrix. <br>
     * Also called scalar matrix multiplication. <br>
     *
     * @return a new matrix containing the result.
     * @throws NotEqual if the provided matrix row count is not equal to the current matrix column count.
     */
    public Matrix multiply(Matrix m) throws NotEqual {
        if (m.rowsCount != columnsCount)
            throw new NotEqual("Provided matrix rowCount must be equal to this matrix columnCount. " + m.rowsCount + "!=" + columnsCount);
        Matrix newM = new Matrix(rowsCount, m.columnsCount);
        List<List<Double>> otherColumns = m.getColumns();
        for (int i = 0; i < rowsCount; i++) {
            List<Double> row = rows.get(i);
            List<Double> newRow = new ArrayList<>(otherColumns.size());
            newM.setRow(i, newRow);
            for (List<Double> otherColumn : otherColumns) {
                double result = 0.0;
                for (int k = 0; k < row.size(); k++) {
                    result += row.get(k) * otherColumn.get(k);
                }
                newRow.add(result);
            }
        }
        return newM;
    }

    public Double determinant() {
        return null;
    }

    public Matrix laplaceExpansion() {
        return null;
    }

    public class NotEqual extends Exception {
        public NotEqual(String message) {
            super(message);
        }

        public NotEqual(Matrix m0, Matrix m1) {
            super("Both matrices must have the same row and column count. m0=" + m0.rowsCount
                    + "x" + m0.columnsCount + " != m1=" + m1.rowsCount + "x" + m1.columnsCount);
        }
    }

    public class NotQuadratic extends Exception {
        public NotQuadratic(Matrix m) {
            super("Matrix must be quadratic (rows count == columns count) to perform this operation! " + m.rowsCount + "!=" + m.columnsCount);
        }
    }
}
