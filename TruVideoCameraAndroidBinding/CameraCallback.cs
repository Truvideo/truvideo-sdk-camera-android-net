namespace TruVideoCameraAndroidBinding;

public class CameraCallback : Java.Lang.Object, TruVideoCameraAndroid.ICameraCallback
{
    private readonly Action<string> _onSuccess;
    private readonly Action<string> _onFailure;

    public CameraCallback(Action<string> onSuccess, Action<string> onFailure) {
        _onSuccess = onSuccess;
        _onFailure = onFailure;
    }

    public void OnSuccess(string result) {
        _onSuccess?.Invoke(result);
    }

    public void OnFailure(string error) {
        _onFailure?.Invoke(error);
    }
}