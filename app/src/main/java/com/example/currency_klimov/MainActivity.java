package com.example.currency_klimov;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private float[] RATES = {92.5f, 99.8f, 12.75f, 0.21f, 117.3f, 1f};
    private String[] CODES = {"USD", "EUR", "CNY", "KZT", "GBP", "RUB"};

    private Spinner spinner;
    private TextView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerCurrency);
        icon = findViewById(R.id.textLinearLayout);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().toString().equals("USD — Доллар")){
                    icon.setText("$");
                } else if (spinner.getSelectedItem().toString().equals("EUR — Евро")) {
                    icon.setText("€");
                } else if (spinner.getSelectedItem().toString().equals("CNY — Юань")) {
                    icon.setText("¥");
                } else if (spinner.getSelectedItem().toString().equals("KZT — Тенге")) {
                    icon.setText("₸");
                } else if (spinner.getSelectedItem().toString().equals("GBP — Фунт")) {
                    icon.setText("£");
                } else if (spinner.getSelectedItem().toString().equals("RUB — Рубли")) {
                    icon.setText("₽");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //  Метод для вывода ошибок в всплывающее диалоговое окно
    public void AlterDialogs(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alter = builder.create();
        alter.show();
    }

    public void Consider(View view){
        //  Получение id елементов в activity_main
        EditText count = findViewById(R.id.etSum);
        TextView tv = findViewById(R.id.courseText);
        Spinner spinner = findViewById(R.id.spinnerCurrency);

        if (count.getText().toString().isEmpty()) {
            AlterDialogs("Уведомление", "Введите сумму");
            return;
        }

        float sum = Float.parseFloat(count.getText().toString());
        int selectedRate = spinner.getSelectedItemPosition();

        // Берём фиксированный курс по позиции Spinner
        float rate = RATES[selectedRate];
        String code = CODES[selectedRate];

        // Конвертация: рубли / курс = валюта
        float converted = sum / rate;

        // Вывод результата
        tv.setText(String.format("%.2f %s", converted, code));
    }

    public void URL(View view){
        // Переход по ссылке при нажатии по тексту
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.sberbank.ru/ru/quotes/currencies"));
        startActivity(intent);
    }
}