package clrcap.CCClient.ui.activity.wizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import clrcap.CCClient.R;
import clrcap.CCClient.ui.dialogs.WelcomeExitDialog;

public class ErrorWithWelcomeDialogActivity extends BaseErrorActivity {
    protected static final String SKIP_BUTTON = "SKIP_BUTTON";

    public static Intent getWeakConnectionIntent(Context context) {
        Intent intent = new Intent(context, ErrorWithWelcomeDialogActivity.class);
        intent.putExtra(MESSAGE, R.string.error_weak_signal);
        intent.putExtra(FIRST_BTN_TEXT, R.string.close);
        return intent;
    }

    public static Intent getDeviceCannotBeAddedErrorIntent(Context context) {
        Intent intent = new Intent(context, ErrorWithWelcomeDialogActivity.class);
        intent.putExtra(MESSAGE, R.string.device_cannot_be_added_to_this_profile);
        intent.putExtra(FIRST_INTENT, new Intent(context, LoginAccActivity.class));
        intent.putExtra(FIRST_BTN_TEXT, R.string.action_login);
        return intent;
    }

    public static Intent getIntent(Context context, Intent firstIntent, int errorMessageId) {
        Intent intent = new Intent(context, ErrorWithWelcomeDialogActivity.class);
        intent.putExtra(FIRST_INTENT, firstIntent);
        intent.putExtra(MESSAGE, errorMessageId);
        return intent;
    }

    public static Intent getIntentWithMessage(Context context, int errorMessageId) {
        Intent intent = new Intent(context, ErrorWithWelcomeDialogActivity.class);
        intent.putExtra(MESSAGE, errorMessageId);
        return intent;
    }

    public static Intent getConnectionErrorIntent(Context context) {
        Intent intent = getIntent(context, new Intent(context, ServiceConnectionActivity.class), R.string.hint_no_internet_service);
        intent.putExtra(SKIP_BUTTON, true);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindSkipButton();
    }

    private void bindSkipButton() {
        if (!getIntent().getBooleanExtra(SKIP_BUTTON, false)) return;

        View skipButton = findViewById(R.id.skip_button);
        skipButton.setVisibility(View.VISIBLE);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedHelper().setWizardSkipped(true);
                finish();
            }
        });
    }

    @Override
    public void onSecondButtonClick() {
        WelcomeExitDialog.show(this);
    }

}
