package com.saucelabs.mydemoapp.android.view.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.saucelabs.mydemoapp.android.Config;
import com.saucelabs.mydemoapp.android.R;
import com.saucelabs.mydemoapp.android.databinding.ActivityMainBinding;
import com.saucelabs.mydemoapp.android.databinding.SortDialogBinding;
import com.saucelabs.mydemoapp.android.interfaces.OnItemClickListener;
import com.saucelabs.mydemoapp.android.model.MenuItem;
import com.saucelabs.mydemoapp.android.utils.Constants;
import com.saucelabs.mydemoapp.android.utils.base.BaseActivity;
import com.saucelabs.mydemoapp.android.utils.base.BaseModel;
import com.saucelabs.mydemoapp.android.view.adapters.MenuAdapter;
import com.saucelabs.mydemoapp.android.view.fragments.AboutFragment;
import com.saucelabs.mydemoapp.android.view.fragments.BiometricFragment;
import com.saucelabs.mydemoapp.android.view.fragments.CartFragment;
import com.saucelabs.mydemoapp.android.view.fragments.CheckoutCompleteFragment;
import com.saucelabs.mydemoapp.android.view.fragments.CheckoutFragment;
import com.saucelabs.mydemoapp.android.view.fragments.CheckoutInfoFragment;
import com.saucelabs.mydemoapp.android.view.fragments.DrawingFragment;
import com.saucelabs.mydemoapp.android.view.fragments.LocationFragment;
import com.saucelabs.mydemoapp.android.view.fragments.LoginFragment;
import com.saucelabs.mydemoapp.android.view.fragments.PlaceOrderFragment;
import com.saucelabs.mydemoapp.android.view.fragments.ProductCatalogFragment;
import com.saucelabs.mydemoapp.android.view.fragments.ProductDetailFragment;
import com.saucelabs.mydemoapp.android.view.fragments.QRFragment;
import com.saucelabs.mydemoapp.android.view.fragments.WebAddressFragment;
import com.saucelabs.mydemoapp.android.view.fragments.WebViewFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	public ActivityMainBinding binding;
	private Fragment currentFragment;
	private MenuAdapter menuAdapter;

	public final static int FRAGMENT_PRODUCT_CATAlOG = 1;
	public final static int FRAGMENT_PRODUCT_DETAIL = 2;
	public final static int FRAGMENT_QR = 3;
	public final static int FRAGMENT_WEB_ADDRESS = 4;
	public final static int FRAGMENT_GEO_LOCATION = 5;
	public final static int FRAGMENT_DRAWING = 6;
	public final static int FRAGMENT_ABOUT = 7;
	public final static int FRAGMENT_BIOMETRICS = 8;
	public final static int FRAGMENT_LOGIN = 9;
	public final static int FRAGMENT_CART = 10;
	public final static int FRAGMENT_WEB_VIEW = 11;
	public final static int FRAGMENT_CHECKOUT_INFO = 12;
	public final static int FRAGMENT_CHECKOUT = 13;
	public final static int FRAGMENT_CHECKOUT_COMPLETE = 14;
	public final static int FRAGMENT_PLACE_ORDER = 15;

	private List<BaseModel> list;
	private String param1, param2;
	private int param3;

	public final int REQ_ID_MULTIPLE_PERMISSIONS = 1;

	public static int NAME_ASC = 1;
	public static int NAME_DESC = 2;
	public static int PRICE_ASC = 3;
	public static int PRICE_DESC = 4;
	public static int selectedSort = NAME_ASC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		initialize();
	}

	@Override
	protected void onStart() {
		super.onStart();

		// sample code for checking new version available for distribution through `Mobile Beta Testing`.
		// uncomment the following line to disable an alert dialog if current version has expired or deleted
		// checkVersionIsStillSupported();
	}

	private void initialize() {
		init();
		setMenu();
//		checkAndRequestPermissions();
		setListener();
		setData();
	}

	private void init() {
		param1 = getIntent().getStringExtra(Constants.ARG_PARAM1);

		if (param1 == null) {
			param1 = "";
		}

		param2 = getIntent().getStringExtra(Constants.ARG_PARAM2);
		if (param2 == null) {
			param2 = "";
		}

		int selectedTab = getIntent().getIntExtra(Constants.SELECTED_TAB, -1);
		param3 = getIntent().getIntExtra(Constants.ARG_PARAM3, -1);
		int reqFrag = getIntent().getIntExtra(Constants.REQUEST_FRAGMENT, -1);
		ST.PREVIOUS_TAB = selectedTab;

		if (reqFrag != -1) {
			setFragment(reqFrag, param1, param2, param3);
		} else {
			setFragment(FRAGMENT_PRODUCT_CATAlOG, param1, param2, param3);
		}
	}

	@Override
	public void onBackPressed() {
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			finish();
		}
	}

	public void setFragment(int frg_type, String param1, String param2, int param3) {
		ST.PREVIOUS_FRAGMENT = ST.CURRENT_FRAGMENT;
		ST.CURRENT_FRAGMENT = frg_type;
		binding.header.getRoot().setVisibility(View.VISIBLE);
		binding.header.sortIV.setVisibility(View.GONE);
		switch (frg_type) {
			case FRAGMENT_PRODUCT_CATAlOG:
				binding.header.sortIV.setVisibility(View.VISIBLE);
				currentFragment = ProductCatalogFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_PRODUCT_DETAIL:
				currentFragment = ProductDetailFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_QR:
				currentFragment = QRFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_CART:
				currentFragment = CartFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_WEB_VIEW:
				currentFragment = WebViewFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_WEB_ADDRESS:
				currentFragment = WebAddressFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_GEO_LOCATION:
				currentFragment = LocationFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_DRAWING:
				currentFragment = DrawingFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_ABOUT:
				currentFragment = AboutFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_BIOMETRICS:
				currentFragment = BiometricFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_LOGIN:
				currentFragment = LoginFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_CHECKOUT_INFO:
				currentFragment = CheckoutInfoFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_CHECKOUT:
				currentFragment = CheckoutFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_CHECKOUT_COMPLETE:
				currentFragment = CheckoutCompleteFragment.newInstance(param1, param2, param3);
				break;
			case FRAGMENT_PLACE_ORDER:
				currentFragment = PlaceOrderFragment.newInstance(param1, param2, param3);
				break;
		}

		if (currentFragment != null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fragment_container, currentFragment, currentFragment.getClass().getName());
			transaction.commit();
		}
	}

	private void setListener() {
		binding.header.menuIV.setOnClickListener(this);
		binding.header.sortIV.setOnClickListener(this);
		binding.header.cartRL.setOnClickListener(this);
	}

	public void setData() {
		binding.header.cartTV.setText("" + ST.getTotalNum());
		int visibility = (ST.getTotalNum() == 0) ? View.GONE : View.VISIBLE;
		((MainActivity) mAct).binding.header.cartCircleRL.setVisibility(visibility);
	}

	@Override
	public void onClick(View view) {
		if (view.equals(binding.header.menuIV)) {
			binding.container.openDrawer(GravityCompat.START);
		} else if (view.equals(binding.header.cartRL)) {
			ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_CART, 1));
		} else if (view.equals(binding.header.sortIV)) {
			showSortDialog();
		}
	}

	private void setMenu() {
		List<MenuItem> list = new ArrayList<>();
		list.add(new MenuItem(getString(R.string.catalog)));
		list.add(new MenuItem(getString(R.string.webview)));
		list.add(new MenuItem(getString(R.string.qr_code_scanner)));
		list.add(new MenuItem(getString(R.string.geo_location)));
		list.add(new MenuItem(getString(R.string.drawing)));
		list.add(new MenuItem(getString(R.string.about)));
		list.add(new MenuItem(getString(R.string.app_reset_state)));
		list.add(new MenuItem(getString(R.string.fingerprint)));
		list.add(new MenuItem(getString(R.string.virtual_usb)));
		list.add(new MenuItem("Crash app (debug)"));
		if (ST.isLogin) {
			list.add(new MenuItem(getString(R.string.logout), "Logout Menu Item"));
		} else {
			list.add(new MenuItem(getString(R.string.login), "Login Menu Item"));
		}

		menuAdapter = new MenuAdapter(list, this, new OnItemClickListener() {
			@Override
			public void OnClick(int position, int status) {
				handleMenuClick(position);
			}
		});

		binding.drawerMenu.menuRV.setLayoutManager(new LinearLayoutManager(this));
		binding.drawerMenu.menuRV.setAdapter(menuAdapter);
	}

	private void handleMenuClick(int position) {
		switch (position) {
			case 0:
				if (!(currentFragment instanceof ProductCatalogFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_PRODUCT_CATAlOG, 1));
				}
				break;
			case 1:
				if (!(currentFragment instanceof WebAddressFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_WEB_ADDRESS, 1));
				}
				break;
			case 2:
				if (!(currentFragment instanceof QRFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_QR, 1));
				}
				break;
			case 3:
				if (!(currentFragment instanceof LocationFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_GEO_LOCATION, 1));
				}
				break;
			case 4:
				if (!(currentFragment instanceof DrawingFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_DRAWING, 1));
				}

				break;
			case 5:
				if (!(currentFragment instanceof AboutFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_ABOUT, 1));
				}

				break;
			case 6:
				showResetDialog();
				break;
			case 7:
				if (!(currentFragment instanceof BiometricFragment)) {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_BIOMETRICS, 1));
				}

				break;
			case 8:
				startActivity(new Intent(this, VirtualUsbActivity.class));
				break;
			case 9:
				startActivity(new Intent(this, DebugCrashActivity.class));
				break;
			case 10:
				if (ST.isLogin) {
					// setFragment(FRAGMENT_CART, param1, param2, param3);
					showLogoutAlertDialog();
				} else {
					ST.startMainActivity(mAct, ST.getBundle(FRAGMENT_LOGIN, 1));
				}
				// setFragment(FRAGMENT_LOGIN, param1, param2, param3);
				break;

		}

		binding.container.closeDrawer(GravityCompat.START);
	}

	private boolean checkAndRequestPermissions() {
		int writeStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
		int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
		int accessCrossLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
		int readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
		int camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

		List<String> listPermissionsNeeded = new ArrayList<>();

		if (writeStoragePermission != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}
		if (readPermission != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
		}

		if (locationPermission != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}
		if (accessCrossLocationPermission != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
		}

		if (camera != PackageManager.PERMISSION_GRANTED) {
			listPermissionsNeeded.add(Manifest.permission.CAMERA);
		}

		if (!listPermissionsNeeded.isEmpty()) {
			ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQ_ID_MULTIPLE_PERMISSIONS);
			return false;
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == REQ_ID_MULTIPLE_PERMISSIONS) {
			Map<String, Integer> perms = new HashMap<>();
			perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
			perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
			if (grantResults.length > 0) {
				for (int i = 0; i < permissions.length; i++) {
					perms.put(permissions[i], grantResults[i]);
				}

				// Check for both permissions
				if (perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
					&& perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
				} else {
//					singleton.showCommonDialog(null, "Permission Denied!", "Please go to settings and enable permissions.", getString(R.string.ok), true);
				}

			}
		}
	}

	public void showSortDialog() {
		final Dialog dialog = new Dialog(mAct);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

		SortDialogBinding sortDialogBinding = DataBindingUtil.inflate(LayoutInflater.from(mAct), R.layout.sort_dialog, null, false);
		dialog.setContentView(sortDialogBinding.getRoot());


		View.OnClickListener clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				sortDialogBinding.nameAscCL.setBackgroundColor(mAct.getResources().getColor(R.color.white));
				sortDialogBinding.nameDesCL.setBackgroundColor(mAct.getResources().getColor(R.color.white));
				sortDialogBinding.priceAscCL.setBackgroundColor(mAct.getResources().getColor(R.color.white));
				sortDialogBinding.priceDesCL.setBackgroundColor(mAct.getResources().getColor(R.color.white));

				sortDialogBinding.tickNameAscIV.setVisibility(View.INVISIBLE);
				sortDialogBinding.tickNameDesIV.setVisibility(View.INVISIBLE);
				sortDialogBinding.tickPriceAscIV.setVisibility(View.INVISIBLE);
				sortDialogBinding.tickPriceDscIV.setVisibility(View.INVISIBLE);

				if (view.equals(sortDialogBinding.nameAscCL)) {
					selectedSort = NAME_ASC;
					sortDialogBinding.nameAscCL.setBackgroundColor(mAct.getResources().getColor(R.color.light_green_bg));
					sortDialogBinding.tickNameAscIV.setVisibility(View.VISIBLE);
					binding.header.sortIV.setImageResource(R.drawable.sort_asc);
				} else if (view.equals(sortDialogBinding.nameDesCL)) {
					selectedSort = NAME_DESC;
					sortDialogBinding.nameDesCL.setBackgroundColor(mAct.getResources().getColor(R.color.light_green_bg));
					sortDialogBinding.tickNameDesIV.setVisibility(View.VISIBLE);
					binding.header.sortIV.setImageResource(R.drawable.sort_des);
				} else if (view.equals(sortDialogBinding.priceAscCL)) {
					selectedSort = PRICE_ASC;
					sortDialogBinding.priceAscCL.setBackgroundColor(mAct.getResources().getColor(R.color.light_green_bg));
					sortDialogBinding.tickPriceAscIV.setVisibility(View.VISIBLE);
					binding.header.sortIV.setImageResource(R.drawable.price_asc);
				} else if (view.equals(sortDialogBinding.priceDesCL)) {
					selectedSort = PRICE_DESC;
					sortDialogBinding.priceDesCL.setBackgroundColor(mAct.getResources().getColor(R.color.light_green_bg));
					sortDialogBinding.tickPriceDscIV.setVisibility(View.VISIBLE);
					binding.header.sortIV.setImageResource(R.drawable.price_des);
				}

				((ProductCatalogFragment) currentFragment).updateData();

				dialog.dismiss();
			}
		};

		sortDialogBinding.nameAscCL.setOnClickListener(clickListener);
		sortDialogBinding.nameDesCL.setOnClickListener(clickListener);
		sortDialogBinding.priceAscCL.setOnClickListener(clickListener);
		sortDialogBinding.priceDesCL.setOnClickListener(clickListener);

		if (selectedSort == NAME_ASC) {
			sortDialogBinding.nameAscCL.performClick();
		} else if (selectedSort == NAME_DESC) {
			sortDialogBinding.nameDesCL.performClick();
		} else if (selectedSort == PRICE_ASC) {
			sortDialogBinding.priceAscCL.performClick();
		} else if (selectedSort == PRICE_DESC) {
			sortDialogBinding.priceDesCL.performClick();
		}

		dialog.show();
	}

	private void showLogoutAlertDialog() {
		new AlertDialog.Builder(mAct, R.style.MyDialogTheme)
			.setTitle("Log Out")
			.setMessage("Are you sure you want to logout")
			.setNegativeButton("CANCEL", null)
			.setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					ST.isLogin = false;
					ST.hasVisualChanges = false;
					setMenu();
					Bundle bundle = ST.getBundle(FRAGMENT_LOGIN, 1);
					bundle.putString(Constants.ARG_PARAM1, ST.LOGOUT);
					ST.startActivityWithDataBundle(mAct, MainActivity.class, bundle, ST.START_ACTIVITY_WITH_FINISH);
				}
			})
			.setCancelable(false)
			.show();
	}

	private void showResetDialog() {
		new AlertDialog.Builder(mAct, R.style.MyDialogTheme)
			.setTitle("Reset App State")
			.setMessage("Are you sure you want to Reset the App")
			.setNegativeButton("CANCEL", null)
			.setPositiveButton("RESET APP", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					ST.isLogin = false;
					ST.cartItemList = new ArrayList<>();
					setData();

					ST.showDialog(null, mAct, "", "App State has been reset.", getString(R.string.ok));

//                        ST.startActivity(mAct, MainActivity.class, ST.START_ACTIVITY_WITH_FINISH);
				}
			})
			.setCancelable(false)
			.show();
	}

	public void saveFile(Bitmap bitmap) {
		String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
		File myDir = new File(root);
		if (!myDir.exists()) {
			myDir.mkdirs();
		}

		String path = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
		path = "IMAGE_" + path + ".jpg";
		File f = new File(myDir.getAbsolutePath() + File.separator + path);

		if (f.exists()) {
			f.delete();
		}

		try {
			showAlert();
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void showAlert() {
		new AlertDialog.Builder(mAct, R.style.MyDialogTheme)
			.setTitle("Save drawing")
			.setMessage("Drawing saved successfully to gallery")
			.setPositiveButton("Ok", null)
			.setCancelable(false)
			.show();
	}

	/**
	 * This is a sample function that checks if the current version of this app is still
	 * available for distribution. If an admin disables distribution or deletes the current
	 * version, then this function will return (false).
	 */
	private boolean isThisVersionStillSupported() {
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);

			URL url = new URL("https://mobile.saucelabs.com/services/?method=testfairy.session.getDistributionStatus");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			String appToken = "SDK-XXXX";

			String urlParameters = "token=" + appToken +
				"&bundleVersion=" + packageInfo.versionCode +
				"&bundleIdentifier=" + packageInfo.packageName +
				"&bundleShortVersion=" + packageInfo.versionName +
				"&platform=0";

			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			writer.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer response = new StringBuffer();

			while (true) {
				String inputLine = in.readLine();
				if (inputLine == null) {
					break;
				}

				response.append(inputLine);
			}

			in.close();
			Log.i(Config.TAG, "Received a response from getDistributionStatus: " + response);

			JSONObject obj = new JSONObject(response.toString());
			boolean distributionEnabled = "enabled".equals(obj.optString("status"));
			Log.i(Config.TAG, "Is distribution enabled: " + distributionEnabled);
			return distributionEnabled;

		} catch (Exception e) {
			Log.e(Config.TAG, "Failed to check for new beta version with exception", e);
			return true;
		}
	}

	private void showVersionExpiredAlert() {
		runOnUiThread(() -> {
			new AlertDialog.Builder(mAct, R.style.MyDialogTheme)
				.setTitle("Version no longer supported")
				.setMessage("This version has expired. Please upgrade to latest.")
				.setPositiveButton("Ok", null)
				.setCancelable(false)
				.show();
		});
	}

	private void checkVersionIsStillSupported() {
		Thread thread = new Thread(() -> {
			if (!isThisVersionStillSupported()) {
				showVersionExpiredAlert();
			}
		});

		thread.start();
	}
}
