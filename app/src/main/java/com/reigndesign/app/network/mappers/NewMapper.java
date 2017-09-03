package com.reigndesign.app.network.mappers;


import com.reigndesign.app.models.New;

import java.util.ArrayList;
import java.util.List;

public class NewMapper {

    public static List<New> map(List<com.reigndesign.app.network.models.New> newNetworkList) {
        List<New> newMapped = new ArrayList<>();
        for (com.reigndesign.app.network.models.New aNew : newNetworkList) {
            newMapped.add(new New(aNew.getCreatedAtEpoch(), aNew.getStoryId(), 0,
                    aNew.getTitle() != null ? aNew.getTitle() : aNew.getStoryTitle(),
                    aNew.getStoryUrl(), aNew.getAuthor(), aNew.getComment(), true));
        }
        return newMapped;
    }
}
