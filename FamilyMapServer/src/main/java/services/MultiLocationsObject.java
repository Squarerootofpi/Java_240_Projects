package services;

public class MultiLocationsObject {
    private LocationObject[] data;

    public MultiLocationsObject(LocationObject[] data) {
        this.data = data;
    }

    public LocationObject[] getData() {
        return data;
    }

    public void setData(LocationObject[] data) {
        this.data = data;
    }

    public int size()
    {
        return data.length;
    }

    public LocationObject at(int index) {
        return data[index];
    }
}
