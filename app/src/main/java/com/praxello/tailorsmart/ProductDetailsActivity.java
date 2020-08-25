package com.praxello.tailorsmart;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.praxello.tailorsmart.adapter.FabricAdapter;
import com.praxello.tailorsmart.fragment.FilterFabricFragment;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Fabric;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.utils.Utils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductDetailsActivity extends BaseActivity {
    @BindView(R.id.header)
    ImageView ivProduct;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvSubTitle)
    TextView tvSubTitle;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvDesc)
    TextView tvDesc;
    @BindView(R.id.tvError)
    TextView tvError;
    @BindView(R.id.rvFabrics)
    RecyclerView rvFabrics;
    AllData data;
    String productId = "";
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btnAddToWishlist)
    Button btnAddToWishlist;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.etSearch)
    MaterialEditText etSearch;
    @BindView(R.id.ivFilter)
    ImageView ivFilter;
    @BindView(R.id.rrSearch)
    RelativeLayout rrSearch;
    private Product product = null;
    private FabricAdapter fabricAdapter;
    private MenuItem action_wishlist;
    public static ProductDetailsActivity activity;
    public String selectedColor = "", selectedBrand = "", selectedType = "", selectedPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        toolbarTitle.setText("Product Details");
        data = getIntent().getParcelableExtra("data");
        product = getIntent().getParcelableExtra("product");

        rvFabrics.setLayoutManager(new LinearLayoutManager(mContext));

        if (product != null) {
            productId = product.getProductId();
            tvTitle.setText(product.getProductTitle());
            tvSubTitle.setText(product.getProductSubTitle());
            setPriceUponSelectedCurrency();
            tvDesc.setText(product.getProductDetails());
            GlideApp.with(mContext).load("http://103.127.146.5/~tailor/Tailorsmart/mobileimages/product/"
                    + productId + ".jpg").thumbnail(0.1f).into(ivProduct);
            ivProduct.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, FullScreenImageActivity.class)
                        .putExtra("title", product.getProductTitle())
                        .putExtra("urlPhotoClick", "http://103.127.146.5/~tailor/Tailorsmart/mobileimages/product/"
                                + productId + ".jpg"));
            });
        }

        setFabricAdapter();
    }

    public void setFabricAdapter() {
        if (data != null && data.getFabrics() != null && data.getFabrics().size() > 0 && !TextUtils.isEmpty(productId)) {
            ArrayList<Fabric> list = new ArrayList<>();
            for (int i = 0; i < data.getFabrics().size(); i++) {
                if (data.getFabrics().get(i).getProductId().equals(productId)) {
                    list.add(data.getFabrics().get(i));
                }
            }
            if (list.size() > 0) {
                fabricAdapter = new FabricAdapter(mContext, list);
                rvFabrics.setAdapter(null);
                rvFabrics.setAdapter(fabricAdapter);
                rvFabrics.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.GONE);
//                if (app.getPreferences().isAddedToWishlist(product)) {
//                    btnAddToWishlist.setVisibility(View.GONE);
//                } else {
                btnAddToWishlist.setVisibility(View.VISIBLE);
//                }
                rrSearch.setVisibility(View.VISIBLE);
                etSearch.addTextChangedListener(new TextWatcher() {
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (!TextUtils.isEmpty(s.toString())) {
                            fabricAdapter.filter(s.toString());
                        } else {
                            fabricAdapter.filter("");
                        }
                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    public void afterTextChanged(Editable s) {
                    }
                });
                ivFilter.setOnClickListener(view -> {
                    FilterFabricFragment filterProductFragment = new FilterFabricFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("productFabricList", list);
                    filterProductFragment.setArguments(bundle);
                    filterProductFragment.show(getSupportFragmentManager(), "FilterFabricFragment");
                });
            } else {
                rrSearch.setVisibility(View.GONE);
//                btnAddToWishlist.setVisibility(View.GONE);
                tvError.setVisibility(View.VISIBLE);
                rvFabrics.setVisibility(View.GONE);
            }
        } else {
            rrSearch.setVisibility(View.GONE);
//            btnAddToWishlist.setVisibility(View.GONE);
            tvError.setVisibility(View.VISIBLE);
            rvFabrics.setVisibility(View.GONE);
        }
    }

    public void setPriceUponSelectedCurrency() {
        if (product != null && !TextUtils.isEmpty(product.getPrice())) {
            String selectedCurrency = app.getPreferences().getSelectedCurrency();
            String selectedCurrencyMultiplier = app.getPreferences().getSelectedCurrencyMultiplier();
            String price = String.valueOf(Double.parseDouble(product.getPrice()) * Double.parseDouble(selectedCurrencyMultiplier));
            product.setCalculatedPrice(price);
            price = Utils.format2Dec(Double.parseDouble(product.getCalculatedPrice()));
            if (Double.parseDouble(product.getCalculatedPrice()) == 0) {
                tvPrice.setVisibility(View.GONE);
            } else {
                if (!TextUtils.isEmpty(product.getIsPriceVariable()) && product.getIsPriceVariable().equals("1"))
                    price = price + Utils.fromHtml("<sup><small>*</small></sup>");
                tvPrice.setText(selectedCurrency + " " + price);
                tvPrice.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (action_wishlist != null) {
            if (app.getPreferences().getWishlistData() != null && app.getPreferences().getWishlistData().size() > 0) {
                action_wishlist.setTitle("Wishlist(" + app.getPreferences().getWishlistData().size() + ")");
                action_wishlist.setVisible(true);
            } else
                action_wishlist.setVisible(false);
        }
        setPriceUponSelectedCurrency();
    }

    public void setFabricList() {
        ArrayList<Fabric> list = new ArrayList<>();
        for (int i = 0; i < data.getFabrics().size(); i++) {
            if (data.getFabrics().get(i).getProductId().equals(productId)) {
                list.add(data.getFabrics().get(i));
            }
        }
        if (list.size() > 0) {
            ArrayList<Fabric> filteredList = new ArrayList<>();
            if (TextUtils.isEmpty(selectedBrand) && TextUtils.isEmpty(selectedColor) && TextUtils.isEmpty(selectedType)) {
                filteredList.addAll(list);
            } else {
                if (!TextUtils.isEmpty(selectedBrand)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (selectedBrand.contains(",")) {
                            String[] selBrands = selectedBrand.split(",");
                            for (String selBrand : selBrands) {
                                if (selBrand.equals(list.get(i).getFabricBrand())) {
                                    filteredList.add(list.get(i));
                                }
                            }
                        } else {
                            if (selectedBrand.equals(list.get(i).getFabricBrand())) {
                                filteredList.add(list.get(i));
                            }
                        }
                    }
                }
                if (!TextUtils.isEmpty(selectedColor)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (selectedColor.contains(list.get(i).getHexColor())) {
                            boolean isProdAdded = false;
                            for (int j = 0; j < filteredList.size(); j++) {
                                if (list.get(i).getFabricId().equals(filteredList.get(j).getFabricId())) {
                                    isProdAdded = true;
                                    break;
                                }
                            }
                            if (!isProdAdded) {
                                filteredList.add(list.get(i));
                            }
                        }
                    }
                }
                if (!TextUtils.isEmpty(selectedType)) {
                    for (int i = 0; i < list.size(); i++) {
                        if (selectedType.contains(list.get(i).getFabricType())) {
                            boolean isProdAdded = false;
                            for (int j = 0; j < filteredList.size(); j++) {
                                if (list.get(i).getFabricId().equals(filteredList.get(j).getFabricId())) {
                                    isProdAdded = true;
                                    break;
                                }
                            }
                            if (!isProdAdded) {
                                filteredList.add(list.get(i));
                            }
                        }
                    }
                }
            }
            if (filteredList.size() > 0) {
                if (!TextUtils.isEmpty(selectedPrice)) {
                    if (selectedPrice.equals("L2H")) {
                        Collections.sort(filteredList, (obj1, obj2) -> {
                            // ## Ascending order
                            return Double.valueOf(obj1.fabricPrice).compareTo(Double.valueOf(obj2.fabricPrice)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            // return Integer.valueOf(obj2.empId).compareTo(Integer.valueOf(obj1.empId)); // To compare integer values
                        });
                    } else {
                        Collections.sort(filteredList, (obj1, obj2) -> {
                            // ## Ascending order
//                            return Double.valueOf(obj1.price).compareTo(Double.valueOf(obj2.price)); // To compare integer values

                            // ## Descending order
                            // return obj2.firstName.compareToIgnoreCase(obj1.firstName); // To compare string values
                            return Double.valueOf(obj2.fabricPrice).compareTo(Double.valueOf(obj1.fabricPrice)); // To compare integer values
                        });
                    }
                }
                rvFabrics.setAdapter(null);
                fabricAdapter = new FabricAdapter(mContext, filteredList);
                rvFabrics.setAdapter(fabricAdapter);
                etSearch.setText(etSearch.getText().toString().trim());
                tvError.setVisibility(View.GONE);
                rvFabrics.setVisibility(View.VISIBLE);
            } else {
                tvError.setVisibility(View.VISIBLE);
                rvFabrics.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.btnAddToWishlist)
    public void onViewClicked() {
        if (fabricAdapter != null) {
            ArrayList<Fabric> fabrics = fabricAdapter.getList();
            if (fabrics != null && fabrics.size() > 0) {
                ArrayList<Fabric> selFabricList = new ArrayList<>();
                for (int i = 0; i < fabrics.size(); i++) {
                    if (fabrics.get(i).isChecked()) {
                        selFabricList.add(fabrics.get(i));
                    }
                }
//                if (selFabricList.size() == 0) {
//                    Utils.showLongToast(mContext, "Please Select Fabric");
//                    scrollToView(scrollView, rvFabrics);
//                    return;
//                }
                if (selFabricList.size() > 15) {
                    Utils.showLongToast(mContext, "Maximum 15 Fabrics can be selected");
                    scrollToView(scrollView, rvFabrics);
                    return;
                }
                product.setFabricList(selFabricList);
            }
        }
        if (app.getPreferences().addToWishlist(product)) {
            if (action_wishlist != null) {
                action_wishlist.setVisible(true);
                action_wishlist.setTitle("Wishlist(" + app.getPreferences().getWishlistData().size() + ")");
            }
            Utils.showLongToast(mContext, "Added to wishlist successfully");
//                    btnAddToWishlist.setVisibility(View.GONE);
        } else {
            app.getPreferences().updateWishlist(product);
            Utils.showLongToast(mContext, "Wishlist updated successfully");
//                    btnAddToWishlist.setVisibility(View.GONE);
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
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Used to scroll to the given view.
     *
     * @param scrollViewParent Parent ScrollView
     * @param view             View to which we need to scroll.
     */
    private void scrollToView(final NestedScrollView scrollViewParent, final View view) {
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumulated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }
}
