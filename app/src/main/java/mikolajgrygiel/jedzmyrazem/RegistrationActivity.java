package mikolajgrygiel.jedzmyrazem;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class RegistrationActivity extends ActionBarActivity {

    EditText txtUsername, txtEmail, txtPassword, txtPasswordConfirm, txtPhone;
    Button btnRegister;
    Boolean dataValidated;

    private Activity fa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button mLoginnButton = (Button) findViewById(R.id.btnLogin);
        mLoginnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtPasswordConfirm = (EditText) findViewById(R.id.txtPasswordConfirm);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dataValidated = true;

                if (txtUsername.getText().toString().length() == 0) {
                    txtUsername.setError(getResources().getString(R.string.registrationValidation_loginEmpty));
                    dataValidated = false;
                }

                if (txtEmail.getText().toString().length() == 0) {
                    txtEmail.setError(getResources().getString(R.string.registrationValidation_emailEmpty));
                    dataValidated = false;
                }

                if (txtPassword.getText().toString().length() == 0) {
                    txtPassword.setError(getResources().getString(R.string.registrationValidation_passwordEmpty));
                    dataValidated = false;
                }

                if (txtPasswordConfirm.getText().toString().length() == 0) {
                    txtPasswordConfirm.setError(getResources().getString(R.string.registrationValidation_passwordConfirmationEmpty));
                    dataValidated = false;
                }


                if (!(txtPassword.getText().toString().equals(txtPasswordConfirm.getText().toString()))) {
                    txtPassword.setError(getResources().getString(R.string.registrationValidation_passwordsNotTheSame));
                    txtPasswordConfirm.setError(getResources().getString(R.string.registrationValidation_passwordsNotTheSame));
                    dataValidated = false;
                }

                if (!isValidEmail(txtEmail.getText().toString())) {
                    txtEmail.setError(getResources().getString(R.string.registrationValidation_emailFormatIncorrect));
                    dataValidated = false;
                }


                if (txtPassword.getText().toString().length() < 8) {
                    txtPassword.setError(getResources().getString(R.string.registrationValidation_passwordAtLeastEightCharacters));
                    dataValidated = false;
                }


                if (txtPhone.getText().toString().length() < 9 || !txtPhone.getText().toString().matches("[0-9]+")) {
                    txtPhone.setError(getResources().getString(R.string.phoneValidation));
                    dataValidated = false;
                }


                if (dataValidated) {
                    String username = txtUsername.getText().toString();
                    String email = txtEmail.getText().toString();
                    String phone = txtPhone.getText().toString();
                    String password = txtPassword.getText().toString();
                    String password_confirmation = txtPasswordConfirm.getText().toString();


                    new SaveUser(fa, username, email, phone, password, password_confirmation);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
