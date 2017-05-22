package com.example.javier.photosorter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Bryan on 5/22/2017.
 */

public class PixelHash {

    public void principal(){

    }

    public String comparar(Bitmap imagen){
        ArrayList<Integer> caca = new ArrayList();
        for(int i = 0; i<255; i++){
            for(int j = 0; j<255; j++){
                caca.add(imagen.getPixel(i,j));
            }
        }
        String x = "";
        for (Integer integer : caca) {
            x+=integer;
        }
        System.out.println(x);
        return x;
    }
    //TEMPORAL PARA DEBBUG



    public Bitmap toGrayscale(Bitmap bmpOriginal){
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public Bitmap resizeImage(Bitmap img){
        return Bitmap.createScaledBitmap(img, 256, 256, true);
    }

}
