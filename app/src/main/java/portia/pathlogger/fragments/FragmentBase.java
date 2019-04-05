package portia.pathlogger.fragments;

import android.support.v4.app.Fragment;

import java.util.List;

import portia.pathlogger.model.CellTowerModel;
import portia.pathlogger.model.GPSModel;
import portia.pathlogger.model.ModelBase;

public abstract class FragmentBase extends Fragment {
    public abstract void onLocationReceived(GPSModel location);
    public abstract void onLocationReceived(List<CellTowerModel> location);
}
