package unal.edu.co.speechrecognizer;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.Image;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView input;
    private ImageButton recordButton;

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = ( TextView )findViewById( R.id.input );
        recordButton = ( ImageButton )findViewById( R.id.start_rec );

        recordButton.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick( View view ){
                promptSpeechInput();
            }
        } );
    }

    private void promptSpeechInput(){
        Intent intent = new Intent( RecognizerIntent.ACTION_RECOGNIZE_SPEECH );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM );
        intent.putExtra( RecognizerIntent.EXTRA_LANGUAGE, "es-CO" );
        intent.putExtra( RecognizerIntent.EXTRA_PROMPT, getString( R.string.speech_prompt ) );

        try{
            startActivityForResult( intent, REQ_CODE_SPEECH_INPUT );
        } catch( ActivityNotFoundException e ){
            Toast.makeText( getApplicationContext(), getString( R.string.speech_not_supported ), Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ){
        super.onActivityResult( requestCode, resultCode, data );

        switch ( requestCode ){
            case REQ_CODE_SPEECH_INPUT:{
                if( resultCode == RESULT_OK && null != data ){
                    ArrayList<String> result = data.getStringArrayListExtra( RecognizerIntent.EXTRA_RESULTS );
                    input.setText( result.get( 0 ) );
                }
                break;
            }
        }
    }
}
