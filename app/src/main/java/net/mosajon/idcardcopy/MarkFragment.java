package net.mosajon.idcardcopy;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MarkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MarkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MarkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    private CheckedTextView checkedTextView1;
    private CheckedTextView checkedTextView2;
    private Button button;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;


    public MarkFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MarkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MarkFragment newInstance(String param1, String param2) {
        MarkFragment fragment = new MarkFragment();
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
        final View view = inflater.inflate(R.layout.fragment_mark, container, false);
        checkedTextView1 = (CheckedTextView) view.findViewById(R.id.checktv_thing);
        checkedTextView2 = (CheckedTextView) view.findViewById(R.id.checktv_date);
        button = (Button) view.findViewById(R.id.button);

        editText1 = (EditText) view.findViewById(R.id.editText1);
        editText2 = (EditText) view.findViewById(R.id.editText2);
        editText3 = (EditText) view.findViewById(R.id.editText3);

        checkedTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkedTextView1.isChecked()) {
                    checkedTextView1.setChecked(true);

                } else {
                    checkedTextView1.setChecked(false);

                }
            }
        });

        checkedTextView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkedTextView2.isChecked()) {
                    checkedTextView2.setChecked(true);

                } else {
                    checkedTextView2.setChecked(false);

                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (checkedTextView1.isChecked() && !editText1.getText().toString().trim().isEmpty()) {
                    MainActivity.strThing = editText1.getText().toString().trim();
                    Toast.makeText(view.getContext(), MainActivity.strThing, Toast.LENGTH_SHORT).show();
                }

                if (checkedTextView2.isChecked() && !editText2.getText().toString().trim().isEmpty() && !editText3.getText().toString().trim().isEmpty()) {
                    MainActivity.startDate = editText2.getText().toString().trim();
                    MainActivity.endDate = editText3.getText().toString().trim();
                    Toast.makeText(view.getContext(), getString(R.string.mark_checktv2) + " : " + MainActivity.startDate + " " + getString(R.string.mark_to) + " " + MainActivity.endDate, Toast.LENGTH_SHORT).show();
                }

            }
        });


        // Inflate the layout for this fragment
        return view;
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