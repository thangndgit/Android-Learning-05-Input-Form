package com.example.inputform;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Wrapper;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextInputEditText name, mssv, cccd, phone, birth, email, homeTown, address;
    TextInputLayout nameWrapper, mssvWrapper, cccdWrapper, phoneWrapper, emailWrapper, homeTownWrapper, addressWrapper;
    Button buttonPick, buttonSave;
    CheckBox checkBoxRule;
    int selectedYear, selectedMonth, selectedDayOfMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        mssv = findViewById(R.id.mssv);
        cccd = findViewById(R.id.cccd);
        phone = findViewById(R.id.phone);
        birth = findViewById(R.id.birth);
        email = findViewById(R.id.email);
        homeTown = findViewById(R.id.homeTown);
        address = findViewById(R.id.address);

        nameWrapper = findViewById(R.id.nameWrapper);
        mssvWrapper = findViewById(R.id.mssvWrapper);
        cccdWrapper = findViewById(R.id.cccdWrapper);
        phoneWrapper = findViewById(R.id.phoneWrapper);
        emailWrapper = findViewById(R.id.emailWrapper);
        homeTownWrapper = findViewById(R.id.homeTownWrapper);
        addressWrapper = findViewById(R.id.addressWrapper);

        buttonPick = findViewById(R.id.button_pick);
        buttonSave = findViewById(R.id.button_save);

        checkBoxRule = findViewById(R.id.checkBox_rule);

        Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0) {
                    nameWrapper.setError("Tr?????ng n??y l?? b???t bu???c");
                } else {
                    nameWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mssv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length() == 0) {
                    mssvWrapper.setError("Tr?????ng n??y l?? b???t bu???c");
                } else if (charSequence.toString().length() != 8){
                    mssvWrapper.setError("MSSV ph???i bao g???m 8 ch??? s???");
                } else {
                    mssvWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        cccd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() == 0) {
                    cccdWrapper.setError("Tr?????ng n??y l?? b???t bu???c");
                } else if (charSequence.toString().length() != 9 && charSequence.toString().length() != 12) {
                    cccdWrapper.setError("S??? CMND/CCCD ph???i bao g???m 9/12 ch??? s???");
                } else {
                    cccdWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().length() == 0) {
                    phoneWrapper.setError("Tr?????ng n??y l?? b???t bu???c");
                } else if (charSequence.toString().length() != 10) {
                    phoneWrapper.setError("S??T ph???i bao g???m 10 ch??? s???");
                } else {
                    phoneWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().trim().length() == 0) {
                    emailWrapper.setError("Tr?????ng n??y l?? b???t bu???c");
                } else if (!validateEmail(charSequence.toString().trim())) {
                    emailWrapper.setError("Email kh??ng h???p l???");
                } else {
                    emailWrapper.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        buttonPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });

        checkBoxRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxRule.isChecked()){
                    buttonSave.setEnabled(true);
                } else {
                    buttonSave.setEnabled(false);
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().length() == 0
                || mssv.getText().toString().length() != 8
                || (cccd.getText().toString().length() != 9 && cccd.getText().toString().length() != 12)
                || phone.getText().toString().length() != 10
                || email.getText().toString().trim().length() == 0
                || !validateEmail(email.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(), "Nh???p thi???u th??ng tin ho???c th??ng tin ch??a h???p l???. Vui l??ng ki???m tra l???i!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Th??ng tin ???? ???????c l??u l???i th??nh c??ng!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void pickDate() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(dayOfMonth < 10){
                    birth.setText("0" + dayOfMonth + "/");
                } else {
                    birth.setText(dayOfMonth + "/");
                }

                if(monthOfYear < 9){
                    birth.append("0" + (monthOfYear + 1) + "/" + year);
                } else {
                    birth.append((monthOfYear + 1) + "/" + year);
                }
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);

        datePickerDialog.show();

    }

}