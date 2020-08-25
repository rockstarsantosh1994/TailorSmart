package com.praxello.tailorsmart;

import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;

public class FilterActivity extends BaseActivity {
//    @BindView(R.id.rb_age)
//    RadioButton rbAge;
//    @BindView(R.id.rv_ageFilter)
//    RecyclerView rvAgeFilter;
//    @BindView(R.id.rv_subCatFilter)
//    RecyclerView rvSubCatFilter;
//    @BindView(R.id.rv_neighbourFilter)
//    RecyclerView rvNeighbourFilter;
//    @BindView(R.id.rb_neighbour)
//    RadioButton rbNeighbour;
//    @BindView(R.id.rb_subcategory)
//    RadioButton rbSubcategory;
//    private List<Filter> ageFilterList;
//    private List<Filter> neighbourFilterList;
//    private List<Filter> subcategoryfilterList;
//    private AgeFilterAdapter ageFilterAdapter;
//    private NeighbourFilterAdapter neighbourFilterAdapter;
//    private SubCatFilterAdapter subCatFilterAdapter;
//    private BMAListFragment bmaListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        rbAge.setChecked(true);
//        if (MainActivity.mainActivity != null) {
//            Fragment fragment = MainActivity.mainActivity.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
//            if (fragment instanceof BMAListFragment) {
//                bmaListFragment = (BMAListFragment) fragment;
//                if (bmaListFragment.subcategory != null)
//                    rbSubcategory.setVisibility(View.GONE);
//            }
//            agefilter();
//        }
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.fragment_filter;
    }

    @OnClick({R.id.rbStyle, R.id.rbSubStyle, R.id.rbCategory, R.id.btnApply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rbStyle:
//                if (ageFilterList != null && ageFilterList.size() > 0) {
//                    rvAgeFilter.setVisibility(View.VISIBLE);
//                    rvNeighbourFilter.setVisibility(View.GONE);
//                    rvSubCatFilter.setVisibility(View.GONE);
//                } else {
//                    agefilter();
//                }
                break;
            case R.id.rbSubStyle:
//                if (neighbourFilterList != null && neighbourFilterList.size() > 0) {
//                    rvAgeFilter.setVisibility(View.GONE);
//                    rvNeighbourFilter.setVisibility(View.VISIBLE);
//                    rvSubCatFilter.setVisibility(View.GONE);
//                } else {
//                    neighbourfilter();
//                }
                break;
            case R.id.rbCategory:
//                if (subcategoryfilterList != null && subcategoryfilterList.size() > 0) {
//                    rvAgeFilter.setVisibility(View.GONE);
//                    rvNeighbourFilter.setVisibility(View.GONE);
//                    rvSubCatFilter.setVisibility(View.VISIBLE);
//                } else {
//                    subcategoryfilter();
//                }
                break;
            case R.id.btnApply:
//                if (ageFilterAdapter != null && ageFilterAdapter.getAgeFilterList() != null && ageFilterAdapter.getAgeFilterList().size() > 0) {
//                    List<Filter> ageFilterList = ageFilterAdapter.getAgeFilterList();
//                    List<String> strings = new ArrayList<>();
//                    for (int i = 0; i < ageFilterList.size(); i++) {
//                        if (ageFilterList.get(i).isChecked()) {
//                            strings.add(ageFilterList.get(i).getId());
//                        }
//                    }
//                    bmaListFragment.agefilter = strings.toString().replace("[", "").replace("]", "");
//                }
//                if (subCatFilterAdapter != null && subCatFilterAdapter.getSubCatFilterList() != null && subCatFilterAdapter.getSubCatFilterList().size() > 0) {
//                    List<Filter> subCatFilterList = subCatFilterAdapter.getSubCatFilterList();
//                    List<String> strings = new ArrayList<>();
//                    for (int i = 0; i < subCatFilterList.size(); i++) {
//                        if (subCatFilterList.get(i).isChecked()) {
//                            strings.add(subCatFilterList.get(i).getId());
//                        }
//                    }
//                    bmaListFragment.subcatfilter = strings.toString().replace("[", "").replace("]", "");
//                }
//                if (neighbourFilterAdapter != null && neighbourFilterAdapter.getNeighbourFilterList() != null && neighbourFilterAdapter.getNeighbourFilterList().size() > 0) {
////                    List<Filter> neighbourFilterList = neighbourFilterAdapter.getNeighbourFilterList();
//                    List<String> strings = new ArrayList<>();
////                    for (int i = 0; i < neighbourFilterList.size(); i++) {
////                        if (neighbourFilterList.get(i).isChecked()) {
////                            strings.add(neighbourFilterList.get(i).getLocArea());
////                        }
////                    }
//                    if (neighbourFilterAdapter != null && neighbourFilterAdapter.getNeighbourFilterList() != null && neighbourFilterAdapter.getNeighbourFilterList().size() > 0) {
//                        if (MainActivity.mainActivity.lastCheckedPosition != -1) {
//                            Filter filter = neighbourFilterAdapter.getNeighbourFilterList().get(MainActivity.mainActivity.lastCheckedPosition);
//                            strings.add(filter.getLocArea());
//                        }
//                    }
//                    if (strings.size() > 0)
//                        bmaListFragment.nearbyfilter = strings.toString().replace("[", "").replace("]", "");
//                    else
//                        bmaListFragment.nearbyfilter = "";
//                }
//                finish();
//                if (bmaListFragment.bmaList != null) bmaListFragment.bmaList.clear();
//                bmaListFragment.page_number = 1;
//                if (bmaListFragment.category != null)
//                    bmaListFragment.bmaList();
//                else if (bmaListFragment.subcategory != null)
//                    bmaListFragment.bmaSubCatList();
//                else if (bmaListFragment.keyword != null)
//                    bmaListFragment.search();
                break;
        }
    }

//    public void agefilter() {
//        Map<String, String> params = new HashMap<>();
//        String type = "", categoryid = "", subcategoryid = "", keyword = "", city = app.getPreferences().getCurrentCity();
//        if (bmaListFragment != null) {
//            if (bmaListFragment.category != null) {
//                type = "category";
//                categoryid = bmaListFragment.category.getCatId();
//            } else if (bmaListFragment.subcategory != null) {
//                type = "subcategory";
//                subcategoryid = bmaListFragment.subcategory.getSubcatId();
//            } else if (bmaListFragment.keyword != null) {
//                type = "search";
//                keyword = bmaListFragment.keyword;
//            }
//        }
//        params.put("type", type);
//        params.put("categoryid", categoryid);
//        params.put("subcategoryid", subcategoryid);
//        params.put("keyword", keyword);
//        if (app.getPreferences().getCurrentCity() != null && !TextUtils.isEmpty(app.getPreferences().getCurrentCity()))
//            params.put("city", city);
//        params.put("userid", app.getPreferences().getLoggedInUser().getUserid());
//        ConnectionDetector cd = new ConnectionDetector(mContext);
//        if (cd.isConnectingToInternet()) {
//            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
//            pd.setTitle("Loading...");
//            pd.show();
//            app.getApiRequestHelper().agefilter(params, new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    ageFilterList = (List<Filter>) object;
//                    if (ageFilterList != null && ageFilterList.size() > 0) {
//                        rvAgeFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//                        ageFilterAdapter = new AgeFilterAdapter(mContext, ageFilterList, bmaListFragment != null ? bmaListFragment.agefilter : "");
//                        rvAgeFilter.setAdapter(ageFilterAdapter);
//                        rvAgeFilter.setVisibility(View.VISIBLE);
//                        rvNeighbourFilter.setVisibility(View.GONE);
//                        rvSubCatFilter.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    Utils.showLongToast(mContext, apiResponse);
//                }
//            });
//        } else {
//            Utils.alert_dialog(mContext);
//        }
//    }
//
//    public void neighbourfilter() {
//        Map<String, String> params = new HashMap<>();
//        String type = "", categoryid = "", subcategoryid = "", keyword = "", city = app.getPreferences().getCurrentCity();
//        if (bmaListFragment.category != null) {
//            type = "category";
//            categoryid = bmaListFragment.category.getCatId();
//        } else if (bmaListFragment.subcategory != null) {
//            type = "subcategory";
//            subcategoryid = bmaListFragment.subcategory.getSubcatId();
//        } else if (bmaListFragment.keyword != null) {
//            type = "search";
//            keyword = bmaListFragment.keyword;
//        }
//        params.put("type", type);
//        params.put("categoryid", categoryid);
//        params.put("subcategoryid", subcategoryid);
//        params.put("keyword", keyword);
//        if (app.getPreferences().getCurrentCity() != null && !TextUtils.isEmpty(app.getPreferences().getCurrentCity()))
//            params.put("city", city);
//        params.put("userid", app.getPreferences().getLoggedInUser().getUserid());
//        ConnectionDetector cd = new ConnectionDetector(mContext);
//        if (cd.isConnectingToInternet()) {
//            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
//            pd.setTitle("Loading...");
//            pd.show();
//            app.getApiRequestHelper().neighbourfilter(params, new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    neighbourFilterList = (List<Filter>) object;
//                    if (neighbourFilterList != null && neighbourFilterList.size() > 0) {
//                        rvNeighbourFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//                        neighbourFilterAdapter = new NeighbourFilterAdapter(mContext, neighbourFilterList, bmaListFragment.nearbyfilter);
//                        rvNeighbourFilter.setAdapter(neighbourFilterAdapter);
//                        rvNeighbourFilter.setVisibility(View.VISIBLE);
//                        rvAgeFilter.setVisibility(View.GONE);
//                        rvSubCatFilter.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    Utils.showLongToast(mContext, apiResponse);
//                }
//            });
//        } else {
//            Utils.alert_dialog(mContext);
//        }
//    }
//
//    public void subcategoryfilter() {
//        Map<String, String> params = new HashMap<>();
//        String type = "", categoryid = "", subcategoryid = "", keyword = "", city = app.getPreferences().getCurrentCity();
//        if (bmaListFragment.category != null) {
//            type = "category";
//            categoryid = bmaListFragment.category.getCatId();
//        } else if (bmaListFragment.subcategory != null) {
//            type = "subcategory";
//            subcategoryid = bmaListFragment.subcategory.getSubcatId();
//        } else if (bmaListFragment.keyword != null) {
//            type = "search";
//            keyword = bmaListFragment.keyword;
//        }
//        params.put("type", type);
//        params.put("categoryid", categoryid);
//        params.put("subcategoryid", subcategoryid);
//        params.put("keyword", keyword);
//        if (app.getPreferences().getCurrentCity() != null && !TextUtils.isEmpty(app.getPreferences().getCurrentCity()))
//            params.put("city", city);
//        params.put("userid", app.getPreferences().getLoggedInUser().getUserid());
//        ConnectionDetector cd = new ConnectionDetector(mContext);
//        if (cd.isConnectingToInternet()) {
//            final CustomProgressDialog pd = new CustomProgressDialog(mContext);
//            pd.setTitle("Loading...");
//            pd.show();
//            app.getApiRequestHelper().subcategoryfilter(params, new ApiRequestHelper.OnRequestComplete() {
//                @Override
//                public void onSuccess(Object object) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    subcategoryfilterList = (List<Filter>) object;
//                    if (subcategoryfilterList != null && subcategoryfilterList.size() > 0) {
//                        rvSubCatFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
//                        subCatFilterAdapter = new SubCatFilterAdapter(mContext, subcategoryfilterList, bmaListFragment.subcatfilter);
//                        rvSubCatFilter.setAdapter(subCatFilterAdapter);
//                        rvSubCatFilter.setVisibility(View.VISIBLE);
//                        rvNeighbourFilter.setVisibility(View.GONE);
//                        rvAgeFilter.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onFailure(String apiResponse) {
//                    if (pd != null && pd.isShowing()) pd.dismiss();
//                    Utils.showLongToast(mContext, apiResponse);
//                }
//            });
//        } else {
//            Utils.alert_dialog(mContext);
//        }
//    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }
}
