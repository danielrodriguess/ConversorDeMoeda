package br.com.eprovafinaldanielebruno;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	Button um, dois;
	EditText dolar, euro, yen;
	SQLiteDatabase db;
	View v, V;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		um = (Button) findViewById(R.id.button1);
		dois = (Button) findViewById(R.id.button25);
		dois.setVisibility(View.INVISIBLE);
		try {
			db = openOrCreateDatabase("provafinal", Context.MODE_PRIVATE, null);
			db.execSQL(
					"CREATE TABLE IF NOT EXISTS valores (id INTEGER PRIMARY KEY AUTOINCREMENT,real varchar(10),valorreal decimal(10,2),dolar varchar(10),valordolar decimal(10,2),euro varchar(10),valoreuro decimal(10,2),yen varchar(10),valoryen decimal(10,2))");
		} catch (SQLException e) {
			Toast.makeText(getApplicationContext(), "Erro na database", Toast.LENGTH_SHORT).show();
		}
		Cursor c = db.rawQuery("SELECT * FROM valores", null);
		if (c.getCount() > 0) {
			AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
			alerta.setTitle("Erro");
			alerta.setMessage("Você já cadastrou valores. O que deseja fazer?");
			alerta.setPositiveButton("Converter", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					Intent intent = new Intent(MainActivity.this, Converter.class);
					startActivity(intent);
				}
			});
			alerta.setNegativeButton("Atualizar valores", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int whichButton) {
					Alterar(v);
				}
			});
			alerta.create().show();
		} else {
			cadastrar(v);
		}
	}

	public void cadastrar(View v) {
		dois.setVisibility(View.INVISIBLE);
		um.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dolar = (EditText) findViewById(R.id.editText1);
				euro = (EditText) findViewById(R.id.editText2);
				yen = (EditText) findViewById(R.id.editText3);
				if (dolar.getText().toString().equals("") || euro.getText().toString().equals("")
						|| yen.getText().toString().equals("")) {
					AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
					alerta.setTitle("Erro");
					alerta.setMessage("Preencha todos os campos");
					alerta.setNeutralButton("Ok", null);
					alerta.show();
				} else {
					double real1 = 1.00;
					double dolar1 = Double.parseDouble(dolar.getText().toString());
					double euro1 = Double.parseDouble(euro.getText().toString());
					double yen1 = Double.parseDouble(yen.getText().toString());
					String moedareal = "real";
					String moedadolar = "dolar";
					String moedaeuro = "euro";
					String moedayen = "yen";
					Cursor c = db.rawQuery("SELECT * FROM valores", null);
					if (c.getCount() > 0) {
						AlertDialog.Builder alertaa = new AlertDialog.Builder(MainActivity.this);
						alertaa.setTitle("Erro");
						alertaa.setMessage("Já existem valores cadastrados");
						alertaa.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int whichButton) {
								Alterar(V);
							}
						});
						alertaa.show();
					} else {
						db.execSQL("INSERT INTO valores VALUES (?,'" + moedareal + "','" + real1 + "','" + moedadolar
								+ "','" + dolar1 + "','" + moedaeuro + "','" + euro1 + "','" + moedayen + "','" + yen1
								+ "')");
						Toast.makeText(getApplicationContext(), "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					}
				}
			}
		});
	}

	public void Alterar(View V) {
		dois.setVisibility(View.VISIBLE);
		um.setText("Alterar");
		um.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dolar = (EditText) findViewById(R.id.editText1);
				euro = (EditText) findViewById(R.id.editText2);
				yen = (EditText) findViewById(R.id.editText3);
				if (dolar.getText().toString().equals("") && euro.getText().toString().equals("")
						&& yen.getText().toString().equals("")) {
					AlertDialog.Builder alertaa = new AlertDialog.Builder(MainActivity.this);
					alertaa.setTitle("Erro");
					alertaa.setMessage("Preencha todos os campos");
					alertaa.setNeutralButton("Ok", null);
					alertaa.show();

				} else {
					if (!dolar.getText().toString().equals("") && !euro.getText().toString().equals("")
							&& !yen.getText().toString().equals("")) {
						double dolar1 = Double.parseDouble(dolar.getText().toString());
						double euro1 = Double.parseDouble(euro.getText().toString());
						double yen1 = Double.parseDouble(yen.getText().toString());
						db.execSQL("UPDATE valores SET valordolar = '" + dolar1 + "',valoreuro = '" + euro1
								+ "',valoryen = '" + yen1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					} else if (!dolar.getText().toString().equals("") && !euro.getText().toString().equals("")
							&& yen.getText().toString().equals("")) {
						double dolar1 = Double.parseDouble(dolar.getText().toString());
						double euro1 = Double.parseDouble(euro.getText().toString());
						db.execSQL("UPDATE valores SET valordolar = '" + dolar1 + "',valoreuro = '" + euro1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					} else if (!dolar.getText().toString().equals("") && !yen.getText().toString().equals("")
							&& euro.getText().toString().equals("")) {
						double dolar1 = Double.parseDouble(dolar.getText().toString());
						double yen1 = Double.parseDouble(yen.getText().toString());
						db.execSQL("UPDATE valores SET valordolar = '" + dolar1 + "',valoryen = '" + yen1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					} else if (!dolar.getText().toString().equals("") && euro.getText().toString().equals("")
							&& yen.getText().toString().equals("")) {
						double dolar1 = Double.parseDouble(dolar.getText().toString());
						db.execSQL("UPDATE valores SET valordolar = '" + dolar1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					} else if (!euro.getText().toString().equals("") && dolar.getText().toString().equals("")
							&& yen.getText().toString().equals("")) {
						double euro1 = Double.parseDouble(euro.getText().toString());
						db.execSQL("UPDATE valores SET valoreuro = '" + euro1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					} else if (!yen.getText().toString().equals("") && dolar.getText().toString().equals("")
							&& euro.getText().toString().equals("")) {
						double yen1 = Double.parseDouble(yen.getText().toString());
						db.execSQL("UPDATE valores SET valoryen = '" + yen1 + "'");
						Toast.makeText(getApplicationContext(), "Valores alterados", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(MainActivity.this, Converter.class);
						startActivity(intent);
					}
				}
			}
		});
		dois.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Converter.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Cursor c = db.rawQuery("SELECT * FROM valores;", null);
			if (c.getCount() > 0) {
				Intent intent = new Intent(MainActivity.this, Converter.class);
				startActivity(intent);
			} else {
				AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
				alerta.setTitle("Erro");
				alerta.setMessage("Você não possui valores cadastrados");
				alerta.setNeutralButton("Ok", null);
				alerta.show();
			}
		}
		return super.onOptionsItemSelected(item);
	}
}
