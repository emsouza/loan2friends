package br.com.eduardo.loan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * @author Eduardo Matos de Souza<br>
 *         EMS - 17/09/2011 <br>
 *         <a href="mailto:eduardo.souza@emsouza.com.br">eduardo.souza@emsouza.
 *         com.br</a>
 */
public class FileUtil {

    /**
     * Salva o bitmap em um arquivo
     * 
     * @param path
     * @param data
     */
    public static void saveFile(String path, Bitmap data) {
        File f = new File(path);
        f.getParentFile().mkdirs();
        try {
            f.createNewFile();

            FileOutputStream pfos = new FileOutputStream(f);
            data.compress(CompressFormat.JPEG, 75, pfos);

            pfos.flush();
            pfos.close();
        } catch (IOException e) {
            Log.e(FileUtil.class.getName(), "Erro ao salvar aquivo.", e);
        }
    }

    /**
     * Le o Bitmap salvo
     * 
     * @param path
     */
    public static Bitmap readFile(String path) {
        File f = new File(path);
        try {
            FileInputStream pfis = new FileInputStream(f);
            Bitmap image = BitmapFactory.decodeStream(pfis);
            pfis.close();
            return image;
        } catch (IOException e) {
            Log.e(FileUtil.class.getName(), "Erro ao salvar aquivo.", e);
            return null;
        }
    }

    /**
     * Le o Bitmap salvo para a Grid
     * 
     * @param path
     */
    public static Bitmap readFileForGrid(String path) {
        File f = new File(path);
        try {
            FileInputStream pfis = new FileInputStream(f);
            Bitmap image = BitmapFactory.decodeStream(pfis);
            image = Bitmap.createScaledBitmap(image, 48, 48, false);
            pfis.close();
            return image;
        } catch (IOException e) {
            Log.e(FileUtil.class.getName(), "Erro ao salvar aquivo.", e);
            return null;
        }
    }

    /**
     * Deleta o Arquivo
     * 
     * @param path
     */
    public static void removeFile(String path) {
        File f = new File(path);
        f.delete();
    }

    public static void copyFile(File src, File dst) throws IOException {
        FileInputStream is = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dst);

        FileChannel inChannel = is.getChannel();
        FileChannel outChannel = out.getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
            if (is != null) {
                is.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
