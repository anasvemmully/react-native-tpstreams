# react-native-tpstreams
[![npm version](https://img.shields.io/npm/v/react-native-tpstreams.svg?style=flat-square)](https://www.npmjs.com/package/react-native-tpstreams)
[![npm downloads](https://img.shields.io/npm/dm/react-native-tpstreams.svg?style=flat-square)](https://www.npmjs.com/package/react-native-tpstreams)
[![license](https://img.shields.io/npm/l/react-native-tpstreams.svg?style=flat-square)](LICENSE)


A React Native video player component for TPStreams with offline download support.

## Installation

```bash
npm install react-native-tpstreams
```

## Quick Start

### 1. Initialize TPStreams

```js
import { TPStreams } from "react-native-tpstreams";

// Initialize once at your app's entry point
TPStreams.initialize('YOUR_ORGANIZATION_ID');
```

### 2. Add Player Component

```js
import { TPStreamsPlayerView } from "react-native-tpstreams";

<TPStreamsPlayerView 
  videoId="YOUR_VIDEO_ID"
  accessToken="YOUR_ACCESS_TOKEN"
  style={{ width: '100%', height: 300 }}
/>
```

## API Reference

### Player Props

| Prop | Type | Required | Description |
|------|------|----------|-------------|
| `videoId` | `string` | Yes | Video ID to play |
| `accessToken` | `string` | Yes | Access token for the video |
| `startAt` | `number` | No | Start position in seconds (default: 0) |
| `shouldAutoPlay` | `boolean` | No | Auto-play on load (default: true) |
| `showDefaultCaptions` | `boolean` | No | Show captions if available (default: false) |
| `enableDownload` | `boolean` | No | Enable download functionality (default: false) |
| `offlineLicenseExpireTime` | `number` | No | License expiration in seconds (default: 15 days) |
| `downloadMetadata` | `object` | No | Custom metadata for downloads |
| `enableNotification` | `boolean` | No | Enable background playback with media notification (Android only, default: false) |
| `disableCaption` | `boolean` | No | Disable caption functionality and hide caption UI (default: false) |
| `metadata` | `object` | No | Custom title and artist for media notification: `{ title?: string, artist?: string }` |

### Player Methods

```js
import { useRef } from 'react';
import type { TPStreamsPlayerRef } from 'react-native-tpstreams';

const playerRef = useRef<TPStreamsPlayerRef>(null);

// Control playback
playerRef.current?.play();
playerRef.current?.pause();
playerRef.current?.seekTo(30000); // 30 seconds
playerRef.current?.setPlaybackSpeed(2.0);

// Update notification metadata (when enableNotification is true)
playerRef.current?.setTitle('New Video Title');
playerRef.current?.setArtist('Artist Name');

// Get player state (inside an async function)
const getPlayerState = async () => {
  const position = await playerRef.current?.getCurrentPosition();
  const duration = await playerRef.current?.getDuration();
  const isPlaying = await playerRef.current?.isPlaying();
  const speed = await playerRef.current?.getPlaybackSpeed();

  return { position, duration, isPlaying, speed };
};
```

### Player Events

```js
<TPStreamsPlayerView
  onPlayerStateChanged={(state) => console.log('State:', state)}
  onIsPlayingChanged={(isPlaying) => console.log('Playing:', isPlaying)}
  onPlaybackSpeedChanged={(speed) => console.log('Speed:', speed)}
  onIsLoadingChanged={(isLoading) => console.log('Loading:', isLoading)}
  onError={(error) => console.error('Error:', error)}
  onAccessTokenExpired={(videoId, callback) => {
    // Fetch new token and call callback(newToken)
  }}
/>
```

## Background Playback (Android)

Enable video playback to continue when the app is in the background with a media notification that shows playback controls.

### Basic Usage

```js
<TPStreamsPlayerView
  videoId="YOUR_VIDEO_ID"
  accessToken="YOUR_ACCESS_TOKEN"
  enableNotification={true}
  metadata={{
    title: 'Video Title',
    artist: 'Channel Name'
  }}
  style={{ width: '100%', height: 300 }}
/>
```

### Features

- **Continue playback in background**: Video keeps playing when app is minimized
- **Media notification**: System notification with play/pause control and seekable progress bar
- **Lock screen controls**: Controls appear on lock screen (with notification permission)
- **Notification updates**: Position updates every second while playing

### Dynamic Metadata Updates

Update notification title and artist dynamically:

```js
const playerRef = useRef<TPStreamsPlayerRef>(null);

// Update metadata when video changes
const onVideoChange = (newVideo) => {
  playerRef.current?.setTitle(newVideo.title);
  playerRef.current?.setArtist(newVideo.channelName);
};
```

### Android Permissions

The following permissions are automatically included in the library's `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK" />
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

**For Android 13+ (API level 33+)**: You must request the `POST_NOTIFICATIONS` permission at runtime for the notification to appear. Use a library like [`react-native-permissions`](https://github.com/zoontek/react-native-permissions):

```js
import { check, request, PERMISSIONS, RESULTS } from 'react-native-permissions';

const requestNotificationPermission = async () => {
  if (Platform.OS === 'android' && Platform.Version >= 33) {
    const result = await request(PERMISSIONS.ANDROID.POST_NOTIFICATIONS);
    return result === RESULTS.GRANTED;
  }
  return true; // Not required for older Android versions
};

// Request before enabling background playback
const granted = await requestNotificationPermission();
if (granted) {
  // Enable background playback
}
```

### Disable Captions

Hide the caption button and disable caption functionality:

```js
<TPStreamsPlayerView
  videoId="YOUR_VIDEO_ID"
  accessToken="YOUR_ACCESS_TOKEN"
  disableCaption={true}
/>
```

## Downloads

### Download Management

```js
import {
  pauseDownload,
  resumeDownload,
  removeDownload,
  getAllDownloads,
  isDownloaded,
  isDownloading,
  getDownloadStatus,
} from 'react-native-tpstreams';

// Check download status
const checkDownloadStatus = async (videoId) => {
  const downloaded = await isDownloaded(videoId);
  const downloading = await isDownloading(videoId);
  const status = await getDownloadStatus(videoId);
  
  return { downloaded, downloading, status };
};

// Manage downloads
const manageDownload = async (videoId, action) => {
  switch (action) {
    case 'pause':
      await pauseDownload(videoId);
      break;
    case 'resume':
      await resumeDownload(videoId);
      break;
    case 'remove':
      await removeDownload(videoId);
      break;
  }
};

// Get all downloads
const getAllDownloadedVideos = async () => {
  const downloads = await getAllDownloads();
  return downloads;
};
```

### Real-time Progress

```js
import {
  addDownloadProgressListener,
  removeDownloadProgressListener,
  onDownloadProgressChanged,
} from 'react-native-tpstreams';

// Start listening
async function startListening() {
  await addDownloadProgressListener();
}

// Listen for updates
const subscription = onDownloadProgressChanged((downloads) => {
  console.log('Download progress:', downloads);
});

// Cleanup
async function cleanup() {
  subscription.remove();
  await removeDownloadProgressListener();
}
```

## Resources

- **Sample App**: [Complete working example](https://github.com/testpress/sample_RN_App)
- **Documentation**: [TPStreams Developer Docs](https://developer.tpstreams.com/docs/mobile-sdk/react-native-sdk)

## License

MIT
