package com.socialwatch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView internetStatus;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable internetChecker = new Runnable() {
        @Override
        public void run() {
            updateInternetStatus();
            handler.postDelayed(this, 1500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
        mainLayout.setPadding(dp(14), dp(8), dp(14), dp(6));

        FrameLayout.LayoutParams mainParams =
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                );

        root.addView(mainLayout, mainParams);

        // Header
        ImageView header = new ImageView(this);
        header.setImageResource(R.drawable.header);
        header.setAdjustViewBounds(true);
        header.setScaleType(ImageView.ScaleType.FIT_CENTER);

        mainLayout.addView(header,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                ));

        // YouTube
        ImageButton youtube = createImageButton(R.drawable.youtube_button);
        mainLayout.addView(youtube);

        youtube.setOnClickListener(v ->
                openService(Config.DEFAULT_YOUTUBE_URL)
        );

        // Facebook
        ImageButton facebook = createImageButton(R.drawable.facebook_button);
        mainLayout.addView(facebook);

        facebook.setOnClickListener(v ->
                openService(Config.DEFAULT_FACEBOOK_URL)
        );

        // TikTok
        ImageButton tiktok = createImageButton(R.drawable.tiktok_button);
        mainLayout.addView(tiktok);

        tiktok.setOnClickListener(v ->
                openService(Config.DEFAULT_TIKTOK_URL)
        );

        // About button
        Button about = new Button(this);
        about.setText("ABOUT");
        about.setTextSize(13);
        about.setAllCaps(false);
        about.setPadding(0, 0, 0, 0);

        LinearLayout.LayoutParams aboutParams =
                new LinearLayout.LayoutParams(
                        dp(105),
                        dp(40)
                );
        aboutParams.gravity = Gravity.CENTER_HORIZONTAL;
        aboutParams.topMargin = dp(2);

        mainLayout.addView(about, aboutParams);

        about.setOnClickListener(v ->
                startActivity(new android.content.Intent(
                        MainActivity.this,
                        AboutActivity.class
                ))
        );

        // Internet status
        internetStatus = new TextView(this);
        internetStatus.setTextSize(10);
        internetStatus.setGravity(Gravity.CENTER);
        internetStatus.setTypeface(null, android.graphics.Typeface.BOLD);
        internetStatus.setBackgroundColor(Color.WHITE);
        internetStatus.setPadding(dp(2), 0, dp(2), 0);

        FrameLayout.LayoutParams statusParams =
                new FrameLayout.LayoutParams(
                        dp(118),
                        dp(28),
                        Gravity.TOP | Gravity.RIGHT
                );

        statusParams.rightMargin = dp(8);
        statusParams.topMargin = dp(3);

        root.addView(internetStatus, statusParams);

        setContentView(root);

        updateInternetStatus();
        handler.postDelayed(internetChecker, 1500);
    }

    private ImageButton createImageButton(int imageResource) {

        ImageButton button = new ImageButton(this);

        button.setImageResource(imageResource);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setAdjustViewBounds(true);
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setPadding(0, 0, 0, 0);
        button.setContentDescription("Social media button");

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        params.topMargin = dp(2);
        params.bottomMargin = dp(2);

        button.setLayoutParams(params);

        return button;
    }

    private void updateInternetStatus() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE
                );

        boolean connected = false;

        if (connectivityManager != null) {

            NetworkInfo networkInfo =
                    connectivityManager.getActiveNetworkInfo();

            connected = networkInfo != null
                    && networkInfo.isConnected();
        }

        if (connected) {
            internetStatus.setText("Internet: AVAILABLE");
            internetStatus.setTextColor(Color.rgb(0, 150, 0));
        } else {
            internetStatus.setText("Internet: UNAVAILABLE");
            internetStatus.setTextColor(Color.RED);
        }
    }

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(internetChecker);
        super.onDestroy();
    }

    private void openService(String url) {
        android.content.Intent intent =
                new android.content.Intent(
                        MainActivity.this,
                        WebActivity.class
                );

        intent.putExtra("url", url);
        startActivity(intent);
    }
}
