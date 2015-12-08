package ncu.lib.activity;

/**
 * Created by chenli-han on 2014/8/28.
 */
public class NcuLibraryApplication extends Application {

    private String token;
    private Boolean isLogin = false;

    protected String getToken() {
        return token;
    }

    public void setToken(String mToken) {
        this.token = mToken;
    }

    public Boolean isLogin() {
        return isLogin;
    }

    public void setLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

}
