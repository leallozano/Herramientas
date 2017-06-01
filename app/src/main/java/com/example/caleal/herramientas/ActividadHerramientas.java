package com.example.caleal.herramientas;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ActividadHerramientas extends AppCompatActivity implements ComunicaMenu, ManejaFlashCamara {

    private Fragment [] misFragmentos;

    private CameraManager miCamara;

    private String idCamara;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_herramientas);

        misFragmentos = new Fragment[3];

        misFragmentos[0]= new Linterna();
        misFragmentos[1]= new Musica();
        misFragmentos[2]= new Nivel();

        Bundle extras=getIntent().getExtras();

        menu(extras.getInt("BOTONPULSADO"));

        miCamara = (CameraManager) getSystemService(Context.CAMERA_SERVICE);


        try {

            idCamara=miCamara.getCameraIdList()[0];

        }catch (Exception e) {

            e.printStackTrace();

        }

    }

    @Override
    public void menu(int queBoton) {

        FragmentManager miManejador = getFragmentManager();

        FragmentTransaction miTransaccion=miManejador.beginTransaction();

        Fragment menu_iluminado= new Menu();

        Bundle datos=new Bundle();

        datos.putInt("BOTONESPULSADO",queBoton);

        menu_iluminado.setArguments(datos);

        miTransaccion.replace(R.id.menu,menu_iluminado);

        miTransaccion.replace(R.id.herramientas,misFragmentos[queBoton]);

        miTransaccion.commit();

    }

    public void enciendeApaga(boolean estadoFlash) {

        try {

            if (estadoFlash) {

                //Toast.makeText(this,"Flash apagado",Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

                    miCamara.setTorchMode(idCamara, false);

                }

            } else {

                //Toast.makeText(this,"Flash encendido",Toast.LENGTH_SHORT).show();

                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {

                    miCamara.setTorchMode(idCamara, true);

                }

            }
        }catch (Exception e){

            e.printStackTrace();

        }

    }

}
