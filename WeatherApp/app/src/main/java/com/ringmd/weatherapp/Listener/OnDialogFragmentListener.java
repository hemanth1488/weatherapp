package com.ringmd.weatherapp.Listener;

import android.os.Bundle;

/**
 * @author Hemanth
 *
 */
public interface OnDialogFragmentListener {
	public void onCancelledDialog(String tag, Bundle bundle);
	public void onClickedPositiveDialog(String tag, Bundle bundle);
}
