package br.com.eduardo.loan.dialog;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.widget.LinearLayout;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.ui.dialog.CustomDialog;
import br.com.eduardo.loan.ui.dialog.Inflateable;
import br.com.eduardo.loan.ui.view.StatusSelectView;
import br.com.eduardo.loan.util.type.Status;
import br.com.eduardo.loan.util.type.StatusParam;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EViewGroup(R.layout.dg_filter)
public class FilterView extends LinearLayout implements Inflateable {

    @ViewById(R.id.lent)
    StatusSelectView lent;

    @ViewById(R.id.returned)
    StatusSelectView returned;

    @ViewById(R.id.archived)
    StatusSelectView archived;

    CustomDialog dialog;

    public FilterView(Context context) {
        super(context);
    }

    @AfterViews
    public void afterView() {
        lent.setStatus(R.string.status_lent, R.color.red);
        returned.setStatus(R.string.status_returned, R.color.green);
        archived.setStatus(R.string.status_achived, R.color.gray_dark);

        for (int id : StatusParam.INSTANCE.getStatus()) {
            if (id == Status.LENDED) {
                lent.setChecked(true);
            } else if (id == Status.RETURNED) {
                returned.setChecked(true);
            } else if (id == Status.ARCHIVED) {
                archived.setChecked(true);
            }
        }
    }

    @Click(R.id.buttonUpdate)
    void finish() {
        if (lent.isChecked()) {
            StatusParam.INSTANCE.add(Status.LENDED);
        } else {
            StatusParam.INSTANCE.remove(Status.LENDED);
        }
        if (returned.isChecked()) {
            StatusParam.INSTANCE.add(Status.RETURNED);
        } else {
            StatusParam.INSTANCE.remove(Status.RETURNED);
        }
        if (archived.isChecked()) {
            StatusParam.INSTANCE.add(Status.ARCHIVED);
        } else {
            StatusParam.INSTANCE.remove(Status.ARCHIVED);
        }
        dialog.dismiss();
    }

    @Override
    public void finishInflate() {
        onFinishInflate();
    }

    @Override
    public void setDialog(CustomDialog dialog) {
        this.dialog = dialog;
    }
}