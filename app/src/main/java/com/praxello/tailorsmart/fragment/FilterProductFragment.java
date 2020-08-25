package com.praxello.tailorsmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.praxello.tailorsmart.ProductListActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.adapter.CategoryFilterAdapter;
import com.praxello.tailorsmart.adapter.StyleFilterAdapter;
import com.praxello.tailorsmart.adapter.SubStyleFilterAdapter;
import com.praxello.tailorsmart.model.AllData;
import com.praxello.tailorsmart.model.Category;
import com.praxello.tailorsmart.model.Offer;
import com.praxello.tailorsmart.model.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterProductFragment extends BottomSheetDialogFragment {
    @BindView(R.id.rbStyle)
    RadioButton rbStyle;
    @BindView(R.id.rbPrice)
    RadioButton rbPrice;
    @BindView(R.id.rbSubStyle)
    RadioButton rbSubStyle;
    @BindView(R.id.rbCategory)
    RadioButton rbCategory;
    @BindView(R.id.rvStyle)
    RecyclerView rvStyle;
    @BindView(R.id.rvSubStyle)
    RecyclerView rvSubStyle;
    @BindView(R.id.rvCategory)
    RecyclerView rvCategory;
    @BindView(R.id.rbLowToHigh)
    RadioButton rbLowToHigh;
    @BindView(R.id.rbHighToLow)
    RadioButton rbHighToLow;
    @BindView(R.id.rbDefault)
    RadioButton rbDefault;
    @BindView(R.id.rgPricing)
    RadioGroup rgPricing;
    @BindView(R.id.vwStyle)
    View vwStyle;
    @BindView(R.id.vwSubStyle)
    View vwSubStyle;
    @BindView(R.id.vwCategory)
    View vwCategory;
    private Unbinder unbinder;
    private AllData data;
    Context mContext;
    List<Offer> categoryList = new ArrayList<>();
    List<Category> styleList = new ArrayList<>();
    List<Category> subStyleList = new ArrayList<>();
    private StyleFilterAdapter styleFilterAdapter;
    private SubStyleFilterAdapter subStyleFilterAdapter;
    private CategoryFilterAdapter categoryFilterAdapter;
    boolean isPriceOnly;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
        //set to adjust screen height automatically, when soft keyboard appears on screen
        if (getDialog() != null && getDialog().getWindow() != null)
            getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_filter, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            data = getArguments().getParcelable("data");
            isPriceOnly = getArguments().getBoolean("isPriceOnly", false);

            Set<String> offerIdSet = new HashSet<>();
            Set<String> styleIdSet = new HashSet<>();
            Set<String> subStyleIdSet = new HashSet<>();
            for (int i = 0; i < data.getData().size(); i++) {
                offerIdSet.add(data.getData().get(i).getCategoryId());
                styleIdSet.add(data.getData().get(i).getStyleId());
                subStyleIdSet.add(data.getData().get(i).getSubStyleId());
            }
            for (int i = 0; i < data.getData().size(); i++) {
                Product product = data.getData().get(i);
                if (offerIdSet.size() > 0 && offerIdSet.contains(product.getCategoryId())) {
                    categoryList.add(new Offer(product.getCategoryId(), product.getCategoryTitle()));
                    offerIdSet.remove(product.getCategoryId());
                }
                if (styleIdSet.size() > 0 && styleIdSet.contains(product.getStyleId())) {
                    styleList.add(new Category(product.getStyleId(), product.getStyleTitle()));
                    styleIdSet.remove(product.getStyleId());
                }
                if (subStyleIdSet.size() > 0 && subStyleIdSet.contains(product.getSubStyleId())) {
                    subStyleList.add(new Category(product.getSubStyleId(), product.getSubStyleTitle()));
                    subStyleIdSet.remove(product.getSubStyleId());
                }
            }
        }

        if (isPriceOnly) {
            rvStyle.setVisibility(View.GONE);
            rbPrice.setChecked(true);
            rgPricing.setVisibility(View.VISIBLE);
            vwStyle.setVisibility(View.GONE);
            vwSubStyle.setVisibility(View.GONE);
            vwCategory.setVisibility(View.GONE);
        } else {
            rbStyle.setChecked(true);
        }
        rvStyle.setLayoutManager(new LinearLayoutManager(mContext));
        rvSubStyle.setLayoutManager(new LinearLayoutManager(mContext));
        rvCategory.setLayoutManager(new LinearLayoutManager(mContext));

//        Log.e("open filter", ProductListActivity.activity.selectedStyle + "||" +
//                ProductListActivity.activity.selectedSubStyle + "||" + ProductListActivity.activity.selectedCategory);

        styleFilterAdapter = new StyleFilterAdapter(mContext, styleList, ProductListActivity.activity.selectedStyle);
        rvStyle.setAdapter(null);
        rvStyle.setAdapter(styleFilterAdapter);

        subStyleFilterAdapter = new SubStyleFilterAdapter(mContext, subStyleList, ProductListActivity.activity.selectedSubStyle);
        rvSubStyle.setAdapter(null);
        rvSubStyle.setAdapter(subStyleFilterAdapter);

        categoryFilterAdapter = new CategoryFilterAdapter(mContext, categoryList, ProductListActivity.activity.selectedCategory);
        rvCategory.setAdapter(null);
        rvCategory.setAdapter(categoryFilterAdapter);

        if (ProductListActivity.activity.selectedPrice.equals("L2H")) {
            rbLowToHigh.setChecked(true);
        } else if (ProductListActivity.activity.selectedPrice.equals("H2L")) {
            rbHighToLow.setChecked(true);
        } else {
            rbDefault.setChecked(true);
        }
    }

    @OnClick({R.id.ivBack, R.id.rbStyle, R.id.rbSubStyle, R.id.rbCategory, R.id.rbPrice, R.id.btnApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                dismiss();
                break;
            case R.id.rbStyle:
                rvStyle.setVisibility(View.VISIBLE);
                rvSubStyle.setVisibility(View.GONE);
                rvCategory.setVisibility(View.GONE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbSubStyle:
                filterSubStyleList();
                rvStyle.setVisibility(View.GONE);
                rvSubStyle.setVisibility(View.VISIBLE);
                rvCategory.setVisibility(View.GONE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbCategory:
                rvStyle.setVisibility(View.GONE);
                rvSubStyle.setVisibility(View.GONE);
                rvCategory.setVisibility(View.VISIBLE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbPrice:
                rvStyle.setVisibility(View.GONE);
                rvSubStyle.setVisibility(View.GONE);
                rvCategory.setVisibility(View.GONE);
                rgPricing.setVisibility(View.VISIBLE);
                break;
            case R.id.btnApply:
                setSelectedStyle();
                setSelectedSubStyle();
                setSelectedCategory();
                if (rbLowToHigh.isChecked())
                    ProductListActivity.activity.selectedPrice = "L2H";
                else if (rbHighToLow.isChecked())
                    ProductListActivity.activity.selectedPrice = "H2L";
                else if (rbDefault.isChecked())
                    ProductListActivity.activity.selectedPrice = "";
                else
                    ProductListActivity.activity.selectedPrice = "";
//                Log.e("selelcted filter", ProductListActivity.activity.selectedStyle + "||" +
//                        ProductListActivity.activity.selectedSubStyle + "||" + ProductListActivity.activity.selectedCategory);
                ProductListActivity.activity.setProductList();
                dismiss();
                break;
        }
    }

    // Filter Sub Style List based on Style
    private void filterSubStyleList() {
        setSelectedStyle();
        if (!TextUtils.isEmpty(ProductListActivity.activity.selectedStyle)) {
            Set<String> subStyleIdSet = new HashSet<>();
            for (int i = 0; i < data.getData().size(); i++) {
                if (ProductListActivity.activity.selectedStyle.contains(data.getData().get(i).getStyleId())) {
                    subStyleIdSet.add(data.getData().get(i).getSubStyleId());
                }
            }
            if (subStyleIdSet.size() > 0) {
                List<Category> list = new ArrayList<>();
                for (int i = 0; i < data.getData().size(); i++) {
                    Product product = data.getData().get(i);
                    if (subStyleIdSet.contains(product.getSubStyleId())) {
                        list.add(new Category(product.getSubStyleId(), product.getSubStyleTitle()));
                        subStyleIdSet.remove(product.getSubStyleId());
                    }
                }
                subStyleFilterAdapter = new SubStyleFilterAdapter(mContext, list, ProductListActivity.activity.selectedSubStyle);
                rvSubStyle.setAdapter(null);
                rvSubStyle.setAdapter(subStyleFilterAdapter);
            } else {
                rvSubStyle.setAdapter(null);
            }
        } else {
            subStyleFilterAdapter = new SubStyleFilterAdapter(mContext, subStyleList, ProductListActivity.activity.selectedSubStyle);
            rvSubStyle.setAdapter(null);
            rvSubStyle.setAdapter(subStyleFilterAdapter);
        }
    }

    private void setSelectedCategory() {
        if (categoryFilterAdapter != null && categoryFilterAdapter.getCategoryFilterList() != null && categoryFilterAdapter.getCategoryFilterList().size() > 0) {
            List<Offer> list = categoryFilterAdapter.getCategoryFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getId());
                }
            }
            ProductListActivity.activity.selectedCategory = strings.toString().replace("[", "").replace("]", "");
        }
    }

    private void setSelectedSubStyle() {
        if (subStyleFilterAdapter != null && subStyleFilterAdapter.getSubStyleFilterList() != null && subStyleFilterAdapter.getSubStyleFilterList().size() > 0) {
            List<Category> list = subStyleFilterAdapter.getSubStyleFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getId());
                }
            }
            ProductListActivity.activity.selectedSubStyle = strings.toString().replace("[", "").replace("]", "");
        }
    }

    private void setSelectedStyle() {
        if (styleFilterAdapter != null && styleFilterAdapter.getStyleFilterList() != null && styleFilterAdapter.getStyleFilterList().size() > 0) {
            List<Category> list = styleFilterAdapter.getStyleFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getId());
                }
            }
            ProductListActivity.activity.selectedStyle = strings.toString().replace("[", "").replace("]", "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }
}
