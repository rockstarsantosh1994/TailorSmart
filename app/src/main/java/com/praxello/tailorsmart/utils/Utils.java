package com.praxello.tailorsmart.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    //public static String tag = "MyFragment";
    public static final String OTP_DELIMITER = ":";
    public static String NO_INTERNET_MSG = "You don't have internet connection.Please connect to internet";
    public static String NO_INTERNET_TITLE = "No Internet Connection";
    public static String SPECIAL_CHARACTERS = "!@#$%^&*()~`-=_+[]{}|:\";',./<>?";
    public static int MAX_PENDING_MINUTE = 5;
    public static int MOBILE_NO_LENGTH = 10;
    public static int MAX_MINUTE = 61;
    public static String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String DATE_FORMAT_DMY = "dd-MM-yyyy";
    public static String UNPROPER_RESPONSE = "Unable to process your request. Please, Try again later.";
    private Context context;
    public static final String PLACES_API_KEY = "AIzaSyCSqeAzrejXqB7exO1EsX1LNHJ8pUnzhv4";

    public Utils(Context context) {
        this.context = context;
    }

    private static String tag = "MyFragment";

    public static String format2Dec(double x) {
        double v = Math.floor(x * 100) / 100;
        double round = round(v);
        return formatDec(round);
    }

    // Remove trailing zeroes, 4.3000 -> 4.3, 5.00 -> 5
    public static String formatDec(double x) {
        DecimalFormat format = new DecimalFormat("0.#");
        return format.format(x);
    }

    public static double round(double number) {
        BigDecimal bd = new BigDecimal(number);
        bd = bd.setScale(0, BigDecimal.ROUND_HALF_DOWN);
        return bd.doubleValue();
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static boolean checkBetween(String dateToCheck, String startDate, String endDate) {
        boolean res = false;
        SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd"); //2013-05-20
        try {
            Date fromDate = fmt1.parse(startDate);
            Date requestDate = fmt1.parse(dateToCheck);
            Date toDate = fmt1.parse(endDate);
            res = requestDate.compareTo(fromDate) >= 0 && requestDate.compareTo(toDate) <= 0;
        } catch (ParseException pex) {
            pex.printStackTrace();
        }
        return res;
    }

    public static void callPhone(String mobile, Context mContext) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.CALL_PHONE},
                    1);
        } else {
            MaterialDialog ok = new MaterialDialog.Builder(mContext)
                    .content("Do you want to call this phone number?")
                    .positiveText("Yes")
                    .negativeText("No")
                    .show();
            ok.getActionButton(DialogAction.POSITIVE).setOnClickListener(view -> {
                ok.dismiss();
                mContext.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile)));
            });
            ok.getActionButton(DialogAction.NEGATIVE).setOnClickListener(view -> ok.dismiss());
        }
    }

    public static String formatTimeToAgo(String strDate) {
        String formattedTime = "";
        if (strDate != null && !TextUtils.isEmpty(strDate) && !strDate.equals("0000-00-00 00:00:00")) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            format.setTimeZone(TimeZone.getTimeZone("US/Central"));
            Date past = null;
            try {
                past = format.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
//            Date currDate = new Date();
            Date now = new Date();
//            Date now = convertTimeZone(currDate, TimeZone.getDefault(), TimeZone.getTimeZone("US/Central"));
            long seconds = 0;
            if (past != null) {
                seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime());
                long minutes = TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
                long hours = TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime());
                long days = TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime());
//
//          System.out.println(TimeUnit.MILLISECONDS.toSeconds(now.getTime() - past.getTime()) + " milliseconds ago");
//          System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
//          System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
//          System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");

                if (seconds < 60) {
                    String strSeconds = seconds > 1 ? "seconds ago" : "second ago";
                    formattedTime = seconds + " s";
                    if (formattedTime.contains("-")) {
                        formattedTime = formattedTime.replace("-", "");
                    }
                } else if (minutes < 60) {
                    String strMinutes = minutes > 1 ? "minutes ago" : "minute ago";
                    formattedTime = minutes + " m";
                } else if (hours < 24) {
                    String strHour = hours > 1 ? "hours ago" : "hour ago";
                    formattedTime = hours + " h";
                } else {
                    String strDay = days > 1 ? "days ago" : "day ago";
                    formattedTime = days + " d";
                }
            }
        }
        return formattedTime;
    }

    public static void showDialog(final Context mContext, String content, boolean isNegativeEnabled,
                                  MaterialDialog.SingleButtonCallback positiveButtonCallback) {
        new MaterialDialog.Builder(mContext)
                .content(content)
                .positiveText("Ok")
                .negativeText(isNegativeEnabled ? "Cancel" : "")
                .cancelable(false)
                .onPositive(positiveButtonCallback)
                .onNegative((dialog, which) -> dialog.dismiss())
                .show();
    }

    /**
     * Returns the unique identifier for the device
     *
     * @return unique identifier for the device
     */
    @SuppressLint({"HardwareIds", "MissingPermission"})
    public static String getDeviceIMEI(Context mContext) {
        String deviceUniqueIdentifier = "";
        try {
            TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
            if (null == deviceUniqueIdentifier || deviceUniqueIdentifier.isEmpty()) {
                deviceUniqueIdentifier = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceUniqueIdentifier;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }
        return phrase.toString();
    }

    public static void openBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static String toBase64Encode(String string) {
        String base64 = "";
        byte[] data;
        try {
            data = string.getBytes("UTF-8");
            base64 = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public static String getCurrentDateTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(c.getTime());
    }

    public static String dmyTod(String str_date) {
        if (!TextUtils.isEmpty(str_date)) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        return "";
    }

    public static String ymdTod(String str_date) {
        if (!TextUtils.isEmpty(str_date)) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        return "";
    }

    public static String ymdHmsTodmyHms(String str_date) {
        if (!TextUtils.isEmpty(str_date)) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        return "";
    }

    public static String ymdTodmy(String str_date) {
        if (!TextUtils.isEmpty(str_date) && !str_date.equalsIgnoreCase("NA")) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        if (str_date.equalsIgnoreCase("NA")) return "NA";
        return "";
    }

    public static String ymdToedmy(String str_date) {
        if (!TextUtils.isEmpty(str_date)) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("EEE, dd MMM yy");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        return "";
    }

    public static String ymdTodMy(String str_date) {
        if (!TextUtils.isEmpty(str_date)) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yy");
            Date date = null;
            try {
                date = originalFormat.parse(str_date); //2017-07-27
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        }
        return "";
    }

    public static Drawable changeVectorColor(Context mContext, View view, int colorId) {
        Drawable background = view.getBackground();
        background.setColorFilter(ContextCompat.getColor(mContext, colorId), PorterDuff.Mode.SRC_IN);
        return background;
    }

    public static int getDpInPx(Context mContext, float val) {
        Resources r = mContext.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, val, r.getDisplayMetrics());
    }

    public static List<String> getDates(String dateString1, String dateString2) {
        List<String> datesStr = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<Date>();
        DateFormat df1 = new SimpleDateFormat("dd/MM/yy");

        Date date1 = null;
        Date date2 = null;

        try {
            date1 = df1.parse(dateString1);
            date2 = df1.parse(dateString2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        while (!cal1.after(cal2)) {
            dates.add(cal1.getTime());
            cal1.add(Calendar.DATE, 1);
        }
        for (int i = 0; i < dates.size(); i++) {
            String format = df1.format(dates.get(i));
            datesStr.add(format);
        }
        return datesStr;
    }

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    public static void alert_dialog(final Context mContext) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setTitle("Internet Connection Error");
        builder1.setMessage("Please connect to working Internet connection");
        builder1.setCancelable(true);
        builder1.setPositiveButton("Go To Settings",
                (dialog, which) -> mContext.startActivity(new Intent(Settings.ACTION_SETTINGS)));
        builder1.setNegativeButton("Cancel",
                (dialog, which) -> dialog.dismiss());
        builder1.show();
    }
//
//    public static void gps_alert_dialog(final Context mContext) {
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
////        builder1.setTitle("Internet Connection Error");
//        builder1.setMessage("GPS is not enabled. Do you want to go to settings menu?");
//        builder1.setCancelable(true);
//        builder1.setPositiveButton("Go To Settings", (dialog, id) -> {
//            mContext.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
//            dialog.dismiss();
//        });
//        builder1.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
//        builder1.show();
//    }

    public static String replaceUnwantedChars(String s) {
        if (s.contains("\n")) {
            s = s.replaceAll("\n", "");
        }
        if (s.contains("\r")) {
            s = s.replaceAll("\r", "");
        }
        return s;
    }

    public static void showLongToast(Context context, String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showShortToast(Context context, String message) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // validating email id
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String parse(float val) {
        DecimalFormat twoDForm = new DecimalFormat("0.00");
        float f = Float.valueOf(twoDForm.format(val));
        String s = String.format("%.02f", f);
        return s;
    }

    /**
     * Mobile No Validation
     *
     * @param no
     * @return true if it is valid else false
     */
    public static boolean isAcceptableMobile(String no) {
        if (TextUtils.isEmpty(no)) {
            System.out.println("empty string.");
            return false;
        }
        no = no.trim();
        int len = no.length();
        if (len < MOBILE_NO_LENGTH || len > MOBILE_NO_LENGTH) {
            System.out.println("Mobile No must have 10 digits");
            return false;
        }
        return true;
    }

//    public static void replaceFragment(Activity activity, Fragment frag, boolean flagsAddToBackStack) {
//        android.support.v4.app.FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
//        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.fragment_container, frag, tag);
//        if (flagsAddToBackStack) {
//            if (ft.isAddToBackStackAllowed()) {
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        }
//    }

    // convert InputStream to String
    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(activity
                        .getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void hideSoftKeyboardInFragment(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

//            Log.e("Package Name=", context.getApplicationContext().getPackageName());
            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                // String key = new String(Base64.encodeBytes(md.digest()));
//                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
//            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
//            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
//            Log.e("Exception", e.toString());
        }
        return key;
    }

    public static String checkNotEmpty(String s) {
        if (s != null && !TextUtils.isEmpty(s) && !s.equalsIgnoreCase("null")) {
            return s;
        }
        return "";
    }

    public static String checkForNull(String s) {
        if (s != null && !TextUtils.isEmpty(s) && !s.equalsIgnoreCase("null")) {
            if (s.contains("\n")) {
                s = s.replaceAll("\n", "");
            }
            if (s.contains("\r")) {
                s = s.replaceAll("\r", "");
            }
            return s;
        }
        return "NA";
    }

    public static String format(Number n) {
        NumberFormat format = DecimalFormat.getInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        format.setMinimumFractionDigits(0);
        format.setMaximumFractionDigits(2);
        return format.format(n);
    }

    public static void setupUI(final Activity context, final View view) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Utils.hideSoftKeyboard(context);
                    v.requestFocus();
                    // et_Searchrest.setError(null);
                    return false;
                }
            });
        }

        // If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(context, innerView);
            }
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 0) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    /*
     * Method for Setting the Height of the ListView dynamically. Hack to fix
     * the issue of not showing all the items of the ListView when placed inside
     * a ScrollView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


}
