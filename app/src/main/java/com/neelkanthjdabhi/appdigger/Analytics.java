package com.neelkanthjdabhi.appdigger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import github.nisrulz.easydeviceinfo.base.EasyAppMod;
import github.nisrulz.easydeviceinfo.base.EasyBatteryMod;
import github.nisrulz.easydeviceinfo.base.EasyDeviceMod;
import github.nisrulz.easydeviceinfo.base.EasyDisplayMod;

public class Analytics extends AppCompatActivity {
    private Toolbar toolbar;
    TextView osValue, modelValue, manufacturerValue, rootValue;
    TextView resolutionValue, densityValue, refreshrateValue;
    TextView voltageValue,tempValue,isdevicechargingValue,percentageValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        //System
        EasyDeviceMod easyDeviceMod = new EasyDeviceMod(getApplicationContext());
        osValue = findViewById(R.id.osValue);
        modelValue = findViewById(R.id.modelValue);
        manufacturerValue = findViewById(R.id.manufacturerValue);
        rootValue = findViewById(R.id.rootValue);

        osValue.setText(easyDeviceMod.getOSVersion());
        modelValue.setText(easyDeviceMod.getModel());
        manufacturerValue.setText(easyDeviceMod.getManufacturer());
        if (easyDeviceMod.isDeviceRooted()) {
            rootValue.setText("Available");
        } else {
            rootValue.setText("Unavailable");
        }


        //Display
        EasyDisplayMod easyDisplayMod = new EasyDisplayMod(getApplicationContext());
        resolutionValue = findViewById(R.id.resolutionValue);
        densityValue = findViewById(R.id.densityValue);
        refreshrateValue = findViewById(R.id.refreshrateValue);

        resolutionValue.setText(easyDisplayMod.getResolution());
        densityValue.setText(easyDisplayMod.getDensity());
        refreshrateValue.setText(Float.toString(easyDisplayMod.getRefreshRate()));


        //Battery
        EasyBatteryMod easyBatteryMod = new EasyBatteryMod(getApplicationContext());
        voltageValue = findViewById(R.id.voltageValue);
        tempValue = findViewById(R.id.tempValue);
        isdevicechargingValue = findViewById(R.id.isdevicechargingValue);
        percentageValue = findViewById(R.id.percentageValue);

        voltageValue.setText(Integer.toString(easyBatteryMod.getBatteryVoltage()));
        tempValue.setText(Float.toString(easyBatteryMod.getBatteryTemperature()));
        if (easyBatteryMod.isDeviceCharging()) {
            isdevicechargingValue.setText("Charging");
        } else {
            isdevicechargingValue.setText("discharging");
        }
        percentageValue.setText(Integer.toString(easyBatteryMod.getBatteryPercentage()));


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
