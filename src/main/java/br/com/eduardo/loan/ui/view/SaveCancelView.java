package br.com.eduardo.loan.ui.view;

import org.androidannotations.annotations.EViewGroup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza <br>
 *         19/05/2013 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
@EViewGroup(R.layout.merge_save_cancel)
public class SaveCancelView extends LinearLayout {

    public SaveCancelView(Context context, AttributeSet atts) {
        super(context, atts);
    }
}