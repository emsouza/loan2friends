package br.com.eduardo.loan.ui.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.ui.button.BootstrapButton;

/**
 * @author Eduardo Matos de Souza <br>
 *         29/06/2013 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EViewGroup(R.layout.gr_textlookup)
public class EditTextLookup extends LinearLayout {

    @ViewById
    TextView grlookupName;

    @ViewById
    BootstrapButton grlookupButton;

    public EditTextLookup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setHint(int resid) {
        grlookupName.setHint(resid);
    }

    public void setOnClickListener(OnClickListener listener) {
        grlookupButton.setOnClickListener(listener);
    }

    public void setText(String text) {
        grlookupName.setText(text);
    }

    public String getText() {
        return grlookupName.getText().toString();
    }

    public void disableTextComponent() {
        grlookupName.setInputType(InputType.TYPE_NULL);
        grlookupName.setCursorVisible(false);
        grlookupName.setFocusable(false);
    }
}
