package com.creasmit.allolecolebackend.controller;

import com.creasmit.allolecolebackend.dto.SchoolDto;
import com.creasmit.allolecolebackend.dto.SchoolSignupDto;
import com.creasmit.allolecolebackend.model.School;
import com.creasmit.allolecolebackend.model.SmsPayload;
import com.creasmit.allolecolebackend.model.Status;
import com.creasmit.allolecolebackend.model.UserProfil;
import com.creasmit.allolecolebackend.service.ProfilService;
import com.creasmit.allolecolebackend.service.SchoolService;
import com.creasmit.allolecolebackend.service.SmsServiceImpl;
import com.creasmit.allolecolebackend.utils.ResourceName;
import com.creasmit.allolecolebackend.utils.StatusResponse;
import com.creasmit.allolecolebackend.utils.ValueException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private ProfilService profilService;

    @Autowired
    private SmsServiceImpl smsService;

    @PostMapping(value = "/admin/login", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginAdm(@RequestBody UserProfil userProfil) {

        StatusResponse statusResponse = new StatusResponse();
        try {
            // ResponseEntity responseEntity = this.send("auth/?type=username-pwd", data,
            // "POST");

            UserProfil userProfil2 = this.profilService.login(userProfil.getName(), userProfil.getPassword());

            // return ResponseEntity.status(HttpStatus);
        } catch (ValueException e) {
            e.printStackTrace();
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    @PostMapping(value = "/admin/signup", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signupAdm(@RequestBody SchoolSignupDto data) {

        StatusResponse<School> statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            UserProfil userProfil = new UserProfil();
            userProfil.setName(data.getResponsible());
            userProfil.setLastname(data.getResponsible());
            userProfil.setPhoneNumber(data.getPhoneNumber());
            userProfil.setPassword(data.getPassword());

            userProfil.setTypeAuthentificationTag("username-pwd");
            if (userProfil.getRoleTag() == null)
                userProfil.setRoleTag("customer");

            // ResponseEntity responseEntity = this.send("users", new
            // ObjectMapper().writeValueAsString(userProfil),
            // "POST");

            UserProfil userProfil2 = this.profilService.create(userProfil);

            return ResponseEntity.status(httpStatus).body(null);
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
            e.printStackTrace();
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    @PostMapping(value = "/login", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity login(@RequestBody UserProfil userProfil) {

        StatusResponse statusResponse = new StatusResponse();
        try {

            // ResponseEntity responseEntity = this.send("auth?type=phone-pwd", data,
            // "POST");

            UserProfil userProfil2 = this.profilService.login(userProfil.getName(), userProfil.getPassword());

            if (userProfil2 != null) {

                // System.out.println(responseEntity.getBody().toString());

                // UserProfil userProfil2 = new
                // ObjectMapper().readValue(responseEntity.getBody().toString(),
                // UserProfil.class);

                School school = this.schoolService.getByUser(userProfil.getId());
                if (school.getStatus().getDescription().equals("Désactivé")) {
                    statusResponse.setMessage("Profil non activé, merci de contacter le service technique.");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(statusResponse);
                }
                SchoolDto schoolDto = new SchoolDto();
                schoolDto.setSchool(school);
                schoolDto.setUserProfil(userProfil);

                // return
                // ResponseEntity.status(HttpStatus.OK).headers(responseEntity.getHeaders()).body(schoolDto);
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(null);
            // return responseEntity;
        } catch (ValueException e) {
            e.printStackTrace();
            statusResponse.setMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            statusResponse.setMessage("Erreur interne");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(statusResponse);
    }

    @PostMapping(value = "/signup", produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@RequestBody SchoolSignupDto data) {

        StatusResponse<School> statusResponse = new StatusResponse();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        try {

            UserProfil userProfil = new UserProfil();
            userProfil.setName(data.getResponsible());
            userProfil.setLastname(data.getResponsible());
            userProfil.setPhoneNumber(data.getPhoneNumber());
            userProfil.setPassword(data.getPassword());

            userProfil.setTypeAuthentificationTag("phone-pwd");
            if (userProfil.getRoleTag() == null)
                userProfil.setRoleTag("customer");

            // ResponseEntity responseEntity = this.send("users", new
            // ObjectMapper().writeValueAsString(userProfil),
            // "POST");

            UserProfil userProfil2 = this.profilService.create(userProfil);

            // System.out.println(responseEntity.getBody());

            if (userProfil2 != null) {

                StatusResponse response = new ObjectMapper().readValue(ResponseEntity.status(httpStatus).toString(),
                        StatusResponse.class);

                School school = new School();
                school.setName(data.getName());

                LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getData();

                System.out.println("id:" + map.get("id"));

                school.setPhoneNumber(data.getPhoneNumber());
                school.setResponsible(data.getResponsible());
                school.setUserId(Long.parseLong(map.get("id").toString()));
                School schoolCreated = this.schoolService.create(school);

                String confirmMessage = ("Votre compte de l'école " + schoolCreated.getName()
                        + " à été crée avec succès, et sera" +
                        " opérationnel ultérieurement. Pour plus d'infos, veuillez contacter le service technique sur: \n"
                        +
                        "support@allo-lecole.com\n" +
                        "+243822748670")
                        .replace("é", "e")
                        .replace("è", "e")
                        .replace("ê", "e")
                        .replace("à", "a")
                        .replace("â", "a")
                        .replace("ô", "o")
                        .replace("î", "i");

                System.out.println(confirmMessage);

                if (schoolCreated != null) {
                    httpStatus = HttpStatus.CREATED;
                    statusResponse.setStatus(httpStatus.name());
                    statusResponse.setData(schoolCreated);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                smsService.sendSms(schoolCreated.getPhoneNumber(), confirmMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

            } else {
                statusResponse = new ObjectMapper().readValue(ResponseEntity.status(httpStatus).toString(),
                        StatusResponse.class);
            }
            return ResponseEntity.status(httpStatus).body(statusResponse);
        } catch (ValueException e) {
            statusResponse.setMessage(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            statusResponse.setMessage("Erreur interne");
            e.printStackTrace();
        }
        return ResponseEntity.status(httpStatus).body(statusResponse);
    }

    public ResponseEntity send(String url, String data, String method) throws Exception {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        MediaType mediaType = MediaType.parse("application/json");
        com.squareup.okhttp.RequestBody body = com.squareup.okhttp.RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url("http://3.140.243.84:8087/v1/" + url)
                .method(method, body)
                .header("x-api-key", ResourceName.KEY_APP)
                .build();
        Response response = client.newCall(request).execute();
        String bodyData = response.body().string();

        String token = response.headers().get(HttpHeaders.AUTHORIZATION);
        System.out.println("Token: " + token);
        System.out.println("Status: " + response.code());
        System.out.println("Status Message: " + response.message());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

        return ResponseEntity.status(response.code())
                .headers(httpHeaders)
                .body(bodyData);
    }

}
