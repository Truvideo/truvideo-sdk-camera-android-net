namespace TruVideoCameraAndroidBinding;

public class CameraEventListener : Java.Lang.Object,TruVideoCameraAndroid.ICameraEventListener
{
    // Event to forward data to .NET MAUI
    public event Action<string>? DataReceived;

    public void OnDataReceived(string data)
    {
        DataReceived?.Invoke(data);
    }

    
}