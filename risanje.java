package com.company;

import processing.core.PApplet;
import java.util.Scanner;
import java.util.ArrayList;


public class risanje extends PApplet {
    //---KONSTANTE--------------------------------------------
    int Sirina = 800;
    int Visina = 800;
    mreza mreza1 = new mreza();
    int animeCLIomejitev = 50;
    //mrezi
    ArrayList<kvadrat> tabela = new ArrayList<>();
    ArrayList<kvadrat> tabelaP = new ArrayList<>();
    //miska postane objekt
    racunalniska_miska miska = new racunalniska_miska();
    //nadzor simulacije
    boolean zagonSimulacije = false;
    boolean prviZagon = true;
    //-----------------------------------------------------------

    public void animeCLI(){
        int i = 0;
        for(i=0;i<animeCLIomejitev;i++){
            System.out.print("*");
        }
        System.out.println("");
    }

    public void navodila() {
        animeCLI();
        System.out.println("Navodila za uporabo: ");
        animeCLI();
        System.out.println("Max stolpcev: " + Sirina);
        System.out.println("Prvotno stevilo FPS: "+ mreza1.fps);
        animeCLI();
    }

    public void konstrukcija(int mrezaX, int mrezaY, int StVrstic, int StStolpcev){
        int stevec = 0;
        for(int i=0; i<StVrstic; i++){
            for(int j=0; j<StStolpcev; j++){
                kvadrat kocka = new kvadrat();
                //BARVA
                kocka.ModraBarva = 255;
                kocka.RdecaBarva = 255;
                kocka.ZelenaBarva = 255;

                //LOKACIJA
                kocka.ime = stevec;
                kocka.x = j;
                kocka.y = i;

                //DIMENZIJE
                kocka.visina = mrezaY/StVrstic;
                kocka.sirina = mrezaX/StStolpcev;

                //KONKRETNE KOORDINATE
                kocka.zacetniX = kocka.zacetniX + kocka.sirina * j;
                kocka.zacetniY = kocka.zacetniY + kocka.visina *i;

                //SPLOSNE LASTNOSTI
                kocka.zivMrtev = false;
                kocka.obiskan = false;

                //povecaj
                stevec++;
                tabela.add(kocka);
                tabelaP.add(kocka);
            }
        }
    }

    public void preverjanjeIzpis(){
        System.out.println("Velikost Tabela:" + tabela.size());
        System.out.println("Velikost TabelaP:"+tabelaP.size());

        for (int i=0; i<tabelaP.size();i++){
            kvadrat tmp1=tabela.get(i);
            kvadrat tmp2=tabelaP.get(i);
            System.out.println("Ime"+tmp1.ime+" Ziv: "+tmp1.zivMrtev);
            System.out.println("Ime"+tmp2.ime+" Ziv: "+tmp2.zivMrtev);
        }
    }

    public void setup() {
        size(Sirina, Visina);

        navodila();
        animeCLI();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Stevilo elementov v vrstici: ");
        mreza1.StVrstic = scanner.nextInt();
        mreza1.StStolpcev = mreza1.StVrstic;
        mreza1.StKvadratov = mreza1.StVrstic * mreza1.StStolpcev;
        konstrukcija(Sirina, Visina, mreza1.StVrstic, mreza1.StStolpcev);

    }
    public void povecajFPS(mreza mreza1){
        mreza1.fps++;
    }
    public void pomanjsajFPS(mreza mreza1){
        mreza1.fps--;
    }

    public void framesPerSecond() {
        if (keyCode == UP) {
            povecajFPS(mreza1);
        }
        if (keyCode == DOWN) {
            pomanjsajFPS(mreza1);
        }
    }

    public int pretvorbaXYvZap(int lokacijaX,int lokacijaY, int elVrstici){
        return (elVrstici*(lokacijaY)+lokacijaX);
    }


    public boolean preveriLevegaSoseda(kvadrat trenutni){
        if(trenutni.zacetniX==0){
            return false;
        }
        else if(trenutni.ime==tabela.size())return false;
        else{
            kvadrat leviSosed = new kvadrat();
            int t=trenutni.ime;
            t=t-1;
            leviSosed = tabelaP.get(t);
            if(leviSosed.zivMrtev == true) return true;
            else return false;
        }
    }


    public boolean preveriDesnegaSoseda(kvadrat trenutni){
        if (trenutni.zacetniX >= ((mreza1.StStolpcev-1) * trenutni.sirina)) return false;
        else if (trenutni.ime==tabela.size()-1) return false;
        else{

            int t=trenutni.ime;
            t=t+1;
            kvadrat desniSosed = tabelaP.get(t);
            if (desniSosed.zivMrtev == true)  return true;
            else return false;
        }
    }
    public boolean preveriZgornjegaSoseda(kvadrat trenutni){
        if(trenutni.zacetniY == 0) return false;
        else{
            int t=trenutni.ime;
            t=t-mreza1.StStolpcev;
            kvadrat zgornjiSosed = tabelaP.get(t);
            if(zgornjiSosed.zivMrtev == true) return true;
            else return false;
        }
    }

    public boolean preveriSpodnjegaSoseda(kvadrat trenutni){
        if(trenutni.zacetniY >= (mreza1.StVrstic-1)*trenutni.visina) return false;
        else{
            int t=trenutni.ime;
            t=t+mreza1.StStolpcev;
            kvadrat spodnjiSosed = tabelaP.get(t);
            if(spodnjiSosed.zivMrtev == true) return true;
            else return false;
        }
    }

    public void izvrzbaPravil(kvadrat trenutni, int st){
        if(st==0){
            trenutni.ubij();
        }
        if(st==1){
            trenutni.ozivi();
        }
        if(st==2){
            trenutni.ozivi();
        }
        if(st==3){
            trenutni.ozivi();
        }
        if(st==4){
            trenutni.ubij();
        }
    }

    public void preveriSosede(kvadrat trenutni){
        boolean leviSosed = preveriLevegaSoseda(trenutni);
        boolean desniSosed = preveriDesnegaSoseda(trenutni);
        boolean zgornjiSosed = preveriZgornjegaSoseda(trenutni);
        boolean spodnjiSosed = preveriSpodnjegaSoseda(trenutni);

        int st=0;
        if (leviSosed==true){
            st++;
        }
        if (desniSosed==true){
            st++;
        }
        if (zgornjiSosed==true){
            st++;
        }
        if (spodnjiSosed==true){
            st++;
        }
        izvrzbaPravil(trenutni, st);

    }

    public void miskaNadKvadratom(int miskaX,int miskaY, int opcija){
        kvadrat tmp = new kvadrat();
        tmp = tabela.get(0);
        int klikX = miskaX;
        int klikY = miskaY;

        int lokacijaX = 0;
        int lokacijaY = 0;

        for (int i=1;i<Sirina ;i++){
            if((i*tmp.sirina)<klikX) lokacijaX++;
            else break;
        }
        for (int i=1;i<Visina;i++){
            if((i*tmp.visina)<klikY)lokacijaY++;
            else break;
        }
        int id = pretvorbaXYvZap(lokacijaX,lokacijaY, mreza1.StStolpcev);

        kvadrat iskanje = new kvadrat();

        for(int i=0; i < (mreza1.StKvadratov);i++){
            iskanje = tabela.get(i);
            if((id) == iskanje.ime){
                if(opcija == 1){
                    iskanje.ozivi();
                }
                else if(opcija == 2){
                    iskanje.ubij();
                }
            }
        }
    }

    public void kopirajOriginal(){
        tabelaP.clear();
        for(int i=0;i<tabela.size();i++){
            kvadrat trenutni = new kvadrat();
            trenutni = tabela.get(i);
            kvadrat novi = new kvadrat();
            novi.ime=trenutni.ime;
            novi.x=trenutni.x;
            novi.y=trenutni.y;
            novi.visina=trenutni.visina;
            novi.sirina=trenutni.sirina;
            novi.RdecaBarva=trenutni.RdecaBarva;
            novi.ZelenaBarva=trenutni.ZelenaBarva;
            novi.ModraBarva=trenutni.ModraBarva;
            novi.zacetniX=trenutni.zacetniX;
            novi.zacetniY=trenutni.zacetniY;
            novi.zivMrtev=trenutni.zivMrtev;
            novi.obiskan=trenutni.obiskan;

            tabelaP.add(novi);
        }
    }

//---KONSTANTNA-ZANKA---------------------------------------------------------------------------------------------------

    public void draw(){
        frameRate(mreza1.fps);

        if(keyPressed){
            //"gor" in "dol" tipki
            framesPerSecond();

            if (keyCode == RIGHT)
                zagonSimulacije = true;

            if (keyCode == LEFT)
                zagonSimulacije = false;
        }

        if(mousePressed){
            miska.x = mouseX;
            miska.y = mouseY;
            int opcija=0;
            if(mouseButton == LEFT) opcija = 1;
            if(mouseButton == RIGHT) opcija = 2;
            miskaNadKvadratom(miska.x,miska.y, opcija);
        }

        if(zagonSimulacije==true){
            if (prviZagon == true){
                mreza1.fps = 3;
                prviZagon = false;
            }
            for (int i=0; i < tabela.size();i++){
                kvadrat tmp = new kvadrat();
                tmp = tabela.get(i);
                preveriSosede(tmp);
            }
        }
        //---IZRIS-MREZE------------------------------------
        for (int i=0; i < tabela.size();i++){
            kvadrat novi = new kvadrat();
            novi = tabela.get(i);
            if((novi.obiskan==true)&&(novi.zivMrtev==false)){
                fill(135,206,250);
            }
            else{
                fill(novi.RdecaBarva, novi.ZelenaBarva, novi.ModraBarva);
            }
            rect(novi.zacetniX,novi.zacetniY,novi.visina,novi.sirina);
        }
        //--------------------------------------------------
        kopirajOriginal();
    }

}