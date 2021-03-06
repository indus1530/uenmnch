package edu.aku.abdulsajid.uenMnch.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import edu.aku.abdulsajid.uenMnch.R;
import edu.aku.abdulsajid.uenMnch.RMOperations.crudOperations;
import edu.aku.abdulsajid.uenMnch.core.MainApp;
import edu.aku.abdulsajid.uenMnch.data.DAO.FormsDAO;
import edu.aku.abdulsajid.uenMnch.databinding.ActivityQoc7Binding;
import edu.aku.abdulsajid.uenMnch.validation.validatorClass;

import static edu.aku.abdulsajid.uenMnch.activities.LoginActivity.db;
import static edu.aku.abdulsajid.uenMnch.activities.RSDInfoActivity.fc;



public class Qoc7 extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener  {

    ActivityQoc7Binding bi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_qoc7);
        bi.setCallback(this);
        this.setTitle(getString(R.string.routinetwo) + "(" + fc.getReportingMonth() + ")");
        events_call();
    }


    public void BtnContinue() {
        if (formValidation()) {
            try {
                SaveDraft();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (UpdateDB()) {
                MainApp.endActivity(this, this, EndingActivity.class, true, RSDInfoActivity.fc);

            } else {
                Toast.makeText(this, "Failed to Update Database!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void BtnEnd() {
        MainApp.endActivity(this, this, EndingActivity.class, false, RSDInfoActivity.fc);
    }

    private boolean UpdateDB() {
        try {
            Long longID = new crudOperations(db, RSDInfoActivity.fc).execute(FormsDAO.class.getName(), "formsDao", "updateForm").get();
            return longID == 1;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean formValidation() {

        return validatorClass.EmptyCheckingContainer(this, bi.llqoc7);
    }

    private void SaveDraft() throws JSONException {

        JSONObject qoc7 = new JSONObject();

        qoc7.put("qg0701a", bi.qg0701aa.isChecked() ? "1" : bi.qg0701ab.isChecked() ? "2" : bi.qg0701a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0701b", bi.qg0701b.getText().toString().trim().length() > 0 ? bi.qg0701b.getText().toString() : "0");
//        qoc7.put("qg0701c", bi.qg0701c.getText().toString().trim().length() > 0 ? bi.qg0701c.getText().toString() : "0");

        qoc7.put("qg0702a", bi.qg0702aa.isChecked() ? "1" : bi.qg0702ab.isChecked() ? "2" : bi.qg0702a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0702b", bi.qg0702b.getText().toString().trim().length() > 0 ? bi.qg0702b.getText().toString() : "0");
//        qoc7.put("qg0702c", bi.qg0702c.getText().toString().trim().length() > 0 ? bi.qg0702c.getText().toString() : "0");

        qoc7.put("qg0703a", bi.qg0703aa.isChecked() ? "1" : bi.qg0703ab.isChecked() ? "2" : bi.qg0703a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0703b", bi.qg0703b.getText().toString().trim().length() > 0 ? bi.qg0703b.getText().toString() : "0");
//        qoc7.put("qg0703c", bi.qg0703c.getText().toString().trim().length() > 0 ? bi.qg0703c.getText().toString() : "0");

        qoc7.put("qg0704a", bi.qg0704aa.isChecked() ? "1" : bi.qg0704ab.isChecked() ? "2" : bi.qg0704a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0704b", bi.qg0704b.getText().toString().trim().length() > 0 ? bi.qg0704b.getText().toString() : "0");
//        qoc7.put("qg0704c", bi.qg0704c.getText().toString().trim().length() > 0 ? bi.qg0704c.getText().toString() : "0");

        qoc7.put("qg0705a", bi.qg0705aa.isChecked() ? "1" : bi.qg0705ab.isChecked() ? "2" : bi.qg0705a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0705b", bi.qg0705b.getText().toString().trim().length() > 0 ? bi.qg0705b.getText().toString() : "0");
//        qoc7.put("qg0705c", bi.qg0705c.getText().toString().trim().length() > 0 ? bi.qg0705c.getText().toString() : "0");

        qoc7.put("qg0706a", bi.qg0706aa.isChecked() ? "1" : bi.qg0706ab.isChecked() ? "2" : bi.qg0706a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0706b", bi.qg0706b.getText().toString().trim().length() > 0 ? bi.qg0706b.getText().toString() : "0");
//        qoc7.put("qg0706c", bi.qg0706c.getText().toString().trim().length() > 0 ? bi.qg0706c.getText().toString() : "0");

        qoc7.put("qg0707a", bi.qg0707aa.isChecked() ? "1" : bi.qg0707ab.isChecked() ? "2" : bi.qg0707a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0707b", bi.qg0707b.getText().toString().trim().length() > 0 ? bi.qg0707b.getText().toString() : "0");
//        qoc7.put("qg0707c", bi.qg0707c.getText().toString().trim().length() > 0 ? bi.qg0707c.getText().toString() : "0");

        qoc7.put("qg0708a", bi.qg0708aa.isChecked() ? "1" : bi.qg0708ab.isChecked() ? "2" : bi.qg0708a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0708b", bi.qg0708b.getText().toString().trim().length() > 0 ? bi.qg0708b.getText().toString() : "0");
//        qoc7.put("qg0708c", bi.qg0708c.getText().toString().trim().length() > 0 ? bi.qg0708c.getText().toString() : "0");

        qoc7.put("qg0709a", bi.qg0709aa.isChecked() ? "1" : bi.qg0709ab.isChecked() ? "2" : bi.qg0709a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0709b", bi.qg0709b.getText().toString().trim().length() > 0 ? bi.qg0709b.getText().toString() : "0");
//        qoc7.put("qg0709c", bi.qg0709c.getText().toString().trim().length() > 0 ? bi.qg0709c.getText().toString() : "0");

        qoc7.put("qg0710a", bi.qg0710aa.isChecked() ? "1" : bi.qg0710ab.isChecked() ? "2" : bi.qg0710a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0710b", bi.qg0710b.getText().toString().trim().length() > 0 ? bi.qg0710b.getText().toString() : "0");
//        qoc7.put("qg0710c", bi.qg0710c.getText().toString().trim().length() > 0 ? bi.qg0710c.getText().toString() : "0");

        qoc7.put("qg0711a", bi.qg0711aa.isChecked() ? "1" : bi.qg0711ab.isChecked() ? "2" : bi.qg0711a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0711b", bi.qg0711b.getText().toString().trim().length() > 0 ? bi.qg0711b.getText().toString() : "0");
//        qoc7.put("qg0711c", bi.qg0711c.getText().toString().trim().length() > 0 ? bi.qg0711c.getText().toString() : "0");

        qoc7.put("qg0712a", bi.qg0712aa.isChecked() ? "1" : bi.qg0712ab.isChecked() ? "2" : bi.qg0712a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0712b", bi.qg0712b.getText().toString().trim().length() > 0 ? bi.qg0712b.getText().toString() : "0");
//        qoc7.put("qg0712c", bi.qg0712c.getText().toString().trim().length() > 0 ? bi.qg0712c.getText().toString() : "0");

        qoc7.put("qg0713a", bi.qg0713aa.isChecked() ? "1" : bi.qg0713ab.isChecked() ? "2" : bi.qg0713a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0713b", bi.qg0713b.getText().toString().trim().length() > 0 ? bi.qg0713b.getText().toString() : "0");
//        qoc7.put("qg0713c", bi.qg0713c.getText().toString().trim().length() > 0 ? bi.qg0713c.getText().toString() : "0");

        qoc7.put("qg0714a", bi.qg0714aa.isChecked() ? "1" : bi.qg0714ab.isChecked() ? "2" : bi.qg0714a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0714b", bi.qg0714b.getText().toString().trim().length() > 0 ? bi.qg0714b.getText().toString() : "0");
//        qoc7.put("qg0714c", bi.qg0714c.getText().toString().trim().length() > 0 ? bi.qg0714c.getText().toString() : "0");

        qoc7.put("qg0715a", bi.qg0715aa.isChecked() ? "1" : bi.qg0715ab.isChecked() ? "2" : bi.qg0715a97.isChecked() ? "NA" : "0");
        qoc7.put("qg0715b", bi.qg0715b.getText().toString().trim().length() > 0 ? bi.qg0715b.getText().toString() : "0");
//        qoc7.put("qg0715c", bi.qg0715c.getText().toString().trim().length() > 0 ? bi.qg0715c.getText().toString() : "0");

        qoc7.put("qg07Ap", bi.qg07Ap.getText().toString().trim().length() > 0 ? bi.qg07Ap.getText().toString() : "0");

        fc.setSG(String.valueOf(qoc7));

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        bi.qg0701b.setEnabled(bi.qg0701ab.isChecked());

        bi.qg0702b.setEnabled(bi.qg0702ab.isChecked());

        bi.qg0703b.setEnabled(bi.qg0703ab.isChecked());

        bi.qg0704b.setEnabled(bi.qg0704ab.isChecked());

        bi.qg0705b.setEnabled(bi.qg0705ab.isChecked());

        bi.qg0706b.setEnabled(bi.qg0706ab.isChecked());

        bi.qg0707b.setEnabled(bi.qg0707ab.isChecked());

        bi.qg0708b.setEnabled(bi.qg0708ab.isChecked());

        bi.qg0709b.setEnabled(bi.qg0709ab.isChecked());

        bi.qg0710b.setEnabled(bi.qg0710ab.isChecked());

        bi.qg0711b.setEnabled(bi.qg0711ab.isChecked());

        bi.qg0712b.setEnabled(bi.qg0712ab.isChecked());

        bi.qg0713b.setEnabled(bi.qg0713ab.isChecked());

        bi.qg0714b.setEnabled(bi.qg0714ab.isChecked());

        bi.qg0715b.setEnabled(bi.qg0715ab.isChecked());
    }

    void events_call() {

        bi.qg0701a.setOnCheckedChangeListener(this);
        bi.qg0702a.setOnCheckedChangeListener(this);
        bi.qg0703a.setOnCheckedChangeListener(this);
        bi.qg0704a.setOnCheckedChangeListener(this);
        bi.qg0705a.setOnCheckedChangeListener(this);
        bi.qg0706a.setOnCheckedChangeListener(this);
        bi.qg0707a.setOnCheckedChangeListener(this);
        bi.qg0708a.setOnCheckedChangeListener(this);
        bi.qg0709a.setOnCheckedChangeListener(this);
        bi.qg0710a.setOnCheckedChangeListener(this);
        bi.qg0711a.setOnCheckedChangeListener(this);
        bi.qg0712a.setOnCheckedChangeListener(this);
        bi.qg0713a.setOnCheckedChangeListener(this);
        bi.qg0714a.setOnCheckedChangeListener(this);
        bi.qg0715a.setOnCheckedChangeListener(this);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "You can't go back", Toast.LENGTH_SHORT).show();
    }
}
