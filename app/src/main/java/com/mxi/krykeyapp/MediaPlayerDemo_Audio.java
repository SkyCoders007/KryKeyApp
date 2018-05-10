/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mxi.krykeyapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import io.vov.vitamio.MediaPlayer;

public class MediaPlayerDemo_Audio {

    private static final String TAG = "MediaPlayerDemo";
    private MediaPlayer mMediaPlayer;
    private String path;
    public Context mContext;
    String station, name, rtmp_id, membershipkey;
    CommanClass cc;

    public void playAudio(final Context mContext, final String station,
                          final String name, final String rtmp_id, final String membershipkey) {
        this.mContext = mContext;
        this.name = name;
        this.station = station;
        this.rtmp_id = rtmp_id;
        this.membershipkey = membershipkey;
        cc = new CommanClass(mContext);

        try {

            path = station + "/";
            path += name + "";

            //Log.e("station link##", path);
            if (mMediaPlayer != null) {
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                } catch (IllegalStateException e) {
                    //  e.printStackTrace();
                }
            }
            try {
                mMediaPlayer = new io.vov.vitamio.MediaPlayer(mContext);
                mMediaPlayer.setDataSource(path);
                mMediaPlayer.prepareAsync();
                // Log.e("station link", path);
            } catch (IOException e) {
                // e.printStackTrace();
            } catch (IllegalArgumentException e) {
                //  e.printStackTrace();
            } catch (SecurityException e) {
                // e.printStackTrace();
            } catch (IllegalStateException e) {
                // e.printStackTrace();
            }
            mMediaPlayer.setOnPreparedListener(new io.vov.vitamio.MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(io.vov.vitamio.MediaPlayer mp) {
                    Log.e("XXX", ">>>>>>>>>>>>>>>>>>>>>>>> onPrepared: mp.start();");
                    mp.start();
                }
            });

            mMediaPlayer.setOnInfoListener(new io.vov.vitamio.MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(io.vov.vitamio.MediaPlayer mp, int what, int extra) {
                    switch (what) {
                        case io.vov.vitamio.MediaPlayer.MEDIA_INFO_FILE_OPEN_OK: // line added 1
                            Log.e("XXX", ">>>>>>>>>>>>>>>>>>>>>>>> onInfo: MEDIA_INFO_FILE_OPEN_OK");
                            long buffersize = mMediaPlayer.audioTrackInit(); // line added 2
                            mMediaPlayer.audioInitedOk(buffersize); // line added 3

                            break;
                    }
                    return true;
                }
            });
            mMediaPlayer.setOnCompletionListener(new io.vov.vitamio.MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    //if (MainActivity.ply_tag == 1) {
                    new StreamMusic().execute();
                    //  MainActivity.ply_tag = 0;
                    // }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopReadio() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.pause();
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();
                }
            }
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "error: " + e.getMessage());
        }
    }

    public class SaveListen extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            MainActivity.gjdata.SaveListen("SaveListen", rtmp_id, membershipkey);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
        }

    }

    public class StreamMusic extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            // TODO Auto-generated method stub
            MainActivity.medioPlayer.playAudio(mContext, station, name, rtmp_id, membershipkey);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            new SaveListen().execute();
        }
    }

}
