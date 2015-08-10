package com.mopub.nativeads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.mopub.common.MoPubBrowser;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.util.Intents;
import com.mopub.exceptions.IntentNotResolvableException;
import com.mopub.exceptions.UrlParseException;

import java.lang.ref.WeakReference;
import java.util.Iterator;

class ClickDestinationResolutionListener implements UrlResolutionTask.UrlResolutionListener {
    private final Context mContext;
    private final Iterator<String> mUrlIterator;
    private final WeakReference<SpinningProgressView> mSpinningProgressView;

    public ClickDestinationResolutionListener(@NonNull final Context context,
            @NonNull final Iterator<String> urlIterator,
            @NonNull final SpinningProgressView spinningProgressView) {
        mContext = context.getApplicationContext();
        mUrlIterator = urlIterator;
        mSpinningProgressView = new WeakReference<SpinningProgressView>(spinningProgressView);
    }

    /**
     * Called upon user click, after the corresponding UrlResolutionTask has followed all redirects
     * successfully. Attempts to open mopubnativebrowser links in the device browser, deep-links in
     * the corresponding application, and all other links in the MoPub in-app browser. In the first
     * two cases, malformed URLs will try to fallback to the next entry in mUrlIterator, and failing
     * that, will no-op.
     */
    @Override
    public void onSuccess(@NonNull final String resolvedUrl) {

        if (Intents.isAboutScheme(resolvedUrl)) {
            MoPubLog.d("Link to about page ignored.");
            removeSpinningProgressView();
            return;
        }

        // Handle MoPubNativeBrowser schemes
        if (Intents.isNativeBrowserScheme(resolvedUrl)) {
            try {
                final Intent intent = Intents.intentForNativeBrowserScheme(resolvedUrl);
                Intents.startActivity(mContext, intent);
                removeSpinningProgressView();
                return;
            } catch (UrlParseException e) {
                MoPubLog.d(e.getMessage());
            } catch (IntentNotResolvableException e) {
                MoPubLog.d("Could not handle intent for URI: " + resolvedUrl);
            }

            if (mUrlIterator.hasNext()) {
                UrlResolutionTask.getResolvedUrl(mUrlIterator.next(), this);
            } else {
                removeSpinningProgressView();
            }
            return;
        }

        // Handle Android deeplinks
        if (Intents.isDeepLink(resolvedUrl)) {
            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(resolvedUrl));

            // Open another Android app from the deep link
            if (Intents.deviceCanHandleIntent(mContext, intent)) {
                try {
                    Intents.startActivity(mContext, intent);
                    return;
                } catch (IntentNotResolvableException e) {
                    MoPubLog.d("Could not handle intent with URI: " + resolvedUrl);
                } finally {
                    removeSpinningProgressView();
                }
            }

            if (mUrlIterator.hasNext()) {
                UrlResolutionTask.getResolvedUrl(mUrlIterator.next(), this);
            } else {
                removeSpinningProgressView();
            }
            return;
        }

        removeSpinningProgressView();
        if (Intents.isHttpUrl(resolvedUrl)) {
            MoPubBrowser.open(mContext, resolvedUrl);
            return;
        }

        MoPubLog.d("Link ignored. Unable to handle url: " + resolvedUrl);
    }

    @Override
    public void onFailure() {
        MoPubLog.d("Failed to resolve URL for click.");
        removeSpinningProgressView();
    }

    private void removeSpinningProgressView() {
        final SpinningProgressView spinningProgressView = mSpinningProgressView.get();
        if (spinningProgressView != null) {
            spinningProgressView.removeFromRoot();
        }
    }
}
