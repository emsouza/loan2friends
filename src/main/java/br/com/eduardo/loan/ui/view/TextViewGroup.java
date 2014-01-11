package br.com.eduardo.loan.ui.view;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.eduardo.loan.R;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 22/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.
 *         com.br</a>
 */
@EViewGroup(R.layout.gr_textview)
public class TextViewGroup extends LinearLayout {

	@ViewById
	protected TextView grName, grText;

	public TextViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setValues(String name, String text, boolean separator) {
		if (separator) {
			name += ":";
		}
		grName.setText(name);
		grText.setText(text);
	}
}