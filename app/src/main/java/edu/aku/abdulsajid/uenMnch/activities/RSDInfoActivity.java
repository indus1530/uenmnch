package edu.aku.abdulsajid.uenMnch.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import edu.aku.abdulsajid.uenMnch.R;
import edu.aku.abdulsajid.uenMnch.RMOperations.crudOperations;
import edu.aku.abdulsajid.uenMnch.core.MainApp;
import edu.aku.abdulsajid.uenMnch.data.DAO.FormsDAO;
import edu.aku.abdulsajid.uenMnch.data.DAO.GetFncDAO;
import edu.aku.abdulsajid.uenMnch.data.entities.District;
import edu.aku.abdulsajid.uenMnch.data.entities.FacilityProvider;
import edu.aku.abdulsajid.uenMnch.data.entities.Forms;
import edu.aku.abdulsajid.uenMnch.data.entities.Tehsil;
import edu.aku.abdulsajid.uenMnch.data.entities.UCs;
import edu.aku.abdulsajid.uenMnch.databinding.ActivityRsdinfoBinding;
import edu.aku.abdulsajid.uenMnch.get.db.GetAllDBData;
import edu.aku.abdulsajid.uenMnch.get.db.GetIndDBData;
import edu.aku.abdulsajid.uenMnch.utils.DateUtils;
import edu.aku.abdulsajid.uenMnch.validation.ClearClass;
import edu.aku.abdulsajid.uenMnch.validation.validatorClass;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static edu.aku.abdulsajid.uenMnch.activities.LoginActivity.db;

public class RSDInfoActivity extends AppCompatActivity {
    private ActivityRsdinfoBinding bi;
    private final String mon = new SimpleDateFormat("MMM-yy").format(new Date().getTime());
    private List<String> reportingMonth, districtNames, districtCodes, tehsilName, tehsilCode, UcNames, ucCode, hfName, hfCode;
    private Map<String, FacilityProvider> facilityMap;
    private Map<String, String> tehsilMap;
    private Map<String, String> UcMap;
    private Map<String, String> FacilityProvider;
    public static Forms fc;
    private static final String TAG = RSDInfoActivity.class.getName();
    private String type;
    String month;

    Forms getForms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_rsdinfo);
        bi.setCallback(this);

        type = getIntent().getStringExtra(MainApp.FORM_TYPE);
//        this.setTitle(type.equals(MainApp.RSD) ? "ROUTINE SERVICE DELIVERY" : type.equals(MainApp.DHMT) ? "DHMT" : type.equals(MainApp.QOC) ? "QUALITY OF CARE" : "");
        this.setTitle(type.equals(MainApp.RSD) ? "DHIS Data-Validation Tools for Decision Making"
                : type.equals(MainApp.DHMT) ? "Performance Evaluation of District Team Meetings"
                : type.equals(MainApp.QOC) ? "Key Quality Indicator Tool for Health Facility" : "");

        MainApp.FORM_SUB_TYPE.clear();

        tempVisible(this);

        if (type.equals(MainApp.DHMT)) {
            ClearClass.ClearAllFields(bi.llrsdInfo01, null);
            ClearClass.ClearAllFields(bi.llrsdInfo03, null);
            ClearClass.ClearAllFields(bi.llpvt, null);
            ClearClass.ClearAllFields(bi.llpub, null);
            bi.llrsdInfo01.setVisibility(GONE);
            bi.llrsdInfo03.setVisibility(GONE);
            bi.llpvt.setVisibility(GONE);
            bi.llpub.setVisibility(GONE);
            bi.llrsdInfo02.setVisibility(VISIBLE);
        }


        if (!type.equals(MainApp.DHMT)) {
            ClearClass.ClearAllFields(bi.llrsdInfo02, null);
            ClearClass.ClearAllFields(bi.llrsdInfo03, null);
            ClearClass.ClearAllFields(bi.llpvt, null);
            ClearClass.ClearAllFields(bi.llpub, null);
            bi.llrsdInfo02.setVisibility(GONE);
            bi.llrsdInfo03.setVisibility(GONE);
            bi.llpvt.setVisibility(GONE);
            bi.llpub.setVisibility(GONE);
            bi.llrsdInfo01.setVisibility(VISIBLE);
        }


        bi.rGpp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                ClearClass.ClearAllFields(bi.llrsdInfo02, null);
                ClearClass.ClearAllFields(bi.llrsdInfo03, null);
                ClearClass.ClearAllFields(bi.llpvt, null);
                ClearClass.ClearAllFields(bi.llpub, null);
                bi.llrsdInfo02.setVisibility(GONE);
                bi.llrsdInfo03.setVisibility(GONE);
                bi.llpvt.setVisibility(GONE);
                bi.llpub.setVisibility(GONE);

                if (checkedId == bi.pub.getId()) {
                    bi.llrsdInfo02.setVisibility(VISIBLE);
                    bi.llpub.setVisibility(View.VISIBLE);
                } else if (checkedId == bi.pvt.getId()) {
                    bi.llrsdInfo02.setVisibility(VISIBLE);
                    bi.llrsdInfo03.setVisibility(VISIBLE);
                    bi.llpvt.setVisibility(View.VISIBLE);
                }

            }

        });


        bi.hfMtime.setTimeFormat("HH:mm");
        bi.hfMtime.setIs24HourView(true);
    }

    private void tempVisible(final Context context) {

        districtNames = new ArrayList<>();
        districtCodes = new ArrayList<>();

        districtNames.add("....");
        districtCodes.add("....");
        Collection<District> districts;
        try {
            districts = (Collection<District>) new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getAllDistricts").execute().get();

            if (districts != null) {
                for (District d : districts) {
                    districtNames.add(d.getDistrict_name());
                    districtCodes.add(d.getDistrict_code());
                }
                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(context,
                        android.R.layout.simple_spinner_dropdown_item, districtNames);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                bi.hfDistrict.setAdapter(dataAdapter);

            } else {
                Toast.makeText(this, "District not found!!", Toast.LENGTH_SHORT).show();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        if (!type.equals(MainApp.DHMT)) {
            bi.hfDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) return;

                    if (bi.pvt.isChecked()) {

                        tehsilName = new ArrayList<>();
                        tehsilCode = new ArrayList<>();
                        tehsilCode.add("....");
                        tehsilName.add("....");

                        Collection<Tehsil> tehsils;
                        try {
                            tehsils =
                                    (Collection<Tehsil>)
                                            new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getTehsil")
                                                    .execute(districtCodes.get(position)).get();

                            if (tehsils.size() != 0) {
                                for (Tehsil fp : tehsils) {
                                    tehsilName.add(fp.getTehsil_name());
                                    tehsilCode.add(fp.getTehsil_code());
                                }
                            }

                            bi.hfTehsil.setAdapter(new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_dropdown_item, tehsilName));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    } else if (bi.pub.isChecked()) {

                        hfName = new ArrayList<>();
                        hfCode = new ArrayList<>();
                        hfCode.add("....");
                        hfName.add("....");

                        Collection<FacilityProvider> hfp;
                        try {
                            hfp =
                                    (Collection<FacilityProvider>)
                                            new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getFacilityProvider")
                                                    .execute(districtCodes.get(position)).get();
                            if (hfp.size() != 0) {
                                for (FacilityProvider fp : hfp) {
                                    hfName.add(fp.getHf_name());
                                    hfCode.add(fp.getHf_uen_code());

                                }
                            }

                            bi.hfNamePublic.setAdapter(new ArrayAdapter<>(context,
                                    android.R.layout.simple_spinner_dropdown_item, hfName));

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            bi.hfTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) return;

                    UcNames = new ArrayList<>();
                    ucCode = new ArrayList<>();
                    ucCode.add("....");
                    UcNames.add("....");

                    Collection<UCs> ucs;
                    try {
                        ucs =
                                (Collection<UCs>)
                                        new GetAllDBData(db, GetFncDAO.class.getName(), "getFncDao", "getUCs")
                                                .execute(tehsilCode.get(position)).get();
                        if (ucs.size() != 0) {
                            for (UCs fp : ucs) {
                                UcNames.add(fp.getUc_name());
                                ucCode.add(fp.getUc_code());

                            }
                        }

                        bi.hfUc.setAdapter(new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_dropdown_item, UcNames));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        reportingMonth = new ArrayList<>();
        reportingMonth.add("....");
        reportingMonth.add(mon.toUpperCase());
        reportingMonth.add(DateUtils.getMonthsBack("MMM-yy", -1).toUpperCase());
        reportingMonth.add(DateUtils.getMonthsBack("MMM-yy", -2).toUpperCase());
        // Creating adapter for spinner
        ArrayAdapter<String> monAdapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, reportingMonth);
        // Drop down layout style - list view with radio button
        monAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        bi.reportMonth.setAdapter(monAdapter);
        bi.reportMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                month = bi.reportMonth.getSelectedItem().toString();
                setTitle(type.equals(MainApp.RSD) ? "DHIS Data-Validation Tools for Decision Making (" + bi.reportMonth.getSelectedItem() + ")"
                        : type.equals(MainApp.DHMT) ? "Performance Evaluation of District Team Meetings (" + bi.reportMonth.getSelectedItem() + ")"
                        : type.equals(MainApp.QOC) ? "Key Quality Indicator Tool for Health Facility (" + bi.reportMonth.getSelectedItem() + ")" : " ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public void BtnContinue() {

        if (formValidation()) {

            if (fc != null) {
                finish();
                startActivity(new Intent(RSDInfoActivity.this, type.equals(MainApp.QOC) ? Qoc1.class : type.equals(MainApp.DHMT) ? DHMT_MonitoringActivity.class : RsdMain.class));
                return;
            }

            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                finish();
                startActivity(new Intent(RSDInfoActivity.this, type.equals(MainApp.QOC) ? Qoc1.class : type.equals(MainApp.DHMT) ? DHMT_MonitoringActivity.class : RsdMain.class));

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        MainApp.endActivity(this, this, EndingActivity.class, false, RSDInfoActivity.fc);
    }

    public boolean formValidation() {
        if (!validatorClass.EmptyCheckingContainer(this, bi.llrsdInfo))
            return false;

        if (type.equals(MainApp.RSD)) {
            Object getData = null;
            if (bi.pub.isChecked()) {
                try {
                    getData = new GetIndDBData(db, GetFncDAO.class.getName(), "getFncDao", "getPendingPublicForm")
                            .execute(bi.reportMonth.getSelectedItem().toString(),
                                    districtCodes.get(bi.hfDistrict.getSelectedItemPosition()),
                                    hfCode.get(bi.hfNamePublic.getSelectedItemPosition())).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    getData = new GetIndDBData(db, GetFncDAO.class.getName(), "getFncDao", "getPendingPrivateForm")
                            .execute(bi.reportMonth.getSelectedItem().toString(),
                                    districtCodes.get(bi.hfDistrict.getSelectedItemPosition()),
                                    bi.hfName.getText().toString()).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (getData == null) {
                fc = null;
                return true;
            }

            if (((Forms) getData).getIstatus().equals("1")) {
                Toast.makeText(this, "Facility already filled for this Month", Toast.LENGTH_LONG).show();
                return false;
            }

            fc = (Forms) getData;

        }

        return true;

    }

    private void SaveDraft() throws JSONException {

        fc = new Forms();
        fc.setDevicetagID(MainApp.getTagName(this));
        fc.setFormType(type);
        fc.setAppversion(MainApp.versionName + "." + MainApp.versionCode);
        fc.setUsername(MainApp.userName);
        fc.setFormDate(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(new Date().getTime()));
        fc.setDeviceID(MainApp.deviceId);
        fc.setReportingMonth(bi.reportMonth.getSelectedItem().toString());
        fc.setDistrictCode(districtCodes.get(bi.hfDistrict.getSelectedItemPosition()));

        if (!type.equals(MainApp.DHMT)) {
            fc.setFacilityType(bi.pub.isChecked() ? "1" : bi.pvt.isChecked() ? "2" : "0");
            fc.setTehsilCode(bi.pvt.isChecked() ? tehsilCode.get(bi.hfTehsil.getSelectedItemPosition()) : "");
            fc.setUcCode(bi.pvt.isChecked() ? ucCode.get(bi.hfUc.getSelectedItemPosition()) : "");

            if (bi.pub.isChecked()) {
                fc.setFacilityCode(hfCode.get(bi.hfNamePublic.getSelectedItemPosition()));
            } else {
                fc.setFacilityCode(bi.hfName.getText().toString());
            }
        }

        setGPS(fc); // Set GPS
    }

    public void setGPS(Forms fc) {
        SharedPreferences GPSPref = getSharedPreferences("GPSCoordinates", Context.MODE_PRIVATE);
        try {
            String lat = GPSPref.getString("Latitude", "0");
            String lang = GPSPref.getString("Longitude", "0");
            String acc = GPSPref.getString("Accuracy", "0");
            String elevation = GPSPref.getString("Elevation", "0");

            if (lat == "0" && lang == "0") {
                Toast.makeText(this, "Could not obtained GPS points", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();
            }

            String date = DateFormat.format("dd-MM-yyyy HH:mm", Long.parseLong(GPSPref.getString("Time", "0"))).toString();

            fc.setGpsLat(lat);
            fc.setGpsLng(lang);
            fc.setGpsAcc(acc);
            fc.setGpsDT(date); // Timestamp is converted to date above
            fc.setGpsElev(elevation);

            Toast.makeText(this, "GPS set", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Log.e(TAG, "setGPS: " + e.getMessage());
        }

    }

    private boolean UpdateDB() {

        try {

            Long longID = new crudOperations(db, fc).execute(FormsDAO.class.getName(), "formsDao", "insertForm").get();

            if (longID != 0) {
                fc.setId(longID.intValue());
                fc.setUid(MainApp.deviceId + fc.getId());
                longID = new crudOperations(db, fc).execute(FormsDAO.class.getName(), "formsDao", "updateForm").get();
                return longID == 1;

            } else {
                return false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;

    }

}
