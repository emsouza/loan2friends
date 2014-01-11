package br.com.eduardo.loan.dialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebView;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 21/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
public class ChangeLog {

    private final Context context;

    private String oldVersion, thisVersion;

    private SharedPreferences sp;

    // this is the key for storing the version name in SharedPreferences
    private static final String VERSION_KEY = "PREFS_VERSION_KEY";

    /**
     * Constructor Retrieves the version names and stores the new version name in SharedPreferences
     */
    public ChangeLog(Context context) {
        this.context = context;

        this.sp = PreferenceManager.getDefaultSharedPreferences(context);

        this.oldVersion = sp.getString(VERSION_KEY, "");

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            this.thisVersion = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            Log.e(ChangeLog.class.getName(), "Erro ao buscar número de versão", e);
        }

        SharedPreferences.Editor editor = sp.edit();
        editor.putString(VERSION_KEY, thisVersion);
        editor.commit();
    }

    public String getSavedVersion() {
        return this.oldVersion;
    }

    public String getManifestVersion() {
        return this.thisVersion;
    }

    /**
     * @return true if this version of your app is started the first time
     */
    public boolean firstRun() {
        return !oldVersion.equals(thisVersion);
    }

    /**
     * @return an AlertDialog displaying the changes since the previous installed version of your app (what's new).
     */
    public AlertDialog getLogDialog() {
        return this.getDialog(false);
    }

    /**
     * @return an AlertDialog with a full change log displayed
     */
    public AlertDialog getFullLogDialog() {
        return this.getDialog(true);
    }

    private AlertDialog getDialog(boolean full) {

        WebView wv = new WebView(this.context);
        wv.setBackgroundColor(0);
        wv.loadData(this.getLog(full), "text/html", "ISO-8859-1");

        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        builder.setTitle(R.string.title_changelog).setView(wv).setCancelable(false)
                .setPositiveButton(context.getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }

    /**
     * @return HTML displaying the changes since the previous installed version of your app (what's new)
     */
    public String getLog() {
        return this.getLog(false);
    }

    /**
     * @return HTML which displays full change log
     */
    public String getFullLog() {
        return this.getLog(true);
    }

    /** modes for HTML-Lists (bullet, numbered) */
    private enum Listmode {
        NONE,
        ORDERED,
        UNORDERED,
    };

    private Listmode listMode = Listmode.NONE;
    private StringBuffer sb = null;
    private static final String EOVS = "END_OF_CHANGE_LOG";

    private String getLog(boolean full) {
        // read changelog.txt file
        sb = new StringBuffer();
        try {
            InputStream ins = context.getResources().openRawResource(R.raw.changelog);
            BufferedReader br = new BufferedReader(new InputStreamReader(ins));

            String line = null;
            boolean advanceToEOVS = false; // true = ignore further version sections
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("$")) {
                    // begin of a version section
                    this.closeList();
                    String version = line.substring(1).trim();
                    // stop output?
                    if (!full) {
                        if (this.oldVersion.equals(version)) {
                            advanceToEOVS = true;
                        } else if (version.equals(EOVS)) {
                            advanceToEOVS = false;
                        }
                    }
                } else if (!advanceToEOVS) {
                    if (line.startsWith("%")) {
                        // line contains version title
                        this.closeList();
                        sb.append("<div class='title'>" + line.substring(1).trim() + "</div>\n");
                    } else if (line.startsWith("_")) {
                        // line contains version title
                        this.closeList();
                        sb.append("<div class='subtitle'>" + line.substring(1).trim() + "</div>\n");
                    } else if (line.startsWith("!")) {
                        // line contains free text
                        this.closeList();
                        sb.append("<div class='freetext'>" + line.substring(1).trim() + "</div>\n");
                    } else if (line.startsWith("#")) {
                        // line contains numbered list item
                        this.openList(Listmode.ORDERED);
                        sb.append("<li>" + line.substring(1).trim() + "</li>\n");
                    } else if (line.startsWith("*")) {
                        // line contains bullet list item
                        this.openList(Listmode.UNORDERED);
                        sb.append("<li>" + line.substring(1).trim() + "</li>\n");
                    } else {
                        // no special character: just use line as is
                        this.closeList();
                        sb.append(line + "\n");
                    }
                }
            }
            this.closeList();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    private void openList(Listmode listMode) {
        if (this.listMode != listMode) {
            closeList();
            if (listMode == Listmode.ORDERED) {
                sb.append("<div class='list'><ol>\n");
            } else if (listMode == Listmode.UNORDERED) {
                sb.append("<div class='list'><ul>\n");
            }
            this.listMode = listMode;
        }
    }

    private void closeList() {
        if (this.listMode == Listmode.ORDERED) {
            sb.append("</ol></div>\n");
        } else if (this.listMode == Listmode.UNORDERED) {
            sb.append("</ul></div>\n");
        }
        this.listMode = Listmode.NONE;
    }
}