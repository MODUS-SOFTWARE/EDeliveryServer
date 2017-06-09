insert into edeliveryserver.document_status (id,status) values(0,'QUEUED');
insert into edeliveryserver.document_status (id,status) values(1,'RUNNING');
insert into edeliveryserver.document_status (id,status) values(2,'COMPLETED');
insert into edeliveryserver.document_status (id,status) values(3,'FAILED');
insert into edeliveryserver.document_status (id,status) values(4,'SKIPPED');
insert into edeliveryserver.document_status (id,status) values(5,'PREPARE');
insert into edeliveryserver.document_status (id,status) values(6,'DELETED');
insert into edeliveryserver.document_status (id,status) values(7,'SUPPORT');


insert into [edeliveryserver].[edeliveryserver].[documents_send] 
  (actual_document_filepath , docId , document_comments , message_id ,message_unique_id ,document_status,document_organization_etiquette, document_title,document_type )
values
  ('1234',55132,'comments',55132,'55132',0,'-','title','type' )



