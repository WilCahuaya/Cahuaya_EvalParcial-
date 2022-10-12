package com.example.cahuaya_evalparcial.ui.InsertarCurso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.cahuaya_evalparcial.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class InsertarCursoFragment extends Fragment{
    EditText edtNomb,edtDesc,edtId,edtNivel,edtHorTeo,edtHorPrac,edtProy;
    Button btnInsert,btnSelectImage;
    ProgressDialog progreso;
    ImageView imgMostrarImagen;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    Bitmap bitmap;
    int PICK_IMAGE_REQUEST = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista= inflater.inflate(R.layout.fragment_insertar_curso, container, false);
        edtId=vista.findViewById(R.id.edtIdIC);
        edtNomb=vista.findViewById(R.id.edtNombreoIC);
        edtDesc=vista.findViewById(R.id.edtDescripcionIC);
        edtNivel=vista.findViewById(R.id.edtNivelIC);
        edtHorTeo=vista.findViewById(R.id.edtHorasTeoriaIC);
        edtHorPrac=vista.findViewById(R.id.edtHorasPracticaIC);
        edtProy=vista.findViewById(R.id.edtProyectoCursoIC);
        imgMostrarImagen=vista.findViewById(R.id.imgMostrarImageIC);
        btnInsert=vista.findViewById(R.id.btnInsertCurso);
        btnSelectImage=vista.findViewById(R.id.btnCargarImagenIC);
        request= Volley.newRequestQueue(getContext());
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertCurso();
            }
        });
        return  vista;
    }


    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void insertCurso() {
        //Mostrar el diálogo de progreso
        String url="http://192.168.19.91:8080/apis/insertarcurso.php";
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Subiendo...", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(getContext(), s, Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(getContext(), " saajajsjsd" +volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                String id = edtId.getText().toString().trim();
                String nombre = edtNomb.getText().toString().trim();
                String descripcion = edtDesc.getText().toString().trim();
                String nivel = edtNivel.getText().toString().trim();
                String horasTeoria = edtHorTeo.getText().toString().trim();
                String horasPractica = edtHorPrac.getText().toString().trim();
                String proyecto = edtProy.getText().toString().trim();

                //Creación de parámetros
                Map<String, String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put("id", id);
                params.put("imagen_ruta", imagen);
                params.put("nombre", nombre);
                params.put("descripcion", descripcion);
                params.put("nivel", nivel);
                params.put("horaTeoria", horasTeoria);
                params.put("horaPractica", horasPractica);
                params.put("proyecto", proyecto);
                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    private void cargarImagen() {
        Toast.makeText(getActivity(), "sisis", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Selecciona Imagen"),PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData()!=null){
            Uri filePath = data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),filePath);
                imgMostrarImagen.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}