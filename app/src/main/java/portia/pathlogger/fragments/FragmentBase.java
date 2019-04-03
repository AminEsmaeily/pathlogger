package portia.pathlogger.fragments;

import android.support.v4.app.Fragment;

import portia.pathlogger.model.ModelBase;

public abstract class FragmentBase extends Fragment {
    public abstract void onLocationReceived(ModelBase location);
}
