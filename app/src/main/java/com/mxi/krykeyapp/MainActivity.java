package com.mxi.krykeyapp;


import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.Vitamio;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.mxi.krykeyapp.RoundKnobButton.RoundKnobButtonListener;

import com.mxi.krykeyapp.json.GetJsonData;

public class MainActivity extends Activity implements OnClickListener {

    Singleton m_Inst = Singleton.getInstance();
    public static TextView main_tv_freq, header_name, main_tv_radio_name, main_tv_radio_genere;
    ImageView main_iv_play, main_iv_stop, main_iv_prv, main_iv_next;
    ImageView main_iv_main, main_iv_search, main_iv_location, main_iv_setting;
    ImageView main_iv_one, main_iv_two, main_iv_three, main_iv_four;
    ProgressBar main_progressBar1;
    public static MediaPlayerDemo_Audio medioPlayer;
    private MediaPlayer mPlayer = null;
    public static GetJsonData gjdata;
    Typeface faceReg, faceBold;
    RoundKnobButton rv;
    //	int percentage=80;
    public static int array_postion;
    public static String radio_frq = "151.0", tag_play = "0", rtmp_url, rtmp_id, rtmp_extnsn;
    public static String h_name, h_gener, h_city;

    WheelView wheel;
    // Wheel scrolled flag
    private boolean wheelScrolled = false;
    Vibrator mVibrator;
   public StreamMusic streamMusic;
    public static int ply_tag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (!LibsChecker.checkVitamioLibs(this))
            return;*/

        Vitamio.isInitialized(this);
        m_Inst.InitGUIFrame(this);

        setContentView(R.layout.activity_main);
        initWheel(R.id.slotwheel);

        MainActivity.tag_play = "0";

        main_iv_search = (ImageView) findViewById(R.id.main_iv_search);
        main_iv_setting = (ImageView) findViewById(R.id.main_iv_setting);
        main_iv_location = (ImageView) findViewById(R.id.main_iv_location);
        main_iv_one = (ImageView) findViewById(R.id.main_iv_one);
        main_iv_two = (ImageView) findViewById(R.id.main_iv_two);
        main_iv_three = (ImageView) findViewById(R.id.main_iv_three);
        main_iv_four = (ImageView) findViewById(R.id.main_iv_four);
        main_iv_play = (ImageView) findViewById(R.id.main_iv_play);
        main_iv_stop = (ImageView) findViewById(R.id.main_iv_stop);
        header_name = (TextView) findViewById(R.id.header_name);
        main_tv_radio_genere = (TextView) findViewById(R.id.main_tv_radio_genere);
        main_tv_radio_name = (TextView) findViewById(R.id.main_tv_radio_name);
        main_iv_prv = (ImageView) findViewById(R.id.main_iv_prv);
        main_iv_next = (ImageView) findViewById(R.id.main_iv_next);
        main_iv_next.setOnClickListener(this);
        main_iv_prv.setOnClickListener(this);

        faceReg = Typeface.createFromAsset(this.getAssets(),
                "fonts/champagne_limousines.ttf");
        faceBold = Typeface.createFromAsset(this.getAssets(),
                "fonts/champagne_limousines_bold.ttf");


        try {
            mVibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        } catch (Exception e) {
//			Log.e( "v", e.toString() );
        }

        main_progressBar1 = (ProgressBar) findViewById(R.id.main_progressBar1);
        main_progressBar1.setVisibility(View.GONE);
        main_iv_stop.setOnClickListener(this);
        main_iv_play.setOnClickListener(this);

        LinearLayout panel = (LinearLayout) findViewById(R.id.panel);
        main_tv_freq = (TextView) findViewById(R.id.main_tv_freq);
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        medioPlayer = new MediaPlayerDemo_Audio();
        gjdata = new GetJsonData();
        header_name.setText("KryKey");

        final TextView tv2 = new TextView(this);
        tv2.setText("");
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        panel.addView(tv2, lp);

        rv = new RoundKnobButton(this, R.drawable.circle, R.drawable.circle1,
                R.drawable.circle1, m_Inst.Scale(350), m_Inst.Scale(350));
        lp = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        panel.addView(rv, lp);

        rv.setRotorPercentage(120);
        rv.SetListener(new RoundKnobButtonListener() {
            public void onStateChange(boolean newstate) {
//				Toast.makeText(MainActivity.this, "New state:" + newstate,
//						Toast.LENGTH_SHORT).show();
            }

            public void onRotate(final int arg) {
                tv2.post(new Runnable() {
                    public void run() {


//						main_tv_freq.setText("" +  arg);
                        if (GetJsonData.frequency.size() > arg) {
                            //Toast.makeText(getApplicationContext(), "Parth 1 "+GetJsonData.frequency.get(arg).freq, 1).show();
                            main_tv_freq.setText(GetJsonData.frequency.get(arg).freq);
                            array_postion = arg;

                            wheel.setCurrentItem(array_postion, true);
                            radio_frq = main_tv_freq.getText().toString();
                        }
//						main_tv_freq.setText("" +  GetJsonData.frequency.get(arg).freq);
                        vibration();
                        playAlertTone(getApplicationContext());

                    }
                });

            }

        });

        main_iv_one.setOnClickListener(this);
        main_iv_two.setOnClickListener(this);
        main_iv_three.setOnClickListener(this);
        main_iv_four.setOnClickListener(this);
        main_iv_search.setOnClickListener(this);
        main_iv_location.setOnClickListener(this);
        main_iv_setting.setOnClickListener(this);

        main_tv_freq.setTypeface(faceBold);
        header_name.setTypeface(faceReg);
        main_tv_radio_genere.setTypeface(faceBold);
        main_tv_radio_name.setTypeface(faceBold);

        wheel = getWheel(R.id.slotwheel);
//		mMediaPlayer = new MediaPlayer(MainActivity.this);
//		final Handler seekHandler = new Handler();
//		Runnable run = new Runnable() {
//
//			@Override
//			public void run() {
//				if (mMediaPlayer.isPlaying()) {
//					Log.e("is play", "playing");
//					play_s=0;
//				} else if (play_s==0) {
//					Log.e("is play", "playing stop");
//					play_s=1;
////					new StreamMusic().execute();
//				} else {
//					Log.e("is play", "wait");
//				}
//
//				seekHandler.postDelayed(this, 1000);
//			}
//		};
//		seekHandler.postDelayed(run, 1000);
//

    }

    @Override
    public void onBackPressed() {
        try {
            // main.showCachedAd(AdConfig.AdType.smartwall);   //This will display the ad but it wont close the app.
            // main.showCachedAd(AdConfig.AdType.smartwall, adListener);
        } catch (Exception e) {
            // close the activity if ad is not available.
            //finish();
        }
    }


    public void settings() {
        Intent setIntent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(setIntent);
    }

    public void search() {
        Intent searchIntent = new Intent(getApplicationContext(), Search.class);
        startActivity(searchIntent);
    }

    public void location() {
        Intent searchIntent = new Intent(getApplicationContext(), Location.class);
        startActivity(searchIntent);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        SharedPreferences prefConfi;
        prefConfi = getApplicationContext()
                .getSharedPreferences("MyPrefSave", MODE_PRIVATE);

        switch (v.getId()) {
            case R.id.main_iv_search:
                search();
                break;
            case R.id.main_iv_setting:
                settings();
                break;
            case R.id.main_iv_location:
                location();
                break;
            case R.id.main_iv_one:

                String save_frq1 = prefConfi.getString("save_radio_frq1", "");
                if (save_frq1.equals("")) {
                    SaveAlret(1, main_tv_freq.getText().toString());
                } else {
                    radio_frq = save_frq1;
                    ply_tag = 1;
                    new getFrqRadio().execute();
                    setRoller();
                }

                break;
            case R.id.main_iv_two:

                String save_frq2 = prefConfi.getString("save_radio_frq2", "");
                if (save_frq2.equals("")) {
                    SaveAlret(2, main_tv_freq.getText().toString());
                } else {
                    radio_frq = save_frq2;
                    ply_tag = 1;
                    new getFrqRadio().execute();
                    setRoller();
                }

                break;
            case R.id.main_iv_three:
                String save_frq3 = prefConfi.getString("save_radio_frq3", "");
                if (save_frq3.equals("")) {
                    SaveAlret(3, main_tv_freq.getText().toString());
                } else {
                    radio_frq = save_frq3;
                    ply_tag = 1;
                    new getFrqRadio().execute();
                    setRoller();
                }

                break;
            case R.id.main_iv_four:

                String save_frq5 = prefConfi.getString("save_radio_frq4", "");
                if (save_frq5.equals("")) {
                    SaveAlret(4, main_tv_freq.getText().toString());
                } else {
                    radio_frq = save_frq5;
                    ply_tag = 1;
                    new getFrqRadio().execute();
                    setRoller();
                }

                break;

            case R.id.main_iv_play:
                try {
                    radio_frq = main_tv_freq.getText().toString();
                    ply_tag = 1;
                    new getFrqRadio().execute();
                } catch (Exception e) {
                    // e.printStackTrace();
                }


                break;

            case R.id.main_iv_stop:
                try {
                    main_iv_play.setEnabled(true);
                    medioPlayer.stopReadio();
                    main_progressBar1.setVisibility(View.GONE);
                } catch (Exception e) {
                    //e.printStackTrace();
                }
                break;
            case R.id.main_iv_prv:

                if (array_postion > 0) {
                    //	Toast.makeText(getApplicationContext(), "Parth 2 "+GetJsonData.frequency.get(array_postion-1).freq, 1).show();
                    main_tv_freq.setText(GetJsonData.frequency.get(array_postion - 1).freq);
                    array_postion = array_postion - 1;
                } else {
                    // play last song
                    array_postion = GetJsonData.frequency.size() - 1;
                    //Toast.makeText(getApplicationContext(), "Parth 3 "+GetJsonData.frequency.get(array_postion).freq, 1).show();
                    main_tv_freq.setText(GetJsonData.frequency.get(array_postion).freq);
                }
                radio_frq = main_tv_freq.getText().toString();
//			new getFrqRadio().execute();
                setRoller();
                vibration();
                break;
            case R.id.main_iv_next:


//		        wheel.scroll(1, 500);	

                if (array_postion < (GetJsonData.frequency.size() - 1)) {
                    //Toast.makeText(getApplicationContext(), "Parth 4 "+GetJsonData.frequency.get(array_postion+1).freq, 1).show();
                    main_tv_freq.setText(GetJsonData.frequency.get(array_postion + 1).freq);
                    array_postion = array_postion + 1;
                } else {
                    // play first song
                    //	Toast.makeText(getApplicationContext(), "Parth 5 "+GetJsonData.frequency.get(0).freq, 1).show();
                    main_tv_freq.setText(GetJsonData.frequency.get(0).freq);
                    array_postion = 0;
                }
                radio_frq = main_tv_freq.getText().toString();
                setRoller();
//			new getFrqRadio().execute();
                vibration();
                break;
        }
    }


    public class StreamMusic extends AsyncTask<String, Void, Void> {
        SharedPreferences prefConfi = MainActivity.this
                .getSharedPreferences("MyPref", MODE_PRIVATE);
        String membershipkey = prefConfi.getString("membershipkey", "notregistor");

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            main_progressBar1.setVisibility(View.VISIBLE);
            main_iv_play.setEnabled(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub

            medioPlayer.playAudio(MainActivity.this, rtmp_url, rtmp_extnsn, rtmp_id, membershipkey);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//			Toast.makeText(getApplicationContext(), radio_frq+"", 1).show();
            main_iv_play.setEnabled(true);
            main_progressBar1.setVisibility(View.GONE);
            new SaveListen().execute();
        }

    }

    public class SaveListen extends AsyncTask<String, Void, Void> {

        SharedPreferences prefConfi = MainActivity.this
                .getSharedPreferences("MyPref", MODE_PRIVATE);
        String membershipkey = prefConfi.getString("membershipkey", "notregistor");

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
//			Log.e("asasdasdasd", membershipkey+":"+rtmp_id);
            gjdata.SaveListen("SaveListen", rtmp_id, membershipkey);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
//			Toast.makeText(getApplicationContext(), radio_frq+"", 1).show();
            main_progressBar1.setVisibility(View.GONE);
        }

    }

    public class getFrqRadio extends AsyncTask<String, Void, Void> {
        ProgressDialog progressDialog = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
//	        progressDialog.show();        
            progressDialog.setContentView(R.layout.custom_progressdialog);
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub

            gjdata.FindStationBy("FindStationByFrequency", radio_frq, "frequency");

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            progressDialog.dismiss();

            try {
                if (GetJsonData.FindStationByArray.size() != 0) {

                    MainActivity.radio_frq = GetJsonData.FindStationByArray.get(0).freq;
                    MainActivity.h_name = GetJsonData.FindStationByArray.get(0).radio;
                    MainActivity.h_gener = GetJsonData.FindStationByArray.get(0).genre;
                    MainActivity.h_city = GetJsonData.FindStationByArray.get(0).city;
                    if (GetJsonData.FindStationByArray.get(0).rtmp_url.equals("null")) {
                        MainActivity.rtmp_url = GetJsonData.FindStationByArray.get(0).rtmpmic;
                        rtmp_extnsn = "mic1";
                    } else {
                        MainActivity.rtmp_url = GetJsonData.FindStationByArray.get(0).rtmp_url;
                        rtmp_extnsn = "dj1";
                    }
                    MainActivity.rtmp_id = GetJsonData.FindStationByArray.get(0).id;
                    //Toast.makeText(getApplicationContext(), "Parth 6 "+radio_frq, 1).show();
                    main_tv_freq.setText(radio_frq);
                    //				Log.e("========", radio_frq+h_name+h_gener+h_city);
                    header_name.setText(h_name + ", " + h_gener + ", " + h_city);
                    main_tv_radio_name.setText(h_name);
                    main_tv_radio_genere.setText(h_gener);

                    if (ply_tag == 1) {
                        new StreamMusic().execute();
                        ply_tag = 0;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Somthing went wrong", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // e.printStackTrace();
            }

        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (tag_play.equals("1")) {

            new StreamMusic().execute();
//			Toast.makeText(getApplicationContext(), radio_frq, 1).show();
            //Toast.makeText(getApplicationContext(), "Parth 7 "+radio_frq, 1).show();
            main_tv_freq.setText(radio_frq);
            header_name.setText(h_name + ", " + h_gener + ", " + h_city);
            main_tv_radio_name.setText(h_name);
            main_tv_radio_genere.setText(h_gener);
            tag_play = "0";

            setRoller();
        } else {
            SharedPreferences pref = this.getSharedPreferences("MyPrefSave", MODE_PRIVATE);
            Boolean flag = pref.getBoolean("save", false);
            String name = pref.getString("h_name", "");
            String city = pref.getString("h_city", "");
            String genre = pref.getString("h_gener", "");
            String frq = pref.getString("radio_frq", "");

            if (flag == true) {
                //Toast.makeText(getApplicationContext(), "Parth 8 "+frq, 1).show();
                main_tv_freq.setText(frq);
                if (name.equals("")) header_name.setText("KryKey");
                else header_name.setText(name + ", " + genre + ", " + city);
                main_tv_radio_name.setText(name);
                main_tv_radio_genere.setText(genre);
                radio_frq = frq;
                setRoller();

            } else {
                radio_frq = "151";
                new getFrqRadio().execute();
                setRoller();
            }
        }
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();

        SharedPreferences prefConfi = getApplicationContext()
                .getSharedPreferences("MyPrefSave", MODE_PRIVATE);
        Editor editorConfi = prefConfi.edit();
        editorConfi.putString("radio_frq", radio_frq);
        editorConfi.putString("h_name", h_name);
        editorConfi.putString("h_gener", h_gener);
        editorConfi.putString("h_city", h_city);
        editorConfi.putBoolean("save", true);
        editorConfi.commit();

    }


    public void setRoller() {

        for (int i = 0; i < GetJsonData.frequency.size(); i++) {
            if (GetJsonData.frequency.get(i).freq.equals(radio_frq)) {
                array_postion = i;
            }
        }
//		Toast.makeText(getApplicationContext(), array_postion+"", 1).show();
//		Toast.makeText(getApplicationContext(), radio_frq+"", 1).show();

        int size = GetJsonData.frequency.size();
        int a = Math.round((float) (360 / size));
        int b = Math.round((float) (array_postion * a));
        wheel.setCurrentItem(array_postion, true);
        rv.setRotorPercentage(b);
    }

    public void SaveAlret(final int btn, final String frq) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setMessage("Frequency " + frq + " set to " + btn + ".");
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        String key_name = "save_radio_frq" + btn;
                        SharedPreferences prefConfi = getApplicationContext()
                                .getSharedPreferences("MyPrefSave", MODE_PRIVATE);
                        Editor editorConfi = prefConfi.edit();
                        editorConfi.putString(key_name, frq);
                        editorConfi.commit();
                        arg0.dismiss();
                    }
                });
        alertDialogBuilder.setNegativeButton("cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Wheel scrolled listener
    OnWheelScrollListener scrolledListener = new OnWheelScrollListener() {
        public void onScrollingStarted(WheelView wheel) {
            wheelScrolled = true;
            // Do something here

        }

        public void onScrollingFinished(WheelView wheel) {
            wheelScrolled = false;
            // Do something here
//            Toast.makeText(getApplicationContext(), wheel.getCurrentItem()+" : ", 1).show();
            // Toast.makeText(getApplicationContext(), "Parth 9 "+GetJsonData.frequency.get(wheel.getCurrentItem()).freq, 1).show();
            main_tv_freq.setText(GetJsonData.frequency.get(wheel.getCurrentItem()).freq);
            array_postion = wheel.getCurrentItem();
            radio_frq = main_tv_freq.getText().toString();
            setRoller();
            new getFrqRadio().execute();
        }
    };


    // Wheel changed listener
    private OnWheelChangedListener changedListener = new OnWheelChangedListener() {
        public void onChanged(WheelView wheel, int oldValue, int newValue) {
            if (!wheelScrolled) {
                // Do something here
//            	Toast.makeText(getApplicationContext(), oldValue+" : "+newValue, 1).show();
            }
            vibration();
        }
    };

    /**
     * Initializes wheel
     *
     * @param id the wheel widget Id
     */
    // @SuppressLint("NewApi")
    private void initWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new SlotMachineAdapter(MainActivity.this));

        SharedPreferences pref = this.getSharedPreferences("MyPrefSave", MODE_PRIVATE);
        String frq = pref.getString("radio_frq", "151");
        for (int i = 0; i < GetJsonData.frequency.size(); i++) {
            if (GetJsonData.frequency.get(i).freq.equals(frq)) {
                array_postion = i;
            }
        }

        wheel.setCurrentItem(array_postion);
        wheel.addChangingListener(changedListener);
        wheel.addScrollingListener(scrolledListener);
        wheel.setCyclic(true);
        wheel.setEnabled(true);
        wheel.setVertical(false);

        wheel.setInterpolator(new AnticipateOvershootInterpolator());
    }

    /**
     * Returns wheel by Id
     *
     * @param id the wheel Id
     * @return the wheel with passed Id
     */
    private WheelView getWheel(int id) {
        return (WheelView) findViewById(id);
    }

    /**
     * Mixes wheel
     *
     * @param id the wheel id
     */
    private void mixWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.scroll(-1, 500);
    }

    /**
     * Slot machine adapter
     */
    private class SlotMachineAdapter extends AbstractWheelAdapter {
        // Image size
        final int IMAGE_WIDTH = 150;
        final int IMAGE_HEIGHT = 80;
        LayoutInflater mInflater;
        int arr_size = GetJsonData.frequency.size();

        // Slot machine symbols
        private final int items[] = new int[arr_size];


        // Cached images
        private List<SoftReference<Bitmap>> images;

        // Layout inflater
        private Context context;

        /**
         * Constructor
         */
        public SlotMachineAdapter(Context context) {
            this.context = context;
            for (int i = 0; i < arr_size; i++) {
                items[i] = R.drawable.arrow1;
            }
            images = new ArrayList<SoftReference<Bitmap>>(items.length);
            for (int id : items) {
                images.add(new SoftReference<Bitmap>(loadImage(id)));
            }
        }

        /**
         * Loads image from resources
         */
        private Bitmap loadImage(int id) {
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
            Bitmap scaled = Bitmap.createScaledBitmap(bitmap, IMAGE_WIDTH, IMAGE_HEIGHT, true);
            bitmap.recycle();
            return scaled;
        }

        @Override
        public int getItemsCount() {
            return items.length;
        }

        // Layout params for image view
        final LayoutParams params = new LayoutParams(IMAGE_WIDTH, IMAGE_HEIGHT);

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            ImageView img;
            TextView mTextView;
            mInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mTextView = new TextView(context);
            if (cachedView != null) {
                img = (ImageView) cachedView;

            } else {
                img = new ImageView(context);

            }
            img.setLayoutParams(params);
            SoftReference<Bitmap> bitmapRef = images.get(index);
            Bitmap bitmap = bitmapRef.get();
            if (bitmap == null) {
                bitmap = loadImage(items[index]);
                images.set(index, new SoftReference<Bitmap>(bitmap));
            }
            mTextView.setText(GetJsonData.frequency.get(index).freq + "");
            mTextView.setTextColor(Color.WHITE);

            img.setImageBitmap(bitmap);

//            cachedView = mInflater.inflate(R.layout.row_radio, parent,
//					false);
//            
//            TextView row_tv_name;
//            row_tv_name = (TextView) cachedView.findViewById(R.id.row_tv_name);
//            row_tv_name.setText(GetJsonData.frequency.get(index).freq);
            return img;
        }

    }

    public void playAlertTone(final Context context) {

        Thread t = new Thread() {
            public void run() {
                MediaPlayer player = null;
                int countBeep = 0;
                while (countBeep < 2) {
                    player = MediaPlayer.create(context,
                            R.raw.facebook_ringtone_pop);
                    player.start();
                    countBeep += 1;
                    try {

                        // 100 milisecond is duration gap between two beep
                        Thread.sleep(player.getDuration() + 100);
                        player.release();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();

    }

    public void vibration() {

        Vibrator v = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(7);

    }

}
