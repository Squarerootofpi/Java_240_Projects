package services;

import java.util.ArrayList;

public class NameStrings {
    private String[] data;

    public NameStrings(String[] data) {
        this.data = data;
    }

    public String[] getNames() {
        return data;
    }

    public void setNames(String[] data) {
        this.data = data;
    }

    public int size() {
        return data.length;
    }

    public String at(int index) {
        return data[index];
    }
}
