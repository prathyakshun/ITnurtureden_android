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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itnurtureden.learning.R.id.relative_layout_fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryCourseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryCourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryCourseFragment extends Fragment {
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
    String Categoryid;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CategoryCourseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryCourseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryCourseFragment newInstance(String param1, String param2) {
        CategoryCourseFragment fragment = new CategoryCourseFragment();
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
        // Inflate the layout for this fragment
        myCourseList.clear();
        myCourseidList.clear();
        myCourseurlList.clear();

        final SharedPreferences pref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        Categoryid = getArguments().getString("categoryid_from");
        View mainview = inflater.inflate(R.layout.fragment_course_list, container, false);
        final ListView listCourse = (ListView) mainview.findViewById(R.id.lvCourses);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        StringRequest obreq = new StringRequest(Request.Method.POST, "http://itnurtureden.com/courses/getCoursesByCategory.php",
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
                                myCourseidList.add(jarr.getJSONObject(i).getString("id"));
                                myCourseStatusList.add(jarr.getJSONObject(i).getString("status"));
                                myCoursePriceList.add("$"+jarr.getJSONObject(i).getString("price"));
                                myCoursePaidList.add(jarr.getJSONObject(i).getString("payment"));
                                myCourseList.add(jarr.getJSONObject(i).getString("name"));
                                myCourseImageList.add(jarr.getJSONObject(i).getString("img"));
                        //        myCourseidList.add(String.valueOf(jarr.getJSONObject(i).getInt("id")));
                                myCourseurlList.add(String.valueOf(jarr.getJSONObject(i).getString("url")));
                                myCourseDurationList.add("Duration: "+String.valueOf(jarr.getJSONObject(i).getString("duration")));
                                Log.e("course_name", jarr.getJSONObject(i).getString("name"));
                                Log.e("course_url",jarr.getJSONObject(i).getString("url"));
                            }
                            courseArray = new String[myCourseList.size()];
                            myCourseList.toArray(courseArray);
                            courseidArray = new String[myCourseidList.size()];
                            myCourseidList.toArray(courseidArray);
                            courseurlArray = new String[myCourseurlList.size()];
                            myCourseurlList.toArray(courseurlArray);
                            coursePriceArray = new String[myCoursePriceList.size()];
                            myCoursePriceList.toArray(coursePriceArray);
                            courseStatusArray = new String[myCourseStatusList.size()];
                            myCourseStatusList.toArray(courseStatusArray);
                            coursePaidArray = new String[myCoursePaidList.size()];
                            myCoursePaidList.toArray(coursePaidArray);
                            numEnrolledArray = new String[myNumEnrolledList.size()];
                            myNumEnrolledList.toArray(numEnrolledArray);
                            courseDurationArray = new String[myCourseDurationList.size()];
                            myCourseDurationList.toArray(courseDurationArray);
                            courseImageArray = new String[myCourseImageList.size()];
                            myCourseImageList.toArray(courseImageArray);
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
                parameters.put("categoryid_post", Categoryid);
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
            View view1 = layoutInflater.inflate(R.layout.all_course_item, null,true);
            view1.setBackgroundColor(Color.LTGRAY);
            TextView tvCourseName = (TextView)view1.findViewById(R.id.tvCourseName);
            tvCourseName.setTextColor(Color.MAGENTA);
            TextView tvHidden = (TextView)view1.findViewById(R.id.tvHidden);
            TextView tvHiddenUrl = (TextView)view1.findViewById(R.id.tvHiddenUrl);
            TextView tvFee= (TextView)view1.findViewById(R.id.tvFee);
            TextView tvStudentsEnrolled = (TextView)view1.findViewById(R.id.tvStudentsEnrolled);
            TextView tvDuration = (TextView)view1.findViewById(R.id.tvDuration);
            if ("free".equals(coursePaidArray[position])) {
                tvFee.setText("FREE");
                tvFee.setTextColor(Color.BLUE);
                Log.e("Its free","free");
            }
            else {
                Log.e("paid", coursePriceArray[position]);
                tvFee.setText(coursePriceArray[position]);
                tvFee.setTextColor(Color.RED);
            }
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
