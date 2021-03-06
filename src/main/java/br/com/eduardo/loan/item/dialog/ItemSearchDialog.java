package br.com.eduardo.loan.item.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.eduardo.loan.R;
import br.com.eduardo.loan.item.adapter.ItemDialogAdapter;
import br.com.eduardo.loan.model.ItemDAO;
import br.com.eduardo.loan.model.entity.ItemDTO;

/**
 * @author Eduardo Matos de Souza<br>
 *         03/06/2011 <br>
 *         <a href="mailto:eduardomatosouza@gmail.com">eduardomatosouza@gmail.com</a>
 */
public class ItemSearchDialog extends Dialog implements OnItemClickListener {

    protected ItemDTO item;

    public ItemSearchDialog(Context context) {
        super(context);
        setTitle(R.string.title_dialog_item);
    }

    public void populate() {
        ItemDAO db = new ItemDAO(getContext());
        ItemDialogAdapter adapter = new ItemDialogAdapter(getContext(), R.layout.dg_item_list, db.findAllAvailable());
        db.close();

        ListView listView = new ListView(getContext());
        listView.setAdapter(adapter);
        listView.setDrawSelectorOnTop(false);
        listView.setOnItemClickListener(this);
        setContentView(listView);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        item = ((ItemDTO) arg0.getItemAtPosition(arg2));
        dismiss();
    }

    public ItemDTO getItemSelected() {
        return item;
    }
}