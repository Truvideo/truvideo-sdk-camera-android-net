public static class TruvideoSdkCameraModeExtensions
{
    public static TruvideoSdkCameraMode GetPictureMode(this TruvideoSdkCameraMode mode) => TruvideoSdkCameraMode.Picture;
    public static TruvideoSdkCameraMode GetVideoMode(this TruvideoSdkCameraMode mode) => TruvideoSdkCameraMode.Video;
    public static TruvideoSdkCameraMode GetVideoAndPictureMode(this TruvideoSdkCameraMode mode) => TruvideoSdkCameraMode.VideoAndPicture;
}