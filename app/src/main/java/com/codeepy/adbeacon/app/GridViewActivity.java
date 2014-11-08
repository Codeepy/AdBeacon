package com.codeepy.adbeacon.app;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import com.codeepy.adbeacon.app.adapter.GridViewImageAdapter;
import com.codeepy.adbeacon.app.helper.AppConstant;
import com.codeepy.adbeacon.app.helper.Codeepy;
import com.codeepy.adbeacon.app.helper.Utils;

public class GridViewActivity extends Activity {

	private Utils utils;
	private ArrayList<String> imagePaths = new ArrayList<String>();
	private GridViewImageAdapter adapter;
	private GridView gridView;
	private int columnWidth;

    private String UUID = "d4:22:3f:db:95:b1";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid_view);

        BluetoothAdapter.getDefaultAdapter().enable();

		gridView = (GridView) findViewById(R.id.grid_view);

		utils = new Utils(this);

		// Initilizing Grid View
		InitilizeGridLayout();

		// loading all image paths from SD card
		//imagePaths = utils.getFilePaths();
        imagePaths = utils.getFileUrls(UUID);

		// Gridview adapter
		adapter = new GridViewImageAdapter(GridViewActivity.this, imagePaths, columnWidth);

		// setting grid view adapter
		gridView.setAdapter(adapter);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grid_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.menu_ble:
                intent = new Intent(this, com.codeepy.adbeacon.app.bluetooth.ble.DeviceScanActivity.class);
                startActivity(intent);
            case R.id.menu_bluetooth:
                intent = new Intent(this, com.codeepy.adbeacon.app.bluetooth.classic.DeviceScanActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

	private void InitilizeGridLayout() {
		Resources r = getResources();
		float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				AppConstant.GRID_PADDING, r.getDisplayMetrics());

		columnWidth = (int) ((utils.getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS + 1) * padding)) / AppConstant.NUM_OF_COLUMNS);

		gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
		gridView.setColumnWidth(columnWidth);
		gridView.setStretchMode(GridView.NO_STRETCH);
		gridView.setPadding((int) padding, (int) padding, (int) padding,
				(int) padding);
		gridView.setHorizontalSpacing((int) padding);
		gridView.setVerticalSpacing((int) padding);
	}

}
