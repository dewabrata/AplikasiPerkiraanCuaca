package com.juara.aplikasiperkiraancuaca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.juara.aplikasiperkiraancuaca.adapter.AdapterListSimple;
import com.juara.aplikasiperkiraancuaca.model.forecast.ForecastModel;
import com.juara.aplikasiperkiraancuaca.service.APIClient;
import com.juara.aplikasiperkiraancuaca.service.APIInterfacesRest;

import org.json.JSONObject;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    EditText txtKota;
    Button btnSearch;
    RecyclerView lstCuaca;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtKota = findViewById(R.id.txtKota);
        btnSearch = findViewById(R.id.btnSearch);
        lstCuaca = findViewById(R.id.lstCuaca);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callForecastbyCity(txtKota.getText().toString());
            }
        });




    }

    APIInterfacesRest apiInterface;
    ProgressDialog progressDialog;
    public void callForecastbyCity(String kota){

        apiInterface = APIClient.getClient().create(APIInterfacesRest.class);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.show();
        Call<ForecastModel> call3 = apiInterface.getForecastByCity(kota,"6c57819f3114a6213bf6a1a0290c4f2c");
        call3.enqueue(new Callback<ForecastModel>() {
            @Override
            public void onResponse(Call<ForecastModel> call, Response<ForecastModel> response) {
                progressDialog.dismiss();
                ForecastModel dataWeather = response.body();
                //Toast.makeText(LoginActivity.this,userList.getToken().toString(),Toast.LENGTH_LONG).show();
                if (dataWeather !=null) {

               //     txtKota.setText(dataWeather.getName());
               //     txtTemperature.setText(new DecimalFormat("##.##").format(dataWeather.getMain().getTemp()-273.15));

                    AdapterListSimple adapter = new AdapterListSimple(MainActivity.this,dataWeather.getList(),dataWeather.getCity().getName());


                    lstCuaca.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    lstCuaca.setItemAnimator(new DefaultItemAnimator());
                    lstCuaca.setAdapter(adapter);





                }else{

                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this, jObjError.getString("message"), Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ForecastModel> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Maaf koneksi bermasalah",Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });




    }
}
