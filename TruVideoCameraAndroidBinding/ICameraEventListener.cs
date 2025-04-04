using Android.Runtime;

namespace TruVideoCameraAndroidBinding;

public interface ICameraEventListener : IJavaObject, IDisposable
{
    void OnDataReceived(string data);
}
