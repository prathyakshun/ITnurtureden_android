package com.itnurtureden.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String username;
    String password;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    //    final String courseUrl = getArguments().getString("course_url_from");
    //    Log.e("Courseurl",courseUrl);
        final SharedPreferences pref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Log.e("hmm","hmm");
        username = pref.getString("username_val","");
        password = pref.getString("userpassword_val","");
        CookieManager cookieManager = CookieManager.getInstance();
        String cookieString = pref.getString("cookiename_val","") + "=" + pref.getString("cookievalue_val","");
        cookieManager.setCookie("http://www.itnurtureden.com/courses",cookieString);
        cookieManager.getInstance();
        cookieManager.acceptCookie();
        cookieManager.setAcceptFileSchemeCookies(true);
        CookieManager.getInstance().setAcceptCookie(true);
        cookieManager.setAcceptCookie(true);
        cookieManager.getInstance().setAcceptCookie(true);
        Log.e("cookieString",cookieString);
        View mainview = inflater.inflate(R.layout.fragment_web_lesson, container, false);
        final ProgressBar pbar = (ProgressBar) mainview.findViewById(R.id.pB1);
        pbar.setScaleY(5f);
        WebView wvChat = (WebView) mainview.findViewById(R.id.wvCourse);
        wvChat.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if(progress < 100 && pbar.getVisibility() == ProgressBar.GONE){
                    pbar.setVisibility(ProgressBar.VISIBLE);
                }

                pbar.setProgress(progress);
                if(progress == 100) {
                    pbar.setVisibility(ProgressBar.GONE);
                }
            }
        });
        wvChat.setWebViewClient(new MyWebViewClient());
        String cookies = CookieManager.getInstance().getCookie("http://www.itnurtureden.com/courses");
        Log.e("cookies",cookies);
        wvChat.getSettings().setDomStorageEnabled(true);
        WebSettings settings = wvChat.getSettings();
        settings.setJavaScriptEnabled(true);
    //    Log.e("courseurl",courseUrl);
        wvChat.loadUrl("http://itnurtureden.com/courses/chat_android");
        Log.e("pls","pls");
        return mainview;
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            //        javascript:document.getElementById('wpadminbar').style.display='none';"
            //    progressBar.setVisibility(View.GONE);
            view.loadUrl("javascript:(function(){document.getElementsByClassName('iflychat-popup')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('myc-content-overlay myc-toggle-closed')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementById('wpadminbar').style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('navigation-top')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('logged-in-as')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('learn-press-breadcrumb')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('site-footer')[0].style.display='none';})()");
            view.loadUrl("javascript:(function(){document.getElementById('content').style.backgroundColor='#f4f7e2';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName(\"single-featured-image-header\")[0].style.backgroundColor= '#f1ead6';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName(\"navigation-top\")[0].style.backgroundColor=\" #f1ead6 \";})()");
            view.loadUrl("javascript:(function(){document.getElementById('wpadminbar').style.backgroundColor=\" #f1ead6 \";})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('ifc-embed-threads ifc-embed-threads-full')[0].style.maxHeight='45px';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('ifc-chat-list-container')[0].style.float='left';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('ifc-embed-threads ifc-embed-threads-full')[0].style.float='left';})()");
            view.loadUrl("javascript:(function(){document.getElementsByClassName('ifc-embed-threads ifc-embed-threads-full')[0].classList.remove('ifc-embed-threads ifc-embed-threads-full');})()");

            view.loadUrl("javascript:(function(){document.getElementsByClassName('ifc-chat-list-container')[0].classList.remove('ifc-chat-list-container');})()");

        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
