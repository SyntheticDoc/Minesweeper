package com.gameobject;

public class Digital_Display_Digit extends Gameasset {
    private static int digitNum = 0;

    public Digital_Display_Digit(double posX, double posY) {
        super("Digit_" + digitNum, AssetHandler.digit_empty, posX, posY, 20, 35);
        ++digitNum;
    }

    public static void resetDigitNum() {
        digitNum = 0;
    }

    public void set(String value) {
        if(value == null) {
            assetFile = AssetHandler.digit_empty;
        }

        switch(value.toLowerCase()) {
            case "":
            case "empty":
                assetFile = AssetHandler.digit_empty;
                break;
            case "minus":
                assetFile = AssetHandler.digit_minus;
                break;
            case "0":
                assetFile = AssetHandler.digit_0;
                break;
            case "1":
                assetFile = AssetHandler.digit_1;
                break;
            case "2":
                assetFile = AssetHandler.digit_2;
                break;
            case "3":
                assetFile = AssetHandler.digit_3;
                break;
            case "4":
                assetFile = AssetHandler.digit_4;
                break;
            case "5":
                assetFile = AssetHandler.digit_5;
                break;
            case "6":
                assetFile = AssetHandler.digit_6;
                break;
            case "7":
                assetFile = AssetHandler.digit_7;
                break;
            case "8":
                assetFile = AssetHandler.digit_8;
                break;
            case "9":
                assetFile = AssetHandler.digit_9;
                break;
            default:
                throw new IllegalArgumentException("ERROR in Digital_Display_Digit.set(): Unknown value " + value);
        }
    }

    public String getValue() {
        if(assetFile == null) {
            return "empty";
        }

        switch(assetFile.toLowerCase()) {
            case AssetHandler.digit_empty:
                return "empty";
            case AssetHandler.digit_minus:
                return "minus";
            case AssetHandler.digit_0:
                return "0";
            case AssetHandler.digit_1:
                return "1";
            case AssetHandler.digit_2:
                return "2";
            case AssetHandler.digit_3:
                return "3";
            case AssetHandler.digit_4:
                return "4";
            case AssetHandler.digit_5:
                return "5";
            case AssetHandler.digit_6:
                return "6";
            case AssetHandler.digit_7:
                return "7";
            case AssetHandler.digit_8:
                return "8";
            case AssetHandler.digit_9:
                return "9";
            default:
                throw new IllegalArgumentException("ERROR in Digital_Display_Digit.set(): Unknown asset file " + assetFile);
        }
    }

    @Override
    protected String enumerateAttributes() {
        return super.enumerateAttributes() + ",currentValue=" + getValue();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
