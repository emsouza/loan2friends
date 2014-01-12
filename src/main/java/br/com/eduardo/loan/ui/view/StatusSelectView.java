package br.com.eduardo.loan.ui.view;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
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
    RadioButton radio;
    
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
        radio.setOnClickListener(onClick);
    }
    
    void processCheck() {
        if (radio.isChecked()) {
            radio.setChecked(false);
        } else {
            radio.setChecked(true);
        }
    }

    public void setStatus(int testId, int colorId){
        color.setBackgroundResource(colorId);
        name.setText(testId);
    }

    public void setChecked(boolean checked) {
        radio.setChecked(checked);
    }

    public boolean isChecked() {
        return radio.isChecked();
    }
}