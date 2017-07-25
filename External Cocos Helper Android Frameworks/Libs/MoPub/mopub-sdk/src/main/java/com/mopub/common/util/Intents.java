package com.mopub.common.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mopub.common.Preconditions;
import com.mopub.common.logging.MoPubLog;
import com.mopub.exceptions.IntentNotResolvableException;
import com.mopub.exceptions.UrlParseException;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.mopub.common.Constants.HTTP;
import static com.mopub.common.Constants.HTTPS;

public class Intents {
    private static final String PLAY_GOOGLE_COM = "play.google.com";
    private static final String MARKET_ANDROID_COM = "market.android.com";
    private static final String MARKET = "market";

    private Intents() {}

    public static void startActivity(@NonNull final Context context, @NonNull final Intent intent)
            throws IntentNotResolvableException {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(intent);

        if (!(context instanceof Activity)) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        }

        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            throw new IntentNotResolvableException(e);
        }
    }

    /**
     * Adding FLAG_ACTIVITY_NEW_TASK with startActivityForResult will always result in a
     * RESULT_CANCELED, so don't use it for Activity contexts.
     */
    public static Intent getStartActivityIntent(@NonNull final Context context,
            @NonNull final Class clazz, @Nullable final Bundle extras) {
        final Intent intent = new Intent(context, clazz);

        if (!(context instanceof Activity)) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        }

        if (extras != null) {
            intent.putExtras(extras);
        }

        return intent;
    }

    public static boolean deviceCanHandleIntent(@NonNull final Context context,
            @NonNull final Intent intent) {
        try {
            final PackageManager packageManager = context.getPackageManager();
            final List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
            return !activities.isEmpty();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static boolean isHttpUrl(final String url) {
        if (url == null) {
            return false;
        }

        final String scheme = Uri.parse(url).getScheme();
        return (HTTP.equals(scheme) || HTTPS.equals(scheme));
    }

    private static boolean isAppStoreUrl(final String url) {
        if (url == null) {
            return false;
        }

        final Uri uri = Uri.parse(url);
        final String scheme = uri.getScheme();
        final String host = uri.getHost();

        if (PLAY_GOOGLE_COM.equals(host) || MARKET_ANDROID_COM.equals(host)) {
            return true;
        }

        if (MARKET.equals(scheme)) {
            return true;
        }

        return false;
    }

    public static boolean isDeepLink(final String url) {
        return isAppStoreUrl(url) || !isHttpUrl(url);
    }

    public static boolean canHandleApplicationUrl(final Context context, final String url) {
        return canHandleApplicationUrl(context, url, true);
    }

    public static boolean canHandleApplicationUrl(final Context context, final String url,
            final boolean logError) {
        // Determine which activities can handle the intent
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        // If there are no relevant activities, don't follow the link
        if (!Intents.deviceCanHandleIntent(context, intent)) {
            if (logError) {
                MoPubLog.w("Could not handle application specific action: " + url + ". " +
                        "You may be running in the emulator or another device which does not " +
                        "have the required application.");
            }
            return false;
        }

        return true;
    }

    public static boolean isNativeBrowserScheme(@NonNull final String url) {
        return url.startsWith("mopubnativebrowser://");
    }

    public static boolean isAboutScheme(@NonNull final String url) {
        return "about".equals(Uri.parse(url).getScheme());
    }

    /**
     * Native Browser Scheme URLs provide a means for advertisers to include links that click out to
     * an external browser, rather than the MoPub in-app browser. Properly formatted native browser
     * URLs take the form of "mopubnativebrowser://navigate?url=http%3A%2F%2Fwww.mopub.com".
     *
     * @param url
     * @return An Intent that will open an app-external browser taking the user to a page specified
     * in the query parameter of the passed-in url
     * @throws UrlParseException if the provided url has an invalid format or is non-hierarchical
     */
    public static Intent intentForNativeBrowserScheme(@NonNull final String url)
            throws UrlParseException {
        Preconditions.checkNotNull(url);

        if (!isNativeBrowserScheme(url)) {
            throw new UrlParseException("URL does not have mopubnativebrowser:// scheme.");
        }

        final Uri uri = Uri.parse(url);

        if (!"navigate".equals(uri.getHost())) {
            throw new UrlParseException("URL missing 'navigate' host parameter.");
        }

        final String urlToOpenInNativeBrowser;
        try {
            urlToOpenInNativeBrowser = uri.getQueryParameter("url");
        } catch (UnsupportedOperationException e) {
            // Accessing query parameters only makes sense for hierarchical URIs as per:
            // http://developer.android.com/reference/android/net/Uri.html#getQueryParameter(java.lang.String)
            MoPubLog.w("Could not handle url: " + url);
            throw new UrlParseException("Passed-in URL did not create a hierarchical URI.");
        }

        if (urlToOpenInNativeBrowser == null) {
            throw new UrlParseException("URL missing 'url' query parameter.");
        }

        final Uri intentUri = Uri.parse(urlToOpenInNativeBrowser);
        return new Intent(Intent.ACTION_VIEW, intentUri);
    }
}
