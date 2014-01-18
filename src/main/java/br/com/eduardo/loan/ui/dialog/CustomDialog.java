package br.com.eduardo.loan.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

public class CustomDialog extends Dialog {

    private final View content;

    private final int title;

    public CustomDialog(View content, int title) {
        super(content.getContext());
        setOwnerActivity((Activity) content.getContext());
        this.title = title;
        this.content = content;
        ((Inflateable) content).finishInflate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.content);
        setTitle(this.title);
        ((Inflateable) content).setDialog(this);
    }
}