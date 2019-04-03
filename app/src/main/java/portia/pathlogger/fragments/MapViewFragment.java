package portia.pathlogger.fragments;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import portia.pathlogger.R;
import portia.pathlogger.model.GPSModel;
import portia.pathlogger.model.ModelBase;

public class MapViewFragment extends FragmentBase {

    private MapView map;
    private boolean centered = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        View view =inflater.inflate(R.layout.fragment_map_view, container, false);
        map = view.findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);

        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(getContext(), map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);
        map.setMultiTouchControls(true);

        MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(map);
        myLocationoverlay.enableFollowLocation();
        myLocationoverlay.enableMyLocation();
        map.getOverlays().add(myLocationoverlay);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onLocationReceived(ModelBase location) {
        if(location instanceof GPSModel) {
            IMapController mapController = map.getController();
            GeoPoint startPoint = new GeoPoint(((GPSModel)location).getLatitude(), ((GPSModel)location).getLongitude(), ((GPSModel)location).getAltitude());
            if(!centered) {
                mapController.setZoom(9.5);
                mapController.setCenter(startPoint);
                centered = true;
            }
        }
    }
}
