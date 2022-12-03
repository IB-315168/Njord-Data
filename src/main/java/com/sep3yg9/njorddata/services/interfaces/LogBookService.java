package com.sep3yg9.njorddata.services.interfaces;

import com.sep3yg9.njorddata.grpc.protobuf.logbook.UpdatingLogBook;
import com.sep3yg9.njorddata.models.LogbookEntity;

public interface LogBookService {
    LogbookEntity addLogBook(LogbookEntity logbookEntity);
    void updateLogBook(UpdatingLogBook logBook);
    void removeLogBook(int id);
    LogbookEntity getById(int id);
    LogbookEntity getByProjectId(int id);

}
