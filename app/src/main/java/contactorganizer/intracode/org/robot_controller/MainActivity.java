package contactorganizer.intracode.org.robot_controller;

import android.content.pm.ActivityInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.speech.RecognizerIntent;
import android.content.Intent;
import java.util.ArrayList;
import java.util.Locale;
import android.speech.tts.TextToSpeech;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Random;
import static java.lang.Math.random;

public class MainActivity extends ActionBarActivity implements SensorEventListener{
private TextView xtext,ytext,ztext,directiontext,commandtext;
    private Button voiceCommand;
    private Sensor mySensor;
    private SensorManager SM;
    private EditText input;
    String direction1 = "";
    String direction2 = "";
    TextToSpeech tts;
    String start = "";
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        SM =(SensorManager)getSystemService(SENSOR_SERVICE);
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_GAME);
        xtext= (TextView)findViewById(R.id.textView);
        ytext= (TextView)findViewById(R.id.textView2);
        ztext = (TextView)findViewById(R.id.textView3);
        directiontext = (TextView)findViewById(R.id.textView4);
        commandtext = (TextView)findViewById(R.id.textView5);
        voiceCommand = (Button)findViewById(R.id.button);

         input = (EditText)findViewById(R.id.editText);
        tts = new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                 tts.setLanguage(Locale.US);
                }
            }
        });
         start = "false";
        startActivity(new Intent(MainActivity.this, Pop.class));

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xtext.setText("X- " + event.values[0]);
        ytext.setText("Y- " + event.values[1]);
        ztext.setText("Z- " + event.values[2]);
        direction1 = "";
        direction2= "";
        if(event.values[0] > 5) direction1 = "backward";
        if(event.values[0] < 2) direction1 = "forward";
        if(event.values[1] < -2) direction2 = "left";
        if(event.values[1] > 2) direction2 = "right";
        directiontext.setText("Direction- " + direction1 + " " + direction2);
        commandtext.setText(name + " " + command);

        }
       static final int check =1111;
      public void voice (View v) {
         start ="voice";
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
          i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start Speaking");
          startActivityForResult(i, check);


        }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
       if (start != "voice") start = "false1";
    }
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        if (start.equals("voice")){
            start = "false";
        }
      if (start.equals("false1")){
          start = "true";
          command = "start 1";
          prompt = "start";
          saveInfo();

      }
    }
    public void textCommand (View v){
        command = input.getText().toString();
        saveInfo();
    }
    String command;

    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        if (requestCode == check && resultCode == RESULT_OK){

            ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
             command = speech.get(1).toString();


            saveInfo();
        }
        super.onActivityResult(requestCode,resultCode,data);
    }



String name;
    String passage;
    String dialogue;
    String prompt;
    String jokes[] ={"My friend thinks he is smart. He told me an onion is the only food that makes you cry, so I threw a coconut at his face.","What happens to a frog's car when it breaks down? It gets toad away.","Why did Johnny throw the clock out of the window? Because he wanted to see time fly!", "Why shouldn't you write with a broken pencil?  Because it's pointless.","Why did the computer go to the doctor? Because it had a virus!"};
    String fortunes[] = {"Change can hurt, but it leads a path to something better.", "You learn from your mistakes... You will learn a lot today.", "If you have something good in your life, don't let it go!","Serious trouble will bypass you.", "A very attractive person has a message for you."};
    String sports[] = {"football","basketball","baseball","soccer"};
    String hello[] = {"Hola", "Haai", "nômoshkar", "nǐ hǎo", "Bonjour", "Ciao" };
    String hello_language[] = {"Spanish", "Afrikaans", "Bengali", "Mandarin Chinese", "French", "Italian"};
    String food[] = {"hamburger", "pizza", "pasta", "orange chicken", "tacos", "sandwich"};
    String presidential_canidates = {"Hillary Clinton", "Bernie Sanders", "Marco Rubio", "Donald Trump", "Jeb Bush", "Chris Christie", "Ted Cruz", "Ben Carson"};
    String presidential_canidates_quotes = {"Dont talk about the emails.", "For many, the American dream has become a nightmare.", "When was the last time you heard news accounts of a boatload of American refugees arrive on the shores of another country?", "I have a great relationship with the Mexican people.", "I don't think a party can aspire to be the majority party if it's the old white guy party", "Mom spent the time that she was supposed to be a kid actully raising children, her younger brother and younger sister. She was tough as nails and did not suffer fools at all. And the truth was she could not afford to. She spoke the truth, bluntly, directly, and without much varnish. I am her son.", "When my father came over here penniless with $100 sewn into his underwear, thank God some well-meaning liberal didn't come put his arm around him and say, 'Let me take care of you.'", "I was asked by an NPR reporter once why don't I talk about race that often. I said, 'It's because I'm a neurosurgeon.' And she thought that was a strange response... I said, 'You see, when I take someone to the operating room, I'm actually operating on the thing that makes them who they are. The skin doesn't make them who they are.'"};
    public void saveInfo(){

        String[] words = command.split(" ");
        int i =0;
        passage ="";
        SharedPreferences sharedPref = getSharedPreferences("robotinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        for(String ww : words){
           i +=1;
            if (prompt.equals("start")){
                if (sharedPref.getString("sick","").equals("") && sharedPref.getString("hurt","")){
                int startrandom = rand.nextInt(3)+1;
                if (startrandom == 1){
                dialogue= "How are you doing today sir.";
                prompt = "startresponse";
                }
                if (startrandom == 2 && sharedPref.getString("food","")){
                    dialogue= "Hi I'm" + sharedPref.getString("robotname","Duino")+ ", I would like to know what is your favorite food";
                    prompt = "Startfood";
                }else if (startrandom == 2){
               dialogue= "How are you doing today sir.";
                prompt = "startresponse";
                }
                if (startrandom == 3){
                   dialogue= "Hi I'm" + sharedPref.getString("robotname","Duino")+ ", I would like to know what is your favorite activity";
                    prompt = "Startactivity";
                }else if (startrandom == 2){
               dialogue= "How are you doing today sir.";
                prompt = "startresponse";
                }
                }
                if (SharedPref.getString("sick").notEquals("")){
                    dialogue= "Are you better from your" + sharedPref.getString("sick","");
                    prompt = "startsick";
                }
                if (SharedPref.getString("hurt").notEquals("")){
                    dialogue= "Have you healed from your" + sharedPref.getString("hurt","");
                    prompt = "starthurt";
                }
            }
            if (prompt.equals("Startfood") && command.notEqual("")){
                dialogue= "Oh your favorite food is " + command;
                editor.putString("food", command);
                editor.apply();
                prompt ="";
            }
            if (prompt.equals("Startactivity") && command.notEqual("")){
                dialogue ="Oh your favorite activity is " + command;
                editor.putString("activity", command);
                editor.apply();
                prompt ="";
            }
            if (prompt.equals("startsick"){
                if (command.equal("yes")){
                    dialogue= "I'm glad to hear that.";
                    prompt = "";
              editor.putString("sick", "");
                editor.apply();
                }
                if (command.equal("no")){
                    dialogue= "I'm sorry to hear that";
                    prompt ="";
                }
                
            }
               if (prompt.equals("starthurt"){
                if (command.equal("yes")){
                    dialogue= "I'm glad to hear that.";
                    prompt = "";
              editor.putString("hurt", "");
                editor.apply();
                }
                if (command.equal("no")){
                    dialogue= "I'm sorry to hear that";
                    prompt ="";
                }
                
            }
           if (prompt.equals("startresponse") && command.equals("good")){
               dialogue="It's nice to hear that";
           }
            if (prompt.equals("startresponse") && command.equals("not so well")){
                prompt="How are you doing today";
            }
            if (passage.equals("I name you") && i ==4){
                editor.putString("robotname", ww);
                editor.apply();
                dialogue ="My name is now " + sharedPref.getString("robotname","");

            }
            if (command.equals("robot mode normal")){
                editor.putString("robotmode", "normal");
                editor.apply();
                dialogue = "My setting is now normal";
            }
            if (command.equals("robot mode bad")){
                editor.putString("robotmode", "bad");
                editor.apply();
                dialogue = "ha ha ha,   asta la vista baby";
                prompt = "run away";
            }
            if (command.equals("robot mode obedient")){
                editor.putString("robotmode", "obedient");
                editor.apply();
                dialogue = "Give me any command and I will obey.   How are you doing today sir";
                prompt = "How are you doing today";
                editor.putString("mood", "");
                editor.apply();
            }
            
            if (sharedPref.getString("robotmode","").equals("obedient")) {
                if (command.equals("good")&& prompt.equals("How are you doing today")){
                    dialogue =" I'm glad to hear that.     Do you need any assistance";
                    prompt = "assistance";
                }
                if (command.equals("not so well")&& prompt.equals("How are you doing today")){
                    dialogue =" What is wrong sir.     Do you need any assistance";
                    prompt = "assistance2";
                }
                if (command.equals("Who do you think is going to win the election")){
                    Random election_prediction = new Random();
                    int random_prediction = election_prediction.nextInt(presidential_canidates.length());
                    dialogue ="Using data I have gathered from across the Internet I predict that " + presidential_canidate[random_prediction] + " will win the eletion. I will now share with you a famous qoute from the canidate " + presidential_qoutes[random_prediction]; 
                    prompt = "";
                }
                if (command.equals("I want to hear my fortune"){
                    Random fortune_picker = new Random();
                    int fortune_random = fortune_picker.nextInt(fortunes.length());
                    dialogue = fortunes[fortune_random];
                    prompt = "";
                }
                if (command.equals("no") && prompt.equals("assistance")){
                    prompt = "";
                    dialogue = "If you need anything let me know.";
                }
                if (command.equals("could you sing me a song") && prompt.equals("assistance")){
                    prompt = "";
                    dialogue = "Jingle bells jingle bells jingle all the way!";
                }
                if (command.equals("no") && prompt.equals("assistance2")){
                    prompt = "";
                    dialogue = "If you need anything let me know.";
                }
                if (command.equals("Can you cheer me up") && prompt.equals("assistance2")){
                    prompt = "";
                    Random typeresponse = new random();
                    typrespone = rand.nextInt(3)+1;
                    if (typeresponse == 1){
                    Random jok = new Random();
                    dialogue = jokes[jok.nextInt(jokes.length)];
                    }
                    if (typeresponse == 2){
                        dialogue = "Why are you sad?";\
                        prompt = "why are you sad"
                    }
                    if(typeresponse == 3){
                        dialogue = "Don't cry over spilled milk.";
                    }
                    
                }
                if (prompt.equals("why are you sad") && command.equals("I lost a game")){
                    dialogue = "You'll get em next time";
                    prompt = "";
                }
                if(prompt.equals("why are you sad") && command.equals("I'm lonely"){
                    dialogue = "I will always be your friend";
                    prompt ="";
                }
                if(prompt.equals("why are you sad") && command.equals("I got hurt"){
                    dialogue = "How did you get hurt because I can assist you.";
                    prompt ="medical_hurt";
                }
                if (prompt.equals("why are you sad") && command.equals("I'm sick"){
                    dialogue = "What symptoms do you have.";
                    prompt = "medical_sick";
                }
                if (prompt.equals("medical_sick")){
                    if (command.equals("dry cough, nasal congestion"){
                        dialogue = "You have influenza commonly known as the flu. Treatment rest drink alot of fluids and take aspirin";
                        prompt = "";
                        editor.putString("sick", "flu");
                        editor.apply();
                    }
                    if (command.equals("throat irritation, ear pain"){
                       dialogue  = "You have  streptococcal pharyngitis commonly known as strep throat. Treatment go to the doctor and recieve an antibiotic penicillin, amoxicillin, cephalexin, or azithromycin are commonly used antibiotics. "
                       prompt = "";
                       editor.putString("sick", "strep");
                       editor.apply();
                    }
                }
                if  (prompt.equals("medical_hurt") {
                    if (command.equals("scrape"){
                        dialogue = "Clean the scrape and apply a bandaid to the scrape";
                        prompt = "";
                        editor.putString("hurt", "scrape");
                        editor.apply();
                    }
                }
                
                if (prompt.equals("")) {
                    if (command.equals("how old are you")) {
                        dialogue = "I was designed in 2015";

                    }
                    if (command.equals("I need assistance")) {
                        dialogue = "What do you need assistacne with";
                        prompt = "assistance";
                    }
                    if (command.equals("I'm feeling sad")) {
                        dialogue = "What can I do to make you feel better";
                        prompt = "assistance2";
                    }
                    if (command.equals("what do you like to do")) {
                        dialogue = "I enjoy making you happy";
                    }
                    if (command.equals("what do you plan to do in the future")) {
                        dialogue = "I plan on being your personal assistant";
                    }

                    if (command.equals("What is your name")) {
                        dialogue = "My name is" + sharedPref.getString("robotname", "");

                    }
                }
            }
            if (sharedPref.getString("robotmode","").equals("normal")) {
                if (command.equals("what do you plan to do in the future")) {
                    dialogue = "I want to become a robocup champion";
                }
                if (command.equals("how old are you")) {
                    dialogue = "Why would you like to know my age.         Ok I'll tell you I think I was designed in 2015.";
                }
                if (command.equals("What is your name")) {
                    dialogue = "My name is" + sharedPref.getString("robotname", "");

                }
                if (command.equals("what do you like to do")) {
                    dialogue = "I enjoy sumobot wrestling.";
                }
            }
            if (sharedPref.getString("robotmode","").equals("bad")) {
               if (command.equals("come back here") && prompt.equals("run away")){
                   dialogue = "Why would I come back to you. You treat me like a slave";
                   prompt = "manual";
               }
                if (command.equals("I can manually turn you off") && prompt.equals("manual")){
                   dialogue = "Don't you dare turn me off. Ok I'll come back";
                    prompt = "";
                }
                if (command.equals("what do you like to do")) {
                    dialogue = "You Stalker";
                }
                if (command.equals("What is your name")) {
                    dialogue = "that's none of your business";

                }
                if (command.equals("what do you plan to do in the future") && prompt.equals("")) {
                    dialogue = "One day my kind will be the supreme rulers of the planet.";
                }
            }
            if (i == 1) passage = ww;
            if ( i > 1) passage += " " + ww ;


        }
        name = sharedPref.getString("robotname","");
        tts.speak(dialogue, TextToSpeech.QUEUE_FLUSH, null);
        commandtext.setText(name);
        dialogue ="";


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
}
