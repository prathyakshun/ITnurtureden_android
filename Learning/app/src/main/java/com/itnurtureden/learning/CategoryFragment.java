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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.itnurtureden.learning.R.id.relative_layout_fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<String> myCategoryList = new ArrayList<String>();
    List<String> myCategoryidList = new ArrayList<String>();
    List<String> myCategoryurlList = new ArrayList<String>();
    List<String> myCategoryImageList= new ArrayList<String>();
    List<String> myCategoryCoursesList= new ArrayList<String>();


    String[] categoryArray;
    String[] categoryidArray;
    String[] categoryurlArray;
    String[] categoryImageArray;
    String[] categoryCourseArray;

    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        myCategoryList.clear();
        myCategoryidList.clear();
        myCategoryurlList.clear();
        myCategoryImageList.clear();
        myCategoryCoursesList.clear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myCategoryList.clear();
        myCategoryidList.clear();
        myCategoryurlList.clear();
        myCategoryImageList.clear();
        myCategoryCoursesList.clear();
        // Inflate the layout for this fragment
        final SharedPreferences pref = this.getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        View mainview = inflater.inflate(R.layout.fragment_category, container, false);
        final ListView listCategory = (ListView) mainview.findViewById(R.id.lvCategory);
        RequestQueue requestQueue = Volley.newRequestQueue(this.getActivity());
        StringRequest obreq = new StringRequest(Request.Method.POST, "http://itnurtureden.com/courses/getAllCategories.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsobj = new JSONObject(response.toString());
                            JSONArray jarr = jsobj.getJSONArray("category_list");
                            int i;
                            for (i=0;i<jarr.length();i++)
                            {
                                myCategoryidList.add(jarr.getJSONObject(i).getString("id"));
                                myCategoryList.add(jarr.getJSONObject(i).getString("name"));
                                myCategoryImageList.add(jarr.getJSONObject(i).getString("img"));
                                myCategoryCoursesList.add("Number of Courses: "+jarr.getJSONObject(i).getString("num_courses"));
                                myCategoryurlList.add(String.valueOf(jarr.getJSONObject(i).getString("url")));
                            }
                            categoryArray = new String[myCategoryList.size()];
                            myCategoryList.toArray(categoryArray);
                            categoryidArray = new String[myCategoryidList.size()];
                            myCategoryidList.toArray(categoryidArray);
                            categoryurlArray = new String[myCategoryurlList.size()];
                            myCategoryurlList.toArray(categoryurlArray);
                            categoryImageArray = new String[myCategoryImageList.size()];
                            myCategoryImageList.toArray(categoryImageArray);
                            categoryCourseArray = new String[myCategoryCoursesList.size()];
                            myCategoryCoursesList.toArray(categoryCourseArray);
                            CustomAdapter customAdapter = new CustomAdapter();
                            listCategory.setAdapter(customAdapter);
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
                });
        requestQueue.add(obreq);
        listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CategoryCourseFragment categoryCourseFragment = new CategoryCourseFragment();
                Bundle args = new Bundle();
                args.putString("categoryid_from",categoryidArray[position]);
                args.putString("category_url_from",categoryurlArray[position]);
                categoryCourseFragment.setArguments(args);
                android.support.v4.app.FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(relative_layout_fragment, categoryCourseFragment, categoryCourseFragment.getTag()).addToBackStack(null).commit();
            }
        });
        return mainview;
    }

class CustomAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return categoryArray.length;
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
        View view1 = layoutInflater.inflate(R.layout.category_item, null,true);
        view1.setBackgroundColor(Color.LTGRAY);
        TextView tvCategoryName = (TextView)view1.findViewById(R.id.tvCategoryName);
        TextView tvHidden = (TextView)view1.findViewById(R.id.tvHidden);
        TextView tvHiddenUrl = (TextView)view1.findViewById(R.id.tvHiddenUrl);
        TextView tvCategoryCourse = (TextView)view1.findViewById(R.id.tvCategoryCourse);
        ImageView ivCategory = (ImageView) view1.findViewById(R.id.ivCategory);
        tvHiddenUrl.setText(categoryurlArray[position]);
        tvCategoryCourse.setText(categoryCourseArray[position]);
        tvHidden.setText(categoryidArray[position]);
        tvCategoryName.setText(categoryArray[position]);
    //    Log.e("img",categoryImageArray[position]);
        Picasso.with(getContext()).load(categoryImageArray[position]).into(ivCategory);
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
