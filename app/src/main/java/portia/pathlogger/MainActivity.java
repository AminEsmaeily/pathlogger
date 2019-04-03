package portia.pathlogger;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import portia.pathlogger.exception.ExceptionBase;
import portia.pathlogger.fragments.CurrentLocationFragment;
import portia.pathlogger.fragments.FragmentBase;
import portia.pathlogger.fragments.MapViewFragment;
import portia.pathlogger.model.ModelBase;
import portia.pathlogger.provider.LocationInformationHandler;

public class MainActivity extends AppCompatActivity {

    private PathLogger pathLogger;
    private final String TAG = "MainActivity";
    private final int locationRequestCode = 10005;
    private ViewPager mViewPager;
    private List<FragmentBase> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        startLogger();
    }

    private void startLogger(){
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED /*||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED*/) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE/*,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE*/},
                        locationRequestCode);
            }
            return;
        }

        if(pathLogger == null) {
            try {
                pathLogger = new PathLogger(getApplicationContext(), new LocationInformationHandler() {
                    @Override
                    public void OnInformationReceived(ModelBase information) {
                        FragmentPagerAdapter adapter = (FragmentPagerAdapter) mViewPager.getAdapter();
                        for(int i = 0; i < adapter.getCount(); i++){
                            FragmentBase fragment = (FragmentBase)adapter.getItem(i);
                            if(fragment != null)
                                fragment.onLocationReceived(information);
                        }
                    }
                });
            } catch (ExceptionBase exceptionBase) {
                Log.e(TAG, "onCreate: Error in creating an instance of PathLogger", exceptionBase);
                finish();
            }
        }

        try {
            pathLogger.start();
        } catch (ExceptionBase exceptionBase) {
            Log.e(TAG, "Error in starting PathLogger", exceptionBase);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == locationRequestCode){
            for (int result :
                    grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED){
                    new AlertDialog.Builder(this)
                            .setTitle("Application Permission")
                            .setMessage("This application needs permissions that you have denied. It cannot continue.")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finish();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return;
                }
            }
        }

        startLogger();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            if(fragments == null)
                fragments = new ArrayList<>(getCount());
        }

        @Override
        public Fragment getItem(int position) {
            try {
                switch (position) {
                    case 0:
                        return getFragmentByType(CurrentLocationFragment.class.getName());
                    case 1:
                        return getFragmentByType(MapViewFragment.class.getName());
                    default:
                        return null;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }

            return null;
        }

        private FragmentBase getFragmentByType(String fragmentName) throws ClassNotFoundException,
                IllegalAccessException, InstantiationException {
            if (fragments != null)
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i).getClass().getName().equals(fragmentName))
                        return fragments.get(i);
                }

            FragmentBase fragmentBase = (FragmentBase) Class.forName(fragmentName).newInstance();
            fragments.add(fragmentBase);
            return fragmentBase;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
