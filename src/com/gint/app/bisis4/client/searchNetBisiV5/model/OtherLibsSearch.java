package com.gint.app.bisis4.client.searchNetBisiV5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author badf00d21  26.3.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtherLibsSearch {

    List<String> libraries = new ArrayList<>();
    SearchModel searchModel;
}
