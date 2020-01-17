package emerge.project.onmealrider.ui.activity.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tuyenmonkey.mkloader.MKLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.onmealrider.R;
import emerge.project.onmealrider.servies.network.NetworkAvailability;
import emerge.project.onmealrider.ui.activity.home.Home;
import emerge.project.onmealrider.utils.entittes.UpdateToken;


public class Login extends Activity implements LoginView{


    @BindView(R.id.proprogressview)
    MKLoader proprogressview;


    @BindView(R.id.edittext_email)
    EditText editTextEmail;

    @BindView(R.id.edittext_user_password)
    EditText editTextPassword;

    LoginPresenter loginPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpli(this);




    }

    @OnClick(R.id.button_login)
    public void onClickLogin(View view) {


        if (NetworkAvailability.isNetworkAvailable(getApplicationContext())) {
            bloackUserInteraction();
            proprogressview.setVisibility(View.VISIBLE);
            loginPresenter.checkLoginValidation(this,editTextEmail.getText().toString(), editTextPassword.getText().toString());

        } else {
            Toast.makeText(this, "No Internet Access !,Please try again", Toast.LENGTH_LONG).show();

        }

    }


    @Override
    public void userNameEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        editTextEmail.setError("Empty");
        Toast.makeText(this, "User name is empty", Toast.LENGTH_LONG).show();

    }

    @Override
    public void passwordEmpty() {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        editTextPassword.setError("Empty");
        Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show();
    }

    @Override
    public void passwordInvalid(String invalidReason) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        editTextPassword.setError("Incorrect");
        Toast.makeText(this, "Password is incorrect", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginValidationSuccessful() {
        proprogressview.setVisibility(View.GONE);
        loginPresenter.updatePushTokenAndAppVersion(this);

    }

    @Override
    public void loginValidationFail(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "You have entered an invalid login ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginError(String msg) {
        unBloackUserInteraction();
        proprogressview.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Login fail,Please try again", Toast.LENGTH_LONG).show();


    }

    @Override
    public void updateStatus(Boolean status, final UpdateToken updateToken) {
        if(status){
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("App Update");
            alertDialogBuilder.setMessage(updateToken.getError().getErrDescription());

            if((updateToken.getError().getErrCode().equals("CE")) || (updateToken.getError().getErrCode().equals("SYSE")) ){
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Login.this, "You can not processed", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        });


            }else {
                alertDialogBuilder.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateToken.getAppUrl()));
                                startActivity(browserIntent);

                                return;
                            }
                        });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Login.this, "You can not processed", Toast.LENGTH_SHORT).show();

                        return;
                    }
                });

            }

            alertDialogBuilder.show();


        }



    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit!");
        alertDialogBuilder.setMessage("Do you really want to exit ?");
        alertDialogBuilder.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                return;
            }
        });
        alertDialogBuilder.show();

    }
}
