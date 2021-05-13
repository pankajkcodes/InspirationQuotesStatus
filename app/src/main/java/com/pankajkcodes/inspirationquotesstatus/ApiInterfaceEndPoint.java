package com.pankajkcodes.inspirationquotesstatus;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceEndPoint {

    @GET("quotes")
    Call<List<QuotesData>> getQuotes();

}
