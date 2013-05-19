package br.com.eduardo.loan.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.eduardo.loan.R;

import com.googlecode.androidannotations.annotations.EFragment;

/**
 * @author Eduardo Matos de Souza <br>
 *         19/05/2013 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
@EFragment
public class TitleFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fg_title, container, false);
	}
}