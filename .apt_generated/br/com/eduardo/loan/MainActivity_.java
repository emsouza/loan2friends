//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations.
//


package br.com.eduardo.loan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import br.com.eduardo.loan.R.layout;
import br.com.eduardo.loan.model.LoanDAO_;
import br.com.emsouza.widget.bar.ActionBar;
import com.googlecode.androidannotations.api.SdkVersionHelper;

public final class MainActivity_
    extends MainActivity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(layout.ac_main);
    }

    private void init_(Bundle savedInstanceState) {
        loanDAO = LoanDAO_.getInstance_(this);
    }

    private void afterSetContentView_() {
        actionBar = ((ActionBar) findViewById(br.com.eduardo.loan.R.id.actionBar));
        listView = ((ListView) findViewById(br.com.eduardo.loan.R.id.ac_main_list));
        {
            AdapterView<?> view = ((AdapterView<?> ) findViewById(br.com.eduardo.loan.R.id.ac_main_list));
            if (view!= null) {
                view.setOnItemLongClickListener(new OnItemLongClickListener() {


                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        listItemLongClicked(position);
                        return true;
                    }

                }
                );
            }
        }
        ((LoanDAO_) loanDAO).afterSetContentView_();
        afterView();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        afterSetContentView_();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        afterSetContentView_();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (((SdkVersionHelper.getSdkInt()< 5)&&(keyCode == KeyEvent.KEYCODE_BACK))&&(event.getRepeatCount() == 0)) {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    public static MainActivity_.IntentBuilder_ intent(Context context) {
        return new MainActivity_.IntentBuilder_(context);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(br.com.eduardo.loan.R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = super.onOptionsItemSelected(item);
        if (handled) {
            return true;
        }
        int itemId_ = item.getItemId();
        if (itemId_ == br.com.eduardo.loan.R.id.settings) {
            openSettings();
            return true;
        }
        if (itemId_ == br.com.eduardo.loan.R.id.friendsOpen) {
            openFriend();
            return true;
        }
        if (itemId_ == br.com.eduardo.loan.R.id.itemsOpen) {
            openItem();
            return true;
        }
        if (itemId_ == br.com.eduardo.loan.R.id.filter) {
            openFilter();
            return true;
        }
        if (itemId_ == br.com.eduardo.loan.R.id.loanAdd) {
            openAddLoan();
            return true;
        }
        return false;
    }

    public static class IntentBuilder_ {

        private Context context_;
        private final Intent intent_;

        public IntentBuilder_(Context context) {
            context_ = context;
            intent_ = new Intent(context, MainActivity_.class);
        }

        public Intent get() {
            return intent_;
        }

        public MainActivity_.IntentBuilder_ flags(int flags) {
            intent_.setFlags(flags);
            return this;
        }

        public void start() {
            context_.startActivity(intent_);
        }

        public void startForResult(int requestCode) {
            if (context_ instanceof Activity) {
                ((Activity) context_).startActivityForResult(intent_, requestCode);
            } else {
                context_.startActivity(intent_);
            }
        }

    }

}
