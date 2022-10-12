package com.example.cahuaya_evalparcial.ui.ListarCurso;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cahuaya_evalparcial.Entidades.Curso;
import com.example.cahuaya_evalparcial.Entidades.CursoAdapter;
import com.example.cahuaya_evalparcial.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListarCursoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    RecyclerView recyclerViewCurso;
    ArrayList<Curso> listaCurso;
    ProgressDialog dialog;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_listar_curso, container, false);
        listaCurso= new ArrayList<>();
        recyclerViewCurso=(RecyclerView) vista.findViewById(R.id.idRecyclerListaCurso);
        recyclerViewCurso.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerViewCurso.setHasFixedSize(true);
        request = Volley.newRequestQueue(getContext());

        //llamarWebservice
        listarCurso();
        return vista;
    }

    private void listarCurso() {
        dialog =new ProgressDialog(getContext());
        dialog.setMessage("Consultando Personajes");
        dialog.show();
        String url="http://192.168.19.91:8080/apis/listarcurso.php";
        jsonObjectRequest= new JsonObjectRequest (Request.Method.GET, url, null,this,this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "aqui"+error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        Curso curso=null;
        JSONArray json=response.optJSONArray("curso");
        try {
            for(int i=0; i<json.length();i++) {
                curso=new Curso();
                JSONObject jsonObject=null;
                jsonObject=json.getJSONObject(i);
                curso.setNombre(jsonObject.optString("nombre"));
                curso.setDescripcion(jsonObject.optString("descripcion"));
                curso.setNivel(jsonObject.optString("nivel"));
                curso.setDato(jsonObject.optString("imagen"));
                listaCurso.add(curso);
            }
            dialog.hide();
            CursoAdapter adapter=new CursoAdapter(listaCurso);
            recyclerViewCurso.setAdapter(adapter);
        }
        catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getContext(), "No hay conexion con el servidor", Toast.LENGTH_SHORT).show();
            dialog.hide();
        }


    }
}