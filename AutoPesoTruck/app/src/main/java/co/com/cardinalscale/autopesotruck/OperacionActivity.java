package co.com.cardinalscale.autopesotruck;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import co.com.cardinalscale.autopesotruck.Clases.TcpClient;


public class OperacionActivity extends AppCompatActivity {
    TcpClient mTcpClient;
    TextView textView;
    String carpetaFuente = "fonts/DS-DIGIT.TTF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operacion);
        textView = (TextView) findViewById(R.id.textView);
        // Cargamos la fuente
        Typeface fuente = Typeface.createFromAsset(getAssets(), carpetaFuente);

// Aplicamos la fuente
        textView.setTypeface(fuente);


        new ConnectTask().execute("");
    }


    @Override
    public void onBackPressed(){
        if (mTcpClient != null) {
            mTcpClient.stopClient();
        }
        finish();
    }

    public class ConnectTask extends AsyncTask<String, String, TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object
            mTcpClient = new TcpClient(new TcpClient.OnMessageReceived() {
                @Override
                //here the messageReceived method is implemented
                public void messageReceived(String message) {
                    //this method calls the onProgressUpdate
                    publishProgress(message);
                }
            });
            mTcpClient.run();

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            //response received from server
            Log.d("test", "response " + values[0]);
            textView.setText(values[0]);
            //process server response here....

        }

    }

}
