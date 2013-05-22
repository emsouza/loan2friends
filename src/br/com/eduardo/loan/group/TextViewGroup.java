package br.com.eduardo.loan.group;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import br.com.eduardo.loan.R;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 22/05/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
@EViewGroup(R.layout.gr_textview)
public class TextViewGroup extends LinearLayout {

	@ViewById
	protected TextView grName, grText;

	public TextViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setValues(String name, String text) {
		grName.setText(name);
		grText.setText(text);
	}
}
