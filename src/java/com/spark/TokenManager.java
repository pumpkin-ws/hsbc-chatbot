package com.spark;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.GlobalCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.core.http.HttpConfig;
import com.huaweicloud.sdk.iam.v3.IamClient;
import com.huaweicloud.sdk.iam.v3.model.*;
import com.huaweicloud.sdk.iam.v3.region.IamRegion;

import javax.json.Json;
import javax.json.JsonArray;
import javax.persistence.Basic;
import java.util.function.Consumer;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TokenManager {
    /**
     * Update token if token has expired
     */
    static public void updateToken() {
        /**
         *  if token exist, will check if content in token is valid.
        *  if token does not exist, fill in the token files with timestamp and requested new token
        */
        if (tokenExists()) {
            /**
             * if token is valid, will not modify the token file, if token is invalid,
             * will request new token from Huawei cloud
             */
            if (isValid()) {
                System.out.println("Token is still valid.");
                return;
            } else {
                updateTokenfile();
            }
        } else {
            System.out.println("Token did not exist");
            updateTokenfile();
        }
    }

    /**
     * update the token file with
     */
    private static void updateTokenfile() {
        String token_dir = "/var/lib/tomcat9/webapps/hsbc-chatbot/token.txt";
        File file = new File(token_dir);
        Long cur_timel = System.currentTimeMillis() / 1000l;
        String cur_time = Long.toString(cur_timel);
        String token = generateToken();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(cur_time);
            bw.newLine();
            bw.write(token);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get token from the system token file
     * @return the acquired token string
     */
    static public String getToken() {
        updateToken();
//        String home_dir = System.getenv("HOME");
        String token_dir = "/var/lib/tomcat9/webapps/hsbc-chatbot/token.txt";
        File file = new File(token_dir);
        try {
            BufferedReader bw = new BufferedReader(new FileReader(file));
            String timestamp = bw.readLine();
            String token = bw.readLine();
            bw.close();
            return token;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Read the token file and make sure the token has not expired
     */
    static public boolean isValid() {
        Long valid_time = new Long(20*60*60); // Although token expires in 24 hr, set the expiration time as 20 hr just to be safe
        String token_dir = "/var/lib/tomcat9/webapps/hsbc-chatbot/token.txt";
        File file = new File(token_dir);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String past_time = br.readLine();
            br.close();
            Long cur_timel = System.currentTimeMillis() / 1000l;
            Long past_timel = Long.valueOf(past_time);

            if ((cur_timel - past_timel) < valid_time) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Check if token file exists, if file exists, return true; if not, create token file in $HOME/Documents/token.txt
     * @return true if token file exists, or false otherwise
     */
    static public boolean tokenExists() {
        String token_dir = "/var/lib/tomcat9/webapps/hsbc-chatbot/token.txt";
        File file = new File(token_dir);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Generate temporary token given the access key, the secret access key, the project id, the region id, the domain name,
     * the user name, and password
     * @return
     */
    static public String generateToken() {

        String ak = "N8BOCLCOWIZ4HHTO0D5E";
        String sk = "0O24jKbXYMvBOIpRvZEJb0DniJG6hzlwpXkqgSvH";

        ICredential auth = new GlobalCredentials()
                .withAk(ak)
                .withSk(sk);

        IamClient client = IamClient.newBuilder()
                .withCredential(auth)
                .withRegion(IamRegion.valueOf("cn-north-4"))
                .build();
        KeystoneCreateUserTokenByPasswordRequest request = new KeystoneCreateUserTokenByPasswordRequest();
        KeystoneCreateUserTokenByPasswordRequestBody body = new KeystoneCreateUserTokenByPasswordRequestBody();
        AuthScopeProject projectScope = new AuthScopeProject();
        projectScope.withId("082ee40b3f0026142f9cc0077217c378")
                .withName("cn-north-4");
        AuthScope scopeAuth = new AuthScope();
        scopeAuth.withProject(projectScope);
        PwdPasswordUserDomain domainUser = new PwdPasswordUserDomain();
        domainUser.withName("hw19605990");
        PwdPasswordUser userPassword = new PwdPasswordUser();
        userPassword.withDomain(domainUser)
                .withName("hw19605990")
                .withPassword("clementine2017");
        PwdPassword passwordIdentity = new PwdPassword();
        passwordIdentity.withUser(userPassword);
        List<PwdIdentity.MethodsEnum> listIdentityMethods = new ArrayList<>();
        listIdentityMethods.add(PwdIdentity.MethodsEnum.fromValue("password"));
        PwdIdentity identityAuth = new PwdIdentity();
        identityAuth.withMethods(listIdentityMethods)
                .withPassword(passwordIdentity);
        PwdAuth authbody = new PwdAuth();
        authbody.withIdentity(identityAuth)
                .withScope(scopeAuth);
        body.withAuth(authbody);
        request.withBody(body);
        try {
            KeystoneCreateUserTokenByPasswordResponse response = client.keystoneCreateUserTokenByPassword(request);
            return response.getXSubjectToken();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
        return "";
    }

    public static void main(String[] args) {
        String home_dir = System.getenv("HOME");
        String token_dir = home_dir + "/Documents/token.txt";
        File file = new File(token_dir);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write("The first line");
            bw.newLine();
            bw.write("The second line");
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
