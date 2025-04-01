public static class TruvideoSdkCameraFlashModeExtensions
{
    public static bool IsOff(this TruvideoSdkCameraFlashMode mode) => mode == TruvideoSdkCameraFlashMode.Off;
    public static bool IsOn(this TruvideoSdkCameraFlashMode mode) => mode == TruvideoSdkCameraFlashMode.On;
}
