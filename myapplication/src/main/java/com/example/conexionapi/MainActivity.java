package com.example.conexionapi;

import android.os.Bundle;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity  implements
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    private TextView textoRecibido;
    private EditText textoEnviar;
    private Button btnEnviar;

    DataClient mDataClient;

    private static final String COUNT_KEY = "CodigoMensaje";
    private static final String COUNT_KEY2 = "TextoMensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoRecibido = findViewById(R.id.texto);
        textoEnviar = findViewById(R.id.txtMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sendStuff(textoEnviar.getText().toString());
            }
        });
    }

    protected void onResume() {
        super.onResume();

        Wearable.getDataClient(this).addListener(this);
        Wearable.getMessageClient(this).addListener(
                this, Uri.parse("mobile://"),
                CapabilityClient.FILTER_REACHABLE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Wearable.getDataClient(this).removeListener(this);
        Wearable.getMessageClient(this).removeListener(this);
        Wearable.getCapabilityClient(this).removeListener(this);
    }

    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {

        Toast.makeText(getApplicationContext(), "Se recibio informacion", Toast.LENGTH_LONG).show();
        for (DataEvent event : dataEventBuffer) {
                if (event.getType() == DataEvent.TYPE_CHANGED) {
                    //DataItem changed
                    DataItem item = event.getDataItem();
                    if (item.getUri().getPath().compareTo("/messageMobile") == 0) {
                        DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                        //Toast.makeText(getApplicationContext(), String.valueOf(dataMap.getInt(COUNT_KEY)), Toast.LENGTH_LONG).show();

                        textoRecibido.setText("Texto desde Movil:" +String.valueOf(dataMap.getString(COUNT_KEY2)));
                    }
                } else if (event.getType() == DataEvent.TYPE_DELETED) {
                    //DataItem deleted
                }
            }
        }

    @Override
    public void onCapabilityChanged(@NonNull CapabilityInfo capabilityInfo) {

    }

    @Override
    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

    }
}