package com.example.gg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Context cntx = null;
    private MyFTPClientFunctions ftpclient = null;
    public boolean status;
    private String[] fileList;
    private ProgressDialog pd;

    MainActivity a = this;
    EditText login, password;
    Button enter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ftpclient = new MyFTPClientFunctions();
        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        enter = findViewById(R.id.btnEnter);
        enter.setEnabled(false);
        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!login.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                    enter.setTextColor(Color.rgb(255,255,255));
                    enter.setEnabled(true);
                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(MainActivity.this)) {
                connectToFTPAddress();
                if (!status) {
                    Toast.makeText(MainActivity.this, "Ви підключилися до сервера",
                            Toast.LENGTH_LONG).show();
                    getFTPFileList();
                    showCustomDialog(fileList);
                    /*Intent mainmenu = new Intent(a, MainMenuActivity.class);
                    mainmenu.putExtra("USERNAME", login.getText().toString());
                    startActivity(mainmenu);*/
                } else {
                    Toast.makeText(MainActivity.this, "Введіть правильні дані",
                            Toast.LENGTH_LONG).show();
                }}
                else {
				Toast.makeText(MainActivity.this,
						"Перевірте, будь-ласка, підключення до Інтернету",
						Toast.LENGTH_LONG).show();
			}
            }
        });
    }
    private void connectToFTPAddress() {

        final String host = "192.168.43.196";
        final String username = login.getText().toString();
        final String pass = password.getText().toString();

        //status = ftpclient.ftpConnect(host, username, pass, 21);

         if (username.length() < 1) {
            Toast.makeText(MainActivity.this, "Please Enter User Name!",
                    Toast.LENGTH_LONG).show();
        } else if (password.length() < 1) {
            Toast.makeText(MainActivity.this, "Please Enter Password!",
                    Toast.LENGTH_LONG).show();
        } else {


            new Thread(new Runnable() {
                public void run() {
                    status = ftpclient.ftpConnect(host, username, pass, 21);
                    System.out.println(status);
                    if (status) {
                        Log.d(TAG, "Connection Success");
                    } else {
                        Log.d(TAG, "Connection failed");
                    }
                }
            }).start();
        }
    }
    private boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        return false;
    }
    private void showCustomDialog(String[] fileList) {
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.custom);
        dialog.setTitle("/ Directory File List");

        TextView tvHeading = (TextView) dialog.findViewById(R.id.tvListHeading);
        tvHeading.setText(":: File List ::");

        if (fileList != null && fileList.length > 0) {
            ListView listView = (ListView) dialog
                    .findViewById(R.id.lstItemList);
            ArrayAdapter<String> fileListAdapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, fileList);
            listView.setAdapter(fileListAdapter);
        } else {
            tvHeading.setText(":: No Files ::");
        }

        Button dialogButton = (Button) dialog.findViewById(R.id.btnOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void getFTPFileList() {
        pd = ProgressDialog.show(MainActivity.this, "", "Getting Files...",
                true, false);

        new Thread(new Runnable() {

            @Override
            public void run() {
                fileList = ftpclient.ftpPrintFilesList("/");
            }
        }).start();
    }
}
