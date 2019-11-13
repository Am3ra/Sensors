package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor gyroSensor;
    TextView mainText;
    TextView gyroText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainText = findViewById(R.id.main_text);
        gyroText = findViewById(R.id.gyro);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        if(accelerometerSensor == null){
            Toast.makeText(this, "UNAVAILABLE SENSOR", Toast.LENGTH_SHORT).show();
            mainText.setText("NULL SENSOR");
            finish();
        }
//
        if (gyroSensor == null){
            gyroText.setText("Null Sensor");
        }

        Log.d("DEBUGGGIGN","REACHED");
        mainText.setText("OK");



    }

    @Override
    protected void onStart() {
        super.onStart();

        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();


        float currentValue = sensorEvent.values[0];

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_ACCELEROMETER:
                mainText.setText(
                        "Accelerometer x: "+ sensorEvent.values[0]  +"\n" +
                        "Accelerometer y:" +sensorEvent.values[1]+"\n" +
                        "Accelerometer z:" +sensorEvent.values[2]+"\n"

                );
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroText.setText(
                        "Gyroscope x: "+ sensorEvent.values[0]  +"\n" +
                        "Gyroscope y:" +sensorEvent.values[1]+"\n" +
                        "Gyroscope z:" +sensorEvent.values[2]+"\n"

                );
            default:
                // do nothing
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
