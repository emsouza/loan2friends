package br.com.eduardo.loan.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.util.type.Status;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class FilterDialog extends Dialog {

    protected CheckBox lentBox, returnBox, archiveBox;

    protected Button updateButton, cancelButton;

    protected boolean operationComplete;

    public FilterDialog(Context context, List<String> status) {
        super(context);
        setContentView(R.layout.dg_filter);
        setTitle(R.string.title_filter);
        getWindow().setLayout(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

        lentBox = (CheckBox) this.findViewById(R.id.flg_lent);
        returnBox = (CheckBox) this.findViewById(R.id.flg_return);
        archiveBox = (CheckBox) this.findViewById(R.id.flg_archive);
        updateButton = (Button) this.findViewById(R.id.dg_filter_update);
        cancelButton = (Button) this.findViewById(R.id.dg_filter_cancel);

        for (String s : status) {
            int idStatus = Integer.valueOf(s);
            if (idStatus == Status.LENDED.id()) {
                lentBox.setChecked(true);
            } else if (idStatus == Status.RETURNED.id()) {
                returnBox.setChecked(true);
            } else if (idStatus == Status.ARCHIVED.id()) {
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
            status.add(String.valueOf(Status.LENDED.id()));
        }
        if (returnBox.isChecked()) {
            status.add(String.valueOf(Status.RETURNED.id()));
        }
        if (archiveBox.isChecked()) {
            status.add(String.valueOf(Status.ARCHIVED.id()));
        }
        return status;
    }

    public boolean isOperationComplete() {
        return operationComplete;
    }
}