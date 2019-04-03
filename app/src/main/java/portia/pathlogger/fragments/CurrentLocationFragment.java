package portia.pathlogger.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import portia.pathlogger.R;
import portia.pathlogger.model.GPSModel;
import portia.pathlogger.model.ModelBase;

public class CurrentLocationFragment extends FragmentBase {
    public static CurrentLocationFragment newInstance() {
        CurrentLocationFragment fragment = new CurrentLocationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView txtLatitude;
    private TextView txtAltitude;
    private TextView txtLongitude;
    private TextView txtAccuracy;
    private TextView txtSpeed;
    private TextView txtTime;

    public CurrentLocationFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_location, container, false);
        txtAccuracy = view.findViewById(R.id.txtAccuracy);
        txtAltitude = view.findViewById(R.id.txtAltitude);
        txtLatitude = view.findViewById(R.id.txtLatitude);
        txtLongitude = view.findViewById(R.id.txtLongitude);
        txtSpeed = view.findViewById(R.id.txtSpeed);
        txtTime = view.findViewById(R.id.txtTime);
        return view;
    }

    @Override
    public void onLocationReceived(ModelBase location) {
        if(location instanceof GPSModel){
            txtAccuracy.setText(String.valueOf(((GPSModel)location).getAccuracy()));
            txtAltitude.setText(String.valueOf(((GPSModel)location).getAltitude()));
            txtLatitude.setText(String.valueOf(((GPSModel)location).getLatitude()));
            txtLongitude.setText(String.valueOf(((GPSModel)location).getLongitude()));
            txtSpeed.setText(String.valueOf(((GPSModel)location).getSpeed()));
            txtTime.setText(String.valueOf(((GPSModel)location).getTime()));
        }
    }
}