package com.ringmd.weatherapp.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ringmd.weatherapp.Listener.OnDialogFragmentListener;
import com.ringmd.weatherapp.R;
import com.ringmd.weatherapp.Util.MUtil;

/**
 * @author Hemanth
 *
 */
public class IndeterminateFragment extends DialogFragment {
	OnDialogFragmentListener mListener;
	private Dialog progressDialog;
	private static IndeterminateFragment newInstance(String tag, boolean cancelable) {
		IndeterminateFragment f = new IndeterminateFragment();
		Bundle bundle = new Bundle();
		bundle.putString(MUtil.KEY_TAG, tag);
		bundle.putBoolean(MUtil.KEY_CANCELABLE, cancelable);
		f.setArguments(bundle);
		return f;
	}
	private String getDialogTag() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_TAG);
	}
	private Boolean getCancelable() {
		return getArguments()==null?null:getArguments().getBoolean(MUtil.KEY_CANCELABLE);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
    @Override
    public void onDestroyView() {
      if (getDialog() != null && getRetainInstance()) getDialog().setDismissMessage(null);
      super.onDestroyView();
    }
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mListener = (OnDialogFragmentListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnDialogFragmentListener");
		}
	}
	
	public static IndeterminateFragment show(FragmentActivity fragmentActivity, String tag, boolean cancelable) {
		try {
			FragmentManager fm = fragmentActivity.getSupportFragmentManager();
			FragmentTransaction ft = fm.beginTransaction();
			IndeterminateFragment f = (IndeterminateFragment) fm.findFragmentByTag(tag);
			if (f!=null) f.dismiss();
			IndeterminateFragment fragment = IndeterminateFragment.newInstance(tag, cancelable);
			fragment.setCancelable(cancelable);
			ft.add(fragment, tag);
			ft.show(fragment);
			ft.commitAllowingStateLoss();
			return fragment;
		} catch (IllegalStateException e) {

			return null;
		}
	}
	
	public static boolean dismiss(FragmentActivity fragmentActivity, String dialogTag) {
		if(fragmentActivity == null) return true; //Already activity is closed by user.
 		IndeterminateFragment fragment = (IndeterminateFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag(dialogTag);
		boolean dismissed = false;
		try {
			if (fragment != null) {
				fragment.progressDialog.dismiss();
				fragmentActivity.getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
				dismissed = true;
			}
		} catch (IllegalStateException e) {
		}
		return dismissed;
	}	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		progressDialog = new ProgressDialog(getActivity());
		progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		progressDialog.setCancelable(getCancelable());
		progressDialog.setCanceledOnTouchOutside(getCancelable());
		progressDialog.show();
		progressDialog.setContentView(R.layout.progress_dialog);
		return progressDialog;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		mListener.onCancelledDialog(getDialogTag(), null);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		mListener.onCancelledDialog(getDialogTag(), null);
		super.onDismiss(dialog);
	}
}