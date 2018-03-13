package com.itnurtureden.learning;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
 * {@link LessonInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonInFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<String> myLessonList = new ArrayList<String>();
    String[] lessonArray;
    List<String> myLessonidList = new ArrayList<String>();
    String[] lessonidArray;

    private OnFragmentInteractionListener mListener;

    public LessonInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LessonInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LessonInFragment newInstance(String param1, String param2) {
        LessonInFragment fragment = new LessonInFragment();
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
        myLessonList.clear();
        myLessonidList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myLessonList.clear();
        myLessonidList.clear();
        final String lessonHeadingid = getArguments().getString("lessonHeadingid_from");
        final SharedPreferences pref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        View mainview = inflater.inflate(R.layout.fragment_lesson, container, false);
        final ListView listLesson = (ListView) mainview.findViewById(R.id.lvLessons);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        Log.e("lessonHeadingid",lessonHeadingid);
        StringRequest obreq = new StringRequest(Request.Method.POST, "http://itnurtureden.com/courses/getLessons.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsobj = new JSONObject(response.toString());
                            JSONArray jarr = jsobj.getJSONArray("lesson_list");
                            int i;
                            Log.e("in","in");
                            for (i=0;i<jarr.length();i++)
                            {
                                myLessonList.add(jarr.getJSONObject(i).getString("lesson_name"));
                                myLessonidList.add(String.valueOf(jarr.getJSONObject(i).getInt("lesson_id")));
                                Log.e("lesson_name", jarr.getJSONObject(i).getString("lesson_name"));
                            }
                            lessonArray = new String[myLessonList.size()];
                            myLessonList.toArray(lessonArray);
                            lessonidArray = new String[myLessonidList.size()];
                            myLessonidList.toArray(lessonidArray);
                            CustomAdapter customAdapter = new CustomAdapter();
                            listLesson.setAdapter(customAdapter);
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
                parameters.put("lessonHeadingid_post", lessonHeadingid);
                return parameters;
            }
        };
        requestQueue.add(obreq);
        listLesson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LessonContentFragment lessonContentFragment = new LessonContentFragment();
                Bundle args = new Bundle();
                args.putString("lessonContentid_from",lessonidArray[position]);
                lessonContentFragment.setArguments(args);
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(relative_layout_fragment, lessonContentFragment, lessonContentFragment.getTag()).addToBackStack(null).commit();
            }
        });
        return mainview;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lessonArray.length;
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
            TextView tvCourseName = (TextView)view1.findViewById(R.id.tvCourseName);
            TextView tvHidden = (TextView)view1.findViewById(R.id.tvHidden);
            tvHidden.setText(lessonidArray[position]);
            tvCourseName.setText(lessonArray[position]);
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
