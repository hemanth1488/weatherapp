package com.ringmd.weatherapp.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.ringmd.weatherapp.Listener.OnDialogFragmentListener;
import com.ringmd.weatherapp.Util.MUtil;

/**
 * @author Hemanth
 *
 */
public class DismissableFragment extends DialogFragment {
	public static final String TAG = DismissableFragment.class.getSimpleName();

	private OnDialogFragmentListener mListener;
	
	private static DismissableFragment newInstance(String title, String message, String dialogTag, boolean canCancelOutside, Bundle bundle, String textPositiveBtn, String textNegativeBtn) {
		DismissableFragment dismissableFragment = new DismissableFragment();
		bundle.putString(MUtil.KEY_TITLE, title);
		bundle.putString(MUtil.KEY_MESSAGE, message);
		bundle.putString(MUtil.KEY_DIALOG_TAG, dialogTag);
		bundle.putBoolean(MUtil.KEY_CAN_CANCEL_OUTSIDE, canCancelOutside);
		bundle.putString(MUtil.KEY_TEXT_POSITIVE_BUTTON, textPositiveBtn);
		bundle.putString(MUtil.KEY_TEXT_NEGATIVE_BUTTON, textNegativeBtn);
		dismissableFragment.setArguments(bundle);
		return dismissableFragment;
	}
	
	public String getTitle() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_TITLE);
	}
	public String getMessage() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_MESSAGE);
	}
	public String getDialogTag() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_DIALOG_TAG);
	}
	public Boolean getCanCancelOutside() {
		return getArguments()==null?null:getArguments().getBoolean(MUtil.KEY_CAN_CANCEL_OUTSIDE);
	}
	public String getTextPositiveBtn() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_TEXT_POSITIVE_BUTTON);
	}
	public String getTextNegativeBtn() {
		return getArguments()==null?null:getArguments().getString(MUtil.KEY_TEXT_NEGATIVE_BUTTON);
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

	/** if we don't want to specify the tag, then it will always be dismissable when you click outside */
	public static DismissableFragment show(Context context, String message) {
		return show(context, "", message, TAG, true);
	}
	
	public static DismissableFragment show(Context context, String message, String dialogTag, boolean canCancelOutside) {
		return show(context, "", message, dialogTag, canCancelOutside);
	}
	
	public static DismissableFragment show(Context context, String title, String message) {
		return show(context, title, message, TAG, true);
	}

	public static DismissableFragment show(Context context, String title, String message, String dialogTag, boolean canCancelOutside) {
		return show(context, title, message, dialogTag, canCancelOutside, new Bundle(), "Ok", null);
	}

	public static DismissableFragment show(Context context, String title, String message, String dialogTag, boolean canCancelOutside, Bundle bundle, String textPositiveBtn, String textNegativeBtn) {
		try {
			DismissableFragment dismissableFragment = DismissableFragment.newInstance(title, message, dialogTag, canCancelOutside, bundle, textPositiveBtn, textNegativeBtn);
			FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
			ft.add(dismissableFragment, dialogTag);
			ft.show(dismissableFragment);
			ft.commitAllowingStateLoss();
			return dismissableFragment;
		} catch (IllegalStateException e) {

			return null;
		}
	}

	public void dismiss(){


	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Builder dialog = new Builder(getActivity());
		dialog.setTitle(getTitle());
		dialog.setMessage(getMessage());
		dialog.setPositiveButton(getTextPositiveBtn(), new Dialog.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mListener.onClickedPositiveDialog(getDialogTag(), getArguments());
			}
		});
		if(getTextNegativeBtn() != null) {
			dialog.setNegativeButton(getTextNegativeBtn(), new Dialog.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			});
		}
		AlertDialog alertDialog = dialog.create();
		alertDialog.setCanceledOnTouchOutside(getCanCancelOutside());
		return alertDialog;
	}
	
	@Override
	public void onCancel(DialogInterface dialog) {
		mListener.onCancelledDialog(getDialogTag(), getArguments());
	}
}
