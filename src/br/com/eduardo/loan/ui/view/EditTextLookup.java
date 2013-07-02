package br.com.eduardo.loan.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.eduardo.loan.R;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza <br>
 *         29/06/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EViewGroup(R.layout.gr_textlookup)
public class EditTextLookup extends LinearLayout {

	@ViewById
	protected TextView grlookupName;

	@ViewById
	protected ImageButton grlookupButton;

	public EditTextLookup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setIcon(int icon) {
		grlookupButton.setImageResource(icon);
	}

	public void addAction(OnClickListener listener) {
		grlookupButton.setOnClickListener(listener);
	}

	public void setText(String text) {
		grlookupName.setText(text);
	}
}
