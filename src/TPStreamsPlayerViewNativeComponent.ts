import codegenNativeComponent from 'react-native/Libraries/Utilities/codegenNativeComponent';
import type { ViewProps } from 'react-native';
import type {
  Double,
  Float,
  Int32,
} from 'react-native/Libraries/Types/CodegenTypes';
import type { HostComponent } from 'react-native';
import type { DirectEventHandler } from 'react-native/Libraries/Types/CodegenTypes';
import codegenNativeCommands from 'react-native/Libraries/Utilities/codegenNativeCommands';

export interface ErrorEvent {
  message: string;
  code: Int32;
  details?: string;
}

export interface NativeProps extends ViewProps {
  videoId?: string;
  accessToken?: string;
  shouldAutoPlay?: boolean;
  startAt?: Double;
  enableDownload?: boolean;
  offlineLicenseExpireTime?: Double;
  showDefaultCaptions?: boolean;
  downloadMetadata?: string;
  enableNotification?: boolean;
  disableCaption?: boolean;
  metadata?: string;

  // Event props for receiving data from native methods
  onCurrentPosition?: DirectEventHandler<{ position: Double }>;
  onDuration?: DirectEventHandler<{ duration: Double }>;
  onIsPlaying?: DirectEventHandler<{ isPlaying: boolean }>;
  onPlaybackSpeed?: DirectEventHandler<{ speed: Float }>;

  // Player event props
  onPlayerStateChanged?: DirectEventHandler<{ playbackState: Int32 }>;
  onIsPlayingChanged?: DirectEventHandler<{ isPlaying: boolean }>;
  onPlaybackSpeedChanged?: DirectEventHandler<{ speed: Double }>;
  onIsLoadingChanged?: DirectEventHandler<{ isLoading: boolean }>;
  onError?: DirectEventHandler<ErrorEvent>;
  onAccessTokenExpired?: DirectEventHandler<{ videoId: string }>;
}

interface TPStreamsPlayerViewCommands {
  play: (viewRef: React.ElementRef<HostComponent<NativeProps>>) => void;
  pause: (viewRef: React.ElementRef<HostComponent<NativeProps>>) => void;
  seekTo: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>,
    positionMs: Double
  ) => void;
  setPlaybackSpeed: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>,
    speed: Float
  ) => void;
  getCurrentPosition: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>
  ) => void;
  getDuration: (viewRef: React.ElementRef<HostComponent<NativeProps>>) => void;
  isPlaying: (viewRef: React.ElementRef<HostComponent<NativeProps>>) => void;
  getPlaybackSpeed: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>
  ) => void;
  setNewAccessToken: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>,
    newToken: string
  ) => void;
  setTitle: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>,
    title: string
  ) => void;
  setArtist: (
    viewRef: React.ElementRef<HostComponent<NativeProps>>,
    artist: string
  ) => void;
}

export const Commands = codegenNativeCommands<TPStreamsPlayerViewCommands>({
  supportedCommands: [
    'play',
    'pause',
    'seekTo',
    'setPlaybackSpeed',
    'getCurrentPosition',
    'getDuration',
    'isPlaying',
    'getPlaybackSpeed',
    'setNewAccessToken',
    'setTitle',
    'setArtist',
  ],
});

export default codegenNativeComponent<NativeProps>('TPStreamsRNPlayerView');
