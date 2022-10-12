package com.example.cahuaya_evalparcial.ui.BuscarCurso;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.cahuaya_evalparcial.Entidades.Curso;
import com.example.cahuaya_evalparcial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class  BuscarCursoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    EditText edtId;
    TextView txtNom, txtDesc,txtNivel,txtHoraTeo,txtHoraPrac,txtProy;
    Button btnBuscar;
    ProgressDialog progress;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    ImageView imgImagen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View vista =inflater.inflate(R.layout.fragment_buscar_curso,container,false);

            edtId=vista.findViewById(R.id.edtIdBC);
            txtNom=vista.findViewById(R.id.txtNombreBC);
            txtDesc=vista.findViewById(R.id.txtDescripcionBC);
            txtNivel=vista.findViewById(R.id.txtNivelBC);
            txtHoraTeo=vista.findViewById(R.id.txtHoraTeoriaBC);
            txtHoraPrac=vista.findViewById(R.id.txtHoraPracticaBC);
            txtProy=vista.findViewById(R.id.txtProyectoBC);
            btnBuscar=vista.findViewById(R.id.btnBuscarCurso);
            imgImagen=vista.findViewById(R.id.imgBC);
            request= Volley.newRequestQueue(getContext());
            btnBuscar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cargarWebservice();
                }
            });

            return vista;
    }
    private void cargarWebservice() {
        progress= new ProgressDialog(getContext());
        progress.setMessage("Consultando........");
        progress.show();
        String url="http://192.168.101.2:8080/apis/consultarcurso.php?id="+
                edtId.getText().toString();
//        String url="http://192.168.21.91:8080/apis/consultarcurso.php?id="+
//                edtId.getText().toString();

        url=url.replace(" ","%20");

        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progress.hide();
        Toast.makeText(getContext(), "No se puedo consultar "+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();
        Curso curso=new Curso();
        JSONArray json= response.optJSONArray("curso");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            curso.setNombre(jsonObject.optString("nombre"));
            curso.setDescripcion(jsonObject.optString("descripcion"));
            curso.setNivel(jsonObject.optString("nivel"));
            curso.setHoraTeoria(jsonObject.optString("horaTeoria"));
            curso.setHoraPractica(jsonObject.optString("horaPractica"));
            curso.setProyecto(jsonObject.optString("proyecto"));
            curso.setDato(jsonObject.optString("imagen"));
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        txtNom.setText(curso.getNombre());
        txtDesc.setText(curso.getDescripcion());
        txtNivel.setText(curso.getNivel());
        txtHoraTeo.setText(curso.getHoraTeoria());
        txtHoraPrac.setText(curso.getHoraPractica());
        txtProy.setText(curso.getProyecto());
        if (curso.getImagen()!=null ){
            imgImagen.setImageBitmap(curso.getImagen());
        }else imgImagen.setImageResource(R.drawable.imagen_base);



    }
}