package edu.wpi.cs3733.D22.teamU.BackEnd;

import java.io.IOException;
import java.util.ArrayList;

public interface DataDao<T> {
    String csvFile = null;
    ArrayList<T> list();
    void CSVToJava() throws IOException;
    void JavaToSQL();
    void SQLToJava();
    void JavaToCSV(String csvFile) throws IOException;
    void printTable();
    void edit(T data);
    void add(T data);
    void remove(T data);
    void search(T data);
}
