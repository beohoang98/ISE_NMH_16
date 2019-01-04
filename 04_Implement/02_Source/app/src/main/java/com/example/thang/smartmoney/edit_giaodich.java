package com.example.thang.smartmoney;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.thang.smartmoney.adapter.CategorySpinnerAdapter;
import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.model.ClassCategory;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.xulysukien.DateFormat;
import com.example.thang.smartmoney.xulysukien.mDatePickerClick;
import com.example.thang.smartmoney.xulysukien.mPriceInput;

import java.text.ParseException;
import java.util.Date;
import java.util.zip.Inflater;

public class edit_giaodich extends AppCompatActivity
    implements View.OnClickListener
{

    Toolbar toolbar;
    mPriceInput priceInput;
    mDatePickerClick datePicker;
    Spinner spinner;
    TextInputEditText noteText;

    Button confirmButton;

    private ClassGiaoDich data;
    private boolean isSaving = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_giaodich);
        int id = getIntent().getIntExtra("id", -1);

        if (id == -1) {
            Toast.makeText(this, "edit " + id, Toast.LENGTH_SHORT).show();
            finish();
        }

        DBGiaoDich.init(this);
        data = DBGiaoDich.getById(id);
        isSaving = ClassTietKiem.isTietKiem(data);

        try {
            AnhXa();
        } catch (ParseException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void AnhXa() throws ParseException
    {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        priceInput = new mPriceInput(this, R.id.edit_price);
        priceInput.setPrice(data.sotien);
        datePicker = new mDatePickerClick(this, R.id.edit_date);
        datePicker.setDate(data.thoigian);
        noteText = findViewById(R.id.edit_note);
        noteText.setText(data.note);

        spinner = findViewById(R.id.edit_category);
        if (!isSaving) {
            ClassCategory category = ClassCategory.getById(data.category_id);
            CategorySpinnerAdapter adapter = new CategorySpinnerAdapter(this, category.type);
            spinner.setAdapter(adapter);
            spinner.setSelection(adapter.getPositionOf(data.category_id));
        } else {
            spinner.setVisibility(View.GONE);
        }

        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_giaodich, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.confirmButton:
                update();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmButton:
                update();
                break;
        }
    }

    void delete() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_remove);

        Button xoa = dialog.findViewById(R.id.btnXoadgl);
        Button cancel = dialog.findViewById(R.id.btnHUYdgl);

        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = DBGiaoDich.xoa(data.id);
                if (success) {
                    Toast.makeText(edit_giaodich.this,
                            "Xoa thanh cong",
                            Toast.LENGTH_SHORT)
                    .show();

                    dialog.dismiss();
                    done();
                } else {
                    Toast.makeText(edit_giaodich.this,
                            getString(R.string.error_message) + " xoa that bai",
                            Toast.LENGTH_SHORT)
                            .show();
                    dialog.cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    void update() {
        int sotien = priceInput.getPrice();
        Date date = datePicker.getDate();
        int category_id = (isSaving) ? -1 : (int)spinner.getSelectedItemId();

        String note = noteText.getText().toString();
        ClassGiaoDich edited = new ClassGiaoDich(data);
        edited.sotien = sotien;
        edited.thoigian = DateFormat.format(date);
        edited.note = note;
        if (!isSaving) edited.category_id = category_id;

        int effect_rows = DBGiaoDich.update(edited);
        if (effect_rows > 0) {
            Toast.makeText(this, "Update thanh cong", Toast.LENGTH_SHORT).show();
            done();
        } else {
            Toast.makeText(this,
                    getString(R.string.error_message) + "Update thanh cong",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void done() {
        setResult(1);
        finish();
    }
}
