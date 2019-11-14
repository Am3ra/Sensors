# Sensors


Utilizar los sensores es algo bastante simple..


https://youtu.be/ZRsAHmMj4P0

Tienes que declarar 2 cosas, el sensorManager y el sensor que quieres leer.

``` java
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
```

Luego, tienes que inicializar las dos cosas:

``` java

  sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

  accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

```

Para poder agregarle el SensorEventListener, hay dos formas de acerlo, crear una nueva clase privada, o simplemente implementarlo en la clase.

``` java
implements SensorEventListener
```


Esta clase requiere dos metodos, onSensorChanged y on accuracy changed.

Tomando el campo ```values``` del ```sensorEvent```, es muy facil utilizar los valores:

```java
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
```

### Cambio de estado

finalmente, tenemos que incluir los metodos de AppCompatActivity para poder usar el cambio de estado de la actividad:

```java
@Override
protected void onStop() {
  super.onStop();
  sensorManager.unregisterListener(this);
}

@Override
protected void onStart() {
  super.onStart();

  if(accelSensor!=null){
      sensorManager.registerListener(this,accelSensor,sensorManager.SENSOR_DELAY_NORMAL);
  }
  if(gyroSensor!=null){
      sensorManager.registerListener(this,gyroSensor,sensorManager.SENSOR_DELAY_FASTEST);
  }
}
```


# Bluetooth:

Para utilizar BlueTooth, primero tienes que agregar los siguientes permisos al Manifest.xml:

``` xml
<uses-permission android:name="android.permission.BLUETOOTH"/>
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN"/>
```

y declarar las siguientes variables:

``` java
BluetoothAdapter bluetoothAdapter ;
private final static int REQUEST_ENABLE_BT = 1;

```

En este caso, ``` REQUEST_ENABLE_BT ``` solo tiene que ser un entero mayor a 0.

Luego, para poder cambiar el estado, puedes llamar un intent muy simple:


``` java 
 if (!bluetoothAdapter.isEnabled()) {
    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
    startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
  }

  Intent discoverableIntent =
      new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
  discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
  startActivity(discoverableIntent);
```


# Wifi

Para wifi, tienes que agregar los siguientes campos:

``` xml
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

y puedes pedirle al usario que lo prenda con el siguiente intent:

``` java
startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
```

para accesar el estado de connectividad actual:

``` java

ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
NetworkInfo wifiState = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

if (wifiState.isConnected()) {
   // hacer lo necesario
}
```
