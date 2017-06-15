USE [edeliveryserver]
GO

/****** Object:  Table [edeliveryserver].[administrative_users]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[administrative_users](
	[id] [int] NOT NULL,
	[username] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[administrative_users_passwords]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[administrative_users_passwords](
	[id] [int] NOT NULL,
	[password] [varchar](255) NOT NULL,
	[owned_by] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[attached_documents_received]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[attached_documents_received](
	[id] [int] NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[reference_document] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[document_status]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[document_status](
	[id] [int] NOT NULL,
	[status] [varchar](255) NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[documents_received]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[documents_received](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[document_acceptance_period] [datetime2](7) NULL,
	[document_authority_applicant] [varchar](255) NULL,
	[document_comments] [varchar](255) NULL,
	[document_description] [varchar](255) NULL,
	[document_etiquette_creation_date] [datetime2](7) NULL,
	[document_issuing_authority] [varchar](255) NULL,
	[document_issuing_organization] [varchar](255) NULL,
	[document_language] [varchar](255) NULL,
	[document_organization_applicant] [varchar](255) NULL,
	[document_organization_etiquette] [varchar](255) NOT NULL,
	[document_purpose] [varchar](255) NULL,
	[document_received_from_ap_date] [datetime2](7) NULL,
	[document_receiver_authority] [varchar](255) NULL,
	[document_receiver_organization] [varchar](255) NULL,
	[document_submited_to_ap_date] [datetime2](7) NULL,
	[document_title] [varchar](255) NOT NULL,
	[document_type] [varchar](255) NOT NULL,
	[document_valid_period] [datetime2](7) NULL,
	[message_id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL,
	[referenced_document_unique_id] [varchar](255) NULL,
	[document_status] [int] NULL
) ON [PRIMARY]


GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[documents_send]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[documents_send](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actual_document_filepath] [varchar](255) NULL,
	[docId] [int] NULL,
	[document_acceptance_period] [datetime] NULL,
	[document_authority_applicant] [varchar](255) NULL,
	[document_comments] [varchar](255) NULL,
	[document_description] [varchar](255) NULL,
	[document_etiquette_creation_date] [datetime] NULL,
	[document_issuing_authority] [varchar](255) NULL,
	[document_issuing_organization] [varchar](255) NULL,
	[document_language] [varchar](255) NULL,
	[document_organization_applicant] [varchar](255) NULL,
	[document_organization_etiquette] [varchar](255) NOT NULL,
	[document_purpose] [varchar](255) NULL,
	[document_received_from_ap_date] [datetime] NULL,
	[document_receiver_authority] [varchar](255) NULL,
	[document_receiver_organization] [varchar](255) NULL,
	[document_submited_to_ap_date] [datetime] NULL,
	[document_title] [varchar](255) NOT NULL,
	[document_type] [varchar](255) NOT NULL,
	[document_valid_period] [datetime] NULL,
	[message_id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL,
	[referenced_document_unique_id] [varchar](255) NULL,
	[document_status] [int] NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[message_received_from_ap]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[message_received_from_ap](
	[id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[message_send_to_ap]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[message_send_to_ap](
	[id] [int] NOT NULL,
	[message_unique_id] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

/****** Object:  Table [edeliveryserver].[Participants]    Script Date: 13/6/2017 12:19:18 μμ ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [edeliveryserver].[Participants](
	[id] [int] NOT NULL,
	[participant_identifier_scheme] [varchar](255) NOT NULL,
	[participant_identifier_value] [varchar](255) NOT NULL
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO



CREATE TABLE [edeliveryserver].[BSD](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[message_unique_id]  [varchar](255) NOT NULL,
	[sender_id] [int] NOT NULL,
	[receiver_id] [int] NOT NULL,
	[di_standard] [varchar](255) NOT NULL,
	[di_type_version] [varchar](255) NOT NULL,
	[di_id] [varchar](255) NOT NULL,
	[di_type] [varchar](255) NOT NULL,
	[di_creatiom_time] [datetime] NOT NULL,
	[man_type] [varchar](255) NOT NULL,
	[man_uni] [varchar](255) NOT NULL,
	[man_descr] [varchar](255) NOT NULL,
	[man_lang] [varchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO


CREATE TABLE [edeliveryserver].[BSD_SCOPE](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[sc_type][varchar](255) NOT NULL,
	[sc_id][varchar](255) NOT NULL,
	[sc_instance][varchar](500) NOT NULL
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]


CREATE TABLE [edeliveryserver].[BSD_HAS_SCOPE](
	[sbd_id] [int] IDENTITY(1,1) NOT NULL,
	[scope_id][varchar](255) NOT NULL
) ON [PRIMARY]
