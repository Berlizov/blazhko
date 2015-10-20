package com.berlizov.dataorgua;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 350z6_000 on 18.10.2015.
 * стуруктура для хранения разпаршеных json
 */
public class JSONTable {
    // хедеры
    private List<String> Headers=new ArrayList<>();
    // типы колонок
    private List<String> Types=new ArrayList<>();
    // содержимое колонок
    private List<List<String>> Rows=new ArrayList<>();

    // АВТОГЕНЕРАЦИЯЯЯЯ!!!!!!!!!!!
    public List<String> getHeaders() {
        return Headers;
    }

    public int getColumIndex(String colName){
        return Headers.indexOf(colName);
    }

    public String getHeader(int index) {
        return Headers.get(index);
    }

    public void addHeader(String header) {
        Headers.add(header);
    }

    public String getType(int index) {
        return Types.get(index);
    }

    public List<String> getTypes() {
        return Types;
    }

    public void addType(String type) {
        Types.add(type);
    }

    public List<List<String>> getRows() {
        return Rows;
    }

    public List<String> getLine(int index) {
        return Rows.get(index);
    }

    public String getRow(int index1,int index2) {
        return Rows.get(index1).get(index2);
    }

    public void addRow(String row) {
        Rows.get(Rows.size()-1).add(row);
    }
    public void addLine() {
        Rows.add(new ArrayList<String>());
    }

    public int  getLinesCount(){
        return Rows.size();
    }

    /**
    * парсинг json
    */
    public void parseJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONObject result = obj.getJSONObject("result");
        JSONArray header = result.getJSONArray("fields");
        //заполняем хедеры и типы ячеек
        for(int i=0;i<header.length();i++){
            JSONObject head = header.getJSONObject(i);
            addHeader(head.getString("id"));
            addType(head.getString("type"));
        }
        //заполняем яейки
        JSONArray lines = result.getJSONArray("records");
        for(int i=0;i<lines.length();i++){
            JSONObject line = lines.getJSONObject(i);
            addLine();
            for (String h : Headers) {
                addRow(line.getString(h));
            }
        }
    }

    @Override
    public String toString() {
        return Headers +
                "\n" + Types +
                "\n" + Rows +
                '}';
    }

    public int getColumnCount() {
        return Headers.size();
    }
}
