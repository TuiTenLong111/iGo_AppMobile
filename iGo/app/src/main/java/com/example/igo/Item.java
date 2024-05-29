package com.example.igo;

public class Item {
    private String iditem, itemname, typename, addresscode, describeitem;
    private float ratings;
    private int pathimg;

    public Item(String iditem, String itemname, String typename, String addresscode, String describeitem, float ratings, int pathimg) {
        this.iditem = iditem;
        this.itemname = itemname;
        this.typename = typename;
        this.addresscode = addresscode;
        this.describeitem = describeitem;
        this.ratings = ratings;
        this.pathimg = pathimg;
    }

    public String getIditem() {
        return iditem;
    }

    public void setIditem(String iditem) {
        this.iditem = iditem;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getAddresscode() {
        return addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode;
    }

    public String getDescribeitem() {
        return describeitem;
    }

    public void setDescribeitem(String describeitem) {
        this.describeitem = describeitem;
    }

    public float getRatings() {
        return ratings;
    }

    public void setRatings(float ratings) {
        this.ratings = ratings;
    }

    public int getPathimg() {
        return pathimg;
    }

    public void setPathimg(int pathimg) {
        this.pathimg = pathimg;
    }
}
