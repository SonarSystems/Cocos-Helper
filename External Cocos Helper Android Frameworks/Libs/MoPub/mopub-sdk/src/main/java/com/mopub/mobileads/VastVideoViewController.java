package com.mopub.mobileads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.mopub.common.DownloadResponse;
import com.mopub.common.DownloadTask;
import com.mopub.common.HttpResponses;
import com.mopub.common.MoPubBrowser;
import com.mopub.common.Preconditions;
import com.mopub.common.VisibleForTesting;
import com.mopub.common.event.BaseEvent;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.util.AsyncTasks;
import com.mopub.common.util.Dips;
import com.mopub.common.util.Drawables;
import com.mopub.common.util.Intents;
import com.mopub.common.util.Streams;
import com.mopub.common.util.Strings;
import com.mopub.common.util.VersionCode;
import com.mopub.exceptions.IntentNotResolvableException;
import com.mopub.exceptions.UrlParseException;
import com.mopub.mobileads.util.vast.VastCompanionAd;
import com.mopub.mobileads.util.vast.VastVideoConfiguration;
import com.mopub.network.TrackingRequest;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
import static com.mopub.common.HttpClient.initializeHttpGet;
import static com.mopub.mobileads.EventForwardingBroadcastReceiver.ACTION_INTERSTITIAL_CLICK;
import static com.mopub.mobileads.EventForwardingBroadcastReceiver.ACTION_INTERSTITIAL_DISMISS;
import static com.mopub.mobileads.EventForwardingBroadcastReceiver.ACTION_INTERSTITIAL_SHOW;
import static com.mopub.network.TrackingRequest.makeTrackingHttpRequest;

public class VastVideoViewController extends BaseVideoViewController implements DownloadTask.DownloadTaskListener {
    static final String VAST_VIDEO_CONFIGURATION = "vast_video_configuration";

    private static final long VIDEO_PROGRESS_TIMER_CHECKER_DELAY = 50;
    private static final int MOPUB_BROWSER_REQUEST_CODE = 1;
    private static final int MAX_VIDEO_RETRIES = 1;
    private static final int VIDEO_VIEW_FILE_PERMISSION_ERROR = Integer.MIN_VALUE;

    static final int DEFAULT_VIDEO_DURATION_FOR_CLOSE_BUTTON = 5 * 1000;
    static final int MAX_VIDEO_DURATION_FOR_CLOSE_BUTTON = 16 * 1000;

    private final VastVideoConfiguration mVastVideoConfiguration;
    private final VastCompanionAd mVastCompanionAd;
    private final VastVideoToolbar mVastVideoToolbar;
    private final VideoView mVideoView;
    private final ImageView mCompanionAdImageView;
    private final View.OnTouchListener mClickThroughListener;

    private final Handler mHandler;
    private final Runnable mVideoProgressCheckerRunnable;
    private boolean mIsVideoProgressShouldBeChecked;
    private int mShowCloseButtonDelay = DEFAULT_VIDEO_DURATION_FOR_CLOSE_BUTTON;

    private boolean mShowCloseButtonEventFired;

    private int mSeekerPositionOnPause;
    private boolean mIsVideoFinishedPlaying;
    private int mVideoRetries;

    private boolean mVideoError;
    private boolean mCompletionTrackerFired;

    private boolean mHasSkipOffset = false;

    VastVideoViewController(final Context context,
            final Bundle bundle,
            final long broadcastIdentifier,
            final BaseVideoViewControllerListener baseVideoViewControllerListener)
            throws IllegalStateException {
        super(context, broadcastIdentifier, baseVideoViewControllerListener);
        mHandler = new Handler();
        mIsVideoProgressShouldBeChecked = false;
        mSeekerPositionOnPause = -1;
        mVideoRetries = 0;

        Serializable serializable = bundle.getSerializable(VAST_VIDEO_CONFIGURATION);
        if (serializable != null && serializable instanceof VastVideoConfiguration) {
            mVastVideoConfiguration = (VastVideoConfiguration) serializable;
        } else {
            throw new IllegalStateException("VastVideoConfiguration is invalid");
        }

        if (mVastVideoConfiguration.getDiskMediaFileUrl() == null) {
            throw new IllegalStateException("VastVideoConfiguration does not have a video disk path");
        }

        mVastCompanionAd = mVastVideoConfiguration.getVastCompanionAd();

        mClickThroughListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP && shouldAllowClickThrough()) {
                    handleClick(
                            mVastVideoConfiguration.getClickTrackers(),
                            mVastVideoConfiguration.getClickThroughUrl()
                    );
                }
                return true;
            }
        };

        createVideoBackground(context);

        mVideoView = createVideoView(context);
        mVideoView.requestFocus();

        mVastVideoToolbar = createVastVideoToolBar(context);
        getLayout().addView(mVastVideoToolbar);

        mCompanionAdImageView = createCompanionAdImageView(context);
        mVideoProgressCheckerRunnable = createVideoProgressCheckerRunnable();
    }

    @Override
    protected VideoView getVideoView() {
        return mVideoView;
    }

    @Override
    protected void onCreate() {
        super.onCreate();

        switch (mVastVideoConfiguration.getCustomForceOrientation()) {
            case FORCE_PORTRAIT:
                getBaseVideoViewControllerListener().onSetRequestedOrientation(SCREEN_ORIENTATION_PORTRAIT);
                break;
            case FORCE_LANDSCAPE:
                getBaseVideoViewControllerListener().onSetRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);
                break;
            case DEVICE_ORIENTATION:
                break;  // don't do anything
            case UNDEFINED:
                break;  // don't do anything
            default:
                break;
        }

        downloadCompanionAd();

        makeTrackingHttpRequest(
                mVastVideoConfiguration.getImpressionTrackers(),
                getContext(),
                BaseEvent.Name.IMPRESSION_REQUEST
        );
        broadcastAction(ACTION_INTERSTITIAL_SHOW);
    }

    @Override
    protected void onResume() {
        // When resuming, VideoView needs to reinitialize its MediaPlayer with the video path
        // and therefore reset the count to zero, to let it retry on error
        mVideoRetries = 0;
        startProgressChecker();

        mVideoView.seekTo(mSeekerPositionOnPause);
        if (!mIsVideoFinishedPlaying) {
            mVideoView.start();
        }
    }

    @Override
    protected void onPause() {
        stopProgressChecker();
        mSeekerPositionOnPause = mVideoView.getCurrentPosition();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        stopProgressChecker();
        broadcastAction(ACTION_INTERSTITIAL_DISMISS);
    }

    // Enable the device's back button when the video close button has been displayed
    @Override
    public boolean backButtonEnabled() {
        return mShowCloseButtonEventFired;
    }

    @Override
    void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == MOPUB_BROWSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            getBaseVideoViewControllerListener().onFinish();
        }
    }

    // DownloadTaskListener
    @Override
    public void onComplete(String url, DownloadResponse downloadResponse) {
        if (downloadResponse != null && downloadResponse.getStatusCode() == HttpStatus.SC_OK) {
            final Bitmap companionAdBitmap = HttpResponses.asBitmap(downloadResponse);
            if (companionAdBitmap != null) {
                // If Bitmap fits in ImageView, then don't use MATCH_PARENT
                final int width = Dips.dipsToIntPixels(companionAdBitmap.getWidth(), getContext());
                final int height = Dips.dipsToIntPixels(companionAdBitmap.getHeight(), getContext());
                final int imageViewWidth = mCompanionAdImageView.getMeasuredWidth();
                final int imageViewHeight = mCompanionAdImageView.getMeasuredHeight();
                if (width < imageViewWidth && height < imageViewHeight) {
                    mCompanionAdImageView.getLayoutParams().width = width;
                    mCompanionAdImageView.getLayoutParams().height = height;
                }
                mCompanionAdImageView.setImageBitmap(companionAdBitmap);
                mCompanionAdImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mVastCompanionAd != null) {
                            handleClick(
                                    mVastCompanionAd.getClickTrackers(),
                                    mVastCompanionAd.getClickThroughUrl()
                            );
                        }
                    }
                });
            }
        }
    }

    private void downloadCompanionAd() {
        if (mVastCompanionAd != null) {
            try {
                final HttpGet httpGet = initializeHttpGet(mVastCompanionAd.getImageUrl(), getContext());
                final DownloadTask downloadTask = new DownloadTask(this);
                AsyncTasks.safeExecuteOnExecutor(downloadTask, httpGet);
            } catch (Exception e) {
                MoPubLog.d("Failed to download companion ad", e);
            }
        }
    }

    private void adjustSkipOffset() {
        int videoDuration = mVideoView.getDuration();

        // Default behavior: video is non-skippable if duration < 16 seconds
        if (videoDuration < MAX_VIDEO_DURATION_FOR_CLOSE_BUTTON) {
            mShowCloseButtonDelay = videoDuration;
        }

        // Override if skipoffset attribute is specified in VAST
        String skipOffsetString = mVastVideoConfiguration.getSkipOffset();
        if (skipOffsetString != null) {
            try {
                if (Strings.isAbsoluteTracker(skipOffsetString)) {
                    Integer skipOffsetMilliseconds = Strings.parseAbsoluteOffset(skipOffsetString);
                    if (skipOffsetMilliseconds != null && skipOffsetMilliseconds < videoDuration) {
                        mShowCloseButtonDelay = skipOffsetMilliseconds;
                        mHasSkipOffset = true;
                    }
                } else if (Strings.isPercentageTracker(skipOffsetString)) {
                    float percentage = Float.parseFloat(skipOffsetString.replace("%", "")) / 100f;
                    int skipOffsetMillisecondsRounded = Math.round(videoDuration * percentage);
                    if (skipOffsetMillisecondsRounded < videoDuration) {
                        mShowCloseButtonDelay = skipOffsetMillisecondsRounded;
                        mHasSkipOffset = true;
                    }
                } else {
                    MoPubLog.d(String.format("Invalid VAST skipoffset format: %s", skipOffsetString));
                }
            } catch (NumberFormatException e) {
                MoPubLog.d(String.format("Failed to parse skipoffset %s", skipOffsetString));
            }
        }
    }

    @NonNull
    private Runnable createVideoProgressCheckerRunnable() {
        // This Runnable must only be run from the main thread due to accessing
        // class instance variables
        return new Runnable() {
            @Override
            public void run() {
                int videoLength = mVideoView.getDuration();
                int currentPosition = mVideoView.getCurrentPosition();

                if (videoLength > 0) {
                    final List<VastTracker> trackersToTrack =
                            getUntriggeredTrackersBefore(currentPosition, videoLength);
                    if (!trackersToTrack.isEmpty()) {
                        final List<String> trackUrls = new ArrayList<String>();
                        for (VastTracker tracker : trackersToTrack) {
                            trackUrls.add(tracker.getTrackingUrl());
                            tracker.setTracked();
                        }
                        TrackingRequest.makeTrackingHttpRequest(trackUrls, getContext());
                    }

                    // show countdown if any of the following conditions is satisfied:
                    // 1) long video
                    // 2) skipoffset is specified in VAST and is less than video duration
                    if (isLongVideo(mVideoView.getDuration()) ||
                            (mHasSkipOffset && mShowCloseButtonDelay < mVideoView.getDuration())) {
                        mVastVideoToolbar.updateCountdownWidget(mShowCloseButtonDelay - mVideoView.getCurrentPosition());
                    }

                    if (shouldBeInteractable()) {
                        makeVideoInteractable();
                    }
                }

                mVastVideoToolbar.updateDurationWidget(mVideoView.getDuration() - mVideoView.getCurrentPosition());

                if (mIsVideoProgressShouldBeChecked) {
                    mHandler.postDelayed(mVideoProgressCheckerRunnable, VIDEO_PROGRESS_TIMER_CHECKER_DELAY);
                }
            }
        };
    }

    /**
     * Returns untriggered VAST progress trackers with a progress before the provided position.
     *
     * @param currentPositionMillis the current video position in milliseconds.
     * @param videoLengthMillis the total video length.
     */
    @NonNull
    private List<VastTracker> getUntriggeredTrackersBefore(int currentPositionMillis, int videoLengthMillis) {
        if (Preconditions.NoThrow.checkArgument(videoLengthMillis > 0)) {
            float progressFraction = currentPositionMillis / (float) (videoLengthMillis);
            List<VastTracker> untriggeredTrackers = new ArrayList<VastTracker>();

            final ArrayList<VastAbsoluteProgressTracker> absoluteTrackers = mVastVideoConfiguration.getAbsoluteTrackers();
            VastAbsoluteProgressTracker absoluteTest = new VastAbsoluteProgressTracker("", currentPositionMillis);
            int absoluteTrackerCount = absoluteTrackers.size();
            for (int i = 0; i < absoluteTrackerCount; i++) {
                VastAbsoluteProgressTracker tracker = absoluteTrackers.get(i);
                if (tracker.compareTo(absoluteTest) > 0) {
                    break;
                }
                if (!tracker.isTracked()) {
                    untriggeredTrackers.add(tracker);
                }
            }

            final ArrayList<VastFractionalProgressTracker> fractionalTrackers = mVastVideoConfiguration.getFractionalTrackers();
            final VastFractionalProgressTracker fractionalTest = new VastFractionalProgressTracker("", progressFraction);
            int fractionalTrackerCount = fractionalTrackers.size();
            for (int i = 0; i < fractionalTrackerCount; i++) {
                VastFractionalProgressTracker tracker = fractionalTrackers.get(i);
                if (tracker.compareTo(fractionalTest) > 0) {
                    break;
                }
                if (!tracker.isTracked()) {
                    untriggeredTrackers.add(tracker);
                }
            }

            return untriggeredTrackers;
        } else {
            return Collections.emptyList();
        }
    }

    private int remainingProgressTrackerCount() {
        return getUntriggeredTrackersBefore(Integer.MAX_VALUE, Integer.MAX_VALUE).size();
    }

    private void createVideoBackground(final Context context) {
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {Color.argb(0,0,0,0), Color.argb(255,0,0,0)}
        );
        Drawable[] layers = new Drawable[2];
        layers[0] = Drawables.THATCHED_BACKGROUND.createDrawable(context);
        layers[1] = gradientDrawable;
        LayerDrawable layerList = new LayerDrawable(layers);
        getLayout().setBackgroundDrawable(layerList);
    }

    private VastVideoToolbar createVastVideoToolBar(final Context context) {
        final VastVideoToolbar vastVideoToolbar = new VastVideoToolbar(context);
        vastVideoToolbar.setCloseButtonOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    TrackingRequest.makeTrackingHttpRequest(
                            mVastVideoConfiguration.getCloseTrackers(), context);
                    TrackingRequest.makeTrackingHttpRequest(
                            mVastVideoConfiguration.getSkipTrackers(), context);
                    getBaseVideoViewControllerListener().onFinish();
                }
                return true;
            }
        });
        vastVideoToolbar.setLearnMoreButtonOnTouchListener(mClickThroughListener);

        // update custom CTA text if specified in VAST extension
        String customCtaText = mVastVideoConfiguration.getCustomCtaText();
        if (customCtaText != null) {
            vastVideoToolbar.updateLearnMoreButtonText(customCtaText);
        }

        // update custom skip text if specified in VAST extensions
        String customSkipText = mVastVideoConfiguration.getCustomSkipText();
        if (customSkipText != null) {
            vastVideoToolbar.updateCloseButtonText(customSkipText);
        }

        // update custom close icon if specified in VAST extensions
        String customCloseIconUrl = mVastVideoConfiguration.getCustomCloseIconUrl();
        if (customCloseIconUrl != null) {
            vastVideoToolbar.updateCloseButtonIcon(customCloseIconUrl);
        }

        return vastVideoToolbar;
    }

    private VideoView createVideoView(final Context context) {
        final VideoView videoView = new VideoView(context);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Called when media source is ready for playback
                adjustSkipOffset();
            }
        });
        videoView.setOnTouchListener(mClickThroughListener);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopProgressChecker();
                makeVideoInteractable();

                videoCompleted(false);
                mIsVideoFinishedPlaying = true;

                // Only fire the completion tracker if we hit all the progress marks. Some Android implementations
                // fire the completion event even if the whole video isn't watched.
                if (!mVideoError && remainingProgressTrackerCount() == 0 && !mCompletionTrackerFired) {
                    makeTrackingHttpRequest(mVastVideoConfiguration.getCompleteTrackers(), context);
                    mCompletionTrackerFired = true;
                }

                videoView.setVisibility(View.GONE);
                // check the drawable to see if the image view was populated with content
                if (mCompanionAdImageView.getDrawable() != null) {
                    mCompanionAdImageView.setVisibility(View.VISIBLE);
                }
            }
        });

        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(final MediaPlayer mediaPlayer, final int what, final int extra) {
                if (retryMediaPlayer(mediaPlayer, what, extra)) {
                    return true;
                } else {
                    stopProgressChecker();
                    makeVideoInteractable();
                    videoError(false);
                    mVideoError = true;
                    return false;
                }
            }
        });

        videoView.setVideoPath(mVastVideoConfiguration.getDiskMediaFileUrl());

        return videoView;
    }

    boolean retryMediaPlayer(final MediaPlayer mediaPlayer, final int what, final int extra) {
        // XXX
        // VideoView has a bug in versions lower than Jelly Bean, Api Level 16, Android 4.1
        // For api < 16, VideoView is not able to read files written to disk since it reads them in
        // a Context different from the Application and therefore does not have correct permission.
        // To solve this problem we obtain the video file descriptor ourselves with valid permissions
        // and pass it to the underlying MediaPlayer in VideoView.
        if (VersionCode.currentApiLevel().isBelow(VersionCode.JELLY_BEAN)
                && what == MediaPlayer.MEDIA_ERROR_UNKNOWN
                && extra == VIDEO_VIEW_FILE_PERMISSION_ERROR
                && mVideoRetries < MAX_VIDEO_RETRIES) {

            FileInputStream inputStream = null;
            try {
                mediaPlayer.reset();
                final File file = new File(mVastVideoConfiguration.getDiskMediaFileUrl());
                inputStream = new FileInputStream(file);
                mediaPlayer.setDataSource(inputStream.getFD());

                // XXX
                // VideoView has a callback registered with the MediaPlayer to set a flag when the
                // media file has been prepared. Start also sets a flag in VideoView indicating the
                // desired state is to play the video. Therefore, whichever method finishes last
                // will check both flags and begin playing the video.
                mediaPlayer.prepareAsync();
                mVideoView.start();
                return true;
            } catch (Exception e) {
                return false;
            } finally {
                Streams.closeStream(inputStream);
                mVideoRetries++;
            }
        }
        return false;
    }

    /**
     * Called upon user click. Attempts open mopubnativebrowser links in the device browser and all
     * other links in the MoPub in-app browser.
     */
    @VisibleForTesting
    void handleClick(final List<String> clickThroughTrackers, final String clickThroughUrl) {
        makeTrackingHttpRequest(clickThroughTrackers, getContext(), BaseEvent.Name.CLICK_REQUEST);

        if (TextUtils.isEmpty(clickThroughUrl)) {
            return;
        }

        broadcastAction(ACTION_INTERSTITIAL_CLICK);

        if (Intents.isAboutScheme(clickThroughUrl)) {
            MoPubLog.d("Link to about page ignored.");
            return;
        }

        if (Intents.isNativeBrowserScheme(clickThroughUrl)) {
            try {
                final Intent intent = Intents.intentForNativeBrowserScheme(clickThroughUrl);
                Intents.startActivity(getContext(), intent);
                return;
            } catch (UrlParseException e) {
                MoPubLog.d(e.getMessage());
            } catch (IntentNotResolvableException e) {
                MoPubLog.d("Could not handle intent for URI: " + clickThroughUrl + ". "
                        + e.getMessage());
            }

            return;
        }

        if (Intents.isHttpUrl(clickThroughUrl)) {
            Bundle bundle = new Bundle();
            bundle.putString(MoPubBrowser.DESTINATION_URL_KEY, clickThroughUrl);

            getBaseVideoViewControllerListener().onStartActivityForResult(MoPubBrowser.class,
                    MOPUB_BROWSER_REQUEST_CODE, bundle);
            return;
        }

        MoPubLog.d("Link ignored. Unable to handle url: " + clickThroughUrl);
    }

    private ImageView createCompanionAdImageView(final Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams layoutParams =
                new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.BELOW, mVastVideoToolbar.getId());
        getLayout().addView(relativeLayout, layoutParams);

        ImageView imageView = new ImageView(context);
        // Set to invisible to have it be drawn to calculate size
        imageView.setVisibility(View.INVISIBLE);

        final RelativeLayout.LayoutParams companionAdLayout = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        relativeLayout.addView(imageView, companionAdLayout);
        return imageView;
    }

    private boolean isLongVideo(final int duration) {
        return (duration >= MAX_VIDEO_DURATION_FOR_CLOSE_BUTTON);
    }

    private void makeVideoInteractable() {
        mShowCloseButtonEventFired = true;
        mVastVideoToolbar.makeInteractable();
    }

    private boolean shouldBeInteractable() {
        return !mShowCloseButtonEventFired && mVideoView.getCurrentPosition() >= mShowCloseButtonDelay;
    }

    private boolean shouldAllowClickThrough() {
        return mShowCloseButtonEventFired;
    }

    private void startProgressChecker() {
        if (!mIsVideoProgressShouldBeChecked) {
            mIsVideoProgressShouldBeChecked = true;
            mHandler.post(mVideoProgressCheckerRunnable);
        }
    }

    private void stopProgressChecker() {
        if (mIsVideoProgressShouldBeChecked) {
            mIsVideoProgressShouldBeChecked = false;
            mHandler.removeCallbacks(mVideoProgressCheckerRunnable);
        }
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    boolean getIsVideoProgressShouldBeChecked() {
        return mIsVideoProgressShouldBeChecked;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    int getVideoRetries() {
        return mVideoRetries;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    boolean getHasSkipOffset() {
        return mHasSkipOffset;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    int getShowCloseButtonDelay() {
        return mShowCloseButtonDelay;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    boolean isShowCloseButtonEventFired() {
        return mShowCloseButtonEventFired;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    void setCloseButtonVisible(boolean visible) {
        mShowCloseButtonEventFired = visible;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    boolean isVideoFinishedPlaying() {
        return mIsVideoFinishedPlaying;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    ImageView getCompanionAdImageView() {
        return mCompanionAdImageView;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    void setVideoError() {
        mVideoError = true;
    }

    // for testing
    @Deprecated
    @VisibleForTesting
    boolean getVideoError() {
        return mVideoError;
    }
}
