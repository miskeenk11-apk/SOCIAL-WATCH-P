package com.socialwatch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.graphics.Color;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URLEncoder;

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
        mainLayout.setPadding(dp(10), dp(5), dp(10), dp(3));

        root.addView(mainLayout,
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                ));

        // Header
        ImageView header = new ImageView(this);
        header.setImageResource(R.drawable.header);
        header.setAdjustViewBounds(true);
        header.setScaleType(ImageView.ScaleType.FIT_CENTER);

        mainLayout.addView(header,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.65f
                ));

        // =========================
        // YouTube Section
        // =========================
        LinearLayout youtubeSection = createSection();

        EditText youtubeSearch = createSearchBox("Search YouTube...");
        youtubeSection.addView(youtubeSearch,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.75f
                ));

        ImageButton youtube =
                createImageButton(R.drawable.youtube_button);

        youtubeSection.addView(youtube,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.25f
                ));

        mainLayout.addView(youtubeSection,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.0f
                ));

        youtube.setOnClickListener(v ->
                openService("YouTube", Config.DEFAULT_YOUTUBE_URL)
        );

        youtubeSearch.setOnEditorActionListener((v, actionId, event) -> {
            searchYouTube(youtubeSearch);
            return true;
        });

        // =========================
        // Facebook Section
        // =========================
        LinearLayout facebookSection = createSection();

        EditText facebookSearch = createSearchBox("Search Facebook...");
        facebookSection.addView(facebookSearch,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.75f
                ));

        ImageButton facebook =
                createImageButton(R.drawable.facebook_button);

        facebookSection.addView(facebook,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.25f
                ));

        mainLayout.addView(facebookSection,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.0f
                ));

        facebook.setOnClickListener(v ->
                openService("Facebook", Config.DEFAULT_FACEBOOK_URL)
        );

        facebookSearch.setOnEditorActionListener((v, actionId, event) -> {
            searchFacebook(facebookSearch);
            return true;
        });

        // =========================
        // TikTok Section
        // =========================
        LinearLayout tiktokSection = createSection();

        EditText tiktokSearch = createSearchBox("Search TikTok...");
        tiktokSection.addView(tiktokSearch,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0.75f
                ));

        ImageButton tiktok =
                createImageButton(R.drawable.tiktok_button);

        tiktokSection.addView(tiktok,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.25f
                ));

        mainLayout.addView(tiktokSection,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1.0f
                ));

        tiktok.setOnClickListener(v ->
                openService("TikTok", Config.DEFAULT_TIKTOK_URL)
        );

        tiktokSearch.setOnEditorActionListener((v, actionId, event) -> {
            searchTikTok(tiktokSearch);
            return true;
        });

        // =========================
        // ABOUT
        // =========================
        Button about = new Button(this);
        about.setText("ABOUT");
        about.setTextSize(12);
        about.setAllCaps(false);
        about.setPadding(0, 0, 0, 0);

        LinearLayout.LayoutParams aboutParams =
                new LinearLayout.LayoutParams(
                        dp(100),
                        dp(34)
                );

        aboutParams.gravity = Gravity.CENTER_HORIZONTAL;

        mainLayout.addView(about, aboutParams);

        about.setOnClickListener(v ->
                startActivity(new android.content.Intent(
                        MainActivity.this,
                        AboutActivity.class
                ))
        );

        // =========================
        // Internet Status
        // =========================
        internetStatus = new TextView(this);
        internetStatus.setTextSize(10);
        internetStatus.setGravity(Gravity.CENTER);
        internetStatus.setTypeface(
                null,
                android.graphics.Typeface.BOLD
        );
        internetStatus.setBackgroundColor(Color.WHITE);
        internetStatus.setPadding(dp(2), 0, dp(2), 0);

        FrameLayout.LayoutParams statusParams =
                new FrameLayout.LayoutParams(
                        dp(125),
                        dp(27),
                        Gravity.TOP | Gravity.RIGHT
                );

        statusParams.rightMargin = dp(5);
        statusParams.topMargin = dp(2);

        root.addView(internetStatus, statusParams);

        setContentView(root);

        updateInternetStatus();
        handler.postDelayed(internetChecker, 1500);
    }

    // =========================
    // Create Section
    // =========================
    private LinearLayout createSection() {

        LinearLayout section = new LinearLayout(this);

        section.setOrientation(LinearLayout.VERTICAL);
        section.setGravity(Gravity.CENTER_HORIZONTAL);

        return section;
    }

    // =========================
    // Create Search Box
    // =========================
    private EditText createSearchBox(String hint) {

        EditText search = new EditText(this);

        search.setHint(hint);
        search.setTextSize(12);
        search.setSingleLine(true);
        search.setInputType(
                InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        );

        search.setPadding(
                dp(10),
                0,
                dp(10),
                0
        );

        search.setBackgroundColor(
                Color.rgb(245, 245, 245)
        );

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                );

        params.leftMargin = dp(5);
        params.rightMargin = dp(5);
        params.topMargin = dp(1);
        params.bottomMargin = dp(1);

        search.setLayoutParams(params);

        return search;
    }

    // =========================
    // Create Social Button
    // =========================
    private ImageButton createImageButton(int imageResource) {

        ImageButton button = new ImageButton(this);

        button.setImageResource(imageResource);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setAdjustViewBounds(true);
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setPadding(0, 0, 0, 0);
        button.setContentDescription("Social media button");

        return button;
    }

    // =========================
    // YouTube Search
    // =========================
    private void searchYouTube(EditText searchBox) {

        String query = searchBox.getText().toString().trim();

        if (query.isEmpty()) {
            return;
        }

        try {
            String encoded =
                    URLEncoder.encode(query, "UTF-8");

            openService(
                    "YouTube",
                    Config.YOUTUBE_SEARCH_BASE + encoded
            );

        } catch (Exception ignored) {
        }
    }

    // =========================
    // Facebook Search
    // =========================
    private void searchFacebook(EditText searchBox) {

        String query = searchBox.getText().toString().trim();

        if (query.isEmpty()) {
            return;
        }

        try {
            String encoded =
                    URLEncoder.encode(query, "UTF-8");

            openService(
                    "Facebook",
                    Config.FACEBOOK_SEARCH_BASE + encoded
            );

        } catch (Exception ignored) {
        }
    }

    // =========================
    // TikTok Search
    // =========================
    private void searchTikTok(EditText searchBox) {

        String query = searchBox.getText().toString().trim();

        if (query.isEmpty()) {
            return;
        }

        try {
            String encoded =
                    URLEncoder.encode(query, "UTF-8");

            openService(
                    "TikTok",
                    Config.TIKTOK_SEARCH_BASE + encoded
            );

        } catch (Exception ignored) {
        }
    }

    // =========================
    // Internet Status
    // =========================
    private void updateInternetStatus() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE
                );

        boolean connected = false;

        if (connectivityManager != null) {

            NetworkInfo networkInfo =
                    connectivityManager.getActiveNetworkInfo();

            connected =
                    networkInfo != null &&
                    networkInfo.isConnected();
        }

        if (connected) {

            internetStatus.setText(
                    "Internet: AVAILABLE"
            );

            internetStatus.setTextColor(
                    Color.rgb(0, 150, 0)
            );

        } else {

            internetStatus.setText(
                    "Internet: UNAVAILABLE"
            );

            internetStatus.setTextColor(
                    Color.RED
            );
        }
    }

    // =========================
    // DP
    // =========================
    private int dp(int value) {

        return (int) (
                value *
                getResources()
                        .getDisplayMetrics()
                        .density
                + 0.5f
        );
    }

    // =========================
    // Open Service
    // =========================
    private void openService(
            String service,
            String url
    ) {

        android.content.Intent intent =
                new android.content.Intent(
                        MainActivity.this,
                        WebActivity.class
                );

        intent.putExtra(
                WebActivity.EXTRA_SERVICE,
                service
        );

        intent.putExtra(
                WebActivity.EXTRA_URL,
                url
        );

        startActivity(intent);
    }

    // =========================
    // Destroy
    // =========================
    @Override
    protected void onDestroy() {

        handler.removeCallbacks(
                internetChecker
        );

        super.onDestroy();
    }
}
