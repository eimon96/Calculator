package com.e.calculator;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * MediaPlayerClass: Αναπαραγωγή μουσικής με mediaplayer
 */
public class MediaPlayerClass
{
    private static MediaPlayer mediaPlayer;

    /**
     * Αρχικοποίηση MediaPlayer
     * @param mContext
     */
    public static void initPlayer(Context mContext)
    {
        try
        {
            Uri mUri = Uri.parse("android.resource://com.eimon.calculator/" + R.raw.nightowle);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioAttributes(
                    new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
            );
            mediaPlayer.setDataSource(mContext, mUri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Παύση αναπαραγωγής
     */
    public static void pausePlayer()
    {
        try
        {
            mediaPlayer.pause();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Συνέχεια αναπαραγωγής
     */
    public static void resumePlayer()
    {
        try
        {
            mediaPlayer.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Αποδέσμευση MediaPlayer
     */
    public static void releasePlayer()
    {
        try
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Συμπεριφορά λούπας
     */
    public static void initLoopBehavior()
    {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            /**
             * Καλείται στην ολοκλήρωση της αναπαραγωγής.
             * Ξαναρχιζει την αναπαραγωγή του τραγουδιού από την αρχή.
             * @param mp: MediaPlayer
             */
            @Override
            public void onCompletion(MediaPlayer mp)
            {
                try
                {
                    mediaPlayer.start();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
