package br.com.eduardo.loan.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.ui.button.BootstrapButton;
import br.com.eduardo.loan.util.type.Status;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FilterDialog extends Dialog {

    protected CheckBox lentBox, returnBox, archiveBox;

    protected BootstrapButton updateButton, cancelButton;

    protected boolean operationComplete;

    public FilterDialog(Context context, List<String> status) {
        super(context);
        setContentView(R.layout.dg_filter);
        setTitle(R.string.title_filter);
        getWindow().setLayout(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        lentBox = (CheckBox) this.findViewById(R.id.flg_lent);
        returnBox = (CheckBox) this.findViewById(R.id.flg_return);
        archiveBox = (CheckBox) this.findViewById(R.id.flg_archive);
        updateButton = (BootstrapButton) this.findViewById(R.id.buttonUpdate);
        cancelButton = (BootstrapButton) this.findViewById(R.id.buttonCancel);

        for (String s : status) {
            int idStatus = Integer.valueOf(s);
            if (idStatus == Status.LENDED) {
                lentBox.setChecked(true);
            } else if (idStatus == Status.RETURNED) {
                returnBox.setChecked(true);
            } else if (idStatus == Status.ARCHIVED) {
                archiveBox.setChecked(true);
            }
        }

        updateButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationComplete = true;
                dismiss();
            }
        });

        cancelButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public List<String> updateStatus() {
        List<String> status = new ArrayList<String>();

        if (lentBox.isChecked()) {
            status.add(String.valueOf(Status.LENDED));
        }
        if (returnBox.isChecked()) {
            status.add(String.valueOf(Status.RETURNED));
        }
        if (archiveBox.isChecked()) {
            status.add(String.valueOf(Status.ARCHIVED));
        }
        return status;
    }

    public boolean isOperationComplete() {
        return operationComplete;
    }
}