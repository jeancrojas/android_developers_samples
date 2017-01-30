package com.scriptingsystems.intentscomunes;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

/**
 * Created by jeanrojas on 28/1/17.
 */

public class InputFilterMinMax implements InputFilter {
    private String TAG = "InputFilterMinMax";
    private int min, max;

    public InputFilterMinMax(int max, int min) {
        this.max = max;
        this.min = min;
    }

    public InputFilterMinMax(String max, String min) {
        this.max = Integer.parseInt( max );
        this.min = Integer.parseInt( min );
    }

    /**
     *
     * @param source, el caracter introducido
     * @param start nº caracter inicial, siempre es 0
     * @param end nº caracter introducido.
     * @param dest, el contenido total de lo introducido
     * @param dstart nº de caracteres totales - 1
     * @param dend nº de caracteres totales
     * @return Si es correcto return null
     */

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Log.d(TAG, "CharSequence()... \nsource: "+source.toString()+"\n start: " + start + "\nend: "+ end+ "\ndest: "+dest.toString() + "\ndstart: "+dstart+"\ndend: "+dend);


        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(min, max, input) && limitCaracter(dend)) {
                return null;
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return "";
    }

    private boolean isInRange(int min, int max, int input) {

        return max > min ? input >= min && input <= max : input >= max && input <= min;
    }

    private boolean limitCaracter (int nCaracteres ){
        return nCaracteres<2 ? true: false;
    }
}
