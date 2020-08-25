package com.praxello.tailorsmart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.praxello.tailorsmart.adapter.CategoryAdapter;
import com.praxello.tailorsmart.adapter.OfferPagerAdapter;
import com.praxello.tailorsmart.adapter.TestimonialPagerAdapter;
import com.praxello.tailorsmart.api.ApiRequestHelper;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Category;
import com.praxello.tailorsmart.model.Data;
import com.praxello.tailorsmart.model.Offer;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.model.UserData;
import com.praxello.tailorsmart.utils.ConnectionDetector;
import com.praxello.tailorsmart.utils.Utils;
import com.praxello.tailorsmart.widget.CirclePageIndicator;
import com.praxello.tailorsmart.widget.LoopViewPager;
import com.praxello.tailorsmart.widget.materialprogress.CustomProgressDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import id.zelory.compressor.Compressor;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.navView)
    NavigationView navigationView;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawer;
    @BindView(R.id.vpOffers)
    LoopViewPager vpOffers;
    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.btnRetry)
    Button btnRetry;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.tvErrorOffers)
    TextView tvErrorOffers;
    @BindView(R.id.tvErrorCategories)
    TextView tvErrorCategories;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tvTestimonials)
    TextView tvTestimonials;
    @BindView(R.id.vpTestimonials)
    LoopViewPager vpTestimonials;
    //    @BindView(R.id.cpiTestimonials)
//    CirclePageIndicator cpiTestimonials;
    @BindView(R.id.rrTestimonials)
    RelativeLayout rrTestimonials;
    @BindView(R.id.vpBg)
    LoopViewPager viewpager;
    //    @BindView(R.id.viewpager)
//    LoopViewPager viewpagerBanner;
    @BindView(R.id.circlePageIndicator)
    CirclePageIndicator circlePageIndicator;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.rrBanner)
    RelativeLayout rrBanner;

    private GoogleSignInClient mGoogleSignInClient;
    private TextView tvName;
    public AllData data;
    //    int[] images = {R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5, R.drawable.bg6, R.drawable.bg7};
    int[] images = {R.drawable.bg8};
    public static MainActivity activity;
    private MenuItem action_wishlist;
    private static final int PICK_IMAGE = 1;
    private static final int REQUEST_CAMERA = 2;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 4;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static String[] PERMISSIONS_CAMERA = {Manifest.permission.CAMERA};

    final String[] items = new String[]{"Camera", "Gallery"};
    private ImageView ivProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.syncState();
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(Gravity.LEFT));
        navigationView.setNavigationItemSelectedListener(this);

//        rvOffers.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setLayoutManager(new LinearLayoutManager(mContext));
        rvCategories.setNestedScrollingEnabled(false);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnRetry.setOnClickListener(view -> getproducts());

        //            alladvertisements();
        swipeRefreshLayout.setOnRefreshListener(this::getproducts);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // This method must be called on a background thread.
                Log.e("in", "doin");
                Glide.get(mContext).clearDiskCache();
                return null;
            }
        }.execute();

//        HomeBgPagerAdapter adapter = new HomeBgPagerAdapter(mContext, images);
//        viewpager.setAdapter(adapter);
//        viewpager.setOnTouchListener((v, event) -> true); // disable viewpager swipe
////        set_slider_animation(); // set auto scrolling viewpager

//        alladvertisements();
        if (navigationView != null) {
            ivProfile = navigationView.getHeaderView(0).findViewById(R.id.ivProfile);
            if (ivProfile != null) {
                ivProfile.setOnClickListener(view -> {
                    set_image();
                });
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {   //Android M Or Over
            ask_permissions();
        }

        //login();

        getproducts();
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (rvOffers != null)
//            rvOffers.stopAutoScroll();
    }

    private void login() {
        if (app.getPreferences().getLoggedInUser() != null && app.getPreferences().getLoggedInUser().getData() != null &&
                !TextUtils.isEmpty(app.getPreferences().getLoggedInUser().getData().getEmail()) &&
                !TextUtils.isEmpty(app.getPreferences().getLoggedInUser().getData().getPassword())) {
            Map<String, String> params = new HashMap<>();
            params.put("usrname", app.getPreferences().getLoggedInUser().getData().getEmail());
            params.put("passwrd", app.getPreferences().getLoggedInUser().getData().getPassword());
            if (!TextUtils.isEmpty(app.getPreferences().getToken()))
                params.put("deviceid", app.getPreferences().getToken());
            params.put("devicetype", "android");
            params.put("uuid", Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID));
            params.put("model", Utils.getDeviceName());
            params.put("imei", Utils.getDeviceIMEI(mContext));
            ConnectionDetector cd = new ConnectionDetector(mContext);
            if (cd.isConnectingToInternet()) {
                final CustomProgressDialog pd = new CustomProgressDialog(mContext);
                pd.setTitle("Loading...");
                pd.show();
                app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().login(params), new ApiRequestHelper.OnRequestComplete() {
                    @Override
                    public void onSuccess(Object object) {
                        if (pd != null && pd.isShowing()) pd.dismiss();
                        UserData response = (UserData) object;
                        if (response != null) {
                            if (response.getResponsecode() != 200) {
                                logout();
                            } else {
                                if (response.getData() != null) {
                                    app.getPreferences().setLoggedInUser(response);
                                }
                            }
                        } else {
                            logout();
                        }
                    }

                    @Override
                    public void onFailure(String apiResponse) {
                        if (pd != null && pd.isShowing()) pd.dismiss();
                    }
                });
            } else {
                Utils.alert_dialog(mContext);
            }
        } else {
            logout();
        }
    }

    public void getproducts() {
        if (cd.isConnectingToInternet()) {
            progressBar.setVisibility(View.VISIBLE);
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().getproducts(), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    progressBar.setVisibility(View.GONE);
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    data = (AllData) object;
                    if (data != null && data.getResponsecode() == 200) {
                        setSelectedCurrency();
                        if (data.getData() != null && data.getData().size() > 0) {
                            Set<String> offerIdSet = new HashSet<>();
                            Set<String> catIdSet = new HashSet<>();
                            for (int i = 0; i < data.getData().size(); i++) {
                                offerIdSet.add(data.getData().get(i).getCategoryId());
                                catIdSet.add(data.getData().get(i).getStyleId());
                            }
                            ArrayList<Offer> offerProductList = new ArrayList<>();
                            ArrayList<Category> categoryProductList = new ArrayList<>();
                            for (int i = 0; i < data.getData().size(); i++) {
                                Product product = data.getData().get(i);
                                if (offerIdSet.size() > 0 && offerIdSet.contains(product.getCategoryId())) {
                                    offerProductList.add(new Offer(product.getCategoryId(), product.getCategoryTitle()));
                                    offerIdSet.remove(product.getCategoryId());
                                }
                                if (catIdSet.size() > 0 && catIdSet.contains(product.getStyleId())) {
                                    categoryProductList.add(new Category(product.getStyleId(), product.getStyleTitle()));
                                    catIdSet.remove(product.getStyleId());
                                }
                            }

                            // Delete item of category id 1
                            for (int i = 0; i < offerProductList.size(); i++) {
                                if (offerProductList.get(i).getId().equals("1")) {
                                    offerProductList.remove(i);
                                    break;
                                }
                            }

                            vpOffers.setAdapter(new OfferPagerAdapter(mContext, offerProductList));
                            if (offerProductList.size() > 1) {
                                set_slider_animation(vpOffers);
                            } else {
                                try {
                                    stopAutoPlay();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
//                            rvOffers.autoScroll(offerProductList.size(), 2000);
                            rvCategories.setAdapter(new CategoryAdapter(mContext, categoryProductList));

                            if (offerProductList.size() == 0 && categoryProductList.size() == 0) {
                                tvError.setVisibility(View.VISIBLE);
                                btnRetry.setVisibility(View.VISIBLE);
                                tvError.setText("No Records available. Please try again later.");
                                llContent.setVisibility(View.GONE);
                                tvErrorOffers.setVisibility(View.GONE);
                                tvErrorCategories.setVisibility(View.GONE);
                            } else {
                                if (offerProductList.size() == 0) {
                                    vpOffers.setVisibility(View.GONE);
                                    tvErrorOffers.setVisibility(View.VISIBLE);
                                }
                                if (categoryProductList.size() == 0) {
                                    rvCategories.setVisibility(View.GONE);
                                    tvErrorCategories.setVisibility(View.VISIBLE);
                                }
                                tvError.setVisibility(View.GONE);
                                btnRetry.setVisibility(View.GONE);
                                llContent.setVisibility(View.VISIBLE);
                            }
                            if (data.getTestimonialList() != null && data.getTestimonialList().size() > 0) {
                                TestimonialPagerAdapter adapter = new TestimonialPagerAdapter(mContext, data.getTestimonialList());
                                vpTestimonials.setAdapter(adapter);
//                                cpiTestimonials.setViewPager(vpTestimonials);
                            }
                        } else {
                            tvError.setVisibility(View.VISIBLE);
                            btnRetry.setVisibility(View.VISIBLE);
                            tvError.setText("No Records available. Please try again later.");
                            llContent.setVisibility(View.GONE);
                            tvErrorOffers.setVisibility(View.GONE);
                            tvErrorCategories.setVisibility(View.GONE);
                        }
                    } else {
                        tvError.setVisibility(View.VISIBLE);
                        btnRetry.setVisibility(View.VISIBLE);
                        tvError.setText("No Records available. Please try again later.");
                        llContent.setVisibility(View.GONE);
                        tvErrorOffers.setVisibility(View.GONE);
                        tvErrorCategories.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(String apiResponse) {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()){
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    progressBar.setVisibility(View.GONE);
                    btnRetry.setVisibility(View.VISIBLE);
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Failed to load data. Please try again later.");
                    llContent.setVisibility(View.GONE);
                    tvErrorOffers.setVisibility(View.GONE);
                    tvErrorCategories.setVisibility(View.GONE);
                }
            }));
        } else {
            if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
            btnRetry.setVisibility(View.VISIBLE);
            tvError.setVisibility(View.VISIBLE);
            tvErrorOffers.setVisibility(View.GONE);
            tvErrorCategories.setVisibility(View.GONE);
            tvError.setText(Utils.NO_INTERNET_MSG);
            llContent.setVisibility(View.GONE);
            if (mContext != null) Utils.alert_dialog(mContext);
        }
    }

    public void setSelectedCurrency() {
        if (app.getPreferences().isLoggedInUser() && app.getPreferences().getLoggedInUser().getData() != null &&
                !TextUtils.isEmpty(app.getPreferences().getLoggedInUser().getData().getCity()) &&
                data.getCurrencyList() != null && data.getCurrencyList().size() > 0) {
            for (int i = 0; i < data.getCurrencyList().size(); i++) {
                if (data.getCurrencyList().get(i).getCityName().equalsIgnoreCase(app.getPreferences().getLoggedInUser().getData().getCity())) {
                    String currencyCode = data.getCurrencyList().get(i).getCurrencyCode();
                    if (currencyCode.equalsIgnoreCase("INR")) currencyCode = "\u20B9";
                    app.getPreferences().setSelectedCurrency(currencyCode);
                    app.getPreferences().setSelectedCurrencyMultiplier(data.getCurrencyList().get(i).getCurrencyMultiplier());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (navigationView != null) {
            tvName = navigationView.getHeaderView(0).findViewById(R.id.tvName);
            MenuItem nav_my_orders = navigationView.getMenu().findItem(R.id.nav_my_orders);
            MenuItem nav_my_appointment = navigationView.getMenu().findItem(R.id.nav_my_appointment);
            if (app.getPreferences().isLoggedInUser()) {
                if (nav_my_orders != null) nav_my_orders.setVisible(true);
                if (nav_my_appointment != null) nav_my_appointment.setVisible(true);
            } else {
                if (nav_my_orders != null) nav_my_orders.setVisible(false);
                if (nav_my_appointment != null) nav_my_appointment.setVisible(false);
            }
            if (app.getPreferences().isLoggedInUser() && app.getPreferences().getLoggedInUser() != null) {
                if (app.getPreferences().getLoggedInUser().getData() != null && tvName != null) {
                    Data data = app.getPreferences().getLoggedInUser().getData();
                    tvName.setText(data.getFirstName() + " " + data.getLastName());
                }
            } else {
                tvName.setText("Guest Login");
            }
            if (ivProfile != null && app.getPreferences().isLoggedInUser()) {
                GlideApp.with(mContext)
                        .load(BuildConfig.BASE_URL + BuildConfig.API_FOLDER + "/customerprofilepics/" + app.getPreferences().getLoggedInUser().getData().getCustomerId() + ".jpg")
                        .placeholder(R.drawable.user_icon)
                        .error(R.drawable.user_icon)
                        .skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(ivProfile);
            }
        }
        if (action_wishlist != null) {
            if (app.getPreferences().getWishlistData() != null && app.getPreferences().getWishlistData().size() > 0) {
                action_wishlist.setTitle("Wishlist(" + app.getPreferences().getWishlistData().size() + ")");
                action_wishlist.setVisible(true);
            } else
                action_wishlist.setVisible(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            logout();
        } else if (id == R.id.nav_my_orders) {
            startActivity(new Intent(mContext, OrdersActivity.class));
        } else if (id == R.id.nav_my_appointment) {
            startActivity(new Intent(mContext, AppointmentsActivity.class));
        } else if (id == R.id.nav_my_wishlist) {
            startActivity(new Intent(mContext, WishListActivity.class)
                    .putExtra("data", data));
        } else if (id == R.id.nav_how) {
            startActivity(new Intent(mContext, SliderActivity.class)
                    .putExtra("isFromMain", true));
        } else if (id == R.id.nav_my_profile) {
            if (app.getPreferences().isLoggedInUser()) {
                startActivity(new Intent(mContext, EditProfileActivity.class));
            } else {
                startActivity(new Intent(mContext, LoginActivity.class)
                        .putExtra("isFromWishlist", true));
            }
        } else if (id == R.id.nav_about_us) {
//            startActivity(new Intent(mContext, WebviewActivity.class)
//                    .putExtra("title", "About Us")
//                    .putExtra("url", "https://tailorsmart.in/about/"));
            startActivity(new Intent(mContext, AboutUsActivity.class));
        } else if (id == R.id.nav_refer_tailor) {
            startActivity(new Intent(mContext, QuickReferralActivity.class));
        } else if (id == R.id.nav_terms) {
            startActivity(new Intent(mContext, WebviewActivity.class)
                    .putExtra("title", mContext.getResources().getString(R.string.terms_amp_conditions))
                    .putExtra("url", "https://drive.google.com/viewerng/viewer?embedded=true&url=" + "https://theecca.com/terms_and_conditions.pdf"));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void alladvertisements() {
//        ConnectionDetector cd = new ConnectionDetector(mContext);
//        if (cd.isConnectingToInternet()) {
//            CustomProgressDialog pd = new CustomProgressDialog(mContext);
//            pd.show();
//            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().alladvertisements(), new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                    if (pd.isShowing()) pd.dismiss();
//                    BannerData bannerData = (BannerData) object;
//                    if (bannerData != null && bannerData.getResponsecode() == 200 && bannerData.getBannerList() != null &&
//                            bannerData.getBannerList().size() > 0) {
//                        BannerPagerAdapter customPagerAdapter = new BannerPagerAdapter(mContext, bannerData.getBannerList());
//                        viewpagerBanner.setAdapter(customPagerAdapter);
//                        circlePageIndicator.setViewPager(viewpagerBanner);
//                        if (bannerData.getBannerList().size() > 1) {
//                            set_slider_animation(viewpagerBanner);
//                        } else {
//                            try {
//                                stopAutoPlay();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        rrBanner.setVisibility(View.VISIBLE);
//                    } else {
//                        viewpagerBanner.setAdapter(null);
//                        rrBanner.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                    if (pd.isShowing()) pd.dismiss();
//                    Log.e("in", "error " + apiResponse);
//                    if (!TextUtils.isEmpty(apiResponse))
//                        Utils.showLongToast(mContext, apiResponse);
//                }
//            }));
//        } else {
//            Utils.alert_dialog(mContext);
//        }
//    }

    private void logout() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(mContext);
        if (acct != null) {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, task -> {
                        app.getPreferences().logOutUser();
                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    })
                    .addOnFailureListener(this, Throwable::printStackTrace);
        } else {
            app.getPreferences().logOutUser();
            startActivity(new Intent(mContext, LoginActivity.class));
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wishlist, menu);
        action_wishlist = menu.findItem(R.id.action_wishlist);
        if (action_wishlist != null) {
            if (app.getPreferences().getWishlistData() != null && app.getPreferences().getWishlistData().size() > 0) {
                action_wishlist.setTitle("Wishlist(" + app.getPreferences().getWishlistData().size() + ")");
                action_wishlist.setVisible(true);
            } else
                action_wishlist.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_wishlist: {
                if (data != null) {
                    startActivity(new Intent(mContext, WishListActivity.class)
                            .putExtra("data", data));
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void uploadImage(String filePath) {
        String strFile = "";
        try {
            Bitmap bm = new Compressor(mContext).compressToBitmap(new File(filePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bm != null) {
                bm.compress(Bitmap.CompressFormat.JPEG, 40, baos); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                strFile = Base64.encodeToString(b, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, RequestBody> map = new HashMap<>();
        map.put("imageName", createPartFromString("customerprofilepics/" + app.getPreferences().getLoggedInUser().getData().getCustomerId() + ".jpg"));
        map.put("angle", createPartFromString("0"));
        map.put("imageData", createPartFromString(strFile));
        ConnectionDetector cd = new ConnectionDetector(mContext);
        if (cd.isConnectingToInternet()) {
            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
//            pd.setTitle("Loading...");
            pd.show();
            callList.add(app.getApiRequestHelper().callRetrofit(app.getApiRequestHelper().getAppService().uploadimage(map), new ApiRequestHelper.OnRequestComplete() {
                @Override
                public void onSuccess(Object object) {
                    pd.dismiss();
                    Utils.showShortToast(mContext, "Profile updated successfully");
                }

                @Override
                public void onFailure(String apiResponse) {
                    pd.dismiss();
                    Utils.showLongToast(mContext, apiResponse);
                }
            }));
        } else {
            Utils.alert_dialog(mContext);
        }
    }

    private void set_image() {
        new AlertDialog.Builder(mContext).setTitle("Select Picture")
                .setItems(items, (dialog, item) -> {
                    switch (items[item]) {
                        case "Camera":
                            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), REQUEST_CAMERA);
                            break;
                        case "Gallery":
                            if (Build.VERSION.SDK_INT <= 19) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                            } else if (Build.VERSION.SDK_INT > 19) {
                                startActivityForResult(Intent.createChooser(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                                        , "Select Picture"), PICK_IMAGE);
                            }
                            break;
                        case "Cancel":
                            dialog.dismiss();
                            break;
                    }
                }).show();
    }


    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MultipartBody.FORM, descriptionString);
    }

    public static Bitmap decodeSampledBitmapFromResource(String strPath, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        if (Build.VERSION.SDK_INT < 21) {
//            options.inPurgeable = true;
//        }else {
//            options.inBitmap= true;
//        }
        BitmapFactory.decodeFile(strPath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(strPath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            String selectedImagePath = getRealPathFromURIForGallery(selectedImageUri);
            uploadImage(selectedImagePath);
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && null != data) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            profileImage.setImageBitmap(photo);

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(mContext.getApplicationContext(), photo);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
            File finalFile = new File(getRealPathFromURI(tempUri));
//            decodeFile(finalFile.toString());
            uploadImage(finalFile.getAbsolutePath());
        }
    }

    public String getRealPathFromURIForGallery(Uri uri) {
        if (uri == null) {
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void set_slider_animation(ViewPager vp) {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            int animationDuration = 1000;
            FixedSpeedScroller scroller = new FixedSpeedScroller(vp.getContext(), new AccelerateDecelerateInterpolator(), animationDuration);
            mScroller.set(vp, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (vp != null && vp.getAdapter() != null && vp.getAdapter().getCount() > 1)
            startAutoPlay();
        else
            stopAutoPlay();
        vp.setOnTouchListener((view, motionEvent) -> {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (vp != null && vp.getAdapter() != null && vp.getAdapter().getCount() > 1)
                        startAutoPlay();
                    else
                        stopAutoPlay();
                    break;

            }
            return false;
        });
    }

    public class FixedSpeedScroller extends Scroller {
        int duration;

        public FixedSpeedScroller(Context context, int duration) {
            super(context);
            this.duration = duration;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, int duration) {
            super(context, interpolator);
            this.duration = duration;
        }

        public FixedSpeedScroller(Context context, Interpolator interpolator, boolean flywheel, int duration) {
            super(context, interpolator, flywheel);
            this.duration = duration;
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, this.duration);
        }
    }

    private static final int MESSAGE_SCROLL = 123;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_SCROLL) {
                if (vpOffers != null) {
                    vpOffers.setCurrentItem(vpOffers.getCurrentItem() + 1);
                    startAutoPlay();
                }
            }
        }
    };

    public void stopAutoPlay() {
        handler.removeMessages(MESSAGE_SCROLL);
    }

    public void startAutoPlay() {
        stopAutoPlay();
        int homeColumnScrollInterval = 4;
        handler.sendEmptyMessageDelayed(MESSAGE_SCROLL, homeColumnScrollInterval * 1000);
    }
}
