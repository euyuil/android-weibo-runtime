Android Weibo Runtime
=====================

This is an Android runtime library project developed with Android Studio.
It's still under construction,
so it might be very unstable and major changes will be made frequently.

It provides an authorization `Activity` and several `ContentProvider`s (consistent with the Android
`ContentProvider` interfaces).


Installation
------------

 1. At the very beginning, you should configure your own client id and secret in `WeiboConfig`.

        WeiboConfig.reset("your_client_id", "your_client_secret");

 2. Then, replace all the occurrence of `com.euyuil` to your own authority. Remember, this is
    **very important**, otherwise you might get conflicts with other applications.

 3. Compile this project and install the APK to your Android device.

 4. Use the interfaces provided by this library in your own project, see usage.

 5. Compile your own project and install the APK to your Android device.

 6. Done.

Note: you **don't** have to reference this project as a library in your own project while developing.


Usage
-----

To start a Weibo session, just do as following in your `Activity`:

    private int ACTION_AUTHORIZE = your_action_code;

    public void onClickAuthorize(View view) {
        startActivityForResult(new Intent("com.euyuil.weibo.ACTION_AUTHORIZE"), ACTION_AUTHORIZE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_AUTHORIZE) {
            String identity = data.getStringExtra("identity"); // identity of user, "weibo/" + UID.
            // TODO
        }
    }

To retrieve the time-line, do as following:

    // TODO
