package mpdam.p1.meteoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    EditText country;
    TextView result;
    TextView feelsLike;
    Button  button;
    ImageView imageView;
    String apiKey="fc46d438b77126caf6de7c5db9f5c1b7";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        country=findViewById(R.id.inputText);
        result=findViewById(R.id.TextView);
        button=findViewById(R.id.Button1);
        feelsLike=findViewById(R.id.textView1);
        System.out.println(country.getText());
        imageView=findViewById(R.id.imageView2);
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String url="https://api.openweathermap.org/data/2.5/weather?q="+country.getText()+"&appid="+apiKey;
        JsonObjectRequest rquest =new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject mainJSonObject = response.getJSONObject("main");
                    String temperature=mainJSonObject.getString("temp");
                    Double temp=Double.parseDouble(temperature)-273.15;
                    JSONArray weatherJSONArray = response.getJSONArray("weather");
                    JSONObject weatherObject = weatherJSONArray.getJSONObject(0);
                    String weatherMain = weatherObject.getString("main");
                    feelsLike.setText(weatherMain);
                    if(weatherMain.equals("Clouds")){
                        imageView.setImageResource(R.drawable.cl);
                    }else if(weatherMain.equals("Clear")){
                        imageView.setImageResource(R.drawable.s);
                    }else if (weatherMain.equals("Rain")){
                        imageView.setImageResource(R.drawable.t);
                    }
                    result.setText(df.format(temp));
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(rquest);
    }
});


    }
}