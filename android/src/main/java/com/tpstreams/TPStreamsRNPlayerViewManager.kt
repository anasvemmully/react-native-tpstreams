package com.tpstreams

import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.ViewManagerDelegate
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.viewmanagers.TPStreamsRNPlayerViewManagerInterface
import com.facebook.react.viewmanagers.TPStreamsRNPlayerViewManagerDelegate
import com.facebook.react.common.MapBuilder

@ReactModule(name = TPStreamsRNPlayerViewManager.NAME)
class TPStreamsRNPlayerViewManager : SimpleViewManager<TPStreamsRNPlayerView>(),
  TPStreamsRNPlayerViewManagerInterface<TPStreamsRNPlayerView> {
  
  companion object {
    const val NAME = "TPStreamsRNPlayerView"
    
    // Event names
    private const val EVENT_CURRENT_POSITION = "onCurrentPosition"
    private const val EVENT_DURATION = "onDuration"
    private const val EVENT_IS_PLAYING = "onIsPlaying"
    private const val EVENT_PLAYBACK_SPEED = "onPlaybackSpeed"
    private const val EVENT_PLAYER_STATE_CHANGED = "onPlayerStateChanged"
    private const val EVENT_IS_PLAYING_CHANGED = "onIsPlayingChanged"
    private const val EVENT_PLAYBACK_SPEED_CHANGED = "onPlaybackSpeedChanged"
    private const val EVENT_IS_LOADING_CHANGED = "onIsLoadingChanged"
    private const val EVENT_ERROR = "onError"
    private const val EVENT_ACCESS_TOKEN_EXPIRED = "onAccessTokenExpired"
  }
  
  private val mDelegate: ViewManagerDelegate<TPStreamsRNPlayerView> = 
    TPStreamsRNPlayerViewManagerDelegate(this)

  override fun getDelegate(): ViewManagerDelegate<TPStreamsRNPlayerView>? = mDelegate

  override fun getName(): String = NAME

  override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any> {
    return MapBuilder.builder<String, Any>()
      .put(EVENT_CURRENT_POSITION, MapBuilder.of("registrationName", EVENT_CURRENT_POSITION))
      .put(EVENT_DURATION, MapBuilder.of("registrationName", EVENT_DURATION))
      .put(EVENT_IS_PLAYING, MapBuilder.of("registrationName", EVENT_IS_PLAYING))
      .put(EVENT_PLAYBACK_SPEED, MapBuilder.of("registrationName", EVENT_PLAYBACK_SPEED))
      .put(EVENT_PLAYER_STATE_CHANGED, MapBuilder.of("registrationName", EVENT_PLAYER_STATE_CHANGED))
      .put(EVENT_IS_PLAYING_CHANGED, MapBuilder.of("registrationName", EVENT_IS_PLAYING_CHANGED))
      .put(EVENT_PLAYBACK_SPEED_CHANGED, MapBuilder.of("registrationName", EVENT_PLAYBACK_SPEED_CHANGED))
      .put(EVENT_IS_LOADING_CHANGED, MapBuilder.of("registrationName", EVENT_IS_LOADING_CHANGED))
      .put(EVENT_ERROR, MapBuilder.of("registrationName", EVENT_ERROR))
      .put(EVENT_ACCESS_TOKEN_EXPIRED, MapBuilder.of("registrationName", EVENT_ACCESS_TOKEN_EXPIRED))
      .build()
  }

  public override fun createViewInstance(context: ThemedReactContext): TPStreamsRNPlayerView =
    TPStreamsRNPlayerView(context)

  @ReactProp(name = "videoId")
  override fun setVideoId(view: TPStreamsRNPlayerView, videoId: String?) {
    view.setVideoId(videoId)
  }

  @ReactProp(name = "accessToken")
  override fun setAccessToken(view: TPStreamsRNPlayerView, accessToken: String?) {
    view.setAccessToken(accessToken)
  }

  @ReactProp(name = "shouldAutoPlay")
  override fun setShouldAutoPlay(view: TPStreamsRNPlayerView, shouldAutoPlay: Boolean) {
    view.setShouldAutoPlay(shouldAutoPlay)
  }

  @ReactProp(name = "startAt")
  override fun setStartAt(view: TPStreamsRNPlayerView, startAt: Double) {
    view.setStartAt(startAt.toLong())
  }

  @ReactProp(name = "showDefaultCaptions")
  override fun setShowDefaultCaptions(view: TPStreamsRNPlayerView, showDefaultCaptions: Boolean) {
    view.setShowDefaultCaptions(showDefaultCaptions)
  }

  @ReactProp(name = "enableDownload")
  override fun setEnableDownload(view: TPStreamsRNPlayerView, enableDownload: Boolean) {
    view.setEnableDownload(enableDownload)
  }

  @ReactProp(name = "offlineLicenseExpireTime")
  override fun setOfflineLicenseExpireTime(view: TPStreamsRNPlayerView, expireTime: Double) {
    view.setOfflineLicenseExpireTime(expireTime.toLong())
  }

  @ReactProp(name = "downloadMetadata")
  override fun setDownloadMetadata(view: TPStreamsRNPlayerView, metadata: String?) {
    val metadataMap = if (!metadata.isNullOrEmpty()) {
      try {
        val jsonObject = org.json.JSONObject(metadata)
        val map = jsonObject.keys()
        .asSequence()
        .associate { it to jsonObject.getString(it) }
        .toMutableMap()
        map
      } catch (e: org.json.JSONException) {
        android.util.Log.w("TPStreamsRN", "Error parsing download metadata: ${e.message}")
        null
      }
    } else null
    view.setDownloadMetadata(metadataMap)
  }

  @ReactProp(name = "enableNotification")
  override fun setEnableNotification(view: TPStreamsRNPlayerView, enabled: Boolean) {
    view.setEnableNotification(enabled)
  }

  @ReactProp(name = "disableCaption")
  override fun setDisableCaption(view: TPStreamsRNPlayerView, disabled: Boolean) {
    view.setDisableCaption(disabled)
  }

  @ReactProp(name = "metadata")
  override fun setMetadata(view: TPStreamsRNPlayerView, metadata: String?) {
    view.setMetadata(metadata)
  }

  // Command implementations
  override fun play(view: TPStreamsRNPlayerView) {
    view.play()
  }
  
  override fun pause(view: TPStreamsRNPlayerView) {
    view.pause()
  }
  
  override fun seekTo(view: TPStreamsRNPlayerView, positionMs: Double) {
    view.seekTo(positionMs.toLong())
  }
  
  override fun setPlaybackSpeed(view: TPStreamsRNPlayerView, speed: Float) {
    view.setPlaybackSpeed(speed)
  }
  
  override fun getCurrentPosition(view: TPStreamsRNPlayerView) {
    view.getCurrentPosition()
  }
  
  override fun getDuration(view: TPStreamsRNPlayerView) {
    view.getDuration()
  }
  
  override fun isPlaying(view: TPStreamsRNPlayerView) {
    view.isPlaying()
  }
  
  override fun getPlaybackSpeed(view: TPStreamsRNPlayerView) {
    view.getPlaybackSpeed()
  }
  
  override fun setNewAccessToken(view: TPStreamsRNPlayerView, newToken: String) {
    view.setNewAccessToken(newToken)
  }

  override fun setTitle(view: TPStreamsRNPlayerView, title: String) {
    view.setTitle(title)
  }

  override fun setArtist(view: TPStreamsRNPlayerView, artist: String) {
    view.setArtist(artist)
  }

  override fun onAfterUpdateTransaction(view: TPStreamsRNPlayerView) {
    super.onAfterUpdateTransaction(view)
    view.tryCreatePlayer()
  }
  
  override fun onDropViewInstance(view: TPStreamsRNPlayerView) {
    super.onDropViewInstance(view)
    view.releasePlayer()
  }
}
