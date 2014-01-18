package br.com.eduardo.loan.util.validator;

import android.content.Context;
import android.widget.Toast;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.model.entity.ItemDTO;
import br.com.eduardo.loan.util.type.ItemFormatter;

/**
 * @author Eduardo Matos de Souza<br>
 *         04/06/2011 <br>
 *         <a
 *         href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com
 *         </a>
 */
public class ItemValidator {

    public static boolean validaItem(Context context, ItemDTO item) {
        if (item.getTitle() != null && item.getTitle().length() > 0) {
            return true;
        } else {
            if (item.getType() == ItemFormatter.TYPE_MONEY) {
                Toast toast = Toast.makeText(context,
                        String.format(context.getString(R.string.msg_item_name_error), context.getString(R.string.label_amount)).toLowerCase(),
                        Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(context,
                        String.format(context.getString(R.string.msg_item_name_error), context.getString(R.string.label_title)).toLowerCase(),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
            return false;
        }
    }
}
