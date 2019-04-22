package com.gint.app.bisis4.client.searchNetBisiV5.retrofit;

import com.gint.app.bisis4.client.searchNetBisiV5.model.BriefInfoModelV5;
import com.gint.app.bisis4.client.searchNetBisiV5.model.OtherLibsSearch;
import com.gint.app.bisis4.client.searchNetBisiV5.model.SearchModel;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisisadmin.DBManager;
import com.gint.app.bisis4.textsrv.Result;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.Vector;
public class LibService {
    private static LibRepo libRepo;
    public static LibRepo getLibRepo() {
        if (libRepo == null){
            DBManager dbManager = new DBManager();
            String bisis5AuthToken = dbManager.getBisis5AuthToken();
            try {
                ServiceGenerator.addAuthInterceptor(bisis5AuthToken);
            }catch(ExceptionInInitializerError e){
                System.out.println(e.getCause());
            }
            libRepo = ServiceGenerator.createService(LibRepo.class);
        }
        return libRepo;
    }
    public static List<LibDTO> prepareServerListBisisV5(){

        Call<List<LibDTO>> call = getLibRepo().getAllLibsInfo();
        List<LibDTO> libs = null;
        try {
            libs = call.execute().body();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return libs;
    }
    public static Vector<BriefInfoModelV5> searchBriefInfoModels(OtherLibsSearch otherLibsSearch) throws IOException {
        Call<Vector<BriefInfoModelV5>> call = getLibRepo().searchIdsMutlipleLibs(otherLibsSearch);
        try{
            return call.execute().body();
        }catch(IOException e){
            e.printStackTrace();
            throw e;
        }

    }
    public static Record getForeignRecord(BriefInfoModelV5 briefInfoModel){
        Call<Record> call = getLibRepo().getForeignRecord(briefInfoModel);
        Record record = null;
        try {
            record = call.execute().body();
        }catch(IOException e){
            e.printStackTrace();
        }
        return record;
    }
    public static Vector<Record> getMixedLibraryRecords(Vector<BriefInfoModelV5> briefs){
        Call<Vector<Record>> call = getLibRepo().getMixedLibraryRecords(briefs);
        try {
            Vector<Record> records = call.execute().body();
            return records;
        }catch(IOException e){
            e.printStackTrace();

        }
        return null;
    }
    public static Result searchRecordsIdsResult(SearchModel searchModel) throws IOException {
        Call<Result> call = getLibRepo().searchRecordsIdsResult(searchModel);
        try {
            Result result = call.execute().body();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }

    }
}
