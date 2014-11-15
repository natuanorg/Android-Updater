package org.natuan.androidupdater;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.natuan.androidupdaterlibrary.UpdateFormat;
import org.natuan.androidupdaterlibrary.UpdateManager;
import org.natuan.androidupdaterlibrary.UpdateOptions;
import org.natuan.androidupdaterlibrary.UpdatePeriod;

public class MainActivity extends ActionBarActivity {
    static String str = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        PackageInfo pinfo = null;
        try {
            pinfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Integer versionCode = pinfo.versionCode; // 1
        String versionName = pinfo.versionName; // 1.0
        str = new StringBuffer()
                .append(getText(R.string.click_to_check_update))
                .append("\n")
                .append("versionCode:")
                .append(versionCode)
                .append("\n")
                .append("versionName:")
                .append(versionName)
                .toString();

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.textview);
            if (textView != null && str != null) {
                textView.setText(str);
            }
            return rootView;
        }
    }

    public void onCheckUpdateClick(View v) {

        UpdateManager manager = new UpdateManager(this);

        UpdateOptions options = new UpdateOptions.Builder(this)
                .checkUrl("https://raw.github.com/snowdream/android-autoupdate/master/docs/test/updateinfo.xml")
                .updateFormat(UpdateFormat.XML)
                .updatePeriod(new UpdatePeriod(UpdatePeriod.EACH_TIME))
                .checkPackageName(true)
                .build();
//
//        UpdateOptions options = new UpdateOptions.Builder(this)
//                .checkUrl("https://raw.github.com/snowdream/android-autoupdate/master/docs/test/updateinfo.json")
//                .updateFormat(UpdateFormat.JSON)
//                .updatePeriod(new UpdatePeriod(UpdatePeriod.EACH_TIME))
//                .checkPackageName(true)
//                .build();

        manager.check(this, options);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_MENU){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
