Android Weibo Runtime
=====================

This is an Android runtime library project developed with Android Studio.
It's still under construction,
so it might be very unstable and major changes will be made frequently.

It provides an authorization `Activity` and several `ContentProvider`s (consistent with the Android
`ContentProvider` interfaces).

You can either install our APK as runtime library to your Android device directly, or integrate our
source code with yours using your own client id and key.


Installation
------------

 1. Install our library APK to your Android phone.

 2. Use the interfaces provided by this library in your own project, see usage.

 3. Compile your own project and install the APK to your Android device.

 4. Done.

Note: you **don't** have to reference this project as a library in your own project while developing
if you decide to use it this way. And in this case, the source code is useless to you.


Integration
-----------

 1. At the very beginning, you should configure your own client id and secret in `WeiboConfig`.

        WeiboConfig.reset("your_client_id", "your_client_secret");

 2. Then, replace all the occurrence of `com.euyuil` with your own authority. Remember, this is
    **very important**, otherwise you might get conflicts with other applications.

 3. Reference this project in your own project.

 4. Use the interfaces provided by this library in your own project, see usage.

 5. Compile your own project and install the APK to your Android device.

 6. Done.


Usage
-----

To start a Weibo session, just do as following in your `Activity`, and remember to replace `com.euyuil`
with your own authority:

    private int ACTION_AUTHORIZE = your_action_code;

    public void onClickAuthorize(View view) {
        startActivityForResult(new Intent("com.euyuil.weibo.ACTION_AUTHORIZE"), ACTION_AUTHORIZE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_AUTHORIZE) {
            String provider = data.getStringExtra("provider"); // "weibo".
            String identity = data.getStringExtra("identity"); // user id in weibo.
            String universal = data.getStringExtra("universal"); // "weibo/" + identity.
            // TODO
        }
    }

To retrieve the time-line, do as following:

    // TODO
