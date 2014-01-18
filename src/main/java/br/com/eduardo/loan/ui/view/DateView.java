package br.com.eduardo.loan.ui.view;

import java.util.Calendar;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import br.com.eduardo.loan.util.DateFormatUtil;

/**
 * @author Eduardo Matos de Souza - SIC/NDS <br>
 *         Dígitro - 26/06/2013 <br>
 *         <a href="mailto:eduardo.souza@digitro.com.br">eduardo.souza@digitro.com.br</a>
 */
@EView
public class DateView extends EditText {

    @Bean
    protected DateFormatUtil dateTimeFormat;

    protected Calendar cal;

    public DateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDate(Calendar cal) {
        this.cal = cal;
        setText(dateTimeFormat.formatDateToScreen(cal.getTime()));
    }

    public Calendar getDate() {
        return cal;
    }
}
