public static class TruvideoSdkCameraLensFacingExtensions
{
    public static bool IsBack(this TruvideoSdkCameraLensFacing lensFacing) => lensFacing == TruvideoSdkCameraLensFacing.Back;
    public static bool IsFront(this TruvideoSdkCameraLensFacing lensFacing) => lensFacing == TruvideoSdkCameraLensFacing.Front;
}

/*public static class TruvideoSdkCameraLensFacingExtensions
{
    public static bool IsBack(this TruvideoSdkCameraLensFacing lens)
    {
        return lens.Equals(TruvideoSdkCameraLensFacing.Back);
    }

    public static bool IsFront(this TruvideoSdkCameraLensFacing lens)
    {
        return lens.Equals(TruvideoSdkCameraLensFacing.Front);
    }
}
*/
