package com.gint.app.bisis4.client.searchNetBisiV5.retrofit;

import com.gint.app.bisis4.client.searchNetBisiV5.model.BriefInfoModelV5;
import com.gint.app.bisis4.client.searchNetBisiV5.model.OtherLibsSearch;
import com.gint.app.bisis4.client.searchNetBisiV5.model.SearchModel;
import com.gint.app.bisis4.textsrv.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;
import java.util.Vector;
import com.gint.app.bisis4.records.Record;
public interface LibRepo {
    @GET("coders/lib_configurations")
    Call<List<LibDTO>> getAllLibsInfo();

    @POST("records/search_ids/multiple_libs")
    Call<Vector<BriefInfoModelV5>> searchIdsMutlipleLibs(@Body OtherLibsSearch otherLibsSearch);

    @POST("records/get_foreign_record")
    Call<Record> getForeignRecord(@Body BriefInfoModelV5 briefInfoModel);

    @POST("records/get_mixed_library_records")
    Call<Vector<Record>> getMixedLibraryRecords(@Body Vector<BriefInfoModelV5> currentBriefs);

    @POST("records/search_ids_result") //vraca kolekciju id-jeva
    Call<Result> searchRecordsIdsResult(@Body SearchModel searchModel);
}
