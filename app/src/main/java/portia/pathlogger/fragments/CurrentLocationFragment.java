package portia.pathlogger.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import portia.pathlogger.R;
import portia.pathlogger.model.CellTowerModel;
import portia.pathlogger.model.GPSModel;
import portia.pathlogger.model.ModelBase;
import portia.pathlogger.provider.CellTower;

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
    private TableLayout towersView;
    private List<CellTowerModel> towers;

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
        towersView = view.findViewById(R.id.towersView);
        towers = new ArrayList<>();
        createTableHeaderColumns();
        return view;
    }

    @Override
    public void onLocationReceived(GPSModel location) {
        txtAccuracy.setText(String.valueOf(((GPSModel) location).getAccuracy()));
        txtAltitude.setText(String.valueOf(((GPSModel) location).getAltitude()));
        txtLatitude.setText(String.valueOf(((GPSModel) location).getLatitude()));
        txtLongitude.setText(String.valueOf(((GPSModel) location).getLongitude()));
        txtSpeed.setText(String.valueOf(((GPSModel) location).getSpeed()));
        txtTime.setText(String.valueOf(((GPSModel) location).getTime()));
    }

    @Override
    public void onLocationReceived(List<CellTowerModel> location) {
        if(getContext() == null)
            return;

        if(towers == null)
            towers = new ArrayList<>();
        boolean exists;
        for (CellTowerModel model :
                location) {
            exists = false;
            for(CellTowerModel model2: towers) {
                if (model.getCellId() == model2.getCellId()) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                addTowerRow(model);
                towers.add(model);
            }
        }
    }

    private void createTableHeaderColumns(){
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView textCellId = new TextView(getContext());
        textCellId.setText("Cell Id");
        textCellId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textCellId.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellId);

        TextView textCellType = new TextView(getContext());
        textCellType.setText("Cell Type");
        textCellType.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textCellType.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellType);

        TextView textTAC = new TextView(getContext());
        textTAC.setText("TAC");
        textTAC.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textTAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textTAC);

        TextView textLAC = new TextView(getContext());
        textLAC.setText("LAC");
        textLAC.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textLAC);

        TextView textDBM = new TextView(getContext());
        textDBM.setText("DBM");
        textDBM.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textDBM.setPadding(5, 5, 5, 0);
        tableRow.addView(textDBM);

        TextView textRSSI = new TextView(getContext());
        textRSSI.setText("RSSI");
        textRSSI.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textRSSI.setPadding(5, 5, 5, 0);
        tableRow.addView(textRSSI);

        TextView textLatitude = new TextView(getContext());
        textLatitude.setText("Latitude");
        textLatitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLatitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLatitude);

        TextView textLongitude = new TextView(getContext());
        textLongitude.setText("Longitude");
        textLongitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLongitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLongitude);

        TextView textAltitude = new TextView(getContext());
        textAltitude.setText("Altitude");
        textAltitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textAltitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textAltitude);

        towersView.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // Add Divider
        tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        textCellId = new TextView(getContext());
        textCellId.setText("-----------");
        textCellId.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textCellId.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellId);

        textCellType = new TextView(getContext());
        textCellType.setText("-----------");
        textCellType.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textCellType.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellType);

        textTAC = new TextView(getContext());
        textTAC.setText("-----------");
        textTAC.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textTAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textTAC);

        textLAC = new TextView(getContext());
        textLAC.setText("-----------");
        textLAC.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textLAC);

        textDBM = new TextView(getContext());
        textDBM.setText("-----------");
        textDBM.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textDBM.setPadding(5, 5, 5, 0);
        tableRow.addView(textDBM);

        textRSSI = new TextView(getContext());
        textRSSI.setText("-----------");
        textRSSI.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textRSSI.setPadding(5, 5, 5, 0);
        tableRow.addView(textRSSI);

        textLatitude = new TextView(getContext());
        textLatitude.setText("-----------");
        textLatitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLatitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLatitude);

        textLongitude = new TextView(getContext());
        textLongitude.setText("-----------");
        textLongitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textLongitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLongitude);

        textAltitude = new TextView(getContext());
        textAltitude.setText("-------------------------");
        textAltitude.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        textAltitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textAltitude);

        towersView.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }

    private void addTowerRow(CellTowerModel model){
        TableRow tableRow = new TableRow(getContext());
        tableRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TextView textCellId = new TextView(getContext());
        textCellId.setText(String.valueOf(model.getCellId()));
        textCellId.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellId);

        TextView textCellType = new TextView(getContext());
        textCellType.setText(model.getCellType());
        textCellType.setPadding(5, 5, 5, 0);
        tableRow.addView(textCellType);

        TextView textTAC = new TextView(getContext());
        textTAC.setText(String.valueOf(model.getTAC()));
        textTAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textTAC);

        TextView textLAC = new TextView(getContext());
        textLAC.setText(String.valueOf(model.getLAC()));
        textLAC.setPadding(5, 5, 5, 0);
        tableRow.addView(textLAC);

        TextView textDBM = new TextView(getContext());
        textDBM.setText(String.valueOf(model.getDBM()));
        textDBM.setPadding(5, 5, 5, 0);
        tableRow.addView(textDBM);

        TextView textRSSI = new TextView(getContext());
        textRSSI.setText(String.valueOf(model.getRSSI()));
        textRSSI.setPadding(5, 5, 5, 0);
        tableRow.addView(textRSSI);

        TextView textLatitude = new TextView(getContext());
        textLatitude.setText("0.0");
        textLatitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLatitude);

        TextView textLongitude = new TextView(getContext());
        textLongitude.setText("0.0");
        textLongitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textLongitude);

        TextView textAltitude = new TextView(getContext());
        textAltitude.setText("0.0");
        textAltitude.setPadding(5, 5, 5, 0);
        tableRow.addView(textAltitude);

        towersView.addView(tableRow, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }
}