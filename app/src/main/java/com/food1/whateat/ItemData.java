package com.food1.whateat;

public class ItemData {
    public String pName;
    public String pPhoneNumber;
    public String distance;

    public int progress;

    public String getPlaceName(){
        return pName;
    }

    public void setPlaceName(String pName){
        this.pName = pName;
    }

    public String getPlacePhoneNumber(){
        return pPhoneNumber;
    }

    public void setPlacePhoneNumber(String phoneNumber){
        this.pPhoneNumber = phoneNumber;
    }

    public String getDistance(){return distance;}

    public void setDistance(String distance){
        this.distance = distance;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress){
        this.progress = progress;
    }
}
