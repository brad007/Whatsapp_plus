package com.fire.fire.whatsapp.ui.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fire.fire.whatsapp.BuildConfig;
import com.fire.fire.whatsapp.R;
import com.fire.fire.whatsapp.Utils.Utils;
import com.fire.fire.whatsapp.adapters.ContactAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 2001;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * THANKS FOR WATCHING PART ONE OF THIS TUTORIAL.
     * THIS WILL BECOME A SERIES WHERE I'LL BE TRYING TO CLONE DIFFERENT
     * SOCIAL NETWORKS. IF YOU'D LIKE TO SEE ME CONTINUE THIS, LEAVE A LIKE
     * IF YOU HAVE ANY QUESTIONS LEAVE IT IN THE COMMENTS
     * AND IF YOU'D LIKE TO FOLLOW ALONG TO THIS TUTORIAL AND SEE MORE FROM ME
     * SUBSCRIBE!
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Utils.showToast(MainActivity.this, "search");
                return true;
            case R.id.action_add_contact:
                Utils.showToast(MainActivity.this, "add contact");
                return true;
            case R.id.action_new_group:
                Utils.showToast(MainActivity.this, R.string.menu_title_new_group);
                return true;
            case R.id.action_new_broadcast:
                Utils.showToast(MainActivity.this, R.string.menu_title_new_broadcast);
                return true;
            case R.id.action_starred_messages:
                Utils.showToast(MainActivity.this, R.string.menu_title_starred_messages);
                return true;
            case R.id.action_status:
                Utils.showToast(MainActivity.this, R.string.menu_title_status);
                return true;
            case R.id.action_settings:
                Utils.showToast(MainActivity.this, R.string.action_settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements
            LoaderManager.LoaderCallbacks<Cursor> {
        private static final int LOADER_ID = 1;
        private RecyclerView mWhatsappRecycler;
        private ContactAdapter mContactAdapter;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private View mRootView;

        private static final String[] FROM_COLUMNS = {
                ContactsContract.Data.CONTACT_ID,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                        ContactsContract.Data.DISPLAY_NAME_PRIMARY : ContactsContract.Data
                        .DISPLAY_NAME,
                ContactsContract.Data.PHOTO_ID
        };
        private Bundle mBundle;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            mRootView = inflater.inflate(R.layout.fragment_main, container, false);

            mBundle = savedInstanceState;
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{
                                Manifest.permission.READ_CONTACTS
                        },
                        REQUEST_PERMISSION
                );
            } else {
                getLoaderManager().initLoader(LOADER_ID, savedInstanceState, this);
            }
            init();


            return mRootView;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == REQUEST_PERMISSION) {
                if (grantResults.length != 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        getLoaderManager().initLoader(LOADER_ID, mBundle, this);
                    } else {
                        getActivity().finish();
                    }
                }
            }
        }

        private void init() {
            mWhatsappRecycler = (RecyclerView) mRootView.findViewById(R.id.whatsapp_recycler);
            mWhatsappRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
            mWhatsappRecycler.setHasFixedSize(true);

            mContactAdapter = new ContactAdapter(getContext(), null, ContactsContract.Data
                    .CONTACT_ID);
            mWhatsappRecycler.setAdapter(mContactAdapter);
        }

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            switch (id) {
                case LOADER_ID:
                    return new CursorLoader(
                            getContext(),
                            ContactsContract.Data.CONTENT_URI,
                            FROM_COLUMNS,
                            null,
                            null,
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                                    ContactsContract.Data.DISPLAY_NAME_PRIMARY : ContactsContract.Data
                                    .DISPLAY_NAME) +
                                    " ASC"
                    );
                default:
                    if (BuildConfig.DEBUG)
                        throw new IllegalArgumentException("no id handled!");
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mContactAdapter.swapCursor(data);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mContactAdapter.swapCursor(null);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CALLS";
                case 1:
                    return "CHATS";
                case 2:
                    return "CONTACTS";
            }
            return null;
        }
    }
}
