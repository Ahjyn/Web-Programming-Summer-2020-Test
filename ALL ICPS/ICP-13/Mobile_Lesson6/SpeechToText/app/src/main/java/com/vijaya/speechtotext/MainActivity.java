package com.vijaya.speechtotext;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;
    TextToSpeech toSpeech;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceInputTv = findViewById(R.id.voiceInput);
        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);

        // Initializing Preferences, Editor
        preferences = getSharedPreferences("namePrefs",0);
        editor = preferences.edit();

        // code to say hello when the app launches
        toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int condition) {
                if(condition != TextToSpeech.ERROR) {  //not equal to error -- can continue
                    toSpeech.setLanguage(Locale.US);
                    toSpeech.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);  // speak hello and then flush data in queue
                    mVoiceInputTv.setText(Html.fromHtml("<p style=\"color:blue;\">Speaker : Hello! </p>")); //output
                }
            }
        });
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View see) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        // Starting Voice Input on click of Mic button
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, how can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            a.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    // Result to output
                    ArrayList<String> value = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if(value != null && value.size() > 0) {
                        mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">User : "+ value.get(0)+"</p>"));
                        // Mirror response of user if hello is said
                        if(value.get(0).equalsIgnoreCase("hello")) {
                            toSpeech.speak("What is your name", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : What is your name ?</p>"));
                        }else if(value.get(0).contains("what time")){
                            // Time output if requested
                            SimpleDateFormat Date =new SimpleDateFormat("HH:mm");//dd/MM/yyyy
                            Date now = new Date();
                            String[] nameDate = Date.format(now).split(":");
                            if(nameDate[1].contains("00")) nameDate[1] = "o'clock";
                            toSpeech.speak("The time is : "+ Date.format(now), TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : The time is : "+ Date.format(now)+"</p>"));
                        }else if(value.get(0).contains("my name")){
                            String name = value.get(0).substring(value.get(0).lastIndexOf(' ') + 1);
                            editor.putString("my name", name).apply();
                            toSpeech.speak("Hello, "+name,
                                    TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : Hello, "+name+"</p>"));
                        }else if(value.get(0).contains("not feeling good")){
                            toSpeech.speak("I can understand. Please tell your symptoms in short",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : I can understand. Please tell your symptoms in short</p>"));
                        }else if(value.get(0).contains("thank you")){
                            toSpeech.speak("Thank you too, "+preferences.getString("name","")+" Take care.", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : Thank you too, "+preferences.getString("name","")+" Take care.</p>"));
                        }else if(value.get(0).contains("medicine")){
                            toSpeech.speak("I think you have fever. Please take this medicine.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : I think you have fever. Please take this medicine.</p>"));
                        } else {
                            toSpeech.speak("Sorry, I can't help you with that", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.append(Html.fromHtml("<p style=\"color:blue;\">Speaker : Sorry, I can't help you with that</p>"));
                        }
                    }
                }
                break;
            }

        }
    }
}