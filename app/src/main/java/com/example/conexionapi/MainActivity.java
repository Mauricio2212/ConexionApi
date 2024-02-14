package com.example.conexionapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import com.google.android.gms.wearable.MessageClient;

public class MainActivity extends AppCompatActivity implements
        DataClient.OnDataChangedListener,
        MessageClient.OnMessageReceivedListener,
        CapabilityClient.OnCapabilityChangedListener {

    private static final String COUNT_KEY = "CodigoMensaje";
    private static final String COUNT_KEY2 = "TextoMensaje";
    DataClient mDataClient;
    TextView textoRecibido;
    EditText textoEnviar;
    Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoRecibido = findViewById(R.id.texto);
        textoEnviar = findViewById(R.id.txtEnviar);
        btnEnviar = findViewById(R.id.btnEnviar);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStuff(textoEnviar.getText().toString());
            }
        });
    }

    private void sendStuff(String datos) {
        mDataClient = Wearable.getDataClient(this);
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/ messageMobile").setUrgent();
        // putDataMapReq.getDataMap().putInt(COUNT_KEY, 100);
        putDataMapReq.getDataMap().putString(COUNT_KEY2, datos);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest().setUrgent();
        Task<DataItem> putDataTask = mDataClient.putDataItem(putDataReq);

        putDataTask.addOnSuccessListener(new OnSuccessListener<DataItem>()
        {
            @Override
            public void onSuccess(DataItem dataItem) {
                Toast.makeText(getApplicationContext(), "eviado", Toast.LENGTH_LONG).show();
            }
        });

    }
}