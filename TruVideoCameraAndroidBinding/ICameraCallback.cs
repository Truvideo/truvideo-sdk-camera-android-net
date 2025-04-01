using Android.Runtime;
using Java.Interop;

namespace TruVideoCameraAndroidBinding;

[Register("com.truvideo.camera.CameraCallback")]
public interface ICameraCallback : IJavaObject, IDisposable
{
    [Export("onSuccess")]
    void OnSuccess(string result);

    [Export("onFailure")]
    void OnFailure(string error);
}