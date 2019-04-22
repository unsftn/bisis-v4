package com.gint.app.bisis4.client.searchNetBisiV5.model;

import lombok.*;

/**
 * @author badf00d21  26.3.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BriefInfoModelV5 {
    private Boolean selected = false;
    private String libPrefix;
    private String libFullName;
    private RecordBriefs briefInfo;
}