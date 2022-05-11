package com.osiris.maths.human;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
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
            for (int j = 0; j < columnsCount; j++) {
                values.add(0.0);
            }
            this.rows.add(values);
        }
    }

    public Matrix copy(){
        Matrix copy = new Matrix(rowsCount, columnsCount);
        copy.rows = new ArrayList<>(this.rows);
        return copy;
    }

    /**
     * Expects string looking like this:
     * 10 2 1
     * 5 5 5
     * 1 2 7
     */
    public Matrix set(String s){
        List<Double> row;
        int rowIndex = 0;
        try(BufferedReader reader = new BufferedReader(new StringReader(s))){
            String line;
            while((line = reader.readLine()) != null){
                row = new ArrayList<>();
                String[] sArr = line.split(" ");
                for (String num : sArr) {
                    String numClean = num.trim();
                    if(!numClean.isEmpty())
                        row.add(Double.parseDouble(numClean));
                    else row.add(0.0);
                }
                setRow(rowIndex, row);
                rowIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
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

    public void fillRow(int i, Double... values) {
        List<Double> list = new ArrayList<>(values.length);
        Collections.addAll(list, values);
        fillRow(i, list);
    }

    public void fillRow(int i, List<Double> values) {
        for (int j = 0; j < this.rows.get(i).size(); j++) {
            this.rows.get(i).set(j, values.get(j));
        }
    }

    /**
     * Switches row at i1 with the row at i2. (i == index)
     */
    public void switchRow(int i1, int i2){
        List<Double> tempRow = rows.get(i1);
        rows.set(i1, rows.get(i2));
        rows.set(i2, tempRow);
    }

    /**
     * Performs addition of provided number, with each number inside the row i.
     */
    public List<Double> sumRowWith(int i, Double num){
        List<Double> newRow = new ArrayList<>(columnsCount);
        for (int j = 0; j < rows.get(i).size(); j++) {
            newRow.add(rows.get(i).get(j) + num);
        }
        return newRow;
    }

    public List<Double> sumRowWith(int i, List<Double> otherRow){
        List<Double> newRow = new ArrayList<>(columnsCount);
        for (int j = 0; j < rows.get(i).size(); j++) {
            newRow.add(rows.get(i).get(j) + otherRow.get(j));
        }
        return newRow;
    }

    /**
     * Performs subtraction of provided number, with each number inside the row i.
     */
    public List<Double> substractRowWith(int i, Double num){
        List<Double> newRow = new ArrayList<>(columnsCount);
        for (int j = 0; j < rows.get(i).size(); j++) {
            newRow.add(rows.get(i).get(j) - num);
        }
        return newRow;
    }

    /**
     * Performs multiplication of provided number, with each number inside the row i.
     */
    public List<Double> multiplyRowWith(int i, Double num){
        List<Double> newRow = new ArrayList<>(columnsCount);
        for (int j = 0; j < rows.get(i).size(); j++) {
            newRow.add(rows.get(i).get(j) * num);
        }
        return newRow;
    }

    /**
     * Performs division of provided number, with each number inside the row i.
     */
    public List<Double> divideRowWith(int i, Double num){
        List<Double> newRow = new ArrayList<>(columnsCount);
        for (int j = 0; j < rows.get(i).size(); j++) {
            newRow.add(rows.get(i).get(j) / num);
        }
        return newRow;
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
            throw new NotEqual("PROVIDED matrix ROW count must be equal to THIS matrix COLUMN count. " + m.rowsCount + "!=" + columnsCount);
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

    public Double determinantByGaussianElimination(boolean printSteps) throws NotQuadratic {
        if(!isQuadratic()) throw new NotQuadratic(this);

        if(printSteps) {
            System.out.println("PERFORM GAUSSIAN ELIMINATION ON: ");
            System.out.println(this);
            System.out.println("Before starting make sure element at(0,0) is == 1 and != 0.");
        }

        // These are operations done that need to be reverted at the end
        List<Variable> operationsToRevert = new ArrayList<>();

        if(at(0, 0) == 0.0) {
            rows.set(0, sumRowWith(0, 1.0));
            operationsToRevert.add(Variable.parse("+1.0"));
        }
        if(at(0,0) != 1.0) {
            rows.set(0, divideRowWith(0, at(0, 0)));
            operationsToRevert.add(Variable.parse("/"+at(0, 0)));
        }
        if(printSteps) System.out.println(this.asString());

        List<List<Double>> columns = getColumns();
        for (int i = 0; i < columnsCount - 1; i++) {
            if(printSteps) System.out.println("Step "+i);
            if(at(i, i) != 1.0){
                if(at(i,i) == 0.0){
                    if(printSteps) System.out.print("at("+i+", "+i+") == "+ at(i,i)+" != 1.0, but 0.0, thus "+this.rows.get(i)+" + 1 = ");
                    List<Double> row = sumRowWith(i, 1.0);
                    operationsToRevert.add(Variable.parse("+1.0"));
                    this.rows.set(i, row);
                    if(printSteps) System.out.println(row);
                } else{
                    if(printSteps) System.out.print("at("+i+", "+i+") == "+ at(i,i)+" != 1.0, thus "+this.rows.get(i)+" / "+at(i,i)+" = ");
                    List<Double> row = divideRowWith(i, at(i,i));
                    operationsToRevert.add(Variable.parse("/"+at(i,i)));
                    this.rows.set(i, row);
                    if(printSteps) System.out.println(row);
                }
            }
            for (int j = i + 1; j < columns.get(i).size(); j++) {
                if(printSteps) System.out.print(rows.get(i)+" * "+Utils.invertNumber(columns.get(i).get(j))+" = ");
                List<Double> row = multiplyRowWith(i, Utils.invertNumber(columns.get(i).get(j)));
                if(printSteps) {
                    System.out.println(row);
                    System.out.print(rows.get(j)+" + "+row+" = ");
                }
                List<Double> result = sumRowWith(j, row);
                if(printSteps) System.out.println(result);
                this.rows.set(j, result);
            }
            columns = getColumns();
            System.out.println("Result:");
            System.out.println(this.asString());
        }

        if(printSteps) System.out.println("Calculating determinant: ");
        Double result = 1.0;
        for (int i = 0; i < rowsCount; i++) {
            if(printSteps) System.out.print(at(i,i));
            if(printSteps && i != rowsCount-1) System.out.print(" *");
            result *= at(i, i);
        }
        if(printSteps) System.out.println(" = "+result);
        for (Variable var : operationsToRevert) {
            if(printSteps) System.out.println("Revert operation: "+var.operator+" "+var.value);
            if(var.operator == Variable.Operator.PLUS) result -= var.value;
            else if(var.operator == Variable.Operator.MINUS) result += var.value;
            else if(var.operator == Variable.Operator.MULTIPLY) result /= var.value;
            else if(var.operator == Variable.Operator.DIVIDE) result *= var.value;
        }
        if(printSteps) System.out.println("Determinant: "+ result);
        return result;
    }

    public boolean isQuadratic(){
        return rowsCount == columnsCount;
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
