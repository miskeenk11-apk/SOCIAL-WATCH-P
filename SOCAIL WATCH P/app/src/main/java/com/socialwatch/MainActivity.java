package com.socialwatch;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int dp(float v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private ImageButton imageButton(int drawableId, String description) {
        ImageButton b = new ImageButton(this);
        b.setImageResource(drawableId);
        b.setScaleType(ImageView.ScaleType.FIT_CENTER);
        b.setAdjustViewBounds(true);
        b.setBackgroundColor(Color.TRANSPARENT);
        b.setPadding(0, 0, 0, 0);
        b.setContentDescription(description);
        b.setFocusable(true);
        return b;
    }

    private void addGap(LinearLayout root, int heightDp) {
        Space gap = new Space(this);
        root.addView(gap, new LinearLayout.LayoutParams(-1, dp(heightDp)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(4,4,4));
        getWindow().setNavigationBarColor(Color.BLACK);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(18), dp(10), dp(18), dp(10));
        root.setBackgroundColor(Color.BLACK);

        // Header and three buttons are cropped from the supplied reference image,
        // preserving its exact visual artwork.
        ImageView header = new ImageView(this);
        header.setImageResource(R.drawable.header);
        header.setScaleType(ImageView.ScaleType.FIT_CENTER);
        root.addView(header, new LinearLayout.LayoutParams(-1, dp(175)));

        addGap(root, 5);

        ImageButton youtube = imageButton(R.drawable.youtube_button, "YouTube Shorts");
        root.addView(youtube, new LinearLayout.LayoutParams(-1, dp(205)));
        youtube.setOnClickListener(v -> openService("YouTube", Config.DEFAULT_YOUTUBE_URL));

        addGap(root, 14);

        ImageButton facebook = imageButton(R.drawable.facebook_button, "Facebook Reels");
        root.addView(facebook, new LinearLayout.LayoutParams(-1, dp(205)));
        facebook.setOnClickListener(v -> openService("Facebook", Config.DEFAULT_FACEBOOK_URL));

        addGap(root, 14);

        ImageButton tiktok = imageButton(R.drawable.tiktok_button, "TikTok Short Videos");
        root.addView(tiktok, new LinearLayout.LayoutParams(-1, dp(205)));
        tiktok.setOnClickListener(v -> openService("TikTok", Config.DEFAULT_TIKTOK_URL));

        Space spacer = new Space(this);
        root.addView(spacer, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView about = new TextView(this);
        about.setText("About");
        about.setTextColor(Color.LTGRAY);
        about.setTextSize(14);
        about.setGravity(Gravity.CENTER);
        about.setBackgroundResource(R.drawable.bg_about);
        about.setFocusable(true);
        about.setOnClickListener(v -> startActivity(new Intent(this, AboutActivity.class)));
        root.addView(about, new LinearLayout.LayoutParams(dp(105), dp(46)));

        setContentView(root);
    }

    private void openService(String service, String url) {
        Intent i = new Intent(this, WebActivity.class);
        i.putExtra(WebActivity.EXTRA_SERVICE, service);
        i.putExtra(WebActivity.EXTRA_URL, url);
        startActivity(i);
    }
}
