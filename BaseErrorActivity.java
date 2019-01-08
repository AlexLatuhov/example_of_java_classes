package clrcap.CCClient.ui.activity.wizard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clrcap.CCClient.R;
import clrcap.CCClient.models.Constants;
import clrcap.CCClient.ui.activity.settings.my_account.AddressActivity;
import clrcap.CCClient.ui.activity.settings.my_account.Emergency911LocationChangedIdleActivity;
import clrcap.CCClient.ui.activity.settings.my_account.Emergency911LocationIdleActivity;

public class BaseErrorActivity extends BaseSetupActivity {
    protected static final String FIRST_INTENT = "FIRST_INTENT";
    protected static final String SECOND_INTENT = "SECOND_INTENT";
    protected static final String MESSAGE = "MESSAGE";
    protected static final String SECOND_BTN_TEXT = "SECOND_BTN_TEXT";
    protected static final String HIDE_SECOND_BUTTON = "HIDE_SECOND_BUTTON";
    protected static final String FIRST_BTN_TEXT = "FIRST_BTN_TEXT";

    @Bind(R.id.error_message)
    protected TextView errorMessage;
    @Bind(R.id.first_button)
    protected TextView firstButton;
    @Bind(R.id.second_button)
    protected TextView secondButton;

    public static Intent getIntent(Context context, Intent firstIntent, Intent secondIntent, int errorMessageId, int secondBtnTextId) {
        Intent intent = new Intent(context, BaseErrorActivity.class);
        intent.putExtra(FIRST_INTENT, firstIntent);
        intent.putExtra(SECOND_INTENT, secondIntent);
        intent.putExtra(MESSAGE, errorMessageId);
        intent.putExtra(SECOND_BTN_TEXT, secondBtnTextId);
        return intent;
    }

    public static Intent getForgotPasswordErrorIntent(Context context) {
        return getIntent(context, null, null, R.string.no_internet_connection, Constants.NO_VALUE);
    }

    public static Intent getLoginErrorIntent(Context context) {
        return getIntent(context, null, new Intent(context, CreateNewAccStepOneActivity.class), R.string.hint_login_error, R.string.request_account);
    }

    public static Intent getNewLocationDetectedIntent(Context context) {
        Intent intent = getIntent(context, new Intent(context, Emergency911LocationChangedIdleActivity.class), null, R.string.activity_alert_warning_setup_check_ip, Constants.NO_VALUE);
        intent.putExtra(FIRST_BTN_TEXT, R.string.confirm);
        intent.putExtra(HIDE_SECOND_BUTTON, true);
        return intent;
    }

    public static Intent getIntent(Context context, Intent tryAgainIntent, int errorMessageId) {
        return getIntent(context, tryAgainIntent, null, errorMessageId, R.string.exit);
    }

    public static Intent getIntent(Context context, Intent tryAgainIntent, Intent secondIntent, int errorMessageId) {
        return getIntent(context, tryAgainIntent, secondIntent, errorMessageId, R.string.exit);
    }

    public static Intent getIntentWithOneButton(Context context, int errorMessageId) {
        Intent intent = getIntent(context, null, null, errorMessageId, Constants.NO_VALUE);
        intent.putExtra(HIDE_SECOND_BUTTON, true);
        return intent;
    }

    public static Intent getIntentWithOneButton(Context context, int buttonTextId, int errorMessageId) {
        Intent intent = getIntentWithOneButton(context, errorMessageId);
        intent.putExtra(FIRST_BTN_TEXT, buttonTextId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        setText(errorMessage, MESSAGE);
        setText(firstButton, FIRST_BTN_TEXT);
        setText(secondButton, SECOND_BTN_TEXT);
        hideSecondButton();
    }

    protected int getLayoutId() {
        return R.layout.activity_error;
    }

    private void hideSecondButton() {
        if (!getIntent().getBooleanExtra(HIDE_SECOND_BUTTON, false)) return;
        secondButton.setVisibility(View.GONE);
    }

    protected void setText(TextView textView, String key) {
        int errorMessageId = getIntent().getIntExtra(key, Constants.NO_VALUE);
        if (errorMessageId == Constants.NO_VALUE) return;
        textView.setText(errorMessageId);
    }

    @OnClick(R.id.btn_customer_care_call)
    public void onCustomerCareCall() {
        callCustomerCare();
    }

    @OnClick(R.id.first_button)
    public void onFirstButtonClick() {
        setResult(RESULT_OK);
        processClick(FIRST_INTENT);
    }

    @OnClick(R.id.second_button)
    public void onSecondButtonClick() {
        processClick(SECOND_INTENT);
    }

    protected void processClick(String intentKey) {
        Intent intent = getIntent().getParcelableExtra(intentKey);
        finish();
        if (intent == null) return;
        startActivity(intent);
    }

    @Override
    protected boolean isSetupWizardScreen() {
        return true;
    }
}
