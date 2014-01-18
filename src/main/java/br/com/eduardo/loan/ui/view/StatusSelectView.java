package br.com.eduardo.loan.ui.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza <br>
 *         19/05/2013 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EViewGroup(R.layout.merge_status_select)
public class StatusSelectView extends RelativeLayout {

    @ViewById(R.id.statusColor)
    TextView color;

    @ViewById(R.id.statusName)
    TextView name;

    @ViewById(R.id.statusRadio)
    CheckBox checkBox;

    public StatusSelectView(Context context, AttributeSet atts) {
        super(context, atts);
    }

    @AfterViews
    void afterView() {
        OnClickListener onClick = new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                processCheck();
            }
        };
        color.setOnClickListener(onClick);
        name.setOnClickListener(onClick);
    }

    void processCheck() {
        checkBox.setChecked(!checkBox.isChecked());
    }

    public void setStatus(int testId, int colorId) {
        color.setBackgroundResource(colorId);
        name.setText(testId);
    }

    public void setChecked(boolean checked) {
        checkBox.setChecked(checked);
    }

    public boolean isChecked() {
        return checkBox.isChecked();
    }
}