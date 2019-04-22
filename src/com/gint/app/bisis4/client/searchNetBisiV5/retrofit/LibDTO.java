package com.gint.app.bisis4.client.searchNetBisiV5.retrofit;


import com.gint.app.bisis4.utils.LatCyrUtils;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibDTO {
    private String id;
    private String libraryName;
    private String libraryFullName;
    public String toString(){
        if (libraryFullName == null){
            return LatCyrUtils.toLatin(libraryName);
        }
        return LatCyrUtils.toLatin(libraryFullName);
    }

}
