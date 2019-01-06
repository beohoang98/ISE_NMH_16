package com.example.thang.smartmoney.xulysukien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.thang.smartmoney.R;

public class DeleteDialog extends AlertDialog
{
    Button confirmBtn, cancleBtn;
    boolean isConfirm = false;
    OnConfirm onConfirmListener;

    public DeleteDialog(Context context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_remove);
        confirmBtn = findViewById(R.id.btnXoadgl);
        cancleBtn = findViewById(R.id.btnHUYdgl);

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onConfirmListener!=null) {
                    onConfirmListener.onConfirm();
                }
                dismiss();
            }
        });
    }

    public void setOnConfirmListener(OnConfirm onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
    }

    public interface OnConfirm {
        void onConfirm();
    }
}
