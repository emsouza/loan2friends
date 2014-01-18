package br.com.eduardo.loan.settings;

import java.io.File;
import java.io.IOException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.dialog.ChangeLogDialog;
import br.com.eduardo.loan.dialog.EmailDialog;
import br.com.eduardo.loan.util.FileUtil;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * @author Eduardo Matos de Souza <br>
 *         09/06/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public class SettingsActivity extends SherlockPreferenceActivity {

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        Preference changelog = findPreference("changelogPref");
        changelog.setOnPreferenceClickListener(new OnPreferenceClickListener() {

            @Override
            public boolean onPreferenceClick(Preference preference) {
                ChangeLogDialog cld = new ChangeLogDialog(SettingsActivity.this);
                cld.show();
                return true;
            }
        });

        Preference feature = findPreference("featurePref");

        feature.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                EmailDialog emailDialog = new EmailDialog(SettingsActivity.this);
                emailDialog.show();
                return true;
            }
        });

        Preference import_data = findPreference("importData");

        import_data.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (isExternalStorageAvail()) {
                    new ImportDatabaseTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.msg_import_export_error), Toast.LENGTH_SHORT)
                            .show();
                }
                SystemClock.sleep(500);
                return true;
            }
        });

        Preference export_data = findPreference("exportData");

        export_data.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (isExternalStorageAvail()) {
                    new ExportDatabaseTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getText(R.string.msg_import_export_error), Toast.LENGTH_SHORT)
                            .show();
                }
                SystemClock.sleep(500);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isExternalStorageAvail() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    protected class ExportDatabaseTask extends AsyncTask<Void, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(SettingsActivity.this);

        // can use UI thread here
        @Override
        protected void onPreExecute() {
            dialog.setMessage(getApplicationContext().getText(R.string.msg_exporting));
            dialog.show();
        }

        // automatically done on worker thread (separate from UI thread)
        @Override
        protected Boolean doInBackground(final Void... args) {

            File dbFile = new File(Environment.getDataDirectory() + "/data/br.com.eduardo.loan/databases/loan_friends.db");

            File exportDir = new File(Environment.getExternalStorageDirectory(), "LOAN_TO_FRIENDS");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }
            File file = new File(exportDir, "loan_to_friends.backup");

            try {
                file.createNewFile();
                FileUtil.copyFile(dbFile, file);
                return true;
            } catch (IOException e) {
                Log.e(SettingsActivity.class.getName(), e.getMessage(), e);
                return false;
            }
        }

        // can use UI thread here
        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (success) {
                Toast.makeText(SettingsActivity.this, getApplicationContext().getText(R.string.msg_export_ok), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, getApplicationContext().getText(R.string.msg_export_fail), Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected class ImportDatabaseTask extends AsyncTask<Void, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(SettingsActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getApplicationContext().getText(R.string.msg_importing));
            dialog.show();
        }

        // could pass the params used here in AsyncTask<String, Void, String> -
        // but not being re-used
        @Override
        protected String doInBackground(final Void... args) {

            File dbBackupFile = new File(Environment.getExternalStorageDirectory() + "/LOAN_TO_FRIENDS/loan_to_friends.backup");
            if (!dbBackupFile.exists()) {
                return getApplicationContext().getText(R.string.msg_import_fail).toString();
            } else if (!dbBackupFile.canRead()) {
                return getApplicationContext().getText(R.string.msg_import_fail).toString();
            }

            File dbFile = new File(Environment.getDataDirectory() + "/data/br.com.eduardo.loan/databases/loan_friends.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }

            try {
                dbFile.createNewFile();
                FileUtil.copyFile(dbBackupFile, dbFile);
                return null;
            } catch (IOException e) {
                Log.e(SettingsActivity.class.getName(), e.getMessage(), e);
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(final String errMsg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if (errMsg == null) {
                Toast.makeText(SettingsActivity.this, getApplicationContext().getText(R.string.msg_import_ok), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SettingsActivity.this, getApplicationContext().getText(R.string.msg_import_fail), Toast.LENGTH_SHORT).show();
            }
        }
    }
}