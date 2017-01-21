package br.com.eprovafinaldanielebruno;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class Converter extends Activity {
	Button salvar;
	EditText valor;
	RadioGroup op;
	TextView passarreal, passardolar, passareuro, passaryen;
	public static String moeda;
	public static double valordaconversao, valordoreal, valordodolar, valordoeuro, valordoyen, valordoyen2,
			pessoadigitou;
	public static int verificar = 0;
	public Cursor c;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_converter);
		salvar = (Button) findViewById(R.id.btnconverter);
		op = (RadioGroup) findViewById(R.id.grupo1);
		passarreal = (TextView) findViewById(R.id.real);
		passardolar = (TextView) findViewById(R.id.dolar);
		passareuro = (TextView) findViewById(R.id.euro);
		passaryen = (TextView) findViewById(R.id.yen);
		passarreal.setVisibility(View.INVISIBLE);
		passardolar.setVisibility(View.INVISIBLE);
		passareuro.setVisibility(View.INVISIBLE);
		passaryen.setVisibility(View.INVISIBLE);
		try {
			db = openOrCreateDatabase("provafinal", Context.MODE_PRIVATE, null);
			db.execSQL(
					"CREATE TABLE IF NOT EXISTS valores (id INTEGER PRIMARY KEY AUTOINCREMENT,real varchar(10),valorreal decimal(10,2),dolar varchar(10),valordolar decimal(10,2),euro varchar(10),valoreuro decimal(10,2),yen varchar(10),valoryen decimal(10,2))");
		} catch (SQLException e) {
			Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
		}
		salvar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				valor = (EditText) findViewById(R.id.valordousuario);
				if (valor.getText().toString().equals("")) {
					AlertDialog.Builder alerta = new AlertDialog.Builder(Converter.this);
					alerta.setTitle("Erro");
					alerta.setMessage("Preencha todos os campos");
					alerta.setNeutralButton("Ok", null);
					alerta.show();
				} else {
					switch (op.getCheckedRadioButtonId()) {
					case R.id.realbotao:
						pessoadigitou = 0;
						valordoreal = 0;
						valordodolar = 0;
						valordoeuro = 0;
						valordoyen = 0;
						verificar += 1;
						moeda = "real";
						break;
					case R.id.dolarbotao:
						pessoadigitou = 0;
						valordoreal = 0;
						valordodolar = 0;
						valordoeuro = 0;
						valordoyen = 0;
						verificar += 1;
						moeda = "dolar";
						break;
					case R.id.eurobotao:
						pessoadigitou = 0;
						valordoreal = 0;
						valordodolar = 0;
						valordoeuro = 0;
						valordoyen = 0;
						verificar += 1;
						moeda = "euro";
						break;
					case R.id.yenbotaoa:
						pessoadigitou = 0;
						valordoreal = 0;
						valordodolar = 0;
						valordoeuro = 0;
						valordoyen = 0;
						verificar += 1;
						moeda = "yen";
						break;

					}
					if (verificar == 1) {
						verificar = 0;
						if (moeda == "real") {
							c = db.rawQuery("SELECT * FROM valores WHERE real = '" + moeda + "'", null);
							if (c.getCount() > 0) {
								pessoadigitou = Double.parseDouble(valor.getText().toString());
								if (c.moveToFirst()) {
									valordodolar = c.getDouble(c.getColumnIndex("valordolar"));
									valordoeuro = c.getDouble(c.getColumnIndex("valoreuro"));
									valordoyen = c.getDouble(c.getColumnIndex("valoryen"));
								}
								double valordodolar1 = pessoadigitou / valordodolar;
								double valordoeuro1 = pessoadigitou / valordoeuro;
								double valordoyen1 = pessoadigitou * valordoyen;
								passarreal.setVisibility(View.VISIBLE);
								passardolar.setVisibility(View.VISIBLE);
								passareuro.setVisibility(View.VISIBLE);
								passaryen.setVisibility(View.VISIBLE);
								passarreal.setText("Valor digitado: " + pessoadigitou);
								passardolar.setText("Valor em dólar: " + valordodolar1);
								passareuro.setText("Valor em euro: " + valordoeuro1);
								passaryen.setText("Valor em iene: " + valordoyen1);
								pessoadigitou = 0;
								valordoreal = 0;
								valordodolar = 0;
								valordoeuro = 0;
								valordoyen = 0;
							}
						} else if (moeda == "dolar") {
							c = db.rawQuery("SELECT * FROM valores WHERE dolar = '" + moeda + "'", null);
							if (c.getCount() > 0) {
								pessoadigitou = Double.parseDouble(valor.getText().toString());

								if (c.moveToFirst()) {
									valordoreal = c.getDouble(c.getColumnIndex("valordolar"));
									valordoeuro = c.getDouble(c.getColumnIndex("valoreuro"));
									valordoyen = c.getDouble(c.getColumnIndex("valordolar"));
									valordoyen2 = c.getDouble(c.getColumnIndex("valoryen"));
								}
								double valordoreal1 = pessoadigitou * valordoreal;
								double valordoeuro1 = pessoadigitou / valordoeuro * valordoreal;
								double valordoyen1 = pessoadigitou * valordoyen * valordoyen2;
								passarreal.setVisibility(View.VISIBLE);
								passardolar.setVisibility(View.VISIBLE);
								passareuro.setVisibility(View.VISIBLE);
								passaryen.setVisibility(View.VISIBLE);
								passardolar.setText("Valor digitado: " + pessoadigitou);
								passarreal.setText("Valor em real: " + valordoreal1);
								passareuro.setText("Valor em euro: " + valordoeuro1);
								passaryen.setText("Valor em iene: " + valordoyen1);
								pessoadigitou = 0;
								valordoreal = 0;
								valordodolar = 0;
								valordoeuro = 0;
								valordoyen = 0;
								valordoyen2 = 0;
							}
						} else if (moeda == "euro") {
							c = db.rawQuery("SELECT * FROM valores WHERE euro = '" + moeda + "'", null);
							if (c.getCount() > 0) {
								pessoadigitou = Double.parseDouble(valor.getText().toString());

								if (c.moveToFirst()) {
									valordoreal = c.getDouble(c.getColumnIndex("valoreuro"));
									valordodolar = c.getDouble(c.getColumnIndex("valordolar"));
									valordoyen = c.getDouble(c.getColumnIndex("valoreuro"));
									valordoyen2 = c.getDouble(c.getColumnIndex("valoryen"));
								}
								double valordoreal1 = pessoadigitou * valordoreal;
								double valordoeuro1 = pessoadigitou / valordodolar * valordoyen;
								double valordoyen1 = pessoadigitou * valordoyen * valordoyen2;
								passarreal.setVisibility(View.VISIBLE);
								passardolar.setVisibility(View.VISIBLE);
								passareuro.setVisibility(View.VISIBLE);
								passaryen.setVisibility(View.VISIBLE);
								passareuro.setText("Valor digitado: " + pessoadigitou);
								passarreal.setText("Valor em real: " + valordoreal1);
								passardolar.setText("Valor em dólar: " + valordoeuro1);
								passaryen.setText("Valor em iene: " + valordoyen1);
								pessoadigitou = 0;
								valordoreal = 0;
								valordodolar = 0;
								valordoeuro = 0;
								valordoyen = 0;
								valordoyen2 = 0;
							}
						} else if (moeda == "yen") {
							c = db.rawQuery("SELECT * FROM valores WHERE yen = '" + moeda + "'", null);
							if (c.getCount() > 0) {
								pessoadigitou = Double.parseDouble(valor.getText().toString());

								if (c.moveToFirst()) {
									valordoyen2 = c.getDouble(c.getColumnIndex("valoryen"));
									valordoreal = c.getDouble(c.getColumnIndex("valorreal"));
									valordodolar = c.getDouble(c.getColumnIndex("valordolar"));
									valordoeuro = c.getDouble(c.getColumnIndex("valoreuro"));
								}
								double valordoreal1 = pessoadigitou / valordoyen2 / valordoreal;
								double valordodolar1 = pessoadigitou / valordoyen2 / valordodolar;
								double valordoeuro1 = pessoadigitou / valordoyen2 / valordoeuro;
								passarreal.setVisibility(View.VISIBLE);
								passardolar.setVisibility(View.VISIBLE);
								passareuro.setVisibility(View.VISIBLE);
								passaryen.setVisibility(View.VISIBLE);
								passaryen.setText("Valor digitado: " + pessoadigitou);
								passarreal.setText("Valor em real: " + valordoreal1);
								passardolar.setText("Valor em dólar: " + valordodolar1);
								passareuro.setText("Valor em euro: " + valordoeuro1);
								pessoadigitou = 0;
								valordoreal = 0;
								valordodolar = 0;
								valordoeuro = 0;
								valordoyen = 0;
								valordoyen2 = 0;
							}
						}
					} else {
						AlertDialog.Builder alerta = new AlertDialog.Builder(Converter.this);
						alerta.setTitle("Erro");
						alerta.setMessage("Selecione uma opção");
						alerta.setNeutralButton("Ok", null);
						alerta.show();
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.converter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(Converter.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}
}
