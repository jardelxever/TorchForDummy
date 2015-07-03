package com.example.lmasiello.torchfordummy;

/**
 * Created by lmasiello on 03/07/2015.
 */
public class ConvertString {
    public String ReturnMorseString(String textWrite){
        Morse morseCharacter = new Morse();
        String morseString = "";

        textWrite = textWrite.trim();
        textWrite = textWrite.toUpperCase();
        for (int i = 0; i <= textWrite.length() -1; i++ ) {
            switch(textWrite.charAt(i)){
                case 'A':
                    morseString = morseString + morseCharacter.getA();
                case 'B':
                    morseString = morseString + morseCharacter.getB();
                case 'C':
                    morseString = morseString + morseCharacter.getC();
                case 'D':
                    morseString = morseString + morseCharacter.getD();
                case 'E':
                    morseString = morseString + morseCharacter.getE();
                case 'F':
                    morseString = morseString + morseCharacter.getF();
                case 'G':
                    morseString = morseString + morseCharacter.getG();
                case 'H':
                    morseString = morseString + morseCharacter.getH();
                case 'I':
                    morseString = morseString + morseCharacter.getI();
                case 'J':
                    morseString = morseString + morseCharacter.getJ();
                case 'K':
                    morseString = morseString + morseCharacter.getK();
                case 'L':
                    morseString = morseString + morseCharacter.getL();
                case 'M':
                    morseString = morseString + morseCharacter.getM();
                case 'N':
                    morseString = morseString + morseCharacter.getN();
                case 'O':
                    morseString = morseString + morseCharacter.getO();
                case 'P':
                    morseString = morseString + morseCharacter.getP();
                case 'Q':
                    morseString = morseString + morseCharacter.getQ();
                case 'R':
                    morseString = morseString + morseCharacter.getR();
                case 'S':
                    morseString = morseString + morseCharacter.getS();
                case 'T':
                    morseString = morseString + morseCharacter.getT();
                case 'U':
                    morseString = morseString + morseCharacter.getU();
                case 'V':
                    morseString = morseString + morseCharacter.getV();
                case 'W':
                    morseString = morseString + morseCharacter.getW();
                case 'X':
                    morseString = morseString + morseCharacter.getX();
                case 'Y':
                    morseString = morseString + morseCharacter.getY();
                case 'Z':
                    morseString = morseString + morseCharacter.getZ();
                case '0':
                    morseString = morseString + morseCharacter.getZero();
                case '1':
                    morseString = morseString + morseCharacter.getUno();
                case '2':
                    morseString = morseString + morseCharacter.getDue();
                case '3':
                    morseString = morseString + morseCharacter.getTre();
                case '4':
                    morseString = morseString + morseCharacter.getQuattro();
                case '5':
                    morseString = morseString + morseCharacter.getCinque();
                case '6':
                    morseString = morseString + morseCharacter.getSei();
                case '7':
                    morseString = morseString + morseCharacter.getSette();
                case '8':
                    morseString = morseString + morseCharacter.getOtto();
                case '9':
                    morseString = morseString + morseCharacter.getNove();
                case '.':
                    morseString = morseString + morseCharacter.getPunto();
                case ',':
                    morseString = morseString + morseCharacter.getVirgola();
                case ':':
                    morseString = morseString + morseCharacter.getDuepunti();
                case '?':
                    morseString = morseString + morseCharacter.getPuntointerrogativo();
                case '=':
                    morseString = morseString + morseCharacter.getUguale();
                case '-':
                    morseString = morseString + morseCharacter.getMeno();
                case '(':
                    morseString = morseString + morseCharacter.getTondaaperta();
                case ')':
                    morseString = morseString + morseCharacter.getTondachiusa();
                case '\"':
                    morseString = morseString + morseCharacter.getVirgolette();
                case '\'':
                    morseString = morseString + morseCharacter.getApice();
                case '/':
                    morseString = morseString + morseCharacter.getSlash();
                case '@':
                    morseString = morseString + morseCharacter.getChiocciola();
            }
        }
        return morseString;
    }
}
