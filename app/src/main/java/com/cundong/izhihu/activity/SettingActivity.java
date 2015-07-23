package com.cundong.izhihu.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cundong.izhihu.Constants;
import com.cundong.izhihu.R;

/**
 * Created by lee on 15/7/21.
 */
public class SettingActivity extends AppCompatActivity {
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
                showAboutDialog();
                return true;
            }

            return false;
        }

        private void showAboutDialog() {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_about);

            TextView textView = (TextView) dialog.findViewById(R.id.dialog_text);
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

//            if (isVersion) {
//                String data = getResources().getString(R.string.setting_aboutme_version);
//
//                data = String.format(data,
//                        PhoneUtils.getApplicationName(this),
//                        PhoneUtils.getPackageInfo(this).versionName);
//
//                textView.setText(data);
//            } else {

            String title = getResources().getString(R.string.app_name) + "<br>";
            String subTitle = getResources().getString(R.string.app_sub_name) + "<br>";
            String gitHubUrl = "<a href='" + Constants.PROJECT_URL + "'>" + Constants.PROJECT_URL + "</a>";
            String data = title + subTitle + gitHubUrl;

            textView.setText(Html.fromHtml(data));
            textView.setMovementMethod(LinkMovementMethod.getInstance());

            dialog.show();
        }
    }
}
