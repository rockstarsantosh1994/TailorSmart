package com.praxello.tailorsmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.praxello.tailorsmart.ProductDetailsActivity;
import com.praxello.tailorsmart.R;
import com.praxello.tailorsmart.adapter.FabricBrandFilterAdapter;
import com.praxello.tailorsmart.adapter.FabricBrandTypeFilterAdapter;
import com.praxello.tailorsmart.adapter.FabricColorFilterAdapter;
import com.praxello.tailorsmart.model.Brand;
import com.praxello.tailorsmart.model.Category;
import com.praxello.tailorsmart.model.ColorObj;
import com.praxello.tailorsmart.model.Fabric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class FilterFabricFragment extends BottomSheetDialogFragment {
    @BindView(R.id.rbPrice)
    RadioButton rbPrice;
    @BindView(R.id.rvBrand)
    RecyclerView rvBrand;
    @BindView(R.id.rvColor)
    RecyclerView rvColor;
    @BindView(R.id.rbLowToHigh)
    RadioButton rbLowToHigh;
    @BindView(R.id.rbHighToLow)
    RadioButton rbHighToLow;
    @BindView(R.id.rbDefault)
    RadioButton rbDefault;
    @BindView(R.id.rgPricing)
    RadioGroup rgPricing;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.btnApply)
    Button btnApply;
    @BindView(R.id.llTopBar)
    RelativeLayout llTopBar;
    @BindView(R.id.vw_divider)
    View vwDivider;
    @BindView(R.id.rbBrand)
    RadioButton rbBrand;
    @BindView(R.id.rbColor)
    RadioButton rbColor;
    @BindView(R.id.rbType)
    RadioButton rbType;
    @BindView(R.id.rvType)
    RecyclerView rvType;
    private Unbinder unbinder;
    private ArrayList<Fabric> productFabricList;
    Context mContext;
    List<Brand> brandlist = new ArrayList<>();
    List<Category> brandTypelist = new ArrayList<>();
    List<ColorObj> colorList = new ArrayList<>();
    private FabricBrandFilterAdapter fabricBrandFilterAdapter;
    private FabricBrandTypeFilterAdapter fabricBrandTypeFilterAdapter;
    private FabricColorFilterAdapter fabricColorFilterAdapter;

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
        View inflate = inflater.inflate(R.layout.fragment_filter_fabric, container, false);
        unbinder = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            productFabricList = getArguments().getParcelableArrayList("productFabricList");
            Set<String> colorSet = new HashSet<>();
            Set<String> brandSet = new HashSet<>();
            Set<String> brandTypeSet = new HashSet<>();
            for (int i = 0; i < productFabricList.size(); i++) {
                colorSet.add(productFabricList.get(i).getHexColor());
                brandSet.add(productFabricList.get(i).getFabricBrand());
                brandTypeSet.add(productFabricList.get(i).getFabricType());
            }
            for (int i = 0; i < productFabricList.size(); i++) {
                Fabric fabric = productFabricList.get(i);
                if (colorSet.size() > 0 && colorSet.contains(fabric.getHexColor())) {
                    colorList.add(new ColorObj(fabric.getFabricId(), fabric.getHexColor(), fabric.getColorName()));
                    colorSet.remove(fabric.getColorName());
                }
                if (brandSet.size() > 0 && brandSet.contains(fabric.getFabricBrand())) {
                    brandlist.add(new Brand(fabric.getFabricId(), fabric.getFabricBrand()));
                    brandSet.remove(fabric.getFabricBrand());
                }
                if (brandTypeSet.size() > 0 && brandTypeSet.contains(fabric.getFabricType())) {
                    brandTypelist.add(new Category(fabric.getFabricId(), fabric.getFabricType()));
                    brandTypeSet.remove(fabric.getFabricType());
                }
            }
        }

        rbPrice.setChecked(true);
        rvColor.setLayoutManager(new LinearLayoutManager(mContext));
        rvBrand.setLayoutManager(new LinearLayoutManager(mContext));
        rvType.setLayoutManager(new LinearLayoutManager(mContext));

//        Log.e("open filter", ProductDetailsActivity.activity.selectedColor + "||" +
//                ProductDetailsActivity.activity.selectedBrand);

        fabricColorFilterAdapter = new FabricColorFilterAdapter(mContext, colorList, ProductDetailsActivity.activity.selectedColor);
        rvColor.setAdapter(null);
        rvColor.setAdapter(fabricColorFilterAdapter);

        fabricBrandFilterAdapter = new FabricBrandFilterAdapter(mContext, brandlist, ProductDetailsActivity.activity.selectedBrand);
        rvBrand.setAdapter(null);
        rvBrand.setAdapter(fabricBrandFilterAdapter);

        fabricBrandTypeFilterAdapter = new FabricBrandTypeFilterAdapter(mContext, brandTypelist, ProductDetailsActivity.activity.selectedType);
        rvType.setAdapter(null);
        rvType.setAdapter(fabricBrandTypeFilterAdapter);

        if (ProductDetailsActivity.activity.selectedPrice.equals("L2H")) {
            rbLowToHigh.setChecked(true);
        } else if (ProductDetailsActivity.activity.selectedPrice.equals("H2L")) {
            rbHighToLow.setChecked(true);
        } else {
            rbDefault.setChecked(true);
        }
    }

    @OnClick({R.id.ivBack, R.id.rbBrand, R.id.rbColor, R.id.rbType, R.id.rbPrice, R.id.btnApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                dismiss();
                break;
            case R.id.rbBrand:
                rvType.setVisibility(View.GONE);
                rvBrand.setVisibility(View.VISIBLE);
                rvColor.setVisibility(View.GONE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbColor:
                rvType.setVisibility(View.GONE);
                rvBrand.setVisibility(View.GONE);
                rvColor.setVisibility(View.VISIBLE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbType:
                rvType.setVisibility(View.VISIBLE);
                rvBrand.setVisibility(View.GONE);
                rvColor.setVisibility(View.GONE);
                rgPricing.setVisibility(View.GONE);
                break;
            case R.id.rbPrice:
                rvType.setVisibility(View.GONE);
                rvBrand.setVisibility(View.GONE);
                rvColor.setVisibility(View.GONE);
                rgPricing.setVisibility(View.VISIBLE);
                break;
            case R.id.btnApply:
                setSelectedBrand();
                setSelectedBrandType();
                setSelectedColor();
                if (rbLowToHigh.isChecked())
                    ProductDetailsActivity.activity.selectedPrice = "L2H";
                else if (rbHighToLow.isChecked())
                    ProductDetailsActivity.activity.selectedPrice = "H2L";
                else if (rbDefault.isChecked())
                    ProductDetailsActivity.activity.selectedPrice = "";
                else
                    ProductDetailsActivity.activity.selectedPrice = "";
//                Log.e("selelcted filter", ProductDetailsActivity.activity.selectedColor + "||" +
//                        ProductDetailsActivity.activity.selectedBrand + "||" + ProductDetailsActivity.activity.selectedPrice);
                ProductDetailsActivity.activity.setFabricList();
                dismiss();
                break;
        }
    }

    private void setSelectedBrand() {
        if (fabricBrandFilterAdapter != null && fabricBrandFilterAdapter.getFilterList() != null && fabricBrandFilterAdapter.getFilterList().size() > 0) {
            List<Brand> list = fabricBrandFilterAdapter.getFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getTitle());
                }
            }
            ProductDetailsActivity.activity.selectedBrand = strings.toString().replace("[", "").replace("]", "");
        }
    }

    private void setSelectedBrandType() {
        if (fabricBrandTypeFilterAdapter != null && fabricBrandTypeFilterAdapter.getStyleFilterList() != null && fabricBrandTypeFilterAdapter.getStyleFilterList().size() > 0) {
            List<Category> list = fabricBrandTypeFilterAdapter.getStyleFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getTitle());
                }
            }
            ProductDetailsActivity.activity.selectedType = strings.toString().replace("[", "").replace("]", "");
        }
    }

    private void setSelectedColor() {
        if (fabricColorFilterAdapter != null && fabricColorFilterAdapter.getStyleFilterList() != null && fabricColorFilterAdapter.getStyleFilterList().size() > 0) {
            List<ColorObj> list = fabricColorFilterAdapter.getStyleFilterList();
            List<String> strings = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).isChecked()) {
                    strings.add(list.get(i).getHex());
                }
            }
            ProductDetailsActivity.activity.selectedColor = strings.toString().replace("[", "").replace("]", "");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) unbinder.unbind();
    }
}
