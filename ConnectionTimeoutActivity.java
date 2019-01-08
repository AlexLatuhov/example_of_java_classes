package clrcap.CCClient.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import clrcap.CCClient.R;
import clrcap.CCClient.core.ConnectionTimeoutObservable;
import clrcap.CCClient.ui.activity.wizard.BaseErrorActivity;

/**
 * Created by Igor on 01.08.2016.
 */
public class ConnectionTimeoutActivity extends BaseErrorActivity {
    public static void start(Context context) {
        final Intent intent = new Intent(context, ConnectionTimeoutActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HIDE_SECOND_BUTTON, true);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        errorMessage.setText(R.string.cannot_reach_server);
    }

    public void onFirstButtonClick() {
        super.onFirstButtonClick();
        ConnectionTimeoutObservable.getInstance().notifyObservers();
    }
}
