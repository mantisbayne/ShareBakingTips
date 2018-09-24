package com.mobile.meredithbayne.recipesharing.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.mobile.meredithbayne.recipesharing.R;
import com.mobile.meredithbayne.recipesharing.model.Recipe;
import com.mobile.meredithbayne.recipesharing.model.Step;
import com.mobile.meredithbayne.recipesharing.ui.activity.RecipeStepDetailActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mobile.meredithbayne.recipesharing.ui.activity.RecipeStepActivity.EXTRA_RECIPE;
import static com.mobile.meredithbayne.recipesharing.ui.activity.RecipeStepActivity.EXTRA_STEP;

public class RecipeStepDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String STATE_POSITION = "position";
    private static final String STATE_PLAY_WHEN_READY = "play_when_ready";


    @BindView(R.id.step_video)
    PlayerView mStepVideo;

    @BindView(R.id.step_thumbnail)
    ImageView mThumbnail;

    @BindView(R.id.step_description)
    TextView mStepDescription;

    @BindView(R.id.next_step_header)
    TextView mNextStepHeader;

    @BindView(R.id.recipe_open_arrow)
    ImageView mNextStepArrow;

    private SimpleExoPlayer mPlayerView;
    private long mPlaybackPosition = 0;
    private boolean mPlayWhenReady = true;

    private Step mStep;
    private Recipe mRecipe;

    public RecipeStepDetailsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            mStep = args.getParcelable(EXTRA_STEP);
            mRecipe = args.getParcelable(EXTRA_RECIPE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.recipe_step_detail, container, false);

        ButterKnife.bind(this, root);

        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_POSITION)) {
            mPlaybackPosition = savedInstanceState.getLong(STATE_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
        }

        mStepDescription.setText(mStep.getDescription());

        if (!mStep.getThumbnailURL().isEmpty()) {
            Picasso.Builder builder = new Picasso.Builder(getActivity());
            builder.build().load(mStep.getThumbnailURL())
                    .noFade()
                    .error(R.drawable.ic_error_outline_black)
                    .into(mThumbnail);
            mThumbnail.setVisibility(View.VISIBLE);
        }

        showOrHideNextStepView();

        mNextStepArrow.setOnClickListener(this);
        mNextStepHeader.setOnClickListener(this);

        return root;
    }

    private void showOrHideNextStepView() {
        int size = mRecipe.getSteps().size();
        if (mStep.getId() == size - 1) {
            mNextStepHeader.setVisibility(View.GONE);
            mNextStepArrow.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(STATE_POSITION, mPlaybackPosition);
        outState.putBoolean(STATE_PLAY_WHEN_READY, mPlayWhenReady);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LinearLayout.LayoutParams params =
                    (LinearLayout.LayoutParams) mStepVideo.getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            mStepVideo.setLayoutParams(params);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mPlayerView == null)) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                initializePlayer();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mPlayerView != null) {
            mPlaybackPosition = mPlayerView.getCurrentPosition();
            mPlayWhenReady = mPlayerView.getPlayWhenReady();
            mPlayerView.release();
            mPlayerView = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mStepVideo.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private void initializePlayer() {
        mPlayerView = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mStepVideo.setPlayer(mPlayerView);

        mPlayerView.setPlayWhenReady(mPlayWhenReady);
        mPlayerView.seekTo(mPlaybackPosition);

        Uri uri = Uri.parse(mStep.getVideoURL());
        MediaSource mediaSource = buildMediaSource(uri);
        mPlayerView.prepare(mediaSource, true, false);
        mStepVideo.setVisibility(View.VISIBLE);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.app_name))).
                createMediaSource(uri);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), RecipeStepDetailActivity.class);
        intent.putExtra(EXTRA_RECIPE, mRecipe);
        intent.putExtra(EXTRA_STEP, mRecipe.getSteps().get(mStep.getId() + 1));
        startActivity(intent);
    }
}
