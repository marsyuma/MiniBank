package com.bintangmarsyumarakhasunujsleepjs.minibank.request;

/**
 * The type Utils api.
 */
public class UtilsApi {

    /**
     * The constant BASE_URL_API.
     */
    public static final String BASE_URL_API = "http://localhost:3000/";

    /**
     * Get api service base api service.
     *
     * @return the base api service
     */
// Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}