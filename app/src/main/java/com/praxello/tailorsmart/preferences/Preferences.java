package com.praxello.tailorsmart.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.praxello.tailorsmart.model.Product;
import com.praxello.tailorsmart.model.UserData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Preferences {
    private static final String LOGGED_IN_USER = "LOGGED_IN_USER";
    private static final String TOKEN = "TOKEN";
    private static final String LAT = "LAT";
    private static final String LNG = "LNG";
    private static final String WISHLIST = "WISHLIST";
    private static final String SELECTED_CURRENCY = "SELECTED_CURRENCY";
    private static final String SELECTED_CURRENCY_MULTIPLIER = "SELECTED_CURRENCY_MULTIPLIER";

    private Context context;
    Set<String> strings;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Preferences(Context context) {
        this.context = context;
    }

    protected SharedPreferences getSharedPreferences(String key) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    private String getString(String key, String def) {
        SharedPreferences prefs = getSharedPreferences(key);
        return prefs.getString(key, def);
    }

    public void setString(String key, String val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putString(key, val);
        e.apply();
    }

    private long getLong(String key, long def) {
        SharedPreferences prefs = getSharedPreferences(key);
        return prefs.getLong(key, def);
    }

    public void setLong(String key, long val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putLong(key, val);
        e.apply();
    }

    private boolean getBoolean(String key, boolean def) {
        SharedPreferences prefs = getSharedPreferences(key);
        boolean b = prefs.getBoolean(key, def);
        return b;
    }

    private void setBoolean(String key, boolean val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putBoolean(key, val);
        e.apply();
    }

    public boolean isLoggedInUser() {
        String json = getString(LOGGED_IN_USER, null);
        return json != null && !TextUtils.isEmpty(json);
    }

    public void logOutUser() {
        SharedPreferences prefs = getSharedPreferences(LOGGED_IN_USER);
        Editor e = prefs.edit();
        e.clear();
        e.apply();
    }

    public UserData getLoggedInUser() {
        String json = getString(LOGGED_IN_USER, null);
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        return new Gson().fromJson(json, UserData.class);
    }

    public void setLoggedInUser(UserData user) {
        setString(LOGGED_IN_USER, new Gson().toJson(user));
    }

    private Set<String> getStringSet(String key, Set<String> def) {
        SharedPreferences prefs = getSharedPreferences(key);
        return prefs.getStringSet(key, def);
    }

    public void setStringSet(String key, Set<String> val) {
        SharedPreferences prefs = getSharedPreferences(key);
        Editor e = prefs.edit();
        e.putStringSet(key, val);
        e.apply();
    }

    public String getToken() {
        return getString(TOKEN, null);
    }

    public void setToken(String token) {
        setString(TOKEN, token);
    }

    public String getSelectedCurrency() {
        return getString(SELECTED_CURRENCY, "\u20B9");
    }

    public void setSelectedCurrency(String currency) {
        setString(SELECTED_CURRENCY, currency);
    }

    public String getSelectedCurrencyMultiplier() {
        return getString(SELECTED_CURRENCY_MULTIPLIER, "1");
    }

    public void setSelectedCurrencyMultiplier(String currencyMultiplier) {
        setString(SELECTED_CURRENCY_MULTIPLIER, currencyMultiplier);
    }

    public String getLat() {
        return getString(LAT, "0.0");
    }

    public void setLat(String lat) {
        setString(LAT, lat);
    }

    public String getLng() {
        return getString(LNG, "0.0");
    }

    public void setLng(String lng) {
        setString(LNG, lng);
    }

    public List<Product> getWishlistData() {
        List<Product> productList = new ArrayList<>();
        String string = getString(WISHLIST, null);
        if (!TextUtils.isEmpty(string)) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            productList = new Gson().fromJson(string, listType);
        }
        return productList;
    }

    public void setWishlistData(List<Product> productList) {
        if (productList != null && productList.size() > 0) {
            Type listType = new TypeToken<List<Product>>() {
            }.getType();
            String json = new Gson().toJson(productList, listType);
            setString(WISHLIST, json);
        } else {
            setString(WISHLIST, "");
        }
    }

    public boolean isAddedToWishlist(Product product) {
        List<Product> cartData = getWishlistData();
        if (cartData != null && cartData.size() > 0) {
            for (int i = 0; i < cartData.size(); i++) {
                if (cartData.get(i).getProductId().equals(product.getProductId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean addToWishlist(Product product) {
        List<Product> cartData = getWishlistData();
        if (cartData == null) cartData = new ArrayList<>();
        for (int i = 0; i < cartData.size(); i++) {
            if (cartData.get(i).getProductId().equals(product.getProductId())) {
                return false;
            }
        }
        cartData.add(product);
        setWishlistData(cartData);
        return true;
    }

    public boolean updateWishlist(Product product) {
        List<Product> cartData = getWishlistData();
        if (cartData == null) cartData = new ArrayList<>();
        for (int i = 0; i < cartData.size(); i++) {
            if (cartData.get(i).getProductId().equals(product.getProductId())) {
                cartData.set(i, product);
            }
        }
        setWishlistData(cartData);
        return true;
    }

//    public void updateCartProductQty(Product product) {
//        List<Product> cartData = getCartData();
//        for (int i = 0; i < cartData.size(); i++) {
//            if (cartData.get(i).getProductId().equals(product.getProductId()) && cartData.get(i).getProductId().equals(product.getProductId())) {
////                cartData.get(i).setQuantity(product.getQuantity());
//                break;
//            }
//        }
//        setCartData(cartData);
//    }

    public void removeProductFromCart(Product product) {
        List<Product> cartData = getWishlistData();
        for (int i = 0; i < cartData.size(); i++) {
            if (cartData.get(i).getProductId().equals(product.getProductId()) && cartData.get(i).getProductId().equals(product.getProductId())) {
                cartData.remove(i);
                break;
            }
        }
        setWishlistData(cartData);
    }
}
