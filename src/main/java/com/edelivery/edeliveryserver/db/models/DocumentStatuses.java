package com.edelivery.edeliveryserver.db.models;
/*insert into edeliveryserver.document_status (id,status) values(0,'QUEUED');
insert into edeliveryserver.document_status (id,status) values(1,'RUNNING');
insert into edeliveryserver.document_status (id,status) values(2,'COMPLETED');
insert into edeliveryserver.document_status (id,status) values(3,'FAILED');
insert into edeliveryserver.document_status (id,status) values(4,'SKIPPED');
insert into edeliveryserver.document_status (id,status) values(5,'PREPARE');
insert into edeliveryserver.document_status (id,status) values(6,'DELETED');
insert into edeliveryserver.document_status (id,status) values(7,'SUPPORT');
*/
public enum DocumentStatuses {
	
	QUEUED(0),
	RUNNING(1),
	COMPLETED(2),
	FAILED(3),
	SKIPPED(4),
	PREPARE(5),
	DELETED(6),
	SUPPORT(7),
	SEND(8)
	;
    private final int value;

    DocumentStatuses(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
    
}
