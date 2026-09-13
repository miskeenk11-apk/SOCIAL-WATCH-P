package com.socialwatch;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private int dp(int value) {
        return (int) (value *
                getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(dp(18), dp(12), dp(18), dp(12));
        mainLayout.setBackgroundColor(Color.WHITE);

        TextView title = new TextView(this);
        title.setText("SOCIAL WATCH");
        title.setTextSize(24);
        title.setTextColor(Color.BLACK);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, android.graphics.Typeface.BOLD);

        mainLayout.addView(title,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(55)
                ));

        ScrollView scrollView = new ScrollView(this);

        TextView info = new TextView(this);
        info.setTextSize(16);
        info.setTextColor(Color.DKGRAY);
        info.setLineSpacing(dp(2), 1.0f);

        info.setText(
                "SOCIAL WATCH\n\n" +

                "یہ ایپ YouTube Shorts، Facebook Reels اور TikTok " +
                "کی مختصر ویڈیوز کو ایک آسان جگہ سے دیکھنے کے لیے بنائی گئی ہے۔\n\n" +

                "استعمال کرنے کا طریقہ:\n" +
                "1. Main Screen پر YouTube، Facebook یا TikTok کا بٹن دبائیں۔\n" +
                "2. منتخب کیا گیا پلیٹ فارم کھل جائے گا۔\n" +
                "3. ویڈیوز دیکھنے کے لیے Swipe کر سکتے ہیں۔\n\n" +

                "Internet:\n" +
                "اس ایپ کو ویڈیوز چلانے کے لیے Internet Connection ضروری ہے۔ " +
                "Wi-Fi یا Mobile Data موجود ہونے پر اوپر Internet: AVAILABLE نظر آئے گا۔ " +
                "Internet نہ ہونے پر Internet: UNAVAILABLE نظر آئے گا۔\n\n" +

                "Installation:\n" +
                "APK فائل اپنے Android فون میں منتقل کریں، APK پر tap کریں " +
                "اور Install منتخب کریں۔ اگر Android security warning دکھائے " +
                "تو صرف اسی صورت میں Install کریں جب APK قابلِ اعتماد source سے حاصل کی گئی ہو۔\n\n" +

                "Compatibility:\n" +
                "یہ ایپ Android 5.0 (API 21) اور اس سے اوپر کے ورژنز کے لیے تیار کی گئی ہے۔ " +
                "YouTube، Facebook اور TikTok کی اپنی website/app compatibility الگ ہو سکتی ہے۔\n\n" +

                "Future Plans:\n" +
                "ہم مستقبل میں SOCIAL WATCH کو مزید powerful اور بہتر بنانے کی کوشش کریں گے۔ " +
                "مزید features، بہتر design اور آسان استعمال شامل کیے جا سکتے ہیں۔\n\n" +

                "Thank you for using SOCIAL WATCH!"
        );

        scrollView.addView(info);

        mainLayout.addView(scrollView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1
                ));

        Button backButton = new Button(this);
        backButton.setText("← BACK");
        backButton.setTextSize(14);
        backButton.setAllCaps(false);

        mainLayout.addView(backButton,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        dp(48)
                ));

        backButton.setOnClickListener(v -> finish());

        setContentView(mainLayout);
    }
}
