package com.sid_hart.stones_marbelpqs;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Siddharth on 4/29/2016.
 */
public class KotaMeasuresPrice {

    private int _id;
    private String _kotameasures;
    private String _price;
    private Context context;
    private DBHandler dbHandlerstones;

    public KotaMeasuresPrice(Context context) {
        this.context = context;
    }

    public KotaMeasuresPrice(String kotameasures, String price) {
        this._kotameasures = kotameasures;
        this._price = price;
    }

    public void set_kotaid(int id) {
        this._id = id;
    }

    public void set_kotameasures(String kotameasure) {
        this._kotameasures = kotameasure;
    }

    public void set_kotaprices(String price) {
        this._price = price;
    }

    public int get_kotaid() {
        return _id;
    }

    public ArrayList<Integer> get_kotameasures() {
        dbHandlerstones = new DBHandler(this.context, null, null, 2);
        return dbHandlerstones.getMeasurements();
    }

    public ArrayList<Integer> get_kotaprice() {
        dbHandlerstones = new DBHandler(this.context, null, null, 2);
        return dbHandlerstones.getPrices();
    }

    public int get_kotaExtraPrice(String extra) {
        dbHandlerstones = new DBHandler(this.context, null, null, 2);
        return dbHandlerstones.getExtraCost(extra);
    }

    public long calculatePrice(int length, int width, int qty, boolean chkDp, boolean chkEp, boolean chkHr, boolean chkFr) {
        dbHandlerstones = new DBHandler(this.context, null, null, 2);
        long price = 0;
        double sqFt = 0;

        ArrayList<Integer> tempPrice = new ArrayList<Integer>();
        tempPrice = dbHandlerstones.getMeasurements();
        Collections.sort(tempPrice);

        for (int i = 0; i < tempPrice.size(); i++) {
            if (length <= tempPrice.get(i)) {
                price = dbHandlerstones.getPrice(tempPrice.get(i));
                break;
            }
        }
        if ((width < 18 && width > 14 && price != 0) || width >= 29) {
            price += dbHandlerstones.getExtraCost(String.valueOf(width));
        }

        if (chkDp)
            price += dbHandlerstones.getExtraCost("DP");
        if (chkEp)
            price += dbHandlerstones.getExtraCost("EdgePolish") * (length / 12.00 + width / 12.00);
        if (chkHr)
            price += dbHandlerstones.getExtraCost("HalfRound") * (length / 12.00 + width / 12.00);
        if (chkFr)
            price += dbHandlerstones.getExtraCost("FullRound") * (length / 12.00 + width / 12.00);

        if (price != 0) {
            price = Math.round(price * calculateSqFt(length, width) * qty);
        }
        return price;
    }

    public double calculateSqFt(int length, int width) {
        double sqFt = 0;
        double lengthFt = 0;
        double widthFt = 0;

        float lDeci = (float) (length / 12.00);
        float l = lDeci - length / 12;

        float wDeci = (float) (width / 12.00);
        float w = wDeci - width / 12;

        if (l < 0.5) {
            lengthFt = Math.floor(length / 12) + 0.5;
        } else if (l >= 0.5) {
            lengthFt = Math.round(length / 12);
        }

        if (w < 18 && w > 14) {
            widthFt = 1.25;
        } else if (w < 0.5) {
            widthFt = Math.floor(width / 12.00) + 0.5;
        } else if (w >= 0.5) {
            widthFt = Math.round(width / 12.00);
        }
        sqFt = lengthFt * widthFt;
        return sqFt;
    }

}
