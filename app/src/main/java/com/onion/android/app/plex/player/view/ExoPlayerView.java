package com.onion.android.app.plex.player.view;

import static android.content.Context.MODE_PRIVATE;
import static com.onion.android.app.constants.PlexConstants.PREF_FILE;
import static com.onion.android.app.constants.PlexConstants.SUBS_BACKGROUND;
import static com.onion.android.app.constants.PlexConstants.SUBS_SIZE;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.text.CaptionStyleCompat;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.onion.android.R;
import com.onion.android.app.plex.data.model.media.MediaModel;
import com.onion.android.app.plex.player.controller.PlayerController;
import com.onion.android.app.plex.player.interfaces.ControlInterface;
import com.onion.android.app.plex.player.interfaces.PlaybackActionCallback;
import com.tubitv.ui.VaudTextView;
import com.tubitv.ui.VaudType;

import java.util.List;

import timber.log.Timber;

/**
 * Created by stoyan tubi_tv_quality_on 3/22/17.
 */
@TargetApi(16)
public class ExoPlayerView extends PlayerView {


    private static final String TAG = ExoPlayerView.class.getSimpleName();
    private static final int SURFACE_TYPE_NONE = 0;
    private static final int SURFACE_TYPE_SURFACE_VIEW = 1;
    private static final int SURFACE_TYPE_TEXTURE_VIEW = 2;

    private final AspectRatioFrameLayout contentFrame;
    private final View shutterView;
    private final View surfaceView;
    private final SubtitleView subtitleView;
    private final ComponentListener componentListener;
    private View mUserInteractionView;
    private SimpleExoPlayer player;
    private PlayerController userController;

    public ExoPlayerView(Context context) {
        this(context, null);
    }

    public ExoPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ExoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (isInEditMode()) {
            contentFrame = null;
            shutterView = null;
            surfaceView = null;
            subtitleView = null;
            mUserInteractionView = null;
            componentListener = null;
            ImageView logo = new ImageView(context, attrs);
            if (Util.SDK_INT >= 23) {
                configureEditModeLogoV23(getResources(), logo);
            } else {
                configureEditModeLogo(getResources(), logo);
            }
            addView(logo);
            return;
        }

        int playerLayoutId = R.layout.plex_player_view;
        int surfaceType = SURFACE_TYPE_SURFACE_VIEW;
        int resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT;
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlayerView, 0, 0);
            try {
                playerLayoutId = a.getResourceId(R.styleable.PlayerView_player_layout_id, playerLayoutId);
                surfaceType = a.getInt(R.styleable.PlayerView_surface_type, surfaceType);
                resizeMode = a.getInt(R.styleable.PlayerView_resize_mode, resizeMode);
            } finally {
                a.recycle();
            }
        }

        LayoutInflater.from(context).inflate(playerLayoutId, this);
        componentListener = new ComponentListener();
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);

        // Content frame.
        contentFrame = findViewById(R.id.exo_content_frame);
        if (contentFrame != null) {
            setResizeModeRaw(contentFrame, resizeMode);
        }

        // Shutter view.
        shutterView = findViewById(R.id.exo_shutter);

        // Create a surface view and insert it into the content frame, if there is one.
        if (contentFrame != null && surfaceType != SURFACE_TYPE_NONE) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            surfaceView = surfaceType == SURFACE_TYPE_TEXTURE_VIEW ? new TextureView(context) : new SurfaceView(context);
            surfaceView.setLayoutParams(params);
            contentFrame.addView(surfaceView, 0);
        } else {
            surfaceView = null;
        }

        // Subtitle view.


        SharedPreferences preferences = getContext().getSharedPreferences(PREF_FILE, MODE_PRIVATE);

        subtitleView = findViewById(R.id.exo_subtitles);


        if (subtitleView != null) {


            switch (preferences.getString(SUBS_BACKGROUND, "Transparent")) {
                case "Transparent":
                case "Grey":
                    subtitleView.setStyle(new CaptionStyleCompat(
                            Color.WHITE,
                            getResources().getColor(R.color.transparent),
                            Color.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_NONE,
                            Color.WHITE,
                            VaudTextView.getFont(context, VaudType.VAUD_REGULAR.getAssetFileName())));
                    break;
                case "Black":
                    subtitleView.setStyle(new CaptionStyleCompat(
                            Color.WHITE,
                            getResources().getColor(R.color.black_subtitles_background),
                            Color.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_NONE,
                            Color.WHITE,
                            VaudTextView.getFont(context, VaudType.VAUD_REGULAR.getAssetFileName())));
                    break;
                case "Red":
                    subtitleView.setStyle(new CaptionStyleCompat(
                            Color.WHITE,
                            getResources().getColor(R.color.red_subtitles_background),
                            Color.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_NONE,
                            Color.WHITE,
                            VaudTextView.getFont(context, VaudType.VAUD_REGULAR.getAssetFileName())));
                    break;
                case "Yellow":
                    subtitleView.setStyle(new CaptionStyleCompat(
                            Color.WHITE,
                            getResources().getColor(R.color.yellow_subtitles_background),
                            Color.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_NONE,
                            Color.WHITE,
                            VaudTextView.getFont(context, VaudType.VAUD_REGULAR.getAssetFileName())));

                    break;
                case "Green":
                    subtitleView.setStyle(new CaptionStyleCompat(
                            Color.WHITE,
                            getResources().getColor(R.color.green_subtitles_background),
                            Color.TRANSPARENT,
                            CaptionStyleCompat.EDGE_TYPE_NONE,
                            Color.WHITE,
                            VaudTextView.getFont(context, VaudType.VAUD_REGULAR.getAssetFileName())));
                    break;
            }
            subtitleView.setFixedTextSize(TypedValue.COMPLEX_UNIT_DIP, Float.parseFloat(preferences.getString(SUBS_SIZE, "16f")));
            subtitleView.setApplyEmbeddedStyles(false);
            subtitleView.setVisibility(View.INVISIBLE);
        }
        userController = new PlayerController();
    }

    @TargetApi(23)
    private static void configureEditModeLogoV23(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo, null));
        logo.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color, null));
    }

    @SuppressWarnings("deprecation")
    private static void configureEditModeLogo(Resources resources, ImageView logo) {
        logo.setImageDrawable(resources.getDrawable(R.drawable.exo_edit_mode_logo));
        logo.setBackgroundColor(resources.getColor(R.color.exo_edit_mode_background_color));
    }

    @SuppressWarnings("ResourceType")
    private static void setResizeModeRaw(AspectRatioFrameLayout aspectRatioFrame, int resizeMode) {
        aspectRatioFrame.setResizeMode(resizeMode);
    }

    public void addUserInteractionView(@Nullable View controlView) {
        if (controlView == null) {
            Timber.e(TAG, "addUserInteractionView()----> adding empty view");
            return;
        }
        // Playback control view.
        View controllerPlaceholder = findViewById(R.id.exo_controller_placeholder);
        if (controllerPlaceholder != null) {
            // Note: rewindMs and fastForwardMs are passed via attrs, so we don't need to make explicit
            // calls to set them.
            mUserInteractionView = controlView;
            ViewGroup parent = ((ViewGroup) controllerPlaceholder.getParent());
            int controllerIndex = parent.indexOfChild(controllerPlaceholder);
            parent.removeView(controllerPlaceholder);
            parent.addView(mUserInteractionView, controllerIndex);
        } else {
            this.mUserInteractionView = null;
        }
    }

    public void setAspectRatio(float widthHeightRatio) {
        contentFrame.setAspectRatio(widthHeightRatio);
    }

    public View getControlView() {
        return mUserInteractionView;
    }

    public ControlInterface getPlayerController() {
        return userController;
    }

    /**
     * Returns the player currently set on this view, or null if no player is set.
     */

    @Override
    public SimpleExoPlayer getPlayer() {
        return player;
    }

    /**
     * Set the {@link SimpleExoPlayer} to use. The {@link SimpleExoPlayer#setTextOutput} and
     * {@link SimpleExoPlayer#setVideoListener} method of the player will be called and previous
     * assignments are overridden.
     *
     * @param player The {@link SimpleExoPlayer} to use.
     */
    public void setPlayer(SimpleExoPlayer player, @NonNull PlaybackActionCallback playbackActionCallback) {
        if (this.player == player) {
            return;
        }
        if (this.player != null) {
            this.player.removeListener(componentListener);
            this.player.removeTextOutput(componentListener);
            this.player.removeVideoListener(componentListener);
            if (surfaceView instanceof TextureView) {
                this.player.clearVideoTextureView((TextureView) surfaceView);
            } else if (surfaceView instanceof SurfaceView) {
                this.player.clearVideoSurfaceView((SurfaceView) surfaceView);
            }
        }
        this.player = player;

        if (userController != null) {
            userController.setPlayer(player, playbackActionCallback, this);
        }


        shutterView.setVisibility(VISIBLE);

        if (player != null) {
            if (surfaceView instanceof TextureView) {
                player.setVideoTextureView((TextureView) surfaceView);
            } else if (surfaceView instanceof SurfaceView) {
                player.setVideoSurfaceView((SurfaceView) surfaceView);
            }
            player.addVideoListener(componentListener);
            player.addTextOutput(componentListener);
            player.addVideoListener(componentListener);
            player.addListener(componentListener);
        }
    }

    /**
     * Sets the resize mode.
     *
     * @param resizeMode The resize mode.
     */

    @Override
    public void setResizeMode(@AspectRatioFrameLayout.ResizeMode int resizeMode) {
        Assertions.checkState(contentFrame != null);
        contentFrame.setResizeMode(resizeMode);
    }

    /**
     * Gets the {@link SubtitleView}.
     *
     * @return The {@link SubtitleView}, or {@code null} if the layout has been customized and the
     * subtitle view is not present.
     */

    @Override
    public SubtitleView getSubtitleView() {
        return subtitleView;
    }

    public void setMediaModel(@NonNull MediaModel mediaModel) {
        if (userController != null) {
            userController.setMediaModel(mediaModel, getContext());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        userController = null;
        player = null;
    }

    private final class ComponentListener implements VideoListener, TextOutput, Player.EventListener {

        // TextRenderer.Output implementation

        @Override
        public void onCues(@NonNull List<Cue> cues) {
            if (subtitleView != null) {
                subtitleView.onCues(cues);
            }
        }

        // SimpleExoPlayer.VideoListener implementation

        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            if (contentFrame != null) {
                float aspectRatio = height == 0 ? 1 : (width * pixelWidthHeightRatio) / height;
                contentFrame.setAspectRatio(aspectRatio);
            }
        }

        @Override
        public void onRenderedFirstFrame() {
            if (shutterView != null) {
                shutterView.setVisibility(INVISIBLE);
            }
        }

        @Override
        public void onTracksChanged(@NonNull TrackGroupArray tracks, @NonNull TrackSelectionArray selections) {
        }

        // ExoPlayer.EventListener implementation
        @Override
        public void onLoadingChanged(boolean isLoading) {
        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        }

        @Override
        public void onRepeatModeChanged(final int repeatMode) {
        }

        @Override
        public void onShuffleModeEnabledChanged(final boolean shuffleModeEnabled) {
        }

        @Override
        public void onPlayerError(@NonNull ExoPlaybackException e) {
        }

        @Override
        public void onPositionDiscontinuity(final int reason) {
        }

        @Override
        public void onPlaybackParametersChanged(@NonNull PlaybackParameters playbackParameters) {
        }

        @Override
        public void onSeekProcessed() {
        }

        @Override
        public void onTimelineChanged(@NonNull final Timeline timeline, final int reason) {
        }

    }

}
