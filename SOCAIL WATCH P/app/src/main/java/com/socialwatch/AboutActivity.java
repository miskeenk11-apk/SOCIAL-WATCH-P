package com.socialwatch;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    private int dp(float v) {
        return (int) (v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView make(String s, float size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setGravity(Gravity.CENTER_HORIZONTAL);
        t.setPadding(dp(12), dp(8), dp(12), dp(8));
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.BLACK);
        getWindow().setNavigationBarColor(Color.BLACK);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22), dp(20), dp(22), dp(20));
        root.setBackgroundColor(Color.BLACK);

        TextView title = make("SOCIAL WATCH", 30, Color.rgb(0,150,245), true);
        root.addView(title, new LinearLayout.LayoutParams(-1, dp(70)));

        TextView body = make(
            "Short Video Browser\n\n" +
            "SOCIAL WATCH ایک سادہ اور lightweight app ہے جس کا مقصد " +
            "صرف Short Videos تک آسان رسائی دینا ہے۔\n\n" +
            "YouTube → Shorts\n" +
            "Facebook → Reels / Short Videos\n" +
            "TikTok → Short Videos\n\n" +
            "Main screen میں کوئی عام browser نہیں ہے۔ ہر button اپنے " +
            "متعلقہ platform کو app کے اندر کھولتا ہے۔ YouTube اور Facebook " +
            "Android WebView میں چلتے ہیں، جبکہ TikTok کو desktop-style view " +
            "میں کھولا جاتا ہے۔\n\n" +
            "یہ app پرانے Android devices کے لیے lightweight رکھا گیا ہے۔ " +
            "App کی minimum Android compatibility API 21 (Android 5.0) ہے۔\n\n" +
            "نوٹ: YouTube, Facebook اور TikTok اپنی websites کی compatibility " +
            "خود control کرتے ہیں، اس لیے بہت پرانے WebView پر کچھ features " +
            "محدود ہو سکتے ہیں۔",
            17, Color.WHITE, false
        );
        body.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        root.addView(body, new LinearLayout.LayoutParams(-1, 0, 1));

        TextView back = make("←  Back", 16, Color.WHITE, true);
        back.setBackgroundResource(com.socialwatch.R.drawable.bg_about);
        back.setOnClickListener(v -> finish());
        root.addView(back, new LinearLayout.LayoutParams(dp(120), dp(50)));

        setContentView(root);
    }
}
