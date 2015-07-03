package com.example.lmasiello.torchfordummy;

/**
 * Created by lmasiello on 03/07/2015.
 */
public class Util {
    public String ReturnMorseString(String textWrite){
        Morse morseCharacter = new Morse();
        String morseString = "";

        textWrite = textWrite.trim();
        textWrite = textWrite.toUpperCase();
        for (int i = 0; i <= textWrite.length() -1; i++ ) {
            switch(textWrite.charAt(i)){
                case 'A':
                    morseString = morseString + morseCharacter.getA() + "_";
                case 'B':
                    morseString = morseString + morseCharacter.getB()+ "_";
                case 'C':
                    morseString = morseString + morseCharacter.getC()+ "_";
                case 'D':
                    morseString = morseString + morseCharacter.getD()+ "_";
                case 'E':
                    morseString = morseString + morseCharacter.getE()+ "_";
                case 'F':
                    morseString = morseString + morseCharacter.getF()+ "_";
                case 'G':
                    morseString = morseString + morseCharacter.getG()+ "_";
                case 'H':
                    morseString = morseString + morseCharacter.getH()+ "_";
                case 'I':
                    morseString = morseString + morseCharacter.getI()+ "_";
                case 'J':
                    morseString = morseString + morseCharacter.getJ()+ "_";
                case 'K':
                    morseString = morseString + morseCharacter.getK()+ "_";
                case 'L':
                    morseString = morseString + morseCharacter.getL()+ "_";
                case 'M':
                    morseString = morseString + morseCharacter.getM()+ "_";
                case 'N':
                    morseString = morseString + morseCharacter.getN()+ "_";
                case 'O':
                    morseString = morseString + morseCharacter.getO()+ "_";
                case 'P':
                    morseString = morseString + morseCharacter.getP()+ "_";
                case 'Q':
                    morseString = morseString + morseCharacter.getQ()+ "_";
                case 'R':
                    morseString = morseString + morseCharacter.getR()+ "_";
                case 'S':
                    morseString = morseString + morseCharacter.getS()+ "_";
                case 'T':
                    morseString = morseString + morseCharacter.getT()+ "_";
                case 'U':
                    morseString = morseString + morseCharacter.getU()+ "_";
                case 'V':
                    morseString = morseString + morseCharacter.getV()+ "_";
                case 'W':
                    morseString = morseString + morseCharacter.getW()+ "_";
                case 'X':
                    morseString = morseString + morseCharacter.getX()+ "_";
                case 'Y':
                    morseString = morseString + morseCharacter.getY()+ "_";
                case 'Z':
                    morseString = morseString + morseCharacter.getZ()+ "_";
                case '0':
                    morseString = morseString + morseCharacter.getZero()+ "_";
                case '1':
                    morseString = morseString + morseCharacter.getUno()+ "_";
                case '2':
                    morseString = morseString + morseCharacter.getDue()+ "_";
                case '3':
                    morseString = morseString + morseCharacter.getTre()+ "_";
                case '4':
                    morseString = morseString + morseCharacter.getQuattro()+ "_";
                case '5':
                    morseString = morseString + morseCharacter.getCinque()+ "_";
                case '6':
                    morseString = morseString + morseCharacter.getSei()+ "_";
                case '7':
                    morseString = morseString + morseCharacter.getSette()+ "_";
                case '8':
                    morseString = morseString + morseCharacter.getOtto()+ "_";
                case '9':
                    morseString = morseString + morseCharacter.getNove()+ "_";
                case '.':
                    morseString = morseString + morseCharacter.getPunto()+ "_";
                case ',':
                    morseString = morseString + morseCharacter.getVirgola()+ "_";
                case ':':
                    morseString = morseString + morseCharacter.getDuepunti()+ "_";
                case '?':
                    morseString = morseString + morseCharacter.getPuntointerrogativo()+ "_";
                case '=':
                    morseString = morseString + morseCharacter.getUguale()+ "_";
                case '-':
                    morseString = morseString + morseCharacter.getMeno()+ "_";
                case '(':
                    morseString = morseString + morseCharacter.getTondaaperta()+ "_";
                case ')':
                    morseString = morseString + morseCharacter.getTondachiusa()+ "_";
                case '\"':
                    morseString = morseString + morseCharacter.getVirgolette()+ "_";
                case '\'':
                    morseString = morseString + morseCharacter.getApice()+ "_";
                case '/':
                    morseString = morseString + morseCharacter.getSlash()+ "_";
                case '@':
                    morseString = morseString + morseCharacter.getChiocciola()+ "_";
                case ' ':
                    morseString = morseString + "?";
            }
        }
        return morseString;
    }
}
