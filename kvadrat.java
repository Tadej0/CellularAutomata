package com.company;

public class kvadrat {
    //LOKACIJA
    int ime;
    int x;
    int y;

    //VELIKOST
    int visina;
    int sirina;

    //IZGLED
    int RdecaBarva;
    int ZelenaBarva;
    int ModraBarva;

    //TOCNE KOORDINATE
    int zacetniX;
    int zacetniY;

    //SPLOSNE LASTNOSTI
    boolean zivMrtev;
    boolean obiskan;


    //njegove funkcije
    public void ozivi(){
        this.ModraBarva=0;
        this.ZelenaBarva=0;
        this.RdecaBarva=0;
        this.obiskan=true;
        this.zivMrtev=true;
    }
    public void ubij(){
        this.ModraBarva=255;
        this.ZelenaBarva=255;
        this.RdecaBarva=255;
        this.zivMrtev=false;
    }
    public void obarvaj(int red, int green, int blue){
        this.ModraBarva=blue;
        this.ZelenaBarva=green;
        this.RdecaBarva=red;
    }

}
