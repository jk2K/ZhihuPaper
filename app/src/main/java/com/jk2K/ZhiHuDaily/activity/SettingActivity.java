package com.jk2K.ZhiHuDaily.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.jk2K.ZhiHuDaily.Constants;
import com.jk2K.ZhiHuDaily.R;

/**
 * Created by lee on 15/7/21.
 */
public class SettingActivity extends AppCompatActivity {
    public static final String KEY_PREF_SPLASH_AUTHOR = "splashAuthor";
    public static final String KEY_PREF_SPLASH_IMAGE = "splashImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment())
                .commit();
    }

    public static class SettingFragment extends PreferenceFragment
            implements Preference.OnPreferenceClickListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.preferences);

            // Register PreferenceClickListener
            findPreference("about").setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equals("about")) {
                MyDialogFragment myDialogFragment = new MyDialogFragment();
                myDialogFragment.show(getFragmentManager(), "dialog_about");
                return true;
            }

            return false;
        }
    }

    public static class MyDialogFragment extends DialogFragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = inflater.inflate(R.layout.fragment_dialog, container, false);
            TextView textView = (TextView) v.findViewById(R.id.dialog_text);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // 点击文本让关于我们的对话框消失
                    dismiss();;
                }
            });

            String title = getResources().getString(R.string.app_name) + "<br>";
            String subTitle = getResources().getString(R.string.app_sub_name) + "<br>";
            String gitHubUrl = "<a href='" + Constants.PROJECT_URL + "'>" + Constants.PROJECT_URL + "</a>";
            String data = title + subTitle + gitHubUrl;

            textView.setText(Html.fromHtml(data));
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            return v;
        }
    }
}
