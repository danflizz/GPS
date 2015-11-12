package location.example.dany.mygpslocation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {

    TextView txtubicacion;
    CheckBox check;
    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordenadas();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void coordenadas()
    {
        check = (CheckBox)findViewById(R.id.checkbox);
        txtubicacion = (TextView) findViewById(R.id.txtUbicacion);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //se obtiene la ultima posiciòn conocida
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //mostramos la ultima posicion conocida
        //muestraPosicion(location);

        //actualizaciones de la posicion
        locationListener = new LocationListener()
        {
            @Override
            //Se llamará cada vez que la posición cambie
            public void onLocationChanged(final Location location)
            {
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        boolean isChecked = ((CheckBox)view).isChecked();


                        if (isChecked)
                        {
                            txtubicacion.setText("");
                            //muestra ubicacion
                            muestraPosicion(location);
                        }
                        else
                        {
                            //limpia la pantalla
                            txtubicacion.setText("");
                            //muestra aviso
                            txtubicacion.append(getString(R.string.aviso));
                        }

                    }
                });

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {
               // txtubicacion.append("");
                 //   txtubicacion.append(getString(R.string.enable));
            }

            @Override
            public void onProviderDisabled(String s) {
              //  txtubicacion.append("");
              //  txtubicacion.append(getString(R.string.disable));
                Intent intent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
    }

    private void muestraPosicion(Location location)
    {
        //se comprueba que la posición existe y es válida
        if(location!=null) {

            //se obtiene la latitud
            double latitud = location.getLatitude();
            //se obtiene la longitud
            double longitud = location.getLongitude();

          //  txt1.setText(getString(R.string.latitud) + Double.toString(latitud));
          //  txt2.setText(getString(R.string.longitud) + Double.toString(longitud));

            try
            {
                //Sirve para obtener la direccion
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                //obtiene la direccion por medio de la latitud, longitud
                List<Address> list = geocoder.getFromLocation(latitud, longitud,1);
                if(!list.isEmpty())
                {
                    //obtener la primera direccion que encontró
                    Address address = list.get(0);

                    String ubicacion = getString(R.string.address)+address.getAddressLine(0)+", "+address.getSubLocality()+", "+address.getLocality()+", "+address.getCountryName();
                    txtubicacion.append(ubicacion);

                }

            }
            catch (IOException e) {
                e.printStackTrace();

            }
        }
        else
        {
            txtubicacion.append(getString(R.string.no_data));
        }
    }

 /*   private TextView lblLatitud;
    private TextView lblLongitud;


    private LocationManager locationManager;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lblLatitud = (TextView)findViewById(R.id.txtLat);
        lblLongitud = (TextView)findViewById(R.id.txtLong);


                actualizarPosicion();



    }

    private void actualizarPosicion()
    {
        //Obtenemos una referencia al LocationManager
        locationManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida
        Location location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //Mostramos la última posición conocida
        muestraPosicion(location);

        //Nos registramos para recibir actualizaciones de la posición
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                muestraPosicion(location);
            }
            public void onProviderDisabled(String provider){

            }
            public void onProviderEnabled(String provider){

            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                Log.i("LocAndroid", "Provider Status: " + status);

            }
        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 15000, 0, locationListener);
    }

    private void muestraPosicion(Location loc) {
        if(loc != null)
        {
            lblLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            lblLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));

            Log.i("LocAndroid", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        }
        else
        {
            lblLatitud.setText("Latitud: (sin_datos)");
            lblLongitud.setText("Longitud: (sin_datos)");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

}
