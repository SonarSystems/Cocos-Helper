package com.mopub.mobileads;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mopub.common.MoPubBrowser;
import com.mopub.common.Preconditions;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.util.Intents;
import com.mopub.exceptions.IntentNotResolvableException;
import com.mopub.exceptions.UrlParseException;

import static com.mopub.mobileads.MoPubErrorCode.UNSPECIFIED;

class HtmlWebViewClient extends WebViewClient {
    static final String MOPUB_FINISH_LOAD = "mopub://finishLoad";
    static final String MOPUB_FAIL_LOAD = "mopub://failLoad";

    private final Context mContext;
    private HtmlWebViewListener mHtmlWebViewListener;
    private BaseHtmlWebView mHtmlWebView;
    private final String mClickthroughUrl;
    private final String mRedirectUrl;

    HtmlWebViewClient(HtmlWebViewListener htmlWebViewListener, BaseHtmlWebView htmlWebView, String clickthrough, String redirect) {
        mHtmlWebViewListener = htmlWebViewListener;
        mHtmlWebView = htmlWebView;
        mClickthroughUrl = clickthrough;
        mRedirectUrl = redirect;
        mContext = htmlWebView.getContext();
    }

    /**
     * Called upon user click, when the WebView attempts to load a new URL. Attempts to handle mopub
     * and phone-specific schemes, open mopubnativebrowser links in the device browser, deep-links
     * in the corresponding application, and all other links in the MoPub in-app browser.
     */
    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        MoPubLog.d("Ad event URL: " + url);

        if (handleSpecialMoPubScheme(url)) {
            return true;
        }

        if (Intents.isAboutScheme(url)) {
            MoPubLog.d("Link to about page ignored.");
            return true;
        }

        if (handlePhoneScheme(url)) {
            return true;
        }

        // MoPubNativeBrowser URLs
        if (Intents.isNativeBrowserScheme(url)) {
            final String errorMessage = "Unable to load mopub native browser url: " + url;
            try {
                final Intent intent = Intents.intentForNativeBrowserScheme(url);
                launchIntentForUserClick(mContext, intent, errorMessage);
            } catch (UrlParseException e) {
                MoPubLog.d(errorMessage + ". " + e.getMessage());
            }

            return true;
        }

        if (Intents.isHttpUrl(url)) {
            showMoPubBrowserForUrl(url);
            return true;
        }

        // Non-http(s) URLs like app deep links
        if (Intents.canHandleApplicationUrl(mContext, url)) {
            launchApplicationUrl(url);
            return true;
        }

        MoPubLog.d("Link ignored. Unable to handle url: " + url);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // If the URL being loaded shares the redirectUrl prefix, open it in the browser.
        if (mRedirectUrl != null && url.startsWith(mRedirectUrl)) {
            view.stopLoading();
            showMoPubBrowserForUrl(url);
        }
    }

    private boolean isSpecialMoPubScheme(String url) {
        return url.startsWith("mopub://");
    }

    private boolean handleSpecialMoPubScheme(String url) {
        if (!isSpecialMoPubScheme(url)) {
            return false;
        }
        Uri uri = Uri.parse(url);
        String host = uri.getHost();

        if ("finishLoad".equals(host)) {
            mHtmlWebViewListener.onLoaded(mHtmlWebView);
        } else if ("close".equals(host)) {
            mHtmlWebViewListener.onCollapsed();
        } else if ("failLoad".equals(host)) {
            mHtmlWebViewListener.onFailed(UNSPECIFIED);
        } else if ("custom".equals(host)) {
            handleCustomIntentFromUri(uri);
        }

        return true;
    }

    private boolean isPhoneScheme(String url) {
        return url.startsWith("tel:") || url.startsWith("voicemail:") ||
                url.startsWith("sms:") || url.startsWith("mailto:") ||
                url.startsWith("geo:") || url.startsWith("google.streetview:");
    }

    private boolean handlePhoneScheme(String url) {
        if (!isPhoneScheme(url)) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String errorMessage = "Could not handle intent with URI: " + url
                + ". Is this intent supported on your phone?";

        launchIntentForUserClick(mContext, intent, errorMessage);

        return true;
    }

    private boolean launchApplicationUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        String errorMessage = "Unable to open intent.";

        return launchIntentForUserClick(mContext, intent, errorMessage);
    }

    private void showMoPubBrowserForUrl(String url) {
        if (url == null || url.equals("")) {
            url = "about:blank";
        }
        MoPubLog.d("Final URI to show in browser: " + url);

        final Bundle extras = new Bundle();
        extras.putString(MoPubBrowser.DESTINATION_URL_KEY, url);
        Intent intent = Intents.getStartActivityIntent(mContext, MoPubBrowser.class, extras);

        String errorMessage = "Could not handle intent action. "
                + ". Perhaps you forgot to declare com.mopub.common.MoPubBrowser"
                + " in your Android manifest file.";

        launchIntentForUserClick(mContext, intent, errorMessage);
    }

    private void handleCustomIntentFromUri(Uri uri) {
        String action;
        String adData;
        try {
            action = uri.getQueryParameter("fnc");
            adData = uri.getQueryParameter("data");
        } catch (UnsupportedOperationException e) {
            MoPubLog.w("Could not handle custom intent with uri: " + uri);
            return;
        }

        Intent customIntent = new Intent(action);
        customIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        customIntent.putExtra(HtmlBannerWebView.EXTRA_AD_CLICK_DATA, adData);

        String errorMessage = "Could not handle custom intent: " + action
                + ". Is your intent spelled correctly?";

        launchIntentForUserClick(mContext, customIntent, errorMessage);
    }

    boolean launchIntentForUserClick(@Nullable final Context context, @NonNull final Intent intent,
            @Nullable final String errorMessage) {
        Preconditions.NoThrow.checkNotNull(intent);

        if (context == null) {
            MoPubLog.d(errorMessage);
            return false;
        }

        if (!mHtmlWebView.wasClicked()) {
            return false;
        }

        try {
            Intents.startActivity(context, intent);
            mHtmlWebViewListener.onClicked();
            mHtmlWebView.onResetUserClick();
            return true;
        } catch (IntentNotResolvableException e) {
            MoPubLog.d(errorMessage);
            return false;
        }
    }
}
