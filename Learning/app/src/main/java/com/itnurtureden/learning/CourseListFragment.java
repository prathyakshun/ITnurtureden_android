package com.itnurtureden.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itnurtureden.learning.R.id.relative_layout_fragment;
import static com.itnurtureden.learning.R.id.tvHiddenUrl;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CourseListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CourseListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseListFragment extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    List<String> myCourseList = new ArrayList<String>();
    List<String> myCourseidList = new ArrayList<String>();
    List<String> myCourseurlList = new ArrayList<String>();
    List<String> myCourseStatusList = new ArrayList<String>();
    List<String> myCoursePaidList = new ArrayList<String>();
    List<String> myCoursePriceList = new ArrayList<String>();
    List<String> myNumEnrolledList = new ArrayList<String>();
    List<String> myCourseDurationList= new ArrayList<String>();
    List<String> myCourseImageList= new ArrayList<String>();

    String[] courseArray;
    String[] courseidArray;
    String[] courseurlArray;
    String[] courseStatusArray;
    String[] coursePaidArray;
    String[] coursePriceArray;
    String[] numEnrolledArray;
    String[] courseDurationArray;
    String[] courseImageArray;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CourseListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CourseListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseListFragment newInstance(String param1, String param2) {
        CourseListFragment fragment = new CourseListFragment();
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
        myCourseList.clear();
        myCourseidList.clear();
        myCourseurlList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myCourseList.clear();
        myCourseidList.clear();
        myCourseurlList.clear();
        // Inflate the layout for this fragment
        final SharedPreferences pref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        View mainview = inflater.inflate(R.layout.fragment_course_list, container, false);
        final ListView listCourse = (ListView) mainview.findViewById(R.id.lvCourses);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        StringRequest obreq = new StringRequest(Request.Method.POST, "http://itnurtureden.com/courses/check1.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsobj = new JSONObject(response.toString());
                            JSONArray jarr = jsobj.getJSONArray("course_list");
                            int i;
                            for (i=0;i<jarr.length();i++)
                            {
                                myNumEnrolledList.add("Number of students enrolled: "+jarr.getJSONObject(i).getString("num_enrolled"));
                                myCourseidList.add(jarr.getJSONObject(i).getString("course_id"));
                                myCourseStatusList.add(jarr.getJSONObject(i).getString("status"));
                                myCourseList.add(jarr.getJSONObject(i).getString("course_name"));
                                myCourseImageList.add(jarr.getJSONObject(i).getString("img"));
                                myCourseurlList.add(String.valueOf(jarr.getJSONObject(i).getString("url")));
                                myCourseDurationList.add("Duration: "+String.valueOf(jarr.getJSONObject(i).getString("duration")));
                            }
                            courseArray = new String[myCourseList.size()];
                            myCourseList.toArray(courseArray);
                            courseidArray = new String[myCourseidList.size()];
                            myCourseidList.toArray(courseidArray);
                            courseurlArray = new String[myCourseurlList.size()];
                            myCourseurlList.toArray(courseurlArray);
                            courseStatusArray = new String[myCourseStatusList.size()];
                            myCourseStatusList.toArray(courseStatusArray);
                            numEnrolledArray = new String[myNumEnrolledList.size()];
                            myNumEnrolledList.toArray(numEnrolledArray);
                            courseDurationArray = new String[myCourseDurationList.size()];
                            myCourseDurationList.toArray(courseDurationArray);
                            courseImageArray = new String[myCourseImageList.size()];
                            myCourseImageList.toArray(courseImageArray);
                            Log.e("testingcourseArray[0]",courseArray[0]);
                            CustomAdapter customAdapter = new CustomAdapter();
                            listCourse.setAdapter(customAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volleyend", "Error");
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("userid_post", pref.getString("userid_val", ""));
                return parameters;
            }
        };
        requestQueue.add(obreq);
        listCourse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebLessonFragment webLessonFragment = new WebLessonFragment();
                Bundle args = new Bundle();
                Log.e("courseurl",courseurlArray[position]);
                args.putString("courseid_from",courseidArray[position]);
                args.putString("course_url_from",courseurlArray[position]);
                webLessonFragment.setArguments(args);
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(relative_layout_fragment, webLessonFragment, webLessonFragment.getTag()).addToBackStack(null).commit();
            }
        });
        return mainview;
        //return inflater.inflate(R.layout.fragment_course_list, container, false);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return courseArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.e("listView","listview");
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = layoutInflater.inflate(R.layout.course_item, null,true);
            view1.setBackgroundColor(Color.LTGRAY);
            TextView tvCourseName = (TextView)view1.findViewById(R.id.tvCourseName);
            tvCourseName.setTextColor(Color.MAGENTA);
            TextView tvHidden = (TextView)view1.findViewById(R.id.tvHidden);
            TextView tvHiddenUrl = (TextView)view1.findViewById(R.id.tvHiddenUrl);
            TextView tvStudentsEnrolled = (TextView)view1.findViewById(R.id.tvStudentsEnrolled);
            TextView tvDuration = (TextView)view1.findViewById(R.id.tvDuration);
            TextView tvRegistered = (TextView) view1.findViewById(R.id.tvRegistered);
            ImageView ivCourse = (ImageView) view1.findViewById(R.id.ivCourse);
            tvStudentsEnrolled.setText(numEnrolledArray[position]);
            tvRegistered.setText(courseStatusArray[position]);
            tvHiddenUrl.setText(courseurlArray[position]);
            tvHidden.setText(courseidArray[position]);
            tvCourseName.setText(courseArray[position]);
            tvDuration.setText(courseDurationArray[position]);
            Log.e("img",courseImageArray[position]);
            Picasso.with(getContext()).load(courseImageArray[position]).into(ivCourse);
            return view1;
        }
    }

    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
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
