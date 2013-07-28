Android Weibo Runtime
=====================

This is an Android runtime library project developed with Android Studio.
It's still under construction,
so it might be very unstable and major changes will be made frequently.

It provides an authorization `Activity` and several `ContentProvider`s (consistent with the Android
`ContentProvider` interfaces).

Usage
-----

To start a Weibo session, just do as following:

    private int ACTION_AUTHORIZE = your_action_code;

    public void onClickAuthorize(View view) {
        Intent intent = new Intent("com.euyuil.weibo.ACTION_AUTHORIZE");
        intent.putExtra("client_id", "your_client_id");
        intent.putExtra("client_secret", "your_client_secret");
        startActivityForResult(intent, ACTION_AUTHORIZE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ACTION_AUTHORIZE) {

            String identity = data.getStringExtra("identity");

            /* .. */
        }
    }

To retrieve the time-line, do as following:

    TODO

Installation
------------

 1. Use the interfaces provided by this library in your own project.
 2. Compile this project and install the APK to your Android device.
 3. Compile your own project and install the APK to your Android device.
 4. Done.

Note: you don't have to reference this project in your own project while developing.
