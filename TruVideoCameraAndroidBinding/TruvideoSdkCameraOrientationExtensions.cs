public static class TruvideoSdkCameraOrientationExtensions
{
    public static bool IsPortrait(this TruvideoSdkCameraOrientation orientation) =>
        orientation == TruvideoSdkCameraOrientation.Portrait || orientation == TruvideoSdkCameraOrientation.PortraitReverse;

    public static bool IsLandscape(this TruvideoSdkCameraOrientation orientation) =>
        orientation == TruvideoSdkCameraOrientation.LandscapeLeft || orientation == TruvideoSdkCameraOrientation.LandscapeRight;
}