package com.calcatz.buahloka;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

/**
 * Created by Fauzan on 31/08/2017.
 */

public class AboutFragment extends Fragment{

	private ImageSwitcher sw;
	private Button b1,b2;

	public AboutFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_home, container, false);

		b1 = (Button) view.findViewById(R.id.button);
		b2 = (Button) view.findViewById(R.id.button2);

		sw = (ImageSwitcher) view.findViewById(R.id.imageswitcher);
		sw.setFactory(new ViewSwitcher.ViewFactory() {
			@Override
			public View makeView() {
				ImageView myView = new ImageView(getContext());
				myView.setScaleType(ImageView.ScaleType.FIT_CENTER);
				myView.setLayoutParams(new
						ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT));
				return myView;
			}
		});
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Previous Image",
						Toast.LENGTH_LONG).show();
				sw.setImageResource(R.drawable.img_apel);
			}
		});

		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Next Image",
						Toast.LENGTH_LONG).show();
				sw.setImageResource(R.drawable.img_jeruk);
			}
		});
        return view;
    }

}
